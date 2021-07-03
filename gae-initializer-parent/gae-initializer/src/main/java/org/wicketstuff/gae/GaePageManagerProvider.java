package org.wicketstuff.gae;

import org.apache.wicket.Application;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.MetaDataKey;
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
		return new GaePersistentPageStore(maxPages);
	}

	private static class GaePersistentPageStore extends InSessionPageStore {
		
		private static MetaDataKey<SessionData> KEY = new MetaDataKey<InSessionPageStore.SessionData>() {
		};

		public GaePersistentPageStore(int maxPages) {
			super(maxPages);
		}

		/**
		 * Provide specific key so we do not interfere with the default InSessionPageStore used
		 * as cache. 
		 */
		@Override
		protected MetaDataKey<SessionData> getKey() {
			return KEY;
		}
	};
}
