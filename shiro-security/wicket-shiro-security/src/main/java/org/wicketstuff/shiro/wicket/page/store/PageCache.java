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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.wicket.IClusterable;
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

	private static final Logger log = LoggerFactory.getLogger(PageCache.class);

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock read = rwl.readLock();
	private final Lock write = rwl.writeLock();

	private final LinkedHashMap<PageKey, SerializedPageWrapper> pages = new LinkedHashMap<PageKey, SerializedPageWrapper>();
	private final TreeMap<PageKey, Integer> pageKeys = new TreeMap<PageKey, Integer>();

	private final AtomicInteger id = new AtomicInteger(Integer.MIN_VALUE);

	private final int MAX_SIZE;

	public PageCache(final int maxSize)
	{
		MAX_SIZE = maxSize;
	}

	public void storePages(final Collection<SerializedPageWrapper> pagesToAdd)
	{
		write.lock();
		try
		{
			// reduce size of page store to within set size if required
			if (MAX_SIZE != SessionPageStore.DEFAULT_MAX_PAGES)
			{
				int numToRemove = pages.size() + pagesToAdd.size() - MAX_SIZE;
				if (numToRemove > 0)
				{
					final Iterator<Map.Entry<PageKey, SerializedPageWrapper>> iter = pages.entrySet()
						.iterator();
					while (iter.hasNext() && numToRemove > 0)
					{
						final Map.Entry<PageKey, SerializedPageWrapper> entry = iter.next();
						iter.remove();
						pageKeys.remove(entry.getKey());
						numToRemove--;
					}
				}
			}
			for (final SerializedPageWrapper wrapper : pagesToAdd)
			{
				final PageKey pageKey = new PageKey(wrapper.getPageId(),
					wrapper.getVersionNumber(), wrapper.getAjaxVersionNumber());
				// remove to preserve access order
				pages.remove(pageKey);
				pages.put(pageKey, wrapper);
				pageKeys.put(pageKey, id.getAndIncrement());
			}
		}
		finally
		{
			write.unlock();
		}
	}

	public boolean containsPage(final int pageId, final int pageVersion)
	{
		read.lock();
		try
		{
			// make PageKeys for below and above this version
			// this id and version but -1 for ajax
			final PageKey below = new PageKey(pageId, pageVersion, -1);
			// this id and version +1, -1 for ajax
			final PageKey above = new PageKey(pageId, pageVersion + 1, -1);

			final SortedMap<PageKey, Integer> thisPageAndVersion = pageKeys.subMap(below, above);

			return !thisPageAndVersion.isEmpty();
		}
		finally
		{
			read.unlock();
		}
	}

	public SerializedPageWrapper getPage(final int id, final int versionNumber,
		final int ajaxVersionNumber)
	{
		read.lock();
		try
		{
			SerializedPageWrapper sPage = null;
			// just find the exact page version
			if (versionNumber != -1 && ajaxVersionNumber != -1)
			{
				sPage = pages.get(new PageKey(id, versionNumber, ajaxVersionNumber));
			}
			// we need to find last recently stored page window - that is page at the end of the
			// list
			else if (versionNumber == -1)
			{
				final PageKey fromKey = new PageKey(id, -1, -1);
				final PageKey toKey = new PageKey(id + 1, -1, -1);

				SortedMap<PageKey, Integer> subMap = pageKeys.subMap(fromKey, toKey);

				int max = -1;
				PageKey maxPageKey = null;

				for (Map.Entry<PageKey, Integer> entry : subMap.entrySet())
				{
					if (entry.getValue() > max)
					{
						max = entry.getValue();
						maxPageKey = entry.getKey();
					}
				}
				if (maxPageKey != null)
				{
					sPage = pages.get(maxPageKey);
				}
			}
			else
			{
				// ajaxVersion is guaranteed to be -1 at this point -
				// make a page key which will be straight after wanted
				// PageKey, ie, version number is one after this one, ajax
				// version number is -1, page id is the same
				final PageKey toElement = new PageKey(id, versionNumber + 1, -1);
				final SortedMap<PageKey, Integer> posiblePageKeys = pageKeys.headMap(toElement);
				if (posiblePageKeys.size() > 0)
				{
					sPage = pages.get(posiblePageKeys.lastKey());
				}
			}
			return sPage;
		}
		finally
		{
			read.unlock();
		}
	}

	public void removePage(final int id)
	{
		write.lock();
		try
		{
			final Iterator<Map.Entry<PageKey, SerializedPageWrapper>> iter = pages.entrySet()
				.iterator();
			while (iter.hasNext())
			{
				final PageKey pKey = iter.next().getKey();
				if (id == pKey.getPageId())
				{
					iter.remove();
					pageKeys.remove(pKey);
				}
			}
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
		for (Map.Entry<PageKey, SerializedPageWrapper> entry : pages.entrySet())
		{
			sb.append("\t").append(entry.getKey().toString()).append("\n");
		}
		if (log.isTraceEnabled())
		{
			sb.append("\tPageKeys TreeSet: ").append(pageKeys.toString());
		}
		return sb.toString();
	}

}
