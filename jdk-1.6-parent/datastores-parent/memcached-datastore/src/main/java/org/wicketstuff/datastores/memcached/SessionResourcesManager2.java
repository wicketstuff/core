package org.wicketstuff.datastores.memcached;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SessionResourcesManager2 implements IDataStore
{
	private static final Logger LOG = LoggerFactory.getLogger(SessionResourcesManager2.class);

	static class PageData
	{
		final int pageId;
		final int size;

		private PageData(int pageId, int size)
		{
			this.pageId = pageId;
			this.size   = size;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			PageData pageData = (PageData) o;

			if (pageId != pageData.pageId) return false;
			if (size != pageData.size) return false;

			return true;
		}

		@Override
		public int hashCode()
		{
			int result = pageId;
			result = 31 * result + size;
			return result;
		}
	}

	static class SessionData
	{
		final String sessionId;
		final Queue<PageData> pages;
		long size;

		private SessionData(String sessionId)
		{
			this.sessionId = Args.notNull(sessionId, "sessionId");
			this.pages = new ArrayDeque<PageData>();
		}

		void addPage(PageData page)
		{
			pages.add(page);
			size += page.size;
		}

		PageData removePage()
		{
			PageData page = pages.poll();
			size -= page.size;
			return page;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			SessionData that = (SessionData) o;

			if (!sessionId.equals(that.sessionId)) return false;

			return true;
		}

		@Override
		public int hashCode()
		{
			return sessionId.hashCode();
		}
	}

	/**
	 * Tracks the keys for all operations per session.
	 * Used to delete all entries for a session when invalidated.
	 *
	 * The entries in Memcached have time-to-live so they will be
	 * removed anyway at some point.
	 */
	final ConcurrentMap<String, SessionData> pagesPerSession =
			new ConcurrentHashMap<String, SessionData>();

	private final Bytes maxSizePerSession;

	private final IDataStore delegate;
	
	public SessionResourcesManager2(IDataStore delegate, Bytes maxSizePerSession)
	{
		this.delegate = Args.notNull(delegate, "delegate");
		this.maxSizePerSession = Args.notNull(maxSizePerSession, "maxSizePerSession");
	}

	@Override
	public byte[] getData(String sessionId, int pageId)
	{
		return delegate.getData(sessionId, pageId);
	}

	@Override
	public void removeData(String sessionId)
	{
		delegate.removeData(sessionId);

		pagesPerSession.remove(sessionId);
	}

	@Override
	public void storeData(String sessionId, int pageId, byte[] data)
	{
		SessionData sessionData = pagesPerSession.get(sessionId);

		if (sessionData == null) {
			sessionData = new SessionData(sessionId);
			SessionData old = pagesPerSession.putIfAbsent(sessionId, sessionData);
			if (old != null)
			{
				sessionData = old;
			}
		}

		int pageSize = data.length;
		while (shouldRemove(sessionData, pageSize))
		{
			PageData page = sessionData.removePage();
			LOG.debug("Removing page '{}' from session '{}' because the quota is reached.");
			delegate.removeData(sessionId, page.pageId);
		}
		PageData page = new PageData(pageId, pageSize);
		sessionData.addPage(page);
		delegate.storeData(sessionId, pageId, data);
	}

	private boolean shouldRemove(SessionData sessionData, int pageSize)
	{
		return sessionData.pages.size() > 0 && // allow a single page with size bigger than maxSizePerSession
				maxSizePerSession.greaterThan(sessionData.size + pageSize) == false;
	}

	/**
	 * Removes a page from {@linkplain #pagesPerSession}
	 *
	 * @param sessionId The id of the http session.
	 * @param pageId    The id of the page
	 */
	@Override
	public void removeData(String sessionId, int pageId) {

		delegate.removeData(sessionId, pageId);

		SessionData sessionData = pagesPerSession.get(sessionId);

		if (sessionData != null) {
			Queue<PageData> pages = sessionData.pages;
			// FIXME : ConcurrentModificationException may occur
			Iterator<PageData> pageIterator = pages.iterator();
			while (pageIterator.hasNext())
			{
				PageData page = pageIterator.next();
				if (page.pageId == pageId)
				{
					pageIterator.remove();
				}
			}

			if (pages.isEmpty()) {
				pagesPerSession.remove(sessionId);
			}
		}
	}

	@Override
	public void destroy()
	{
		pagesPerSession.clear();
		delegate.destroy();
	}

	@Override
	public boolean isReplicated()
	{
		return delegate.isReplicated();
	}

	@Override
	public boolean canBeAsynchronous()
	{
		return delegate.canBeAsynchronous();
	}


	/**
	 * A suffix for the keys to avoid duplication of entries
	 * in Memcached entered by another process and to make
	 * it easier to find out who put the data at the server
	 */
	String KEY_SUFFIX = "Wicket-Memcached";

	/**
	 * A separator used for the key construction
	 */
	String SEPARATOR = "|||";

	/**
	 * Creates a key that is used for the lookup in Memcached.
	 * The key starts with sessionId and the pageId so
	 * {@linkplain #pagesPerSession} can be sorted faster.
	 *
	 * @param sessionId The id of the http session.
	 * @param pageId    The id of the stored page
	 * @return A key that is used for the lookup in Memcached
	 */
	private String makeKey(String sessionId, int pageId) {
		return new StringBuilder()
				.append(sessionId)
				.append(SEPARATOR)
				.append(pageId)
				.append(SEPARATOR)
				.append(KEY_SUFFIX)
				.toString();
	}
}
