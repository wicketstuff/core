package org.wicketstuff.datastores.common;

import java.util.ArrayDeque;
import java.util.Queue;

import org.apache.wicket.util.lang.Args;

/**
*
*/
class SessionData
{
	final String sessionId;
	final Queue<PageData> pages;
	long size;

	SessionData(String sessionId)
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
