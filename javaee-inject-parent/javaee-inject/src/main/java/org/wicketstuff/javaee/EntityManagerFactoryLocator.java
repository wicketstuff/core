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

import jakarta.persistence.Persistence;

import org.apache.wicket.proxy.IProxyTargetLocator;

/**
 * Implementation of {@link IProxyTargetLocator} to locate Java EE 5 EntityManagerFactory
 * <p/>
 * To use this technique in a Wicket Page, just insert a line like<br/>
 * <p/>
 * private @PersistenceUnit(unitName="defaultPersistenceContext") EntityManagerFactory emf;
 * <p/>
 * The 'unitName' attribute is mandatory, and refers to the name of the persistence unit you have
 * declared in your persistence.xml file
 * 
 * @author Filippo Diotalevi
 */
public class EntityManagerFactoryLocator implements IProxyTargetLocator
{

	private static final long serialVersionUID = 1L;
	private String persistenceUnit;

	/**
	 * Constructor
	 * 
	 * @param pUnit
	 *            - persistence unit
	 */
	public EntityManagerFactoryLocator(String pUnit)
	{
		if (pUnit == null)
		{
			throw new IllegalArgumentException(
				"You must always specify an attribute 'unitName' for annotation @PersistenceUnit.");
		}
		persistenceUnit = pUnit;
	}

	/**
	 * @see org.apache.wicket.proxy.IProxyTargetLocator#locateProxyTarget()
	 */
	@Override
	public Object locateProxyTarget()
	{
		return Persistence.createEntityManagerFactory(persistenceUnit);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof EntityManagerFactoryLocator)
		{
			EntityManagerFactoryLocator other = (EntityManagerFactoryLocator)obj;
			return persistenceUnit.equals(other.persistenceUnit);
		}
		return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return persistenceUnit.hashCode();
	}

	public String getPersistenceUnit()
	{
		return persistenceUnit;
	}
}
