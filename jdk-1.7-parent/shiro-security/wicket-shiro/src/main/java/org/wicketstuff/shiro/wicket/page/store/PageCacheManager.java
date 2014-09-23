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


import org.apache.wicket.util.io.IClusterable;

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

	private final PageCache cache;

	private final int MAX_PAGES_PER_MAP;

	public PageCacheManager(final int maxNumPagesPerMap)
	{
		MAX_PAGES_PER_MAP = maxNumPagesPerMap;
		cache = new PageCache(MAX_PAGES_PER_MAP);
	}

	public PageCache getPageCache()
	{
		return cache;
	}
}
