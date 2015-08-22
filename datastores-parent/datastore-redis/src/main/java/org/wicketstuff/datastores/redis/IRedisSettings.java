package org.wicketstuff.datastores.redis;

import org.apache.wicket.util.time.Duration;

/**
 * Settings for RedisDataStore
 */
public interface IRedisSettings
{
	/**
	 * Sets the time to live for the records in the table
	 *
	 * @param ttl The time to live for the records in the table
	 * @return this instance, for chaining
	 */
	IRedisSettings setRecordTtl(Duration ttl);

	/**
	 * @return the time to live for the records in the table
	 */
	Duration getRecordTtl();

	/**
	 * @return A list of contact points (hostname:port) to contact to.
	 */
	String getHostname();

	IRedisSettings setHostname(String hostname);

	int getPort();

	IRedisSettings setPort(int port);
}
