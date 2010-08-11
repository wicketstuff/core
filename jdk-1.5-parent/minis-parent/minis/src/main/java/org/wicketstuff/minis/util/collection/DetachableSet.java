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
package org.wicketstuff.minis.util.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.wicketstuff.minis.util.IDetachCodec;

/**
 * A set that can be converted between an attached and detached state.
 * 
 * @see AbstractDetachableCollection
 * 
 * @author Igor Vaynberg (ivaynberg)
 * 
 * @param <T>
 */
public abstract class DetachableSet<T> extends AbstractDetachableCollection<T> implements Set<T>
{

	/**
	 * Constructor
	 * 
	 * @param codec
	 *            codec that will be used to transcode elements between attached
	 *            and detached states
	 */
	public DetachableSet(IDetachCodec<T> codec)
	{
		super(codec);
	}

	/**
	 * @see org.wicketstuff.minis.util.collection.AbstractDetachableCollection#getAttachedStore()
	 */
	public Set<T> getAttachedStore()
	{
		return (Set<T>)super.getAttachedStore();
	}

	/**
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(T e)
	{
		return getAttachedStore().add(e);
	}

	/**
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends T> c)
	{
		return getAttachedStore().addAll(c);
	}

	/**
	 * @see java.util.Collection#clear()
	 */
	public void clear()
	{
		getAttachedStore().clear();
	}

	/**
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object o)
	{
		return getAttachedStore().contains(o);
	}

	/**
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c)
	{
		return getAttachedStore().containsAll(c);
	}

	/**
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty()
	{

		return getAttachedStore().isEmpty();
	}

	/**
	 * @see java.util.Collection#iterator()
	 */
	public Iterator<T> iterator()
	{
		return getAttachedStore().iterator();
	}

	/**
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object o)
	{

		return getAttachedStore().remove(o);
	}

	/**
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c)
	{

		return getAttachedStore().removeAll(c);
	}

	/**
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c)
	{
		return getAttachedStore().retainAll(c);
	}

	/**
	 * @see java.util.Collection#size()
	 */
	public int size()
	{

		return getAttachedStore().size();
	}

	/**
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray()
	{

		return getAttachedStore().toArray();
	}

	/**
	 * @see java.util.Collection#toArray(T[])
	 */
	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a)
	{

		return getAttachedStore().toArray(a);
	}


}
