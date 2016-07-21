package org.wicketstuff.datastores.cassandra;

import java.util.List;

import org.apache.wicket.util.time.Duration;

/**
 * Settings for CassandraDataStore
 */
public interface ICassandraSettings
{
	/**
	 * Sets the name of the keyspace where the data will be saved
	 *
	 * @param keyspaceName The name of the keyspace
	 * @return this instance, for chaining
	 */
	ICassandraSettings setKeyspaceName(String keyspaceName);

	/**
	 * @return the name of the keyspace where the data will be saved
	 */
	String getKeyspaceName();

	/**
	 * Sets the name of the table where the data will be saved
	 *
	 * @param tableName The name of the table
	 * @return this instance, for chaining
	 */
	ICassandraSettings setTableName(String tableName);

	/**
	 * @return the name of the table where the data will be saved
	 */
	String getTableName();

	/**
	 * Sets the time to live for the records in the table
	 *
	 * @param ttl The time to live for the records in the table
	 * @return this instance, for chaining
	 */
	ICassandraSettings setRecordTtl(Duration ttl);

	/**
	 * @return the time to live for the records in the table
	 */
	Duration getRecordTtl();

	/**
	 * @return A list of contact points (hostname:port) to contact to.
	 */
	List<String> getContactPoints();
}
