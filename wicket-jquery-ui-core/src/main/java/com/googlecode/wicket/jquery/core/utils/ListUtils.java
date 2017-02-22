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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Utility class for {@link List}({@code s})
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class ListUtils
{
	private static final int MAX = 20;

	/**
	 * Utility class
	 */
	private ListUtils()
	{
	}

	/**
	 * Returns a pseudo-random {@code int} value between 0 and the specified bound (exclusive)
	 * 
	 * @param bound the maximum bound (exclusive)
	 * @return a pseudo-random {@code int}
	 */
	static int random(int bound)
	{
		return new Random((long) (Math.random() * 1000000L)).nextInt(bound);
	}

	/**
	 * Gets a random object from the supplied array
	 * 
	 * @param array the array
	 * @param <T> the object type
	 * @return {@code null} if the supplied array is empty
	 */
	public static <T> T random(T[] array)
	{
		if (array.length > 0)
		{
			return array[ListUtils.random(array.length)];
		}

		return null;
	}

	/**
	 * Gets a random object from the supplied {@link List}
	 * 
	 * @param list the {@link List}
	 * @param <T> the object type
	 * @return {@code null} if the supplied list is empty
	 */
	public static <T> T random(List<T> list)
	{
		if (!list.isEmpty())
		{
			return list.get(ListUtils.random(list.size()));
		}

		return null;
	}

	/**
	 * Empty a {@link List}
	 * 
	 * @param list the list to empty
	 */
	public static void empty(List<?> list)
	{
		Iterator<?> iterator = list.iterator();

		while (iterator.hasNext())
		{
			iterator.next();
			iterator.remove();
		}
	}

	/**
	 * Gets the list-item matching the given hash against the list
	 *
	 * @param <T> the object type
	 * @param hash the hashcode to match
	 * @param list the {@link List} to search in
	 * @return the list-item or {@code null} if not found
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
	 *
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
	 * Utility method to move a list-item at a new position in the specified list<br>
	 * This method will use the hashcode of the list-item to retrieve it against the list.
	 *
	 * @param <T> the object type
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
	 * Returns a sub list of items of type T having their textual representation (toString()) containing the search criteria<br>
	 * The max size of the sub list is {@link #MAX}
	 *
	 * @param <T> the type
	 * @param search search criteria
	 * @param list reference list
	 * @return the sub list
	 */
	public static <T> List<T> contains(String search, List<T> list)
	{
		return ListUtils.contains(search, list, MAX);
	}

	/**
	 * Returns a sub list of items of type T having their textual representation (toString()) containing the search criteria<br>
	 *
	 * @param <T> the type
	 * @param search search criteria
	 * @param list reference list
	 * @param max max size of the sub list to be returned
	 * @return the sub list
	 */
	public static <T> List<T> contains(String search, List<T> list, int max)
	{
		List<T> choices = new ArrayList<T>();
		String searchLowerCase = search.toLowerCase();

		int count = 0;
		for (T choice : list)
		{
			if (choice.toString().toLowerCase().contains(searchLowerCase))
			{
				choices.add(choice);

				if (++count == max)
				{
					break;
				}
			}
		}

		return choices;
	}

	/**
	 * Returns a sub list of items of type T having their textual representation (toString()) starting with the search criteria<br>
	 * The max size of the sub list is {@link #MAX}
	 *
	 * @param <T> the type
	 * @param search search criteria
	 * @param list reference list
	 * @return the sub list
	 */
	public static <T> List<T> startsWith(String search, List<T> list)
	{
		return ListUtils.startsWith(search, list, MAX);
	}

	/**
	 * Returns a sub list of items of type T having their textual representation (toString()) starting with the search criteria<br>
	 *
	 * @param <T> the type
	 * @param search search criteria
	 * @param list reference list
	 * @param max max size of the sub list to be returned
	 * @return the sub list
	 */
	public static <T> List<T> startsWith(String search, List<T> list, int max)
	{
		List<T> choices = new ArrayList<T>();
		String searchLowerCase = search.toLowerCase();

		int count = 0;
		for (T choice : list)
		{
			if (choice.toString().toLowerCase().startsWith(searchLowerCase))
			{
				choices.add(choice);

				if (++count == max)
				{
					break;
				}
			}
		}

		return choices;
	}

	/**
	 * Excludes items from a {@code List} and return a new {@code List}
	 * 
	 * @param list the original {@link List}
	 * @param items items to remove
	 * @return a new {@code List} without excluded items
	 */
	public static List<String> exclude(List<String> list, String... items)
	{
		return ListUtils.exclude(list, Arrays.asList(items));
	}

	/**
	 * Excludes items from a {@code List} and return a new {@code List}
	 * 
	 * @param list the original {@link List}
	 * @param items items to remove
	 * @return a new {@code List} without excluded items
	 */
	public static List<String> exclude(List<String> list, List<String> items)
	{
		List<String> copy = new ArrayList<String>(list);

		for (String item : items)
		{
			copy.remove(item);
		}

		return copy;
	}
}
