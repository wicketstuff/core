package org.wicketstuff.datastores.common;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;

/**
 * Keeps the information about a session
 */
class SessionData {
	/**
	 * The session identifier
	 */
	private final String sessionId;

	/**
	 * An ordered collection of used pages in this session
	 */
	final ConcurrentLinkedQueue<PageData> pages;

	/**
	 * The total size of the session (a sum of the sizes of all pages)
	 */
	long size;

	/**
	 * Constructor.
	 *
	 * @param sessionId The session identifier
	 */
	SessionData(String sessionId) {
		this.sessionId = Args.notNull(sessionId, "sessionId");
		this.pages = new ConcurrentLinkedQueue<PageData>();
	}

	/**
	 * Appends a page to the collection of used pages in this session
	 * @param page The page to append
	 */
	synchronized void addPage(PageData page) {
		Args.notNull(page, "page");

		removePage(page.pageId);
		pages.add(page);
		size += page.size;
	}

	/**
	 * Removes the oldest page in the collection of used pages
	 * @return The oldest page.
	 */
	private PageData removePage() {
		if (pages.isEmpty()) {
			throw new IllegalStateException(String.format("There are no used pages in session '%s'", sessionId));
		}

		PageData page = pages.poll();
		size -= page.size;
		return page;
	}

	/**
	 * Removes a page by its identifier
	 *
	 * @param pageId The id of the page to remove
	 */
	synchronized void removePage(int pageId) {
		Iterator<PageData> pageIterator = pages.iterator();
		while (pageIterator.hasNext()) {
			PageData page = pageIterator.next();
			if (page.pageId == pageId) {
				pageIterator.remove();
				size -= page.size;
				break;
			}
		}
	}

	/**
	 * Determines whether the data for older page in the session should
	 * be removed because the quota has reached.
	 *
	 * @param pageSize           The size of the page that will be stored.
	 * @param maxSizePerSession  The quota
	 * @return The id of the oldest page if the quota is reached
	 */
	synchronized Integer removePageIfQuotaExceeded(int pageSize, Bytes maxSizePerSession) {
		Integer removedPageId = null;
		if (size > 0 && !maxSizePerSession.greaterThan(size + pageSize)) {
			PageData removedPage = removePage();
			removedPageId = removedPage.pageId;
		}
		return removedPageId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SessionData that = (SessionData) o;

		return sessionId.equals(that.sessionId);
	}

	@Override
	public int hashCode() {
		return sessionId.hashCode();
	}
}
