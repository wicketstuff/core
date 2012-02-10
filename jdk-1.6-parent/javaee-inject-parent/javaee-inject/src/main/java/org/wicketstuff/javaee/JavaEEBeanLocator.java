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
import org.wicketstuff.javaee.naming.IJndiNamingStrategy;

/**
 * Implementation of {@link IProxyTargetLocator} to locate ejbs using Java EE 5 resource injection.
 * To use this technique in a Wicket Page, annotate a instance variable with
 * 
 * @author Filippo Diotalevi
 * @EJB, e.g.<br/>
 *       <p/>
 *       private
 * @EJB(name="ejb/myejb") org.acme.MyEjb myejb
 *                        <p/>
 *                        If the 'name' attribute is specified, the {@link JavaEEBeanLocator} will
 *                        search in the JNDI registry for an EJB named 'java:comp/env/&lt;name&gt;'
 *                        (in the example: 'java:comp/env/ejb/myejb')
 *                        <p/>
 *                        If the 'name' attribute is not specified the {@link JavaEEBeanLocator}
 *                        will search in the JNDI registry for an EJB named
 *                        'java:comp/env/&lt;complete-class-name-of-the-ejb&gt;' (in the example:
 *                        'java:comp/env/com.acme.MyEjb)
 */
public class JavaEEBeanLocator implements IProxyTargetLocator
{

	private static final long serialVersionUID = 1L;
	private String beanName;
	private Class<?> beanType;
	private IJndiNamingStrategy namingStrategy;

	/**
	 * Constructor
	 * 
	 * @param beanId
	 *            bean name
	 * @param beanType
	 *            bean class
	 * @param namingStrategy
	 *            - naming strategy
	 */
	public JavaEEBeanLocator(String beanId, Class<?> beanType, IJndiNamingStrategy namingStrategy)
	{
		if (beanType == null)
		{
			throw new IllegalArgumentException("[beanType] argument cannot be null");
		}
		this.beanType = beanType;
		beanName = beanId;
		this.namingStrategy = namingStrategy;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof JavaEEBeanLocator)
		{
			JavaEEBeanLocator other = (JavaEEBeanLocator)obj;
			return beanType.equals(other.beanType) && Objects.equal(beanName, other.beanName);
		}
		return false;
	}

	public String getBeanName()
	{
		return beanName;
	}

	public Class<?> getBeanType()
	{
		return beanType;
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

	/**
	 * @see org.apache.wicket.proxy.IProxyTargetLocator#locateProxyTarget()
	 */
	@Override
	public Object locateProxyTarget()
	{
		if (beanName != null && beanName.length() > 0)
		{
			return lookupEjb(beanName, beanType);
		}
		else
		{
			return lookupEjb(null, beanType);
		}
	}

	private String calculateName(String name, Class<?> type)
	{
		return namingStrategy.calculateName(name, type);
	}

	private Object lookupEjb(String name, Class<?> type)
	{
		String lookupName = calculateName(name, type);
		InitialContext ic;
		try
		{
			ic = new InitialContext();
			return ic.lookup(lookupName);
		}
		catch (NamingException e)
		{
			String errorMessage = "Could not locate ejb of class [[" + type + "]] ";
			if (name != null && name.length() > 0)
			{
				errorMessage += "and name [[" + name + "]] ";
			}
			throw new RuntimeException(errorMessage, e);
		}
	}
}
