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
package org.apache.wicket.portlet;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * <p>
 * Proxy for a Servlet HttpSession to attach to a PortletSession, providing only access to
 * PORTLET_SCOPE session attributes and hiding the APPLICATION_SCOPE attributes from the Servlet. <br/>
 * This Proxy can be used to isolate two instances of the same Portlet dispatching to Servlets so
 * they don't overwrite or read each others session attributes.
 * </p>
 * <p>
 * Note: If an APPLICATION_SCOPE session attribute is going to be stored with a name which does
 * not start with a wicket prefix (look at
 * {@link WebApplication#getSessionAttributePrefix(org.apache.wicket.request.http.WebRequest, String)}
 * ) then the attribute will be stored normally as APPLICATION_SCOPED session attribute and this
 * attribute will be accessible from every wicket portlet.
 * </p>
 * 
 * @author <a href="mailto:ate@douma.nu">Ate Douma</a>
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 * @author Konstantinos Karavitis
 */
public class PortletHttpSessionWrapper implements InvocationHandler
{
	private static class NamespacedNamesEnumeration implements Enumeration<String>
	{
		private final Enumeration<String> namesEnumeration;
		private final String namespace;

		private String nextName;
		private boolean isDone;

		public NamespacedNamesEnumeration(final Enumeration<String> namesEnumeration,
			final String namespace)
		{
			this.namesEnumeration = namesEnumeration;
			this.namespace = namespace;
			hasMoreElements();
		}

		public boolean hasMoreElements()
		{
			if (isDone)
				return false;

			if (nextName == null)
			{
				while (namesEnumeration.hasMoreElements())
				{
					final String name = namesEnumeration.nextElement();
					if (name.startsWith(namespace))
					{
						nextName = name.substring(namespace.length());
						break;
					}
				}
				isDone = nextName == null;
			}
			return !isDone;
		}

		public String nextElement()
		{
			if (isDone)
				throw new NoSuchElementException();
			final String name = nextName;
			nextName = null;
			return name;
		}
	}

	public static HttpSession createProxy(final HttpServletRequest request,
		final String portletWindowId, final String wicketSessionAttributePrefix)
	{
		final String portletWindowNamespace = "javax.portlet.p." + portletWindowId;
		final HttpSession servletSession = request.getSession();
		final Set<Class<?>> interfaces = new HashSet<Class<?>>();
		interfaces.add(HttpSession.class);
		Class<?> current = servletSession.getClass();
		while (current != null)
			try
			{
				final Class<?>[] currentInterfaces = current.getInterfaces();
				for (final Class<?> currentInterface : currentInterfaces)
					interfaces.add(currentInterface);
				current = current.getSuperclass();
			}
			catch (final Exception ex)
			{
				current = null;
			}

		return (HttpSession)Proxy.newProxyInstance(servletSession.getClass().getClassLoader(),
			interfaces.toArray(new Class[interfaces.size()]),
			new PortletHttpSessionWrapper(request.getSession(), portletWindowNamespace,
				wicketSessionAttributePrefix));

	}

	private final HttpSession servletSession;
	private final String portletWindowPrefix;
	private final String wicketSessionAttributePrefix;

	private PortletHttpSessionWrapper(final HttpSession servletSession,
		final String portletWindowPrefix, final String wicketSessionAttributePrefix)
	{
		this.servletSession = servletSession;
		this.portletWindowPrefix = portletWindowPrefix;
		this.wicketSessionAttributePrefix = wicketSessionAttributePrefix;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object invoke(final Object proxy, final Method m, final Object[] args) throws Throwable
	{	
		if (("getAttribute".equals(m.getName()) || "getValue".equals(m.getName()))
			&& args.length == 1 && args[0] instanceof String)
		{
			// fix for #488 issue : https://github.com/wicketstuff/core/issues/488
			return servletSession.getAttribute(fixAttributeName((String)args[0]));
		}

		if (("setAttribute".equals(m.getName()) || "putValue".equals(m.getName()))
			&& args.length == 2 && args[0] instanceof String)
		{
			// fix for #488 issue : https://github.com/wicketstuff/core/issues/488
			servletSession.setAttribute(fixAttributeName((String)args[0]), args[1]);
			return null;
		}

		if (("removeAttribute".equals(m.getName()) || "removeValue".equals(m.getName()))
			&& args.length == 1 && args[0] instanceof String)
		{
			servletSession.removeAttribute(portletWindowPrefix + (String)args[0]);
			return null;
		}

		if ("getAttributeNames".equals(m.getName()) && args == null)
			return new NamespacedNamesEnumeration(servletSession.getAttributeNames(),
				portletWindowPrefix);

		if ("getValueNames".equals(m.getName()) && args == null)
		{
			final List<String> list = new ArrayList<String>();
			for (final Enumeration<String> en = new NamespacedNamesEnumeration(
				servletSession.getAttributeNames(), portletWindowPrefix); en.hasMoreElements();)
				list.add(en.nextElement());
			return list.toArray(new String[list.size()]);
		}
		return m.invoke(servletSession, args);
	}
	
	/***
	 * fix for #488 issue : https://github.com/wicketstuff/core/issues/488
	 * @return session attribute fixed name
	 * */
	private String fixAttributeName(String attributeName)
	{
		if (attributeName.startsWith(wicketSessionAttributePrefix))
		{
			attributeName = portletWindowPrefix + attributeName;
		}
		return attributeName;
	}
}
