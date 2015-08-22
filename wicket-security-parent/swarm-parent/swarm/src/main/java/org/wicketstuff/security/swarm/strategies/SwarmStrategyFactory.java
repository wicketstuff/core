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
package org.wicketstuff.security.swarm.strategies;

import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.ISecurePage;
import org.wicketstuff.security.hive.Hive;
import org.wicketstuff.security.hive.authorization.permissions.ComponentPermission;
import org.wicketstuff.security.strategies.StrategyFactory;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * A factory to return new {@link SwarmStrategy}s.
 * 
 * @author marrink
 */
public class SwarmStrategyFactory implements StrategyFactory
{
	private final Class<? extends ISecureComponent> secureClass;

	private Object hiveQueen;

	/**
	 * Constructs a new factory. All the strategies will require {@link ISecurePage}s to have access
	 * rights.
	 * 
	 * @param hiveQueen
	 *            the key to get the {@link Hive}
	 */
	public SwarmStrategyFactory(Object hiveQueen)
	{
		this(ISecurePage.class, hiveQueen);
	}

	/**
	 * Constructs a new factory.
	 * 
	 * @param secureClass
	 *            instances of this class will be required to have access rights.
	 * @param hiveQueen
	 *            hiveQueen the key to get the {@link Hive}
	 */
	public SwarmStrategyFactory(Class<? extends ISecureComponent> secureClass, Object hiveQueen)
	{
		this.secureClass = secureClass;
		this.hiveQueen = hiveQueen;

	}

	/**
	 * @see org.wicketstuff.security.strategies.StrategyFactory#destroy()
	 */
	public void destroy()
	{
		// should we clean up all sessions or is that taken care of
		// automatically
		// when the
		// session is invalidated

	}

	/**
	 * @see org.wicketstuff.security.strategies.StrategyFactory#newStrategy()
	 */
	public WaspAuthorizationStrategy newStrategy()
	{
		return new SwarmStrategy(secureClass, hiveQueen);
	}

	/**
	 * The key to the hive.
	 * 
	 * @return the key
	 */
	protected final Object getHiveKey()
	{
		return hiveQueen;
	}

	/**
	 * All instance of this class will be required to have {@link ComponentPermission} with at least
	 * the access action.
	 * 
	 * @return the class required to have instantiation rights
	 */
	protected final Class<? extends ISecureComponent> getSecureClass()
	{
		return secureClass;
	}
}
