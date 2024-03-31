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

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import org.apache.wicket.model.IDetachable;
import org.wicketstuff.minis.model.IDetachCodec;

/**
 * A collection that can be converted between attached and detached state via calls to
 * {@link #attach()} and {@link #detach()}. Elements are converted between the two states using the
 * specified {@link IDetachCodec}.
 *
 * This collection allows the use of real objects, and yet has the convenience of a small session
 * footprint.
 *
 * If the collection is detached, invocation of any method from {@link Collection} will cause this
 * collection to be attached.
 *
 * NOTICE: During the conversion to either state N method calls are invoked on the
 * {@link IDetachCodec}, one for each element in the collection. This can cause a performance
 * problem in certain situations.
 *
 * Example
 *
 * <pre>
 *
 * class SelectUsersPanel extends Panel {
 *
 *   // codec to transcode user object to and from its detached state
 *   private static final IDetachCodec&lt;User&gt; userCodec=new IDetachCodec&lt;User&gt; {
 *     public Serializable detach(User object) { return object.getId(); }
 *     public User attach(Serializable object) { return UserDao.get().userForId(object); }
 *   }
 *
 *   // collection used to store selected user objects
 *   private final DetachableHashSet&lt;User&gt; selected=new DetachableHashSet&lt;User&gt;(userCodec);
 *
 *    protected void onDetach() {
 *      // this will shrink the size of selected collection before the page is stored in session.
 *      // the collection will be attached automatically when some method is invoked on it.
 *      selected.detach();
 *      super.onDetach();
 *    }
 *
 *    public SelectUsersPanel (String id) {
 * 		super(id);
 * 	    Form form=new Form(&quot;form&quot;);
 *      add(form);
 * 	    CheckGroup checked=new CheckGroup(&quot;checked&quot;, new PropertyModel(this, &quot;selected&quot;));
 *      form.add(checked);
 *      checked.add(new DataView(&quot;users&quot;, new UsersDataProvider()) {
 *        protected void populateItem(Item item) {
 *          item.add(new Check(&quot;user&quot;, item.getModel()));
 *          item.add(new Label(&quot;username&quot;, new PropertyModel(item.getModel(), &quot;username&quot;)));
 *        }
 *      }
 * 	    ...
 *    }
 *
 * </pre>
 *
 * @author Igor Vaynberg (ivaynberg)
 *
 * @param <T>
 */
public abstract class AbstractDetachableCollection<T> implements Collection<T>, IDetachable
{
	private static final long serialVersionUID = 1L;

	private final IDetachCodec<T> codec;
	private transient boolean attached = false;
	private Serializable[] detachedStore;
	private Collection<T> attachedStore;

	/**
	 * Constructor
	 *
	 * @param codec
	 *            codec that will be used to transcode elements between attached and detached states
	 */
	public AbstractDetachableCollection(final IDetachCodec<T> codec)
	{
		if (codec == null)
			throw new IllegalArgumentException("Argument `codec` cannot be null");
		this.codec = codec;
	}


	/**
	 * Converts the collection into its attached state. If the collection is already attached this
	 * is a noop.
	 */
	public final void attach()
	{
		if (!attached)
		{
			attachedStore = newAttachedStore();
			if (detachedStore != null)
				for (final Serializable detached : detachedStore)
				{
					final T object = codec.attach(detached);
					attachedStore.add(object);
				}
			attached = true;
			detachedStore = null;
		}
	}

	/**
	 * Converts the collection into a detached state. If the collection is already detached this is
	 * a noop.
	 */
	public final void detach()
	{
		if (attached)
		{
			if (!attachedStore.isEmpty())
			{
				detachedStore = new Serializable[attachedStore.size()];
				int idx = 0;
				for (final T object : attachedStore)
					detachedStore[idx++] = codec.detach(object);
			}
			attachedStore = null;
			attached = false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (obj == this)
			return true;
		else if (obj == null)
			return false;
		else if (obj instanceof AbstractDetachableCollection)
		{
			final AbstractDetachableCollection<?> other = (AbstractDetachableCollection<?>)obj;
			return getAttachedStore().equals(other.getAttachedStore());
		}
		else if (obj instanceof Collection)
		{
			final Collection<?> other = (Collection<?>)obj;
			return other.equals(getAttachedStore());
		}
		else
			return false;
	}

	/**
	 * Returns collection used to store elements in attached state. If this collection is currently
	 * detached it will be attached.
	 *
	 * @return collection used to store elements in attached state
	 */
	protected Collection<T> getAttachedStore()
	{
		if (!attached)
			attach();
		return attachedStore;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		return getAttachedStore().hashCode();
	}

	/**
	 * Creates a collection used to store elements in attached state
	 *
	 * @return collection used to store elements in attached state
	 */
	protected abstract Collection<T> newAttachedStore();

	private void writeObject(final java.io.ObjectOutputStream out) throws IOException
	{
		if (attached)
			throw new IllegalStateException(
				"Detachable collection `" +
					getClass().getName() +
					"` is being serialized in its attached state. detach() must be invoked before any serialization attempt.");
		out.defaultWriteObject();
	}
}
