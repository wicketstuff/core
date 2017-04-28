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

import java.io.Serializable;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.page.IManageablePage;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.util.lang.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link IPageStore IPageStore}
 * implementation that stores serialized Pages in JSecurity's
 * {@link org.apache.shiro.session.Session Session}. This implementation exists to support
 * applications that use JSecurity's Enterprise Sessions instead of HTTP-only sessions.
 * <p/>
 * In a distributed application, the JSecurity Session data might not reside on the same host that
 * runs the Wicket application. In these cases the default
 * {@link org.apache.wicket.pageStore.DiskDataStore} used by Wicket is
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
public class SessionPageStore implements IPageStore
{
	private static final Logger LOG = LoggerFactory.getLogger(SessionPageStore.class);
	private static final String PAGE_MAP_SESSION_KEY = SessionPageStore.class.getName() +
		"_PAGE_CACHE_MANAGER_SESSION_KEY";

	protected static final int DEFAULT_MAX_PAGES = -1;

	private final int MAX_PAGE_MAP_SIZE;

	public SessionPageStore()
	{
		this(DEFAULT_MAX_PAGES);
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

	public boolean containsPage(final String sessionId, final int pageId)
	{
		return getPageCacheManager(sessionId).getPageCache().containsPage(pageId);
	}

    @Override
	public IManageablePage convertToPage(final Object page)
	{
		if (page == null)
			return null;

		if (page instanceof IManageablePage)
			return (IManageablePage)page;

		if (page instanceof byte[])
			return deserializePage((byte[])page);

		throw new IllegalArgumentException("Unknown object type " + page.getClass().getName());

	}

	protected IManageablePage deserializePage(final byte data[])
	{
		// TODO: test this, Serializer replacing old
		// WicketObjects.byteArrayToObject(data);
		// call
		return (IManageablePage)Application.get()
			.getFrameworkSettings()
			.getSerializer()
			.deserialize(data);
	}

    @Override
	public void destroy()
	{
		// do nothing - session timeout will cleanup automatically
	}

	public int getMaxPageMapSize()
	{
		return MAX_PAGE_MAP_SIZE;
	}

    @Override
	public Page getPage(final String sessionId, final int pageId)
	{
		final SerializedPageWrapper wrapper = getPageCacheManager(sessionId).getPageCache()
			.getPage(pageId);
		final byte[] sPage = wrapper != null ? (byte[])wrapper.getPage() : null;
		return (Page)(sPage != null ? deserializePage(sPage) : null);
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
				throw new WicketRuntimeException(
					"The specified Wicket sessionId [" +
						sessionId +
						"] is not the same as Shiro's current Subject Session with id [" +
						session.getId() +
						"], indicating the Wicket request's session is not the same as Shiro's current Subject Session. " +
						"The two must always be equal when using the " +
						getClass().getName() +
						" implementation. " +
						"If you're seeing this exception, ensure you have configured Shiro to use Enterprise Sessions and not (the default) HTTP-only Sessions.");
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

	public void pageAccessed(final String sessionId, final Page page)
	{
		// nothing to do
	}

    @Override
	public Serializable prepareForSerialization(final String sessionId, final Serializable page)
	{
		return null;
	}

    @Override
    public void removePage(final String sessionId, final int pageId)
	{
		if (pageId != -1)
		{
			LOG.debug("Removing page with id [{}]", pageId);
			getPageCacheManager(sessionId).getPageCache().removePage(pageId);
		}
	}

    @Override
	public Object restoreAfterSerialization(final Serializable serializable)
	{
		return null;
	}

	protected SerializedPageWrapper serialize(final String sessionId, final IManageablePage page)
	{
		final byte[] serializedPage = serializePage(sessionId, page);
		return wrap(serializedPage, page.getPageId());
	}

	protected byte[] serializePage(final String sessionId, final IManageablePage page)
	{
		Args.notNull(sessionId, "sessionId");
		Args.notNull(page, "page");

		// TODO: test this, Serializer replacing old
		// WicketObjects.objectToByteArray(page, applicationName);
		// call
		final byte data[] = Application.get()
			.getFrameworkSettings()
			.getSerializer()
			.serialize(page);
		return data;
	}

    @Override
	public void storePage(final String sessionId, final IManageablePage page)
	{
		final SerializedPageWrapper wrapper = serialize(sessionId, page);
		getPageCacheManager(sessionId).getPageCache().storePages(wrapper);
		if (LOG.isDebugEnabled())
			LOG.debug("storePage {}", page.toString());
	}

    @Override
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

	SerializedPageWrapper wrap(final byte[] serializedPages, final int pageId)
	{
		final SerializedPageWrapper wrapper = new SerializedPageWrapper(serializedPages, pageId);
		return wrapper;
	}

	@Override
	public boolean canBeAsynchronous() 
	{
		return false;
	}
}
