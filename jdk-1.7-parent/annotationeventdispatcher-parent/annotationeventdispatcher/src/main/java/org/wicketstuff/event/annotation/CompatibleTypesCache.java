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
package org.wicketstuff.event.annotation;

import org.apache.wicket.util.collections.ClassMetaCache;

import java.util.HashSet;
import java.util.Set;

class CompatibleTypesCache
{
	private static final ClassMetaCache<Set<Class<?>>> allTypesByType = new ClassMetaCache<Set<Class<?>>>();

	static Set<Class<?>> getCompatibleTypes(final Class<?> clazz)
	{
		Set<Class<?>> types = allTypesByType.get(clazz);
		if (types == null)
		{
			types = getAllTypes(clazz);
			allTypesByType.put(clazz, types);
		}
		return types;
	}

	private static Set<Class<?>> getAllTypes(Class<?> clazz)
	{
		Set<Class<?>> types = new HashSet<Class<?>>();
		while (clazz != null)
		{
			types.add(clazz);
			addAllInterfaces(clazz, types);
			clazz = clazz.getSuperclass();
		}
		return types;
	}

	private static void addAllInterfaces(Class<?> clazz, Set<Class<?>> types)
	{
		for (Class<?> interfaze : clazz.getInterfaces())
		{
			if (types.add(interfaze))
			{
				addAllInterfaces(interfaze, types);
			}
		}
	}
}
