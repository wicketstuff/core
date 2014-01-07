package org.wicketstuff.datastores.common;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.wicket.util.lang.Args;

/**
 * Keeps the information about a session
 */
class SessionData {
	/**
	 * The session identifier
	 */
	final String sessionId;

	/**
	 * An ordered collection of used pages in this session
	 */
	final ConcurrentLinkedQueue<PageData> pages;

	/**
	 * The total size of the session (a sum of the sizes of all pages)
	 */
	volatile long size;

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

		pages.add(page);
		size += page.size;
	}

	/**
	 * Removes the oldest page in the collection of used pages
	 * @return The oldest page.
	 */
	synchronized PageData removePage() {
		if (pages.isEmpty()) {
			throw new IllegalStateException(String.format("There are no used pages in session '%s'", sessionId));
		}

		PageData page = pages.poll();
		size -= page.size;
		return page;
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
