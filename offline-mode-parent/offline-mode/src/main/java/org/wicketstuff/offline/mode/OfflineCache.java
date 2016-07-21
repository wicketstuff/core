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
package org.wicketstuff.offline.mode;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.protocol.http.WebApplication;

import java.util.List;

/**
 * The offline cache to initialize / load the client side cache.<br>
 * <br>
 * Example:
 * 
 * <pre>
 * <code>
 * List<OfflineCacheEntry> offlineCacheEntries = new ArrayList<>();
 * offlineCacheEntries.add(new OfflineCacheEntry().setCacheObject(new CssResourceReference(HomePage.class, "main.css")));
 * offlineCacheEntries.add(new OfflineCacheEntry().setCacheObject(HomePage.class));
 * OfflineCache.init(this, "MyCache-1", offlineCacheEntries);
 * </code>
 * </pre>
 * 
 * To contribute the offline cache use the following snippet in the renderHead method of a page:
 * 
 * <pre>
 * <code>
 * OfflineCache.renderHead(response);
 * </code>
 * </pre>
 * 
 * If the cache name is going to be changed all resources are going to be reloaded again. This happens
 * if the browser loads the new service worker content (after a browser refresh). The new service
 * worker is going to be initialized as "waiting". (See chapter "How to Update a Service Worker" of
 * the html5 rocks article) If you then switch the page to google for example and switch back, the
 * new content is going to be shown up and the old service worker is going to be marked as
 * "REDUNDANT". You have to leave the page with ALL tabs! Wicket handles older caches to be invalid
 * and clear them - so when you switch from "MyCache-1" to "MyCache-2" all entries of "MyCache-1"
 * are going to be cleared.<br>
 * <br>
 * For more information see:<br>
 * <br>
 * - https://www.w3.org/TR/service-workers/#navigator-service-worker-waiting<br>
 * - https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API<br>
 * - http://www.html5rocks.com/en/tutorials/service-worker/introduction/<br>
 * - http://caniuse.com/#search=Service%20Worker
 * <br>
 * <b>Important:</b> local development can be achieved through HTTP (localhost, 0.0.0.0, 127.0.0.1),
 * with DNS you have to use HTTPS! Service worker only running with HTTPS if not local.<br>
 * <br><br>
 * 
 * @author Tobias Soloschenko
 *
 */
public class OfflineCache
{
	private static final MetaDataKey<List<OfflineCacheEntry>> OFFLINE_CACHE_ENTRIES = new MetaDataKey<List<OfflineCacheEntry>>()
	{ };

	/**
	 * Initializes the offline cache (used in the init method of a wicket application)
	 * 
	 * @param webApplication
	 *            the web application to mount the offline cache references
	 * @param cacheName
	 *            the cache name
	 * @param offlineCacheEntries
	 *            the offline cache entries to be cached
	 */
	public static void init(WebApplication webApplication, String cacheName,
		List<OfflineCacheEntry> offlineCacheEntries)
	{
		OfflineCache.setOfflineCacheEntries(webApplication, offlineCacheEntries);
		webApplication.mountResource("wicket-offlinecache-serviceworkerregistration",
			ServiceWorkerRegistration.getInstance());
		webApplication.mountResource("wicket-offlinecache-serviceworker",
			new ServiceWorker(cacheName, offlineCacheEntries));
	}

	/**
	 * Loads the service worker registration and the service worker implementation java script
	 * references
	 * 
	 * @param response
	 *            the response to contribute the offline cache references
	 */
	public static void renderHead(IHeaderResponse response)
	{
		response.render(JavaScriptHeaderItem.forReference(ServiceWorker.getInstance()));
	}

	/**
	 * Gets the offline cache entries
	 * 
	 * @return the offline cache entries
	 */
	public static List<OfflineCacheEntry> getOfflineCacheEntries(WebApplication application)
	{
		return application.getMetaData(OFFLINE_CACHE_ENTRIES);
	}

	/**
	 * Sets the offline cache entries (can only be used if the offline cache hasn't been initialized
	 * yet)
	 *
	 * @param application
	 *          the application instance
	 * @param offlineCacheEntries
	 *          the offline cache entries to store
	 */
	public static void setOfflineCacheEntries(final WebApplication application, List<OfflineCacheEntry> offlineCacheEntries)
	{
		application.setMetaData(OFFLINE_CACHE_ENTRIES, offlineCacheEntries);
	}
}
