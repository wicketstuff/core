/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.shiro.wicket.page.store;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.pagestore.AbstractPageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link org.apache.wicket.protocol.http.SecondLevelCacheSessionStore.IPageStore IPageStore}
 * implementation that stores serialized Pages in JSecurity's
 * {@link org.apache.shiro.session.Session Session}. This implementation exists to support
 * applications that use JSecurity's Enterprise Sessions instead of HTTP-only sessions.
 * <p/>
 * In a distributed application, the JSecurity Session data might not reside on the same host that
 * runs the Wicket application. In these cases the default
 * {@link org.apache.wicket.protocol.http.pagestore.DiskPageStore DiskPageStore} used by Wicket is
 * not suitable. Instead Page state must be serialized to a mechanism that is 'cluster-friendly'.
 * <p/>
 * JSecurity's enterprise {@code Session}s are cluster-friendly, so storing pages in the
 * {@code Session} is a good choice. This means applications can utilize true generic load balancing
 * and do not need <a
 * href="http://en.wikipedia.org/wiki/Load_balancing_(computing)#Persistence">sticky sessions</a> -
 * the Wicket PageMap can be updated on one host which is then available for any next load-balanced
 * request on another host because the {@code Session} is clustered.
 * 
 * @author Les Hazlewood
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class SessionPageStore extends AbstractPageStore
{
	private static final Logger LOG = LoggerFactory.getLogger(SessionPageStore.class);

	private static final String PAGE_MAP_SESSION_KEY = SessionPageStore.class.getName() +
		"_PAGE_CACHE_MANAGER_SESSION_KEY";

	protected static final int DEFAULT_MAX_PAGES = -1;

	private final int MAX_PAGE_MAP_SIZE;

	public SessionPageStore()
	{
		MAX_PAGE_MAP_SIZE = DEFAULT_MAX_PAGES;
		LOG.info("Created SessionPageStore: unlimited number of pages allowed.");
	}

	public SessionPageStore(final int maxPageMapSize)
	{
		if (maxPageMapSize < -1)
		{
			MAX_PAGE_MAP_SIZE = DEFAULT_MAX_PAGES;
			LOG.info("Created SessionPageStore: unlimited number of pages allowed.");
		}
		else
		{
			MAX_PAGE_MAP_SIZE = maxPageMapSize;
			LOG.info("Created SessionPageStore: [{}] maximum number of pages allowed.",
				maxPageMapSize);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsPage(final String sessionId, final String pageMapName, final int pageId,
		final int pageVersion)
	{
		return getPageCacheManager(sessionId).getPageCache(pageMapName).containsPage(pageId,
			pageVersion);
	}

	/**
	 * {@inheritDoc}
	 */
	public void destroy()
	{
		// do nothing - session timeout will cleanup automatically
	}

	public int getMaxPageMapSize()
	{
		return MAX_PAGE_MAP_SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> Page getPage(final String sessionId, final String pageMapName, final int id,
		final int versionNumber, final int ajaxVersionNumber)
	{
		final SerializedPageWrapper wrapper = getPageCacheManager(sessionId).getPageCache(
			pageMapName).getPage(id, versionNumber, ajaxVersionNumber);
		final SerializedPage sPage = wrapper != null ? (SerializedPage)wrapper.getPage() : null;
		return sPage != null ? deserializePage(sPage.getData(), versionNumber) : null;
	}

	protected PageCacheManager getPageCacheManager(final String sessionId)
	{
		final Session session = getSessionForUpdate(sessionId);
		PageCacheManager pcc = (PageCacheManager)session.getAttribute(PAGE_MAP_SESSION_KEY);
		if (pcc == null)
		{
			pcc = new PageCacheManager(getMaxPageMapSize());
			session.setAttribute(PAGE_MAP_SESSION_KEY, pcc);
		}
		return pcc;
	}

	protected Session getSession(final String sessionId)
	{
		Session session = null;
		final Subject currentSubject = SecurityUtils.getSubject();
		if (currentSubject != null)
		{
			session = currentSubject.getSession(false);
			/*
			 * guarantee we pulled the same session that Wicket expects us to pull. Because Shiro's
			 * Subject acquisition in web apps is based on the incoming request, and so is Wicket's,
			 * this should _always_ be the same. If not, something is seriously wrong:
			 */
			if (session != null && sessionId != null && !sessionId.equals(session.getId()))
			{
				final String msg = "The specified Wicket sessionId [" +
					sessionId +
					"] is not the same as JSecurity's " +
					"current Subject Session with id [" +
					session.getId() +
					"], indicating the Wicket request's " +
					"session is not the same as JSecurity's current Subject Session.  The two must always be " +
					"equal when using the " +
					getClass().getName() +
					" implementation.  If you're seeing this " +
					"exception, ensure you have configured JSecurity to use Enterprise Sessions and not " +
					"(the default) HTTP-only Sessions.";
				throw new WicketRuntimeException(msg);
			}
		}
		return session;
	}

	protected Session getSessionForUpdate(final String sessionId)
	{
		Session session = getSession(sessionId);
		if (session == null)
			session = SecurityUtils.getSubject().getSession();
		return session;
	}

	/**
	 * {@inheritDoc}
	 */
	public void pageAccessed(final String sessionId, final Page page)
	{
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 */
	public void removePage(final String sessionId, final String pageMapName, final int id)
	{
		if (id == -1)
		{
			LOG.debug("Removing page map [{}]", pageMapName);
			getPageCacheManager(sessionId).removePageCache(pageMapName);
		}
		else
		{
			LOG.debug("Removing page with id [{}] from page map [{}]", id, pageMapName);
			getPageCacheManager(sessionId).getPageCache(pageMapName).removePage(id);
		}
	}

	protected Collection<SerializedPageWrapper> serialize(final Page page)
	{
		final Collection<SerializedPage> serializedPages = serializePage(page);
		return wrap(serializedPages);
	}

	/**
	 * {@inheritDoc}
	 */
	public void storePage(final String sessionId, final Page page)
	{
		final Collection<SerializedPageWrapper> wrapped = serialize(page);
		getPageCacheManager(sessionId).getPageCache(page.getPageMapName()).storePages(wrapped);
		if (LOG.isDebugEnabled())
		{
			LOG.debug("storePage {}", page.toString());
			LOG.debug(getPageCacheManager(sessionId).getPageCache(page.getPageMapName()).toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void unbind(final String sessionId)
	{
		final Session active = getSession(sessionId);
		if (active != null)
		{
			final Object existing = active.removeAttribute(PAGE_MAP_SESSION_KEY);
			if (existing != null)
				LOG.debug("Removed PageMap [{}] from the Session (destroying)", existing);
		}
	}

	Collection<SerializedPageWrapper> wrap(final Collection<SerializedPage> serializedPages)
	{
		final Collection<SerializedPageWrapper> wrappers = new ArrayList<SerializedPageWrapper>(
			serializedPages.size());
		for (final SerializedPage sPage : serializedPages)
		{
			final SerializedPageWrapper wrapper = new SerializedPageWrapper(sPage,
				sPage.getPageId(), sPage.getPageMapName(), sPage.getVersionNumber(),
				sPage.getAjaxVersionNumber());
			wrappers.add(wrapper);
		}
		return wrappers;
	}
}
