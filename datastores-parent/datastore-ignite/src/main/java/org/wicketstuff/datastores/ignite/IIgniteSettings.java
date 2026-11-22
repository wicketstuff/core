package org.wicketstuff.datastores.ignite;

import java.util.List;

/**
 * Settings for IgniteDataStore
 */
public interface IIgniteSettings {
	/**
	 * Sets the name of the table where the data will be saved
	 *
	 * @param tableName The name of the table
	 * @return this instance, for chaining
	 */
	IIgniteSettings setTableName(String tableName);

	/**
	 * @return the name of the table where the data will be saved
	 */
	String getTableName();

	/**
	 * @return A list of addresses (hostname:port) to contact to.
	 */
	List<String> getAddresses();
}
