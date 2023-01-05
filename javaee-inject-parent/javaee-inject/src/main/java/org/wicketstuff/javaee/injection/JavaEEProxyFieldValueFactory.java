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
package org.wicketstuff.javaee.injection;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.persistence.PersistenceUnit;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.wicket.injection.IFieldValueFactory;
import org.apache.wicket.proxy.IProxyTargetLocator;
import org.apache.wicket.proxy.LazyInitProxyFactory;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.javaee.EntityManagerFactoryLocator;
import org.wicketstuff.javaee.JavaEEBeanLocator;
import org.wicketstuff.javaee.JndiObjectLocator;
import org.wicketstuff.javaee.naming.IJndiNamingStrategy;
import org.wicketstuff.javaee.naming.StandardJndiNamingStrategy;

/**
 * {@link IFieldValueFactory} that creates proxies of EJBs based on the {@link javax.ejb.EJB}
 * annotation applied to a field.
 * 
 * @author Filippo Diotalevi
 */
public class JavaEEProxyFieldValueFactory implements IFieldValueFactory
{

	private final ConcurrentHashMap<IProxyTargetLocator, Object> cache = new ConcurrentHashMap<>();
	private final IJndiNamingStrategy namingStrategy;

	/**
	 * Constructor
	 */
	public JavaEEProxyFieldValueFactory()
	{
		this(new StandardJndiNamingStrategy());
	}

	/**
	 * Constructor
	 * 
	 * @param namingStrategy
	 *            - naming strategy
	 */
	public JavaEEProxyFieldValueFactory(IJndiNamingStrategy namingStrategy)
	{
		this.namingStrategy = namingStrategy;
	}

	/**
	 * @see org.apache.wicket.injection.IFieldValueFactory#getFieldValue(java.lang.reflect.Field,
	 *      java.lang.Object)
	 */
	@Override
	public Object getFieldValue(Field field, Object fieldOwner)
	{
		IProxyTargetLocator locator = getProxyTargetLocator(field);
		return getCachedProxy(field, locator);
	}

	/**
	 * @see org.apache.wicket.injection.IFieldValueFactory#supportsField(java.lang.reflect.Field)
	 */
	@Override
	public boolean supportsField(Field field)
	{
		return field.isAnnotationPresent(EJB.class) || field.isAnnotationPresent(Resource.class) ||
			field.isAnnotationPresent(PersistenceUnit.class);
	}

	/**
	 * Gets the corresponding object (or its proxy) for the field from the cache, or if it's not
	 * cached, then returns a newly created proxy or the object itself.
	 * 
	 * @param field
	 *            The field, in which we're trying to inject
	 * @param locator
	 *            The locator, which will locate the corresponding object for the field
	 * @return The injectable value
	 */
	private Object getCachedProxy(Field field, IProxyTargetLocator locator)
	{
		Class<?> type = field.getType();
		if (locator == null)
		{
			return null;
		}

		// if the field contains the "stateful" description, or the field itself
		// is a no-interfaceview Stateful bean
		if ((field.isAnnotationPresent(EJB.class) &&
			field.getAnnotation(EJB.class).description().equals("stateful") || field.getType()
			.isAnnotationPresent(Stateful.class)))
		{
			// creates a session if there wasn't already

			HttpServletRequest httpServletRequest = (HttpServletRequest)RequestCycle.get()
				.getRequest()
				.getContainerRequest();

			HttpSession session = httpServletRequest.getSession();

			// check if it's already cached
			Object retValue = session.getAttribute(type.getName());
			if (retValue == null)
			{
				if (!Modifier.isFinal(type.getModifiers()))
				{
					retValue = LazyInitProxyFactory.createProxy(type, locator);
				}
				else
				{
					retValue = locator.locateProxyTarget();
				}
				session.setAttribute(type.getName(), retValue);
			}
			return retValue;
		}
		else
		{

			if (cache.containsKey(locator))
			{
				return cache.get(locator);
			}
			if (!Modifier.isFinal(type.getModifiers()))
			{
				Object proxy = LazyInitProxyFactory.createProxy(type, locator);
				cache.put(locator, proxy);
				return proxy;
			}
			else
			{
				Object value = locator.locateProxyTarget();
				cache.put(locator, value);
				return value;
			}
		}
	}

	private IProxyTargetLocator getProxyTargetLocator(Field field)
	{
		if (field.isAnnotationPresent(EJB.class))
		{
			return new JavaEEBeanLocator(field.getAnnotation(EJB.class).name(), field.getType(),
				namingStrategy);
		}

		if (field.isAnnotationPresent(PersistenceUnit.class))
		{
			return new EntityManagerFactoryLocator(field.getAnnotation(PersistenceUnit.class)
				.unitName());
		}

		if (field.isAnnotationPresent(Resource.class))
		{
			return new JndiObjectLocator(field.getAnnotation(Resource.class).name(),
				field.getType());
		}
		// else
		return null;
	}
}
