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
package org.apache.wicket.security.hive;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authorization.Permission;

/**
 * A very simple caching mechanism on top of {@link BasicHive}. If you want more control
 * over your cache you could for example use EHCache and extend BasicHive yourself. This
 * cache is cleared when a subject logs off but no guarantees are given that the cache
 * will not be cleared prematurely or how long it takes after a user logs off to clear the
 * cached results.
 * 
 * @author marrink
 */
public class SimpleCachingHive extends BasicHive
{
	private final WeakHashMap<Subject, Map<Permission, Boolean>> cache;

	/**
	 * Constructor.
	 */
	public SimpleCachingHive()
	{
		super();
		// reasonable init cache size
		cache = new WeakHashMap<Subject, Map<Permission, Boolean>>(50);
	}

	/**
	 * 
	 * @see org.apache.wicket.security.hive.BasicHive#cacheLookUp(org.apache.wicket.security.hive.authentication.Subject,
	 *      org.apache.wicket.security.hive.authorization.Permission)
	 */
	@Override
	protected Boolean cacheLookUp(Subject subject, Permission permission)
	{
		// easier not use cache when subject is null, since there is no timeout
		// mechanism
		if (subject == null || permission == null)
			return null;
		// no synch, since it does not matter much if we miss a cache hit
		Map<Permission, Boolean> result = cache.get(subject);
		if (result != null)
			return result.get(permission);
		return null;

	}

	/**
	 * 
	 * @see org.apache.wicket.security.hive.BasicHive#cacheResult(org.apache.wicket.security.hive.authentication.Subject,
	 *      org.apache.wicket.security.hive.authorization.Permission, boolean)
	 */
	@Override
	protected void cacheResult(Subject subject, Permission permission, boolean result)
	{
		if (subject == null || permission == null)
			return;

		// again no caching, does not matter much if we overwrite a new cache
		Map<Permission, Boolean> resultMap = cache.get(subject);
		if (resultMap == null)
		{
			resultMap = new HashMap<Permission, Boolean>();
			resultMap.put(permission, result);
			cache.put(subject, resultMap);
		}
		else
			resultMap.put(permission, result);
	}

}
