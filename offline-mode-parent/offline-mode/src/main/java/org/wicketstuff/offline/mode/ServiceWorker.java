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

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptPackageResource;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.offline.mode.OfflineCacheEntry.Cors;

/**
 * The actual service worker which applies the URLs of the offline cache entries to the client side
 * cache with the given name.
 * 
 * @author Tobias Soloschenko.
 *
 */
public class ServiceWorker extends JavaScriptResourceReference
{
	private static final long serialVersionUID = 6850710261262467396L;

	private static ServiceWorker self;

	private final String cacheName;

	private final List<OfflineCacheEntry> offlineCacheEntries;

	/**
	 * Creates the service worker
	 * 
	 * @param cacheName
	 *            the name of the cache
	 * @param offlineCacheEntries
	 *            the entries to be cached
	 */
	protected ServiceWorker(String cacheName, List<OfflineCacheEntry> offlineCacheEntries)
	{
		super(ServiceWorker.class, ServiceWorker.class.getSimpleName() + ".js");
		this.cacheName = cacheName;
		this.offlineCacheEntries = offlineCacheEntries;
		ServiceWorker.self = this;
	}

	/**
	 * Gets the current service worker instance
	 * 
	 * @return the service worker instance
	 */
	public static ServiceWorker getInstance()
	{
		return self;
	}

	/**
	 * If the service worker resource is shipped the first time the script is filled with the URLs
	 * of the resources to be cached.
	 * 
	 * @return the java script package resource to provide the script with the URLs to be cached
	 */
	@Override
	public JavaScriptPackageResource getResource()
	{
		final JavaScriptPackageResource resource = new JavaScriptPackageResource(getScope(),
			getName(), getLocale(), getStyle(), getVariation())
		{

			private static final long serialVersionUID = 5615537870274839300L;

			@Override
			protected void setResponseHeaders(ResourceResponse resourceResponse,
				Attributes attributes)
			{
				super.setResponseHeaders(resourceResponse, attributes);
				((WebResponse)attributes.getResponse()).disableCaching();
			}

			@SuppressWarnings("unchecked")
			@Override
			protected byte[] processResponse(Attributes attributes, byte[] bytes)
			{
				byte[] bytesToReturn = null;
				try
				{
					RequestCycle requestCycle = RequestCycle.get();
					String script = new String(bytes, "UTF-8");
					script = script.replace("$(cacheName)", cacheName);
					JSONArray urlJsonArray = new JSONArray();
					for (OfflineCacheEntry offlineCacheEntry : offlineCacheEntries)
					{
						Object cacheObject = offlineCacheEntry.getCacheObject();
						PageParameters pageParameters = offlineCacheEntry.getPageParameters();
						String suffix = offlineCacheEntry.getSuffix();
						Cors cors = offlineCacheEntry.getCors();

						if (cacheObject == null)
						{
							throw new WicketRuntimeException(
								"Please provide a cache object to each OfflineCacheEntry.");
						}

						CharSequence urlFor;
						if (cacheObject instanceof ResourceReference)
						{
							urlFor = requestCycle.urlFor((ResourceReference)cacheObject,
								pageParameters);
						}
						else if (cacheObject instanceof Class)
						{
							urlFor = requestCycle.urlFor((Class<? extends Page>)cacheObject,
								pageParameters);
						}
						else if (cacheObject instanceof IRequestHandler)
						{
							urlFor = requestCycle.urlFor((IRequestHandler)offlineCacheEntry);
						}
						else
						{
							urlFor = cacheObject.toString();
						}

						if (urlFor.toString().equals("."))
						{
							urlFor = "/";
						}
						else if (urlFor.toString().startsWith("."))
						{
							urlFor = urlFor.toString().substring(1);
						}

						String url = urlFor.toString() + (suffix != null ? suffix : "");
						JSONObject object = new JSONObject();
						if (cors != null)
						{
							object.put("cors", cors.getRealName());
						}
						object.put("url", url);
						urlJsonArray.put(object);
					}
					String urlJsonArrayAsString = urlJsonArray.toString();
					script = script.replace("$(offlineCacheEntries)", urlJsonArrayAsString);
					bytesToReturn = script.getBytes();
				}
				catch (UnsupportedEncodingException e)
				{
					// NOOP
				}
				return super.processResponse(attributes, bytesToReturn);
			}
		};
		removeCompressFlagIfUnnecessary(resource);
		return resource;
	}

	@Override
	public List<HeaderItem> getDependencies() {
		List<HeaderItem> dependencies = super.getDependencies();
		dependencies.add(JavaScriptHeaderItem.forReference(ServiceWorkerRegistration.getInstance()));
		return dependencies;
	}
}
