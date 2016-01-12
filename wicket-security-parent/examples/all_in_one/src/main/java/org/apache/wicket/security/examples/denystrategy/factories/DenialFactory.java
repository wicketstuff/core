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
package org.apache.wicket.security.examples.denystrategy.factories;

import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;
import org.apache.wicket.security.swarm.strategies.SwarmStrategyFactory;

/**
 * A new factory to launch our denialstrategy from.
 * 
 * @author marrink
 */
public class DenialFactory extends SwarmStrategyFactory
{

	/**
	 * Construct.
	 * 
	 * @param hiveQueen
	 */
	public DenialFactory(Object hiveQueen)
	{
		super(hiveQueen);
	}

	/**
	 * Construct.
	 * 
	 * @param secureClass
	 * @param hiveQueen
	 */
	public DenialFactory(Class< ? extends ISecureComponent> secureClass, Object hiveQueen)
	{
		super(secureClass, hiveQueen);
	}

	/**
	 * @see org.apache.wicket.security.swarm.strategies.SwarmStrategyFactory#newStrategy()
	 */
	@Override
	public WaspAuthorizationStrategy newStrategy()
	{
		return new DenialStrategy(getSecureClass(), getHiveKey());
	}

}
