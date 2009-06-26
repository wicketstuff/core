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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.wicket.IClusterable;

/**
 * continues effort started by Richard Wilkinson (<a
 * href="http://www.nabble.com/file/p18280052/TerracottaPageStore.java">to support TerraCotta</a>,
 * but the architecture has been separated into multiple classes, along with other
 * architectural/stylistic changes.
 * 
 * @author Les Hazlewood
 * @since Feb 13, 2009 10:37:12 PM
 */
public class PageCacheManager implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock read = rwl.readLock();
	private final Lock write = rwl.writeLock();
	private final Map<String, PageCache> caches = new HashMap<String, PageCache>();

	private final int MAX_PAGES_PER_MAP;

	public PageCacheManager(final int maxNumPagesPerMap)
	{
		MAX_PAGES_PER_MAP = maxNumPagesPerMap;
	}

	public PageCache getPageCache(final String name)
	{
		read.lock();
		PageCache toReturn;
		try
		{
			toReturn = caches.get(name);
		}
		finally
		{
			read.unlock();
		}

		if (toReturn == null)
		{
			// Create a new PageCache, but note that another thread might have added one while no
			// lock was held,
			// so we still have to obtain the lock
			write.lock();
			try
			{
				toReturn = new PageCache(MAX_PAGES_PER_MAP);
				final PageCache old = caches.put(name, toReturn);
				if (old != null)
				{
					// already exists, revert and use existing
					toReturn = old;
					caches.put(name, toReturn);
				}
			}
			finally
			{
				write.unlock();
			}
		}
		return toReturn;
	}

	public void removePageCache(final String name)
	{
		write.lock();
		try
		{
			caches.remove(name);
		}
		finally
		{
			write.unlock();
		}
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		for (final Map.Entry<String, PageCache> entry : caches.entrySet())
		{
			sb.append("PageCache: ").append(entry.getKey()).append("\n");
			sb.append(entry.getValue().toString());
		}
		return sb.toString();
	}
}
