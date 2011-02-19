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

/**
 * <p>
 * An abstract base class for referring spring beans.
 * </p>
 * <p>
 * This class supports serialization. The referred spring bean does not get
 * serialized, after deserialization it is looked up again on the first access.
 * </p>
 * <p>
 * Subclasses must implement the {@link #getSupporter()} method used to locate
 * the {@link AbstractSpringReferenceSupporter} object. This class does not
 * depend on wicket or spring-web. So in theory subclasses can be used in
 * non-wicket, non-web spring applications too.
 * </p>
 * 
 * @param <T>
 *            type of the wrapped spring bean
 * 
 * @author akiraly
 */
public abstract class AbstractSpringReference<T> implements Serializable {
	private static final long serialVersionUID = 4097373492449915070L;

	private final Class<T> clazz;

	private String name;

	private final boolean clazzBasedOnlyLookup;

	private transient T instance;

	/**
	 * Constructor.
	 * 
	 * @param clazz
	 *            class of the wrapped spring bean, not null
	 * @param name
	 *            beanName of the wrapped spring bean, can be null
	 */
	protected AbstractSpringReference(Class<T> clazz, String name) {
		this.clazz = clazz;
		this.name = name;
		clazzBasedOnlyLookup = name == null;
	}

	/**
	 * Returns the referred spring bean. Lookup is made lazily on the first
	 * call.
	 * 
	 * @return referred spring bean
	 */
	public T get() {
		if (instance != null)
			return instance;

		getSupporter().findAndSetInstance(this);

		return instance;
	}

	/**
	 * @return object used to find the wrapped spring bean, not null
	 */
	protected abstract AbstractSpringReferenceSupporter getSupporter();

	/**
	 * @param instance
	 *            spring bean
	 */
	protected void setInstance(T instance) {
		this.instance = instance;
	}

	/**
	 * Can change during lookup if it was not set originally.
	 * 
	 * @return name of the spring bean, can be null
	 */
	protected String getName() {
		return name;
	}

	/**
	 * @param name
	 *            name of the spring bean
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @return class of the spring bean
	 */
	protected Class<T> getClazz() {
		return clazz;
	}

	/**
	 * @return true if the spring bean name was not given at construction time
	 */
	protected boolean isClazzBasedOnlyLookup() {
		return clazzBasedOnlyLookup;
	}
}
