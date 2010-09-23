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
import org.apache.wicket.util.lang.WicketObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link org.apache.wicket.protocol.http.SecondLevelCacheSessionStore.IPageStore IPageStore} implementation
 * that stores serialized Pages in JSecurity's {@link org.apache.shiro.session.Session Session}.  This implementation
 * exists to support applications that use JSecurity's Enterprise Sessions instead of HTTP-only sessions.
 * <p/>
 * In a distributed application, the JSecurity Session data might not reside on the same host that runs the Wicket
 * application.  In these cases the default {@link org.apache.wicket.protocol.http.pagestore.DiskPageStore DiskPageStore}
 * used by Wicket is not suitable.  Instead Page state must be serialized to a mechanism that is 'cluster-friendly'.
 * <p/>
 * JSecurity's enterprise {@code Session}s are cluster-friendly, so storing pages in the {@code Session} is a good
 * choice.  This means applications can utilize true generic load balancing and do not need
 * <a href="http://en.wikipedia.org/wiki/Load_balancing_(computing)#Persistence">sticky sessions</a> - the Wicket
 * PageMap can be updated on one host which is then available for any next load-balanced request on another host
 * because the {@code Session} is clustered.
 *
 * @author Les Hazlewood
 * @since Feb 13, 2009 4:25:12 PM
 */
public class SessionPageStore implements IPageStore {

    private static final Logger log = LoggerFactory.getLogger(SessionPageStore.class);
    private static final String PAGE_MAP_SESSION_KEY = SessionPageStore.class.getName() + "_PAGE_CACHE_MANAGER_SESSION_KEY";

    protected static final int DEFAULT_MAX_PAGES = -1;

    private final int MAX_PAGE_MAP_SIZE;
    
    private final String applicationName;

    public SessionPageStore() {
    	this(DEFAULT_MAX_PAGES);
    }

    public SessionPageStore(int maxPageMapSize) {
        if (maxPageMapSize < -1) {
            MAX_PAGE_MAP_SIZE = DEFAULT_MAX_PAGES;
            log.info("Created SessionPageStore: unlimited number of pages allowed.");
        } else {
            MAX_PAGE_MAP_SIZE = maxPageMapSize;
            log.info("Created SessionPageStore: [{}] maximum number of pages allowed.", maxPageMapSize);
        }
        this.applicationName = Application.get().getName();
    }

    public int getMaxPageMapSize() {
        return MAX_PAGE_MAP_SIZE;
    }

    protected Session getSessionForUpdate(String sessionId) {
        Session session = getSession(sessionId);
        if (session == null) {
            session = SecurityUtils.getSubject().getSession();
        }
        return session;
    }

    protected Session getSession(String sessionId) {
        Session session = null;
        Subject currentSubject = SecurityUtils.getSubject();
        if (currentSubject != null) {
            session = currentSubject.getSession(false);
            //guarantee we pulled the same session that Wicket expects us to pull.  Because JSecurity's Subject
            //acquisition in web apps is based on the incoming request, and so is Wicket's, this should
            //_always_ be the same.  If not, something is seriously wrong:
            if (session != null && sessionId != null && !sessionId.equals(session.getId())) {
                String msg = "The specified Wicket sessionId [" + sessionId + "] is not the same as JSecurity's " +
                    "current Subject Session with id [" + session.getId() + "], indicating the Wicket request's " +
                    "session is not the same as JSecurity's current Subject Session.  The two must always be " +
                    "equal when using the " + getClass().getName() + " implementation.  If you're seeing this " +
                    "exception, ensure you have configured JSecurity to use Enterprise Sessions and not " +
                    "(the default) HTTP-only Sessions.";
                throw new WicketRuntimeException(msg);
            }
        }
        return session;
    }

    public void destroy() {
        //do nothing - session timeout will cleanup automatically
    }

    public Page getPage(String sessionId, int pageId) {
        final SerializedPageWrapper wrapper = getPageCacheManager(sessionId).getPageCache().getPage(pageId);
        byte[] sPage = wrapper != null ? (byte[]) wrapper.getPage() : null;
        return (Page) (sPage != null ? deserializePage(sPage) : null);
    }

    protected IManageablePage deserializePage(final byte data[])
	{
		return (IManageablePage)WicketObjects.byteArrayToObject(data);
	}
    
    public void pageAccessed(String sessionId, Page page) {
        //nothing to do
    }

    public void removePage(String sessionId, int pageId) {
        if (pageId != -1) {
            log.debug("Removing page with id [{}]", pageId);
            getPageCacheManager(sessionId).getPageCache().removePage(pageId);
        }
    }

    protected PageCacheManager getPageCacheManager(final String sessionId) {
        Session session = getSessionForUpdate(sessionId);
        PageCacheManager pcc = (PageCacheManager) session.getAttribute(PAGE_MAP_SESSION_KEY);
        if (pcc == null) {
            pcc = new PageCacheManager(getMaxPageMapSize());
            session.setAttribute(PAGE_MAP_SESSION_KEY, pcc);
        }
        return pcc;
    }

    SerializedPageWrapper wrap(byte[] serializedPages, int pageId) {
        
    	SerializedPageWrapper wrapper = new SerializedPageWrapper(serializedPages, pageId);
        return wrapper;
    }

    protected SerializedPageWrapper serialize(String sessionId, IManageablePage page) {
        byte[] serializedPage = serializePage(sessionId, page);
        return wrap(serializedPage, page.getPageId());
    }
    
    protected byte[] serializePage(final String sessionId, final IManageablePage page)
	{
		Args.notNull(sessionId, "sessionId");
		Args.notNull(page, "page");

		byte data[] = WicketObjects.objectToByteArray(page, applicationName);
		return data;
	}


    public void storePage(String sessionId, IManageablePage page) {
        SerializedPageWrapper wrapper = serialize(sessionId, page);
        getPageCacheManager(sessionId).getPageCache().storePages(wrapper);
        if (log.isDebugEnabled()) {
            log.debug("storePage {}", page.toString());
        }
    }

    public void unbind(String sessionId) {
        Session active = getSession(sessionId);
        if (active != null) {
            Object existing = active.removeAttribute(PAGE_MAP_SESSION_KEY);
            if (existing != null) {
                log.debug("Removed PageMap [{}] from the Session (destroying)", existing);
            }
        }
    }

    public boolean containsPage(String sessionId, int pageId) {
        return getPageCacheManager(sessionId).getPageCache().containsPage(pageId);
    }

	public Serializable prepareForSerialization(String sessionId, Object page) {
		return null;
	}

	public Object restoreAfterSerialization(Serializable serializable) {
		return null;
	}

	public IManageablePage convertToPage(Object page) {
		if (page == null)
		{
			return null;
		}
		else if (page instanceof IManageablePage)
		{
			return (IManageablePage)page;
		}
		else if (page instanceof byte[])
		{
			IManageablePage deserializePage = deserializePage((byte[]) page);
			return deserializePage;
		}

		String type = page != null ? page.getClass().getName() : null;
		throw new IllegalArgumentException("Unknown object type " + type);

	}
}
