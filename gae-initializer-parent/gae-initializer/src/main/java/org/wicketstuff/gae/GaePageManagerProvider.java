package org.wicketstuff.gae;

import org.apache.wicket.Application;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.page.IPageManager;
import org.apache.wicket.page.IPageManagerContext;
import org.apache.wicket.page.PageStoreManager;
import org.apache.wicket.pageStore.DefaultPageStore;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;
import org.apache.wicket.pageStore.memory.IDataStoreEvictionStrategy;
import org.apache.wicket.serialize.ISerializer;

public class GaePageManagerProvider extends DefaultPageManagerProvider
{

	private final Application application;

	private final IDataStoreEvictionStrategy evictionStrategy;

	public GaePageManagerProvider(Application application,
		IDataStoreEvictionStrategy evictionStrategy)
	{
		super(application);

		this.application = application;
		this.evictionStrategy = evictionStrategy;
	}

	@Override
	public IPageManager apply(IPageManagerContext pageManagerContext)
	{
		IDataStore dataStore = new HttpSessionDataStore(pageManagerContext, evictionStrategy);

		int cacheSize = application.getStoreSettings().getInmemoryCacheSize();
		ISerializer pageSerializer = application.getFrameworkSettings().getSerializer();
		IPageStore pageStore = new DefaultPageStore(pageSerializer, dataStore, cacheSize);
		return new PageStoreManager(application.getName(), pageStore, pageManagerContext);

	}
}
