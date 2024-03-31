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

import org.apache.wicket.Page;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * The cache entry is used to be add a Page, a ResourceReference, an IRequestHandler or simply a
 * String to the caching context.
 * 
 * @author Tobias Soloschenko
 *
 */
public class OfflineCacheEntry
{
	private Object cacheObject;

	private PageParameters pageParameters;

	private String suffix;

	private Cors cors;
	
	/**
	 * CORS settings to be applied to the service worker request / against the server or the cache
	 * 
	 * @author Tobias Soloschenko
	 *
	 */
	public enum Cors
	{
		/**
		 * request the server with cors option
		 */
		CORS("cors"),

		/**
		 * no cross orign
		 */
		NO_CORS("no-cors"),

		/**
		 * from same origin
		 */
		SAME_ORIGIN("same-origin");

		private String realName;

		Cors(String realName)
		{
			this.realName = realName;
		}

		/**
		 * Gets the real name for the cors option
		 * 
		 * @return the real name
		 */
		public String getRealName()
		{
			return realName;
		}
	}

	/**
	 * Gets the object to be cached
	 * 
	 * @return the object to be cached
	 */
	public Object getCacheObject()
	{
		return cacheObject;
	}

	/**
	 * Sets the object to be cached
	 * 
	 * @param cacheObject
	 *            the object to be cached
	 * @return the current instance of the entry
	 */
	public OfflineCacheEntry setCacheObject(IRequestHandler cacheObject)
	{
		this.cacheObject = cacheObject;
		return this;
	}

	/**
	 * Sets the object to be cached
	 * 
	 * @param cacheObject
	 *            the object to be cached
	 * @return the current instance of the entry
	 */
	public OfflineCacheEntry setCacheObject(ResourceReference cacheObject)
	{
		this.cacheObject = cacheObject;
		return this;
	}

	/**
	 * Sets the object to be cached
	 * 
	 * @param cacheObject
	 *            the object to be cached
	 * @return the current instance of the entry
	 */
	public OfflineCacheEntry setCacheObject(Class<? extends Page> cacheObject)
	{
		this.cacheObject = cacheObject;
		return this;
	}

	/**
	 * Sets the object to be cached
	 * 
	 * @param url
	 *            the URL to be cached (e.g. /wicket/myMountedResource)
	 * @return the current instance of the entry
	 */
	public OfflineCacheEntry setCacheObject(String url)
	{
		this.cacheObject = url;
		return this;
	}

	/**
	 * Gets the page parameters to be cached
	 * 
	 * @return the page parameters to be cached
	 */
	public PageParameters getPageParameters()
	{
		return pageParameters;
	}

	/**
	 * Sets the page parameter to be cached
	 * 
	 * @param pageParameters
	 *            the page parameters to be cached
	 * @return the current instance of the entry
	 */
	public OfflineCacheEntry setPageParameters(PageParameters pageParameters)
	{
		this.pageParameters = pageParameters;
		return this;
	}

	/**
	 * Gets the suffix to be placed behind the object / page parameters that are cached
	 * 
	 * @return the suffix
	 */
	public String getSuffix()
	{
		return suffix;
	}

	/**
	 * Sets the suffix to be placed behind the object / page parameters that are cached
	 * 
	 * @param suffix
	 *            to be placed behind the object / page parameters that are cached
	 * @return the current instance of the entry
	 */
	public OfflineCacheEntry setSuffix(String suffix)
	{
		this.suffix = suffix;
		return this;
	}

	/**
	 * Gets the cors settings of the entity
	 * 
	 * @return the cors settings
	 */
	public Cors getCors()
	{
		return cors;
	}

	/**
	 * Sets the cors settings of the entity
	 * 
	 * @param cors
	 *            the cors settings to be used
	 * @return the current instance of the entry
	 */
	public OfflineCacheEntry setCors(Cors cors)
	{
		this.cors = cors;
		return this;
	}
}
