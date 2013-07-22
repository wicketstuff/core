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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Utility class for {@link List}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class ListUtils
{
	/**
	 * Utility method to move a list-item at a new position<br/>
	 * This method will use the hashcode of the list-item to retrieve it against the list.
	 *
	 * @param list the {@link List}
	 * @param item the item
	 * @param index the position to move to
	 * @return a new {@link List} reflecting the new order
	 */
	public static <T> List<T> move(final List<T> list, final T item, int index)
	{
		List<T> l = new ArrayList<T>(list); //shadow copy the list, but not the mutable items, if any (refs will be kept)
		l.add(index, l.remove(ListUtils.indexOf(l, item.hashCode())));

		return l;
	}

	/**
	 * Gets the index of the item occurrence matching the specified hashcode.
	 * @param list the {@link List}
	 * @param hash the hashcode to match
	 * @return the index of the item matching the hashcode or -1 if not found
	 */
	public static int indexOf(List<?> list, int hash)
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
	 * Utility class
	 */
	private ListUtils()
	{
	}
}
