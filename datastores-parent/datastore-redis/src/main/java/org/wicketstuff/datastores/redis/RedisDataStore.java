package org.wicketstuff.datastores.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.page.IManageablePage;
import org.apache.wicket.pageStore.AbstractPersistentPageStore;
import org.apache.wicket.pageStore.IPersistedPage;
import org.apache.wicket.pageStore.IPersistentPageStore;
import org.apache.wicket.pageStore.SerializedPage;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.util.SafeEncoder;

/**
 * An IPageStore that saves serialized pages in Redis.
 */
public class RedisDataStore extends AbstractPersistentPageStore implements IPersistentPageStore {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisDataStore.class);

	private static final String ATTRIBUTES = "attributes";

	private static final String ID = "id";
	
	private static final String TYPE = "type";

	private static final String SIZE = "size";
	
	/**
	 * A pool of connections to the cluster
	 */
	private final JedisPool jedisPool;

	/**
	 * The various settings
	 */
	private final IRedisSettings settings;

	/**
	 * Constructor.
	 *
	 * Creates a connection pool by using the hostname and port from
	 * the provided settings
	 *
	 * @param settings The various settings
	 */
	public RedisDataStore(String applicationname, IRedisSettings settings) {
		this(applicationname, createPool(settings), settings);
	}

	private static JedisPool createPool(IRedisSettings settings) {
		Args.notNull(settings, "settings");

		JedisPool pool = new JedisPool(settings.getHostname(), settings.getPort());
		return pool;
	}

	/**
	 * Constructor.
	 *
	 * @param pool     The pool with Jedis connections
	 * @param settings The various settings
	 */
	public RedisDataStore(String applicationname, JedisPool pool, IRedisSettings settings)
	{
		super(applicationname);
		
		this.jedisPool = Args.notNull(pool, "pool");
		this.settings = Args.notNull(settings, "settings");
	}
	
	@Override
	public boolean supportsVersioning() {
		return true;
	}
	
	@Override
	protected IManageablePage getPersistedPage(String sessionIdentifier, int id) {
		try (Jedis resource = jedisPool.getResource()) {
			String key = makeKey(sessionIdentifier, id);
			byte[] bytes = resource.get(SafeEncoder.encode(key));
			
			String attributesKey = makeKey(sessionIdentifier, id, ATTRIBUTES);
			String type = new String(resource.hmget(attributesKey, TYPE).get(0));
			
			LOGGER.debug("Got {} for session '{}' and page id '{}'",
					new Object[] {bytes != null ? "data" : "'null'", sessionIdentifier, id});
			
			return new SerializedPage(id,  type,  bytes);
		}
	}

	@Override
	protected void removePersistedPage(String sessionIdentifier, IManageablePage page) {
		try (Jedis resource = jedisPool.getResource())
		{
			String key = makeKey(sessionIdentifier, page.getPageId());
			resource.del(key);

			String attributesKey = makeKey(sessionIdentifier, page.getPageId(), ATTRIBUTES);
			resource.del(attributesKey);
		}

		LOGGER.debug("Deleted data for session '{}' and page with id '{}'", sessionIdentifier, page.getPageId());
	}

	@Override
	protected void removeAllPersistedPages(String sessionIdentifier) {
		try (Jedis resource = jedisPool.getResource()) {
			String glob = makeKey(sessionIdentifier, "*");
			Set<String> keys = resource.keys(glob);
			for (String key : keys) {
				resource.del(key);
			}
		}
		LOGGER.debug("Deleted data for session '{}'", sessionIdentifier);
	}

	@Override
	protected void addPersistedPage(String sessionIdentifier, IManageablePage page) {
		if (page instanceof SerializedPage == false)
		{
			throw new WicketRuntimeException("RedisDataStore works with serialized pages only");
		}
		SerializedPage serializedPage = (SerializedPage)page;
		
		try (Jedis resource = jedisPool.getResource()) {
			String key = makeKey(sessionIdentifier, serializedPage.getPageId());
			resource.set(SafeEncoder.encode(key), serializedPage.getData());
			
			String attributesKey = makeKey(sessionIdentifier, serializedPage.getPageId(), ATTRIBUTES);
			resource.hmset(attributesKey, makeHm(serializedPage));
			
			if (settings.getRecordTtl() != null) {
				resource.expire(key, (int) settings.getRecordTtl().seconds());
				resource.expire(attributesKey, (int) settings.getRecordTtl().seconds());
			}
		}
		LOGGER.debug("Inserted data for session '{}' and page id '{}'", sessionIdentifier, page.getPageId());
	}

	@Override
	public void destroy()
	{
		if (jedisPool != null)
		{
			jedisPool.destroy();
		}
	}

	@Override
	public Set<String> getSessionIdentifiers()
	{
		Set<String> sessionIdentifiers = new HashSet<>();
		
		try (Jedis resource = jedisPool.getResource()) {
			String glob = makeKey("*");
			Set<String> keys = resource.keys(glob);
			for (String key : keys) {
				if (key.indexOf(SEPARATOR, glob.length()) == -1) {
					sessionIdentifiers.add(key);
				}
			}
		}
		
		return sessionIdentifiers;
	}
	
	@Override
	public List<IPersistedPage> getPersistedPages(String sessionIdentifier) {
		List<IPersistedPage> pages = new ArrayList<>();
		
		try (Jedis resource = jedisPool.getResource()) {
			String glob = makeKey(sessionIdentifier, "*");
			Set<String> keys = resource.keys(glob);
			for (String key : keys) {
				if (key.endsWith(ATTRIBUTES)) {
					PersistedPage page = parseHm(resource, key);

					pages.add(page);
				}
			}
		}
		
		return pages;
	}
	
	@Override
	public Bytes getTotalSize() {
		return null;
	}
	
	/**
	 * A prefix for the keys to avoid duplication of entries
	 * in Redis entered by another process and to make
	 * it easier to find out who put the data at the server
	 */
	private static final String KEY_PREFIX = "Wicket-Redis";

	/**
	 * A separator used for the key construction
	 */
	private static final String SEPARATOR = "|||";

	/**
	 * Creates a key in Redis.
	 */
	private String  makeKey(Object... segments) {
		StringBuilder string = new StringBuilder();
		
		string.append(KEY_PREFIX);
		
		for (Object segment : segments) {
			string.append(SEPARATOR);
			string.append(segment);
		}
		
		return string.toString();
	}
	
	private Map<String, String> makeHm(SerializedPage serializedPage) {
		Map<String, String> hm = new HashMap<>();
		
		hm.put(ID, String.valueOf(serializedPage.getPageId()));
		hm.put(TYPE, serializedPage.getPageType());
		hm.put(SIZE, String.valueOf(serializedPage.getData().length));
		
		return hm;
	}
	
	private PersistedPage parseHm(Jedis resource, String key) {
		List<String> hm = resource.hmget(key, ID, TYPE, SIZE);
		
		return new PersistedPage(Integer.parseInt(hm.get(0)), hm.get(1), Integer.parseInt(hm.get(2)));
	}
}