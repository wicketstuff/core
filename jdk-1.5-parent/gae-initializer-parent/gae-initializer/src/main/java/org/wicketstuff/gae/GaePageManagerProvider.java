package org.wicketstuff.gae;

import org.apache.wicket.Application;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.page.IPageManager;
import org.apache.wicket.page.IPageManagerContext;
import org.apache.wicket.page.PersistentPageManager;
import org.apache.wicket.pageStore.DefaultPageStore;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.memory.DataStoreEvictionStrategy;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;

public class GaePageManagerProvider extends DefaultPageManagerProvider {

	private final Application application;
	
	private final DataStoreEvictionStrategy evictionStrategy;
	
	public GaePageManagerProvider(Application application, DataStoreEvictionStrategy evictionStrategy) {
		super(application);
		
		this.application = application;
		this.evictionStrategy = evictionStrategy;
	}

	@Override
	public IPageManager get(IPageManagerContext pageManagerContext)
	{
		IDataStore dataStore = new HttpSessionDataStore(pageManagerContext, evictionStrategy);
		IPageStore pageStore = new DefaultPageStore(application.getName(), dataStore,
			getCacheSize());
		return new PersistentPageManager(application.getName(), pageStore, pageManagerContext);

	}
}
