package org.wicketstuff.datastores.ignite.demo;

import org.apache.ignite.Ignition;
import org.apache.wicket.pageStore.IPageStore;
import org.wicketstuff.datastores.common.app.BaseDataStoreApplication;
import org.wicketstuff.datastores.ignite.IgniteDataStore;

/**
 * See {@link IgniteDataStore} on how to run on Java 9. 
 */
public class IgniteDataStoreApplication extends BaseDataStoreApplication {
	
	@Override
	protected IPageStore createDataStore()
	{
		return new IgniteDataStore(getName(), Ignition.start());
	}
}
