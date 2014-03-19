package org.wicketstuff.datastores.redis;

import java.nio.charset.Charset;
import java.util.Set;

import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.util.lang.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * An IDataStore that saves the pages' bytes in Redis
 */
public class RedisDataStore implements IDataStore
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisDataStore.class);

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
	public RedisDataStore(IRedisSettings settings) {
		this(createPool(settings), settings);
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
	public RedisDataStore(JedisPool pool, IRedisSettings settings)
	{
		this.jedisPool = Args.notNull(pool, "pool");
		this.settings = Args.notNull(settings, "settings");
	}

	@Override
	public byte[] getData(String sessionId, int pageId)
	{
		byte[] bytes = null;
		Jedis resource = jedisPool.getResource();
		try {
			byte[] key = makeKey(sessionId, pageId);
			bytes = resource.get(key);
			LOGGER.debug("Got {} for session '{}' and page id '{}'",
					new Object[] {bytes != null ? "data" : "'null'", sessionId, pageId});
		} finally {
			jedisPool.returnResource(resource);
		}
		return bytes;
	}

	@Override
	public void removeData(String sessionId, int pageId)
	{
		Jedis resource = jedisPool.getResource();
		try {
			byte[] key = makeKey(sessionId, pageId);
			resource.del(key);
		} finally {
			jedisPool.returnResource(resource);
		}

		LOGGER.debug("Deleted data for session '{}' and page with id '{}'", sessionId, pageId);
	}

	@Override
	public void removeData(String sessionId)
	{
		Jedis resource = jedisPool.getResource();
		try {
			byte[] glob = makeGlob(sessionId);
			Set<byte[]> keys = resource.keys(glob);
			for (byte[] key : keys) {
				resource.del(key);
			}
		} finally {
			jedisPool.returnResource(resource);
		}
		LOGGER.debug("Deleted data for session '{}'", sessionId);
	}

	@Override
	public void storeData(String sessionId, int pageId, byte[] data)
	{
		Jedis resource = jedisPool.getResource();
		try {
			byte[] key = makeKey(sessionId, pageId);
			resource.set(key, data);
		} finally {
			jedisPool.returnResource(resource);
		}
		LOGGER.debug("Inserted data for session '{}' and page id '{}'", sessionId, pageId);
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
	public boolean isReplicated()
	{
		return true;
	}

	@Override
	public boolean canBeAsynchronous()
	{
		return true;
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
	 * Creates a key that is used for the lookup in Redis.
	 *
	 * @param sessionId The id of the http session.
	 * @param pageId    The id of the stored page
	 * @return A key that is used for the lookup in Redis
	 */
	private byte[] makeKey(String sessionId, int pageId) {
		return makePrefix(sessionId)
				.append(pageId)
				.toString()
				.getBytes(Charset.forName("UTF-8"));
	}

	private byte[] makeGlob(String sessionId) {
		return makePrefix(sessionId)
				.append('*')
				.toString()
				.getBytes(Charset.forName("UTF-8"));
	}

	private StringBuilder makePrefix(String sessionId) {
		return new StringBuilder()
				.append(KEY_PREFIX)
				.append(SEPARATOR)
				.append(sessionId)
				.append(SEPARATOR);
	}
}
