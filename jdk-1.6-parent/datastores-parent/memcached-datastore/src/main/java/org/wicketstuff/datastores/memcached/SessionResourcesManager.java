package org.wicketstuff.datastores.memcached;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SessionResourcesManager implements ISessionResourcesManager
{
	private static final Logger LOG = LoggerFactory.getLogger(SessionResourcesManager.class);

	/**
	 * Tracks the keys for all operations per session.
	 * Used to delete all entries for a session when invalidated.
	 *
	 * The entries in Memcached have time-to-live so they will be
	 * removed anyway at some point.
	 */
	private final ConcurrentMap<String, Set<String>> keysPerSession =
			new ConcurrentHashMap<String, Set<String>>();

	@Override
	public Set<String> removeData(String sessionId)
	{
		Set<String> keys = keysPerSession.get(sessionId);
		keysPerSession.remove(sessionId);
		return keys;
	}

	@Override
	public void storeData(String sessionId, int pageId, byte[] data, String key)
	{
		Set<String> keys = keysPerSession.get(sessionId);
		if (keys == null) {
			keys = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
			Set<String> old = keysPerSession.putIfAbsent(sessionId, keys);
			if (old != null) {
				keys = old;
			}
		}
		keys.add(key);
	}


	/**
	 * Removes a key from {@linkplain #keysPerSession}
	 *
	 * @param sessionId The id of the http session.
	 * @param key       The key for Memcached lookup
	 */
	@Override
	public void removeKey(String sessionId, String key) {
		Set<String> keys = keysPerSession.get(sessionId);
		if (keys != null) {
			// maybe the entry has expired
			keys.remove(key);

			if (keys.isEmpty()) {
				keysPerSession.remove(sessionId);
			}
		}
	}

	@Override
	public void destroy()
	{
		keysPerSession.clear();
	}

	/**
	 * Creates a key that is used for the lookup in Memcached.
	 * The key starts with sessionId and the pageId so
	 * {@linkplain #keysPerSession} can be sorted faster.
	 *
	 * @param sessionId The id of the http session.
	 * @param pageId    The id of the stored page
	 * @return A key that is used for the lookup in Memcached
	 */
	@Override
	public String getKey(String sessionId, int pageId) {
		return new StringBuilder()
				.append(sessionId)
				.append(SEPARATOR)
				.append(pageId)
				.append(SEPARATOR)
				.append(KEY_SUFFIX)
				.toString();
	}
}
