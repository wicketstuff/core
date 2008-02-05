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
package org.apache.wicket.security.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Maps keys to lists of values and values to lists of keys. The whole concept
 * of key value is a bit vague here because each value is also a key. Consider
 * the following example: A maps to B and C, B maps to D. get(A) would return B
 * and C, get(B) would return A and D, get(C) would return A, get(D) would
 * return B. Each mapping is bidirectional.
 * 
 * @author marrink
 */
public class ManyToManyMap
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Iterator EMPTY_ITERATOR = new EmptyIterator();

	private Map mappings;

	/**
	 * Creates map with default initial size and load factor.
	 */
	public ManyToManyMap()
	{
		mappings = new HashMap();
	}

	/**
	 * Creates map with default load factor and specified initial size.
	 * 
	 * @param initialCapacity
	 */
	public ManyToManyMap(int initialCapacity)
	{
		mappings = new HashMap(initialCapacity);
	}

	/**
	 * Creates map with specified initial size and load factor. For more
	 * information about these see {@link HashMap}
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public ManyToManyMap(int initialCapacity, float loadFactor)
	{
		mappings = new HashMap(initialCapacity, loadFactor);
	}

	/**
	 * Adds a key value mapping in this map. Since this maps many to many
	 * relations no previous mappings will be overridden. The size of the map
	 * may or may not change depending on whether both objects are already
	 * present or not
	 * 
	 * @param left
	 * @param right
	 */
	public void add(Object left, Object right)
	{
		if (left == null)
			throw new NullPointerException("left must not be null.");
		if (right == null)
			throw new NullPointerException("right must not be null.");
		Set manys = (Set)mappings.get(left);
		if (manys == null)
			manys = new HashSet();
		manys.add(right);
		mappings.put(left, manys);
		manys = (Set)mappings.get(right);
		if (manys == null)
			manys = new HashSet();
		manys.add(left);
		mappings.put(right, manys);
	}

	/**
	 * Removes a many to many mapping between two objects. The size of the map
	 * may or may not change depending on on whether or not both objects have
	 * other mappings.
	 * 
	 * @param left
	 *            left side of the mapping
	 * @param right
	 *            right side of the mapping
	 * @return false if the mapping did not exist, true otherwise
	 */
	public boolean remove(Object left, Object right)
	{
		Set manys = (Set)mappings.get(left);
		if (manys != null)
		{
			if (manys.remove(right))
			{
				if (manys.isEmpty())
					mappings.remove(left);
				manys = (Set)mappings.get(right);
				manys.remove(left);
				if (manys.isEmpty())
					mappings.remove(right);
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove all mappings for an object. The size of the map will at least
	 * decrease by one (if the object is present) but possibly more.
	 * 
	 * @param leftOrRight
	 *            the left or right side of the many to many mapping
	 * @return the mappings that will be removed by this action
	 */
	public Set removeAllMappings(Object leftOrRight)
	{
		Set manys = (Set)mappings.remove(leftOrRight);
		if (manys != null)
		{
			Iterator it = manys.iterator();
			Set temp = null;
			Object next;
			while (it.hasNext())
			{
				next = it.next();
				temp = (Set)mappings.get(next);
				temp.remove(leftOrRight);
				if (temp.isEmpty())
					mappings.remove(next);
			}
		}
		return manys;
	}

	/**
	 * Gets the bidirectional mappings for this object.
	 * 
	 * @param left
	 * @return the many to many mappings for this object
	 */
	public Set get(Object left)
	{
		Set set = (Set)mappings.get(left);
		if (set == null)
			return Collections.EMPTY_SET;
		return Collections.unmodifiableSet(set);
	}

	/**
	 * The number of distinct objects that are mapped.
	 * 
	 * @return the number of distinct objects
	 */
	public int size()
	{
		return mappings.size();
	}

	/**
	 * Returns the number of keys mapped to a value.
	 * 
	 * @param value
	 * @return the number of keys mapped to this value
	 */
	public int numberOfmappings(Object value)
	{
		Set set = (Set)mappings.get(value);
		if (set == null)
			return 0;
		return set.size();
	}

	/**
	 * Check if this map contains a key.
	 * 
	 * @param key
	 *            a mapped object
	 * @return true if this map contains the key, false otherwise
	 */
	public boolean contains(Object key)
	{
		return mappings.containsKey(key);
	}

	/**
	 * Check if this map contains any mappings. If this map does is empty size
	 * will be 0.
	 * 
	 * @return true if no mappings are present, false otherwise
	 */
	public boolean isEmpty()
	{
		return mappings.isEmpty();
	}

	/**
	 * Removes all mappings.
	 */
	public void clear()
	{
		mappings.clear();
	}

	/**
	 * Returns an <tt>Iterator</tt> over every left and right hand mapping in
	 * this map. In no particular order.
	 * 
	 * @return an iterator over this map
	 */
	public Iterator iterator()
	{
		if (mappings.isEmpty())
			return EMPTY_ITERATOR;
		return mappings.keySet().iterator();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (obj instanceof ManyToManyMap)
			return mappings.equals(((ManyToManyMap)obj).mappings);
		return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return 37 * mappings.hashCode() + 1979;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return mappings.toString();
	}
}
