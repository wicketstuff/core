package org.wicketstuff.datastores.ignite.demo;

import org.apache.wicket.pageStore.IPageStore;
import org.wicketstuff.datastores.common.app.BaseDataStoreApplication;
import org.wicketstuff.datastores.ignite.IIgniteSettings;
import org.wicketstuff.datastores.ignite.IgniteDataStore;
import org.wicketstuff.datastores.ignite.IgniteSettings;

/**
 * See {@link IgniteDataStore} on how to run on Java 9.
 */
public class IgniteDataStoreApplication extends BaseDataStoreApplication {

	@Override
	protected IPageStore createDataStore()
	{
		IIgniteSettings settings = new IgniteSettings();
		settings.getAddresses().add("localhost");
		return new IgniteDataStore(getName(), settings);
	}
}
