package org.wicketstuff.datastores.memcached;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SessionResourcesManager implements ISessionResourcesManager
{
	private static final Logger LOG = LoggerFactory.getLogger(SessionResourcesManager.class);

	static class PageData
	{
		final int pageId;
		final int size;
		final String key;

		private PageData(int pageId, int size, String key)
		{
			this.pageId = pageId;
			this.size   = size;
			this.key    = key;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			PageData pageData = (PageData) o;

			if (pageId != pageData.pageId) return false;
			if (size != pageData.size) return false;
			if (!key.equals(pageData.key)) return false;

			return true;
		}

		@Override
		public int hashCode()
		{
			int result = pageId;
			result = 31 * result + size;
			result = 31 * result + key.hashCode();
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

	public SessionResourcesManager(Bytes maxSizePerSession)
	{
		this.maxSizePerSession = Args.notNull(maxSizePerSession, "maxSizePerSession");
	}

	@Override
	public Iterable<String> removeData(String sessionId)
	{
		List<String> keys = new ArrayList<String>();
		SessionData sessionData = pagesPerSession.get(sessionId);
		if (sessionData != null)
		{
			PageData[] pages = sessionData.pages.toArray(new PageData[sessionData.pages.size()]);
			for (PageData page : pages)
			{
				keys.add(page.key);
			}
			pagesPerSession.remove(sessionId);
		}
		return keys;
	}

	@Override
	public Iterable<String> storeData(String sessionId, int pageId, byte[] data)
	{
		String key = makeKey(sessionId, pageId);
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
		List<String> keysToRemove = new ArrayList<String>();
		while (shouldRemove(sessionData, pageSize))
		{
			PageData page = sessionData.removePage();
			keysToRemove.add(page.key);
		}
		PageData page = new PageData(pageId, pageSize, key);
		sessionData.addPage(page);

		return keysToRemove;
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
	public void removePage(String sessionId, int pageId) {
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
	}

	/**
	 * Creates a key that is used for the lookup in Memcached.
	 * The key starts with sessionId and the pageId so
	 * {@linkplain #pagesPerSession} can be sorted faster.
	 *
	 * @param sessionId The id of the http session.
	 * @param pageId    The id of the stored page
	 * @return A key that is used for the lookup in Memcached
	 */
	@Override
	public String makeKey(String sessionId, int pageId) {
		return new StringBuilder()
				.append(sessionId)
				.append(SEPARATOR)
				.append(pageId)
				.append(SEPARATOR)
				.append(KEY_SUFFIX)
				.toString();
	}
}
