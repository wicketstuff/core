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
package org.wicketstuff.javaee;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.proxy.IProxyTargetLocator;
import org.apache.wicket.util.lang.Objects;

/**
 * Implementation of {@link IProxyTargetLocator} to locate object using JNDI
 * <p/>
 * To use this technique in a Wicket Page, just insert a line like<br/>
 * <p/>
 * private @Resource(name="referenceName") YouClass obj;
 * <p/>
 * The 'referenceName' attribute is mandatory, and refers to the name of the object as declared in
 * the web.xml file
 * 
 * @author Filippo Diotalevi
 */
public class JndiObjectLocator implements IProxyTargetLocator
{

	private static final long serialVersionUID = 1L;
	private Class<?> beanType;
	private String beanName;

	/**
	 * Constructor
	 * 
	 * @param beanId
	 *            bean name
	 * @param beanType
	 *            bean class
	 */
	public JndiObjectLocator(String beanId, Class<?> beanType)
	{
		if (beanType == null)
		{
			throw new IllegalArgumentException("[beanType] argument cannot be null");
		}
		if (beanId == null)
		{
			throw new IllegalArgumentException("[beanId] argument cannot be null");
		}

		this.beanType = beanType;
		beanName = beanId;
	}

	/**
	 * @see org.apache.wicket.proxy.IProxyTargetLocator#locateProxyTarget()
	 */
	@Override
	public Object locateProxyTarget()
	{
		return lookup(beanName, beanType);
	}

	private static Object lookup(String name, Class<?> type)
	{
		String lookupName = "java:comp/env/" + name;
		InitialContext ic;
		try
		{
			ic = new InitialContext();
			return ic.lookup(lookupName);
		}
		catch (NamingException e)
		{
			String errorMessage = "Could not locate resource of class [[" + type +
				"]] and name [[" + name + "]] ";
			throw new RuntimeException(errorMessage, e);
		}
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof JndiObjectLocator)
		{
			JndiObjectLocator other = (JndiObjectLocator)obj;
			return beanType.equals(other.beanType) && Objects.equal(beanName, other.beanName);
		}
		return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		int hashcode = beanType.hashCode();
		if (beanName != null)
		{
			hashcode = hashcode + (127 * beanName.hashCode());
		}
		return hashcode;
	}

	public String getBeanName()
	{
		return beanName;
	}

	public Class<?> getBeanType()
	{
		return beanType;
	}
}
