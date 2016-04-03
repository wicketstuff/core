package org.wicketstuff.datastores.ignite.demo;

import org.apache.wicket.pageStore.IDataStore;
import org.wicketstuff.datastores.common.app.BaseDataStoreApplication;
import org.wicketstuff.datastores.ignite.IgniteDataStore;

import org.apache.ignite.Ignition;

public class IgniteDataStoreApplication extends BaseDataStoreApplication {
	
	@Override
	protected IDataStore createDataStore()
	{
		return new IgniteDataStore(Ignition.start());
	}
}
