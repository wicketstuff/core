package org.wicketstuff.gae;

import org.apache.wicket.Application;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.page.IPageManager;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.InSessionPageStore;

/**
 * Provider of a {@link IPageManager} suitable for GAE.  
 * 
 * @author svenmeier
 */
public class GaePageManagerProvider extends DefaultPageManagerProvider {

	private int maxPages;

	/**
	 * Constructor.
	 * 
	 * @param application application
	 * @param maxPages maximum number of pages to hold in session
	 */
	public GaePageManagerProvider(Application application, int maxPages) {
		super(application);

		this.maxPages = maxPages;
	}

	/**
	 * InSessionPageStore does not support storing asynchronously.
	 */
	@Override
	protected IPageStore newAsynchronousStore(IPageStore pageStore) {
		return pageStore;
	}
	
	/**
	 * Store maximum number of pages in the session.
	 */
	@Override
	protected IPageStore newPersistentStore() {
		return new InSessionPageStore(maxPages);
	}
}
