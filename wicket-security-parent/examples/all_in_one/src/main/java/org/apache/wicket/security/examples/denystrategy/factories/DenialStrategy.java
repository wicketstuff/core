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

import org.apache.wicket.Component;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.models.ISecureModel;
import org.apache.wicket.security.swarm.strategies.SwarmStrategy;

/**
 * This is a very simple attempt to turn all permissions in a policy file into denials.
 * meaning if the policy says permission ${ComponentPermission}
 * "org.apache.wicket.security.examples.tabs.pages.MasterPage", "inherit, render"; it
 * means you are not allowed to render that page. This example is not an attempt to show
 * you how to turn swarm 180 degrees around but rather to show you how to install a custom
 * strategy.
 * 
 * @author marrink
 */
public class DenialStrategy extends SwarmStrategy
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param hiveQueen
	 */
	public DenialStrategy(Object hiveQueen)
	{
		super(hiveQueen);
	}

	/**
	 * Construct.
	 * 
	 * @param secureClass
	 * @param hiveQueen
	 */
	public DenialStrategy(Class< ? extends ISecureComponent> secureClass, Object hiveQueen)
	{
		super(secureClass, hiveQueen);
	}

	/*
	 * Rather then changing all securitychecks we simply invert there return value. Note
	 * that this is probably not a good idea if you want to build your own denial strategy
	 * but for this example it will do.
	 */

	/**
	 * @see org.apache.wicket.security.swarm.strategies.SwarmStrategy#isClassAuthorized(java.lang.Class,
	 *      org.apache.wicket.security.actions.WaspAction)
	 */
	@Override
	public boolean isClassAuthorized(Class< ? > clazz, WaspAction action)
	{
		return !super.isClassAuthorized(clazz, action);
	}

	/**
	 * @see org.apache.wicket.security.swarm.strategies.SwarmStrategy#isComponentAuthorized(org.apache.wicket.Component,
	 *      org.apache.wicket.security.actions.WaspAction)
	 */
	@Override
	public boolean isComponentAuthorized(Component component, WaspAction action)
	{
		return !super.isComponentAuthorized(component, action);
	}

	/**
	 * @see org.apache.wicket.security.swarm.strategies.SwarmStrategy#isModelAuthorized(org.apache.wicket.security.models.ISecureModel,
	 *      org.apache.wicket.Component, org.apache.wicket.security.actions.WaspAction)
	 */
	@Override
	public boolean isModelAuthorized(ISecureModel< ? > model, Component component, WaspAction action)
	{
		return !super.isModelAuthorized(model, component, action);
	}
}
