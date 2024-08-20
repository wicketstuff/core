package org.wicketstuff.datastores.common;

import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.page.IManageablePage;
import org.apache.wicket.pageStore.DelegatingPageStore;
import org.apache.wicket.pageStore.IPageContext;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.SerializedPage;
import org.apache.wicket.pageStore.SerializingPageStore;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;

/**
 * A store implementation that delegates the actual work to another {@link IPageStore}.
 * <p>
 * The extra logic that this class provides is to manage the quota per
 * client (http session). This way one client cannot add too much data
 * in the real/delegate data store and eventually drop another client's data.
 */
public class SessionQuotaManagingDataStore extends DelegatingPageStore {

	private static final MetaDataKey<SessionData> KEY = new MetaDataKey<SessionData>()
	{
		private static final long serialVersionUID = 1L;
	};

	private final Supplier<SessionData> dataCreator;

	/**
	 * Keep {@code maxPages} for each session.
	 *    
	 * @param maxPages
	 *            max pages per session
	 */
	public SessionQuotaManagingDataStore(IPageStore delegate, int maxPages)
	{
		this(delegate, () -> new CountLimitedData(maxPages));
	}

	/**
	 * Keep page up to {@code maxBytes} for each session.
	 * <p>
	 * All pages added to this store <em>must</em> be {@code SerializedPage}s. You can achieve this by letting
	 * a {@link SerializingPageStore} delegate to this store.
	 * 
	 * @param maxBytes
	 *            maximum bytes to keep in session
	 */
	public SessionQuotaManagingDataStore(IPageStore delegate, Bytes maxBytes)
	{
		this(delegate, () -> new SizeLimitedData(maxBytes));
	}

	SessionQuotaManagingDataStore(IPageStore delegate, Supplier<SessionData> dataCreator)
	{
		super(delegate);
		
		this.dataCreator = dataCreator;
	}

	private SessionData getSessionData(IPageContext context, boolean create)
	{
		return context.getSessionData(KEY, () -> {
			if (create)
			{
				return dataCreator.get();
			}
			
			return null;
		});
	}

	@Override
	public boolean canBeAsynchronous(IPageContext context) {
		getSessionData(context, true);
		
		return getDelegate().canBeAsynchronous(context);
	}
	
	@Override
	public void removeAllPages(IPageContext context) {
		SessionData sessionData = getSessionData(context, false);

		if (sessionData != null) {
			sessionData.removeAllPages(context, getDelegate());
		}
	}

	@Override
	public void addPage(IPageContext context, IManageablePage page) {
		if (page instanceof SerializedPage == false)
		{
			throw new WicketRuntimeException("SessionQuotaDataStore works with serialized pages only");
		}
		SerializedPage serializedPage = (SerializedPage)page;

		SessionData sessionData = getSessionData(context, true);

		sessionData.addPage(context, getDelegate(), serializedPage);
	}

	@Override
	public void removePage(IPageContext context, IManageablePage page) {
		if (page instanceof SerializedPage == false)
		{
			throw new WicketRuntimeException("SessionQuotaDataStore works with serialized pages only");
		}
		SerializedPage serializedPage = (SerializedPage)page;

		SessionData sessionData = getSessionData(context, false);

		if (sessionData != null) {
			sessionData.removePage(context, getDelegate(), serializedPage, true);
		}
	}
	
	/**
	 * Keeps the information about a session
	 */
	static abstract class SessionData implements Serializable {

		/**
		 * An ordered collection of delegated pages in this session
		 */
		final ConcurrentLinkedQueue<DelegatedPage> pages = new ConcurrentLinkedQueue<>();

		/**
		 * Appends a page to the collection of used pages in this session
		 * @param page The page to append
		 */
		synchronized void addPage(IPageContext context, IPageStore delegate, SerializedPage page) {
			Args.notNull(page, "page");

			removePage(context, delegate, page, false);
			
			pages.add(new DelegatedPage(page.getPageId(), page.getData().length));
			
			delegate.addPage(context, page);
		}

		synchronized void removeAllPages(IPageContext context, IPageStore delegate) {
			pages.clear();
			
			delegate.removeAllPages(context);
		}
		
		/**
		 * Removes a page by its identifier
		 *
		 * @param page The page to remove
		 * @param removeFromDelegate Whether to remove the page from the delegate
		 * @return The removed page or {@code null} if no page was removed      
		 */
		synchronized DelegatedPage removePage(IPageContext context, IPageStore delegate, SerializedPage page, boolean removeFromDelegate) {
			DelegatedPage delegatedPage = null;
			
			Iterator<DelegatedPage> pageIterator = pages.iterator();
			while (pageIterator.hasNext()) {
				DelegatedPage candidate = pageIterator.next();
				if (candidate.pageId == page.getPageId()) {
					pageIterator.remove();
					
					delegatedPage = candidate;
					break;
				}
			}
			
			if (removeFromDelegate) {
				delegate.removePage(context, page);
			}
			
			return delegatedPage;
		}
	}
	
	static class CountLimitedData extends SessionData {

		private final int maxPages;

		public CountLimitedData(int maxPages) {
			this.maxPages = maxPages;
		}
		
		@Override
		synchronized void addPage(IPageContext context, IPageStore delegate, SerializedPage page) {
			super.addPage(context, delegate, page);
			
			while (pages.size() > maxPages) {
				DelegatedPage polled = pages.poll();
				
				removePage(context, delegate, new SerializedPage(polled.pageId,  new byte[0]), true);
			}
		}
	}
	
	static class SizeLimitedData extends SessionData {

		private final Bytes maxBytes;
		
		private int size;

		public SizeLimitedData(Bytes maxBytes) {
			this.maxBytes = maxBytes;
		}
		
		@Override
		synchronized void addPage(IPageContext context, IPageStore delegate, SerializedPage page) {
			super.addPage(context, delegate, page);
			
			size += page.getData().length;
			
			while (size > maxBytes.bytes()) {
				DelegatedPage polled = pages.peek();
				
				removePage(context, delegate, new SerializedPage(polled.pageId, new byte[0]), true);
			}
		}
		
		@Override
		synchronized void removeAllPages(IPageContext context, IPageStore delegate) {
			super.removeAllPages(context, delegate);
			
			size = 0;
		}
		
		@Override
		synchronized DelegatedPage removePage(IPageContext context, IPageStore delegate, SerializedPage page, boolean removeFromDelegate) {
			DelegatedPage removedPage = super.removePage(context, delegate, page, removeFromDelegate);
			if (removedPage != null) {
				size -= removedPage.pageSize;
			}
			
			return removedPage;
		}
	}
	
	static class DelegatedPage implements Serializable
	{
		public final int pageId;

		public final long pageSize;

		public DelegatedPage(int pageId, long pageSize)
		{
			this.pageId = pageId;
			this.pageSize = pageSize;
		}
	}
}
