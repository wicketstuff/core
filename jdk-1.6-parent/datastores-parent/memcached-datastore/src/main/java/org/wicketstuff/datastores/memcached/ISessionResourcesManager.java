package org.wicketstuff.datastores.memcached;

import java.util.Set;

/**
 * A manager that tracks the usage of resources per http session.
 */
public interface ISessionResourcesManager
{
	/**
	 * A suffix for the keys to avoid duplication of entries
	 * in Memcached entered by another process and to make
	 * it easier to find out who put the data at the server
	 */
	String KEY_SUFFIX = "Wicket-Memcached";

	/**
	 * A separator used for the key construction
	 */
	String SEPARATOR = "|||";

	Set<String> removeData(String sessionId);

	void storeData(String sessionId, int pageId, byte[] data, String key);

	void removeKey(String sessionId, String key);

	void destroy();

	String getKey(String sessionId, int pageId);
}
