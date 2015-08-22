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
package org.wicketstuff.security.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Maps keys to lists of values and values to lists of keys. The whole concept of key value is a bit
 * vague here because each value is also a key. Consider the following example: A maps to B and C, B
 * maps to D. get(A) would return B and C, get(B) would return A and D, get(C) would return A,
 * get(D) would return B. Each mapping is bidirectional.
 * 
 * @author marrink
 */
public class ManyToManyMap<L, R>
{
	private final Map<L, Set<R>> lToRMappings;

	private final Map<R, Set<L>> rToLMappings;

	/**
	 * Creates map with default initial size and load factor.
	 */
	public ManyToManyMap()
	{
		lToRMappings = new HashMap<L, Set<R>>();
		rToLMappings = new HashMap<R, Set<L>>();
	}

	/**
	 * Creates map with default load factor and specified initial size.
	 * 
	 * @param initialCapacity
	 */
	public ManyToManyMap(int initialCapacity)
	{
		lToRMappings = new HashMap<L, Set<R>>(initialCapacity);
		rToLMappings = new HashMap<R, Set<L>>(initialCapacity);
	}

	/**
	 * Creates map with specified initial size and load factor. For more information about these see
	 * {@link HashMap}
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public ManyToManyMap(int initialCapacity, float loadFactor)
	{
		lToRMappings = new HashMap<L, Set<R>>(initialCapacity, loadFactor);
		rToLMappings = new HashMap<R, Set<L>>(initialCapacity, loadFactor);
	}

	/**
	 * Adds a key value mapping in this map. Since this maps many to many relations no previous
	 * mappings will be overridden. The size of the map may or may not change depending on whether
	 * both objects are already present or not
	 * 
	 * @param left
	 * @param right
	 */
	public void add(L left, R right)
	{
		if (left == null)
			throw new NullPointerException("left must not be null.");
		if (right == null)
			throw new NullPointerException("right must not be null.");

		Set<R> rights = lToRMappings.get(left);
		if (rights == null)
			rights = new HashSet<R>();
		rights.add(right);
		lToRMappings.put(left, rights);

		Set<L> lefts = rToLMappings.get(right);
		if (lefts == null)
			lefts = new HashSet<L>();
		lefts.add(left);
		rToLMappings.put(right, lefts);
	}

	/**
	 * Removes a many to many mapping between two objects. The size of the map may or may not change
	 * depending on on whether or not both objects have other mappings.
	 * 
	 * @param left
	 *            left side of the mapping
	 * @param right
	 *            right side of the mapping
	 * @return false if the mapping did not exist, true otherwise
	 */
	public boolean remove(L left, R right)
	{
		Set<R> rights = lToRMappings.get(left);
		if (rights != null)
		{
			if (rights.remove(right))
			{
				if (rights.isEmpty())
					lToRMappings.remove(left);

				Set<L> lefts = rToLMappings.get(right);
				lefts.remove(left);
				if (lefts.isEmpty())
					rToLMappings.remove(right);
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove all mappings for an object. The size of the map will at least decrease by one (if the
	 * object is present) but possibly more.
	 * 
	 * @param left
	 *            the left side of the many to many mapping
	 * @return the mappings that will be removed by this action
	 */
	public Set<R> removeAllMappingsForLeft(L left)
	{
		Set<R> rights = lToRMappings.remove(left);
		if (rights != null)
		{
			for (R curRight : rights)
			{
				Set<L> curLefts = rToLMappings.get(curRight);
				curLefts.remove(left);
				if (curLefts.isEmpty())
					rToLMappings.remove(curRight);
			}
		}
		return rights;
	}

	/**
	 * Remove all mappings for an object. The size of the map will at least decrease by one (if the
	 * object is present) but possibly more.
	 * 
	 * @param right
	 *            the right side of the many to many mapping
	 * @return the mappings that will be removed by this action
	 */
	public Set<L> removeAllMappingsForRight(R right)
	{
		Set<L> lefts = rToLMappings.remove(right);
		if (lefts != null)
		{
			for (L curLeft : lefts)
			{
				Set<R> curRights = lToRMappings.get(curLeft);
				curRights.remove(right);
				if (curRights.isEmpty())
					lToRMappings.remove(curLeft);
			}
		}
		return lefts;
	}

	/**
	 * Gets the bidirectional mappings for this object.
	 * 
	 * @param left
	 * @return the many to many mappings for this object
	 */
	public Set<R> getRight(L left)
	{
		Set<R> set = lToRMappings.get(left);
		if (set == null)
			return Collections.emptySet();
		return Collections.unmodifiableSet(set);
	}

	/**
	 * Gets the bidirectional mappings for this object.
	 * 
	 * @param right
	 * @return the many to many mappings for this object
	 */
	public Set<L> getLeft(R right)
	{
		Set<L> set = rToLMappings.get(right);
		if (set == null)
			return Collections.emptySet();
		return Collections.unmodifiableSet(set);
	}

	/**
	 * Returns the number of mapped values, left or right
	 * 
	 * @return the number of mapped values
	 */
	public int size()
	{
		return lToRMappings.size() + rToLMappings.size();
	}

	/**
	 * Returns the number of keys mapped to a value.
	 * 
	 * @param left
	 * @return the number of keys mapped to this value
	 */
	public int numberOfmappingsForLeft(L left)
	{
		Set<R> set = lToRMappings.get(left);
		if (set == null)
			return 0;
		return set.size();
	}

	/**
	 * Returns the number of keys mapped to a value.
	 * 
	 * @param right
	 * @return the number of keys mapped to this value
	 */
	public int numberOfmappingsForRight(R right)
	{
		Set<L> set = rToLMappings.get(right);
		if (set == null)
			return 0;
		return set.size();
	}

	/**
	 * Check if this map contains a key.
	 * 
	 * @param left
	 *            a mapped object
	 * @return true if this map contains the key, false otherwise
	 */
	public boolean containsLeft(L left)
	{
		return lToRMappings.containsKey(left);
	}

	/**
	 * Check if this map contains a key.
	 * 
	 * @param right
	 *            a mapped object
	 * @return true if this map contains the key, false otherwise
	 */
	public boolean containsRight(R right)
	{
		return rToLMappings.containsKey(right);
	}

	/**
	 * Check if this map contains any mappings. If this map does is empty size will be 0.
	 * 
	 * @return true if no mappings are present, false otherwise
	 */
	public boolean isEmpty()
	{
		return lToRMappings.isEmpty();
	}

	/**
	 * Removes all mappings.
	 */
	public void clear()
	{
		lToRMappings.clear();
		rToLMappings.clear();
	}

	/**
	 * Returns an <tt>Iterator</tt> over every left hand mapping in this map. In no particular
	 * order.
	 * 
	 * @return an iterator over this map
	 */
	public Iterator<L> leftIterator()
	{
		return lToRMappings.keySet().iterator();
	}

	/**
	 * Returns an <tt>Iterator</tt> over every rightt hand mapping in this map. In no particular
	 * order.
	 * 
	 * @return an iterator over this map
	 */
	public Iterator<R> rightIterator()
	{
		return rToLMappings.keySet().iterator();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ManyToManyMap<?, ?>)
		{
			ManyToManyMap<?, ?> other = (ManyToManyMap<?, ?>)obj;
			return lToRMappings.equals(other.lToRMappings) &&
				rToLMappings.equals(other.lToRMappings);
		}
		return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return 7 * rToLMappings.hashCode() ^ 37 * lToRMappings.hashCode() + 1979;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return lToRMappings.toString() + '\n' + rToLMappings.toString();
	}
}
