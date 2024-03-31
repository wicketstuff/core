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
package org.wicketstuff.minis.model.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.wicketstuff.minis.model.IDetachCodec;

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
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param codec
	 *            codec that will be used to transcode elements between attached and detached states
	 */
	public DetachableSet(final IDetachCodec<T> codec)
	{
		super(codec);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean add(final T e)
	{
		return getAttachedStore().add(e);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addAll(final Collection<? extends T> c)
	{
		return getAttachedStore().addAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear()
	{
		getAttachedStore().clear();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(final Object o)
	{
		return getAttachedStore().contains(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsAll(final Collection<?> c)
	{
		return getAttachedStore().containsAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<T> getAttachedStore()
	{
		return (Set<T>)super.getAttachedStore();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isEmpty()
	{
		return getAttachedStore().isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterator<T> iterator()
	{
		return getAttachedStore().iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean remove(final Object o)
	{
		return getAttachedStore().remove(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean removeAll(final Collection<?> c)
	{
		return getAttachedStore().removeAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean retainAll(final Collection<?> c)
	{
		return getAttachedStore().retainAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public int size()
	{
		return getAttachedStore().size();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] toArray()
	{
		return getAttachedStore().toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("hiding")
	public <T> T[] toArray(final T[] a)
	{
		return getAttachedStore().toArray(a);
	}
}
