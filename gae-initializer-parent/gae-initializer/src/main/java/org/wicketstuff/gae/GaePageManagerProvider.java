package org.wicketstuff.gae;

import org.apache.wicket.Application;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.InSessionPageStore;
import org.apache.wicket.pageStore.NoopPageStore;

public class GaePageManagerProvider extends DefaultPageManagerProvider
{

	private int maxPages;

	public GaePageManagerProvider(Application application, int maxPages)
	{
		super(application);

		this.maxPages = maxPages;
	}

	@Override
	protected IPageStore newPersistentStore()
	{
		return new InSessionPageStore(new NoopPageStore(), maxPages, getSerializer());
	}
}
