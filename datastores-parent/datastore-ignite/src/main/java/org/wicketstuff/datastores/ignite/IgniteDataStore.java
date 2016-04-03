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
package org.wicketstuff.datastores.ignite;

import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.util.lang.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;

/**
 * An IDataStore implementation that saves serialized pages in Apache Ignite
 *
 * @author Alexey Prudnikov
 */
public class IgniteDataStore implements IDataStore
{
	private static final Logger LOGGER = LoggerFactory.getLogger(IgniteDataStore.class);

	/**
	 * Apache Ignite instance
	 */
	private final Ignite ignite;

	/**
	 * Constructor
	 *
	 * @param ignite     The Apache Ignite instance
	 */
	public IgniteDataStore(Ignite ignite)
	{
		this.ignite = Args.notNull(ignite, "ignite");
	}

	/**
	 * Returns (and creates if needed) named cache from Ignite instance
	 *
	 * @param cacheName     Cache name
	 */
	private IgniteCache<Integer, byte[]> getIgniteCache(String cacheName)
	{
		return ignite.getOrCreateCache(cacheName);
	}

	@Override
	public byte[] getData(String sessionId, int pageId)
	{
		byte[] bytes = null;
		IgniteCache<Integer, byte[]> cache = getIgniteCache(sessionId);
		if (cache != null)
		{
			bytes = cache.get(pageId);
		}

		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Got {} for session '{}' and page id '{}'",
				bytes != null ? "data" : "'null'", sessionId, pageId
			);
		}

		return bytes;
	}

	@Override
	public void removeData(String sessionId, int pageId)
	{
		IgniteCache<Integer, byte[]> cache = getIgniteCache(sessionId);
		if (cache != null && cache.remove(pageId) && LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Deleted data for session '{}' and page with id '{}'", sessionId, pageId);
		}
	}

	@Override
	public void removeData(String sessionId)
	{
		IgniteCache<Integer, byte[]> cache = getIgniteCache(sessionId);
		if (cache != null)
		{
			cache.destroy();
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Deleted data for session '{}'", sessionId);
			}
		}
	}

	@Override
	public void storeData(String sessionId, int pageId, byte[] data)
	{
		IgniteCache<Integer, byte[]> cache = getIgniteCache(sessionId);
		if (cache != null)
		{
			cache.put(pageId, data);
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Inserted data for session '{}' and page id '{}'", sessionId, pageId);
			}
		}
	}

	@Override
	public void destroy()
	{
		if (ignite != null)
		{
			try
			{
				ignite.close();
			}
			catch (IgniteException e)
			{
				LOGGER.error("Can't close ignite instance", e);
			}
		}
	}

	@Override
	public boolean isReplicated()
	{
		return true;
	}

	@Override
	public boolean canBeAsynchronous()
	{
		return true;
	}
}
