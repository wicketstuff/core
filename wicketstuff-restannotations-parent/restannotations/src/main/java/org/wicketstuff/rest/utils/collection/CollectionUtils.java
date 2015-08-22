/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.utils.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CollectionUtils
{
	/**
	 * Make a list map immutable.
	 *
	 * @param listMap
	 *            the list map in input.
	 * @return the immutable list map.
	 */
	public static <C, E> Map<C, List<E>> makeListMapImmutable(Map<C, List<E>> listMap)
	{
		for (C key : listMap.keySet())
		{
			listMap.put(key, Collections.unmodifiableList(listMap.get(key)));
		}

		return Collections.unmodifiableMap(listMap);
	}
	/**
	 * Copy the source collection into the destination one filtering source elements by the given type.
	 * 
	 * @param source
	 * 			the source collection
	 * @param destination
	 * 			the destination collection
	 * @param clazz
	 * 			the filtering type
	 */
	@SuppressWarnings("unchecked")
	public static <T, E extends T> void filterCollectionByType(Collection<T> source, Collection<E> destination, Class<E> clazz)
	{
		for (T element : source)
		{
			if(clazz.isInstance(element))
			{
				destination.add((E)element);
			}
		}
	}
}
