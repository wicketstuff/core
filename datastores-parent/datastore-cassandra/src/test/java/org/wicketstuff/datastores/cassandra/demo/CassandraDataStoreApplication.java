package org.wicketstuff.datastores.cassandra.demo;

import org.apache.wicket.pageStore.IDataStore;
import org.wicketstuff.datastores.cassandra.CassandraDataStore;
import org.wicketstuff.datastores.cassandra.CassandraSettings;
import org.wicketstuff.datastores.cassandra.ICassandraSettings;
import org.wicketstuff.datastores.common.app.BaseDataStoreApplication;

public class CassandraDataStoreApplication extends BaseDataStoreApplication {
	
	@Override
	protected IDataStore createDataStore() {
		ICassandraSettings settings = new CassandraSettings();
		settings.getContactPoints().add("localhost");
		return new CassandraDataStore(settings);
	}
}
