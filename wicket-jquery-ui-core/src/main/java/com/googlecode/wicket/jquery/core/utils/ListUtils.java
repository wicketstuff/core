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
package com.googlecode.wicket.jquery.core.utils;

import java.util.Iterator;
import java.util.List;

/**
 * Utility class for {@link List}(<tt>s</tt>)
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class ListUtils
{
	/**
	 * Gets the list-item matching the given hash against the list
	 * @param hash the hashcode to match
	 * @param list the {@link List} to search in
	 * @return the list-item or <code>null</code> if not found
	 */
	public static synchronized <T> T fromHash(int hash, List<T> list)
	{
		int index = ListUtils.indexOf(hash, list);

		if (index > -1)
		{
			return list.get(index);
		}

		return null;
	}

	/**
	 * Gets the index of the item occurrence matching the specified hashcode.
	 * @param list the {@link List}
	 * @param hash the hashcode to match
	 * @return the index of the item matching the hashcode or -1 if not found
	 */
	public static synchronized int indexOf(int hash, List<?> list)
	{
		Iterator<?> iterator = list.iterator();

		for (int i = 0; iterator.hasNext(); i++)
		{
			if (iterator.next().hashCode() == hash)
			{
				return i;
			}
		}

		return -1;
	}

	/**
	 * Utility method to move a list-item at a new position in the specified list<br/>
	 * This method will use the hashcode of the list-item to retrieve it against the list.
	 *
	 * @param list the {@link List}
	 * @param item the item
	 * @param index the position to move to
	 */
	public static synchronized <T> void move(final T item, int index, final List<T> list)
	{
		if (index < list.size())
		{
			list.add(index, list.remove(ListUtils.indexOf(item.hashCode(), list)));
		}
	}

	/**
	 * Utility class
	 */
	private ListUtils()
	{
	}
}
