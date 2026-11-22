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

import redis.clients.jedis.RedisClient;
import redis.clients.jedis.util.SafeEncoder;

/**
 * An IPageStore that saves serialized pages in Redis.
 */
public class RedisDataStore extends AbstractPersistentPageStore implements IPersistentPageStore {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisDataStore.class);
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


	private static final String ATTRIBUTES = "attributes";

	private static final String ID = "id";

	private static final String TYPE = "type";

	private static final String SIZE = "size";

	/**
	 * A pool of connections to the cluster
	 */
	private final RedisClient redis;

	/**
	 * The various settings
	 */
	private final IRedisSettings settings;

	/**
	 * Creates a connection pool by using the hostname and port from
	 * the provided settings
	 *
	 * @param applicationname    The name of wicket application
	 * @param settings The various settings
	 */
	public RedisDataStore(String applicationname, IRedisSettings settings)
	{
		super(applicationname);

		this.settings = Args.notNull(settings, "settings");
		this.redis = RedisClient.builder()
				.hostAndPort(settings.getHostname(), settings.getPort())
				.build();
	}

	@Override
	public boolean supportsVersioning() {
		return true;
	}

	@Override
	protected IManageablePage getPersistedPage(String sessionIdentifier, int id) {
		String key = makeKey(sessionIdentifier, id);
		byte[] bytes = redis.get(SafeEncoder.encode(key));

		String attributesKey = makeKey(sessionIdentifier, id, ATTRIBUTES);
		String type = new String(redis.hmget(attributesKey, TYPE).get(0));

		LOGGER.debug("Got {} for session '{}' and page id '{}'",
				new Object[] {bytes != null ? "data" : "'null'", sessionIdentifier, id});

		return new SerializedPage(id,  type,  bytes);
	}

	@Override
	protected void removePersistedPage(String sessionIdentifier, IManageablePage page) {
		String key = makeKey(sessionIdentifier, page.getPageId());
		redis.del(key);

		String attributesKey = makeKey(sessionIdentifier, page.getPageId(), ATTRIBUTES);
		redis.del(attributesKey);

		LOGGER.debug("Deleted data for session '{}' and page with id '{}'", sessionIdentifier, page.getPageId());
	}

	@Override
	protected void removeAllPersistedPages(String sessionIdentifier) {
		String glob = makeKey(sessionIdentifier, "*");
		Set<String> keys = redis.keys(glob);
		for (String key : keys) {
			redis.del(key);
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

		String key = makeKey(sessionIdentifier, serializedPage.getPageId());
		redis.set(SafeEncoder.encode(key), serializedPage.getData());

		String attributesKey = makeKey(sessionIdentifier, serializedPage.getPageId(), ATTRIBUTES);
		redis.hset(attributesKey, makeHm(serializedPage));

		if (settings.getRecordTtl() != null) {
			redis.expire(key, (int) settings.getRecordTtl().toSeconds());
			redis.expire(attributesKey, (int) settings.getRecordTtl().toSeconds());
		}
		LOGGER.debug("Inserted data for session '{}' and page id '{}'", sessionIdentifier, page.getPageId());
	}

	@Override
	public void destroy() {
		if (redis != null) {
			redis.close();
		}
	}

	@Override
	public Set<String> getSessionIdentifiers()
	{
		Set<String> sessionIdentifiers = new HashSet<>();

		String glob = makeKey("*");
		Set<String> keys = redis.keys(glob);
		for (String key : keys) {
			if (key.indexOf(SEPARATOR, glob.length()) == -1) {
				sessionIdentifiers.add(key);
			}
		}

		return sessionIdentifiers;
	}

	@Override
	public List<IPersistedPage> getPersistedPages(String sessionIdentifier) {
		List<IPersistedPage> pages = new ArrayList<>();

		String glob = makeKey(sessionIdentifier, "*");
		Set<String> keys = redis.keys(glob);
		for (String key : keys) {
			if (key.endsWith(ATTRIBUTES)) {
				List<String> hm = redis.hmget(key, ID, TYPE, SIZE);

				pages.add(new PersistedPage(Integer.parseInt(hm.get(0)), hm.get(1), Integer.parseInt(hm.get(2))));
			}
		}

		return pages;
	}

	@Override
	public Bytes getTotalSize() {
		return null;
	}

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
}