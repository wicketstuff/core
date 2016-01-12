/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.springreference;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * <p>
 * An abstract base class for referring spring beans.
 * </p>
 * <p>
 * This class supports serialization. The referred spring bean does not get serialized, after
 * deserialization it is looked up again on the first access.
 * </p>
 * <p>
 * Subclasses must implement the {@link #getSupporter()} method used to locate the
 * {@link AbstractSpringReferenceSupporter} object. This class does not depend on wicket or
 * spring-web. So in theory subclasses can be used in non-wicket, non-web spring applications too.
 * </p>
 * 
 * @param <T>
 *            type of the wrapped spring bean
 * 
 * @author akiraly
 */
public abstract class AbstractSpringReference<T> implements Serializable, Cloneable
{
	private static final long serialVersionUID = 4097373492449915070L;

	private final Class<T> clazz;

	private String name;

	private final boolean clazzBasedOnlyLookup;

	private transient WeakReference<T> instanceRef;

	/**
	 * Constructor.
	 * 
	 * @param clazz
	 *            class of the wrapped spring bean, not null
	 * @param name
	 *            beanName of the wrapped spring bean, can be null
	 */
	protected AbstractSpringReference(Class<T> clazz, String name)
	{
		this.clazz = clazz;
		this.name = name;
		clazzBasedOnlyLookup = name == null;
	}

	/**
	 * Returns the referred spring bean. Lookup is made lazily on the first call.
	 * 
	 * @return referred spring bean or throws a {@link RuntimeException} if the bean could not be
	 *         found.
	 */
	public T get()
	{
		WeakReference<T> ref = instanceRef;
		T instance;
		if (ref == null || (instance = ref.get()) == null)
			instance = getSupporter().findAndSetInstance(this);

		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractSpringReference<T> clone()
	{
		try
		{
			return (AbstractSpringReference<T>)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// should not happen
			throw new IllegalStateException(e);
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + (clazzBasedOnlyLookup ? 1231 : 1237);
		AbstractSpringReferenceSupporter supporter = getSupporter();
		result = prime * result + ((supporter == null) ? 0 : supporter.hashCode());
		// name field only matters if not clazzBasedOnlyLookup
		if (!clazzBasedOnlyLookup)
			result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractSpringReference<?> other = (AbstractSpringReference<?>)obj;
		if (clazz == null)
		{
			if (other.clazz != null)
				return false;
		}
		else if (!clazz.equals(other.clazz))
			return false;
		if (clazzBasedOnlyLookup != other.clazzBasedOnlyLookup)
			return false;
		// name field only matters if not clazzBasedOnlyLookup
		if (!clazzBasedOnlyLookup)
		{
			if (name == null)
			{
				if (other.name != null)
					return false;
			}
			else if (!name.equals(other.name))
				return false;
		}
		if (getSupporter() != other.getSupporter())
			return false;
		return true;
	}

	/**
	 * @return object used to find the wrapped spring bean, not null
	 */
	protected abstract AbstractSpringReferenceSupporter getSupporter();

	/**
	 * @param instanceRef
	 *            weak reference to the spring bean
	 */
	protected void setInstanceRef(WeakReference<T> instanceRef)
	{
		this.instanceRef = instanceRef;
	}

	/**
	 * Can change during lookup if it was not set originally.
	 * 
	 * @return name of the spring bean, can be null
	 */
	protected String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            name of the spring bean
	 */
	protected void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return class of the spring bean
	 */
	protected Class<T> getClazz()
	{
		return clazz;
	}

	/**
	 * @return true if the spring bean name was not given at construction time
	 */
	protected boolean isClazzBasedOnlyLookup()
	{
		return clazzBasedOnlyLookup;
	}
}
