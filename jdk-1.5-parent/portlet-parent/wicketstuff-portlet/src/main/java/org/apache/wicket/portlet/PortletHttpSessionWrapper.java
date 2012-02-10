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

/**
 * Proxy for a Servlet HttpSession to attach to a PortletSession, providing only access to
 * PORTLET_SCOPE session attributes and hiding the APPLICATION_SCOPE attributes from the Servlet. <br/>
 * This Proxy can be used to isolate two instances of the same Portlet dispatching to Servlets so
 * they don't overwrite or read each others session attributes. <br/>
 * Caveat: APPLICATION_SCOPE sessions attributes cannot be used anymore (directly) for inter-portlet
 * communication, or when using Servlets directly which also need to "attach" to the PORTLET_SCOPE
 * session attributes.<br/>
 * The {@link org.apache.portals.bridges.util.PortletWindowUtils} class can help out with that
 * though. <br/>
 * Note: copied and adapted from the Apache Portal Bridges Common project
 * 
 * @author <a href="mailto:ate@douma.nu">Ate Douma</a>
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
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
		final String portletWindowId)
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
		final Object proxy = Proxy.newProxyInstance(servletSession.getClass().getClassLoader(),
			interfaces.toArray(new Class[interfaces.size()]), new PortletHttpSessionWrapper(
				request.getSession(), portletWindowNamespace));
		return (HttpSession)proxy;
	}

	private final HttpSession servletSession;
	private final String portletWindowPrefix;

	private PortletHttpSessionWrapper(final HttpSession servletSession,
		final String portletWindowPrefix)
	{
		this.servletSession = servletSession;
		this.portletWindowPrefix = portletWindowPrefix;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Object invoke(final Object proxy, final Method m, final Object[] args) throws Throwable
	{
		if (("getAttribute".equals(m.getName()) || "getValue".equals(m.getName())) &&
			args.length == 1 && args[0] instanceof String)
			return servletSession.getAttribute(portletWindowPrefix + (String)args[0]);

		if (("setAttribute".equals(m.getName()) || "putValue".equals(m.getName())) &&
			args.length == 2 && args[0] instanceof String)
		{
			servletSession.setAttribute(portletWindowPrefix + (String)args[0], args[1]);
			return null;
		}

		if (("removeAttribute".equals(m.getName()) || "removeValue".equals(m.getName())) &&
			args.length == 1 && args[0] instanceof String)
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
}
