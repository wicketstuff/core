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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.wicket.util.io.IClusterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * continues effort started by Richard Wilkinson (<a
 * href="http://www.nabble.com/file/p18280052/TerracottaPageStore.java">to support TerraCotta</a>,
 * but the architecture has been separated into multiple classes, along with other
 * architectural/stylistic changes.
 * 
 * @author Les Hazlewood
 * @since Feb 13, 2009 10:23:43 PM
 */
public class PageCache implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(PageCache.class);

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock read = rwl.readLock();
	private final Lock write = rwl.writeLock();

	private final LinkedHashMap<Integer, SerializedPageWrapper> pages = new LinkedHashMap<Integer, SerializedPageWrapper>();
	private final TreeMap<Integer, Integer> pageKeys = new TreeMap<Integer, Integer>();

	private final AtomicInteger id = new AtomicInteger(Integer.MIN_VALUE);

	private final int MAX_SIZE;

	public PageCache(final int maxSize)
	{
		MAX_SIZE = maxSize;
	}

	public boolean containsPage(final int pageId)
	{
		read.lock();
		try
		{
			return pageKeys.containsKey(pageId);
		}
		finally
		{
			read.unlock();
		}
	}

	public SerializedPageWrapper getPage(final int pageId)
	{
		read.lock();
		try
		{
			return pages.get(pageId);
		}
		finally
		{
			read.unlock();
		}
	}

	public void removePage(final Integer pageId)
	{
		write.lock();
		try
		{
			pages.remove(pageId);
		}
		finally
		{
			write.unlock();
		}
	}

	public void storePages(final SerializedPageWrapper wrapper)
	{
		write.lock();
		try
		{
			// reduce size of page store to within set size if required
			if (MAX_SIZE != SessionPageStore.DEFAULT_MAX_PAGES)
			{
				int numToRemove = pages.size() + 1 - MAX_SIZE;
				if (numToRemove > 0)
				{
					final Iterator<Map.Entry<Integer, SerializedPageWrapper>> iter = pages.entrySet()
						.iterator();
					while (iter.hasNext() && numToRemove > 0)
					{
						final Map.Entry<Integer, SerializedPageWrapper> entry = iter.next();
						iter.remove();
						pageKeys.remove(entry.getKey());
						numToRemove--;
					}
				}
			}
			final Integer pageKey = wrapper.getPageId();
			// remove to preserve access order
			pages.remove(pageKey);
			pages.put(pageKey, wrapper);
			pageKeys.put(pageKey, id.getAndIncrement());
		}
		finally
		{
			write.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		for (final Entry<Integer, SerializedPageWrapper> entry : pages.entrySet())
			sb.append("\t").append(entry.getKey().toString()).append("\n");
		if (LOG.isTraceEnabled())
			sb.append("\tPageKeys TreeSet: ").append(pageKeys.toString());
		return sb.toString();
	}
}
