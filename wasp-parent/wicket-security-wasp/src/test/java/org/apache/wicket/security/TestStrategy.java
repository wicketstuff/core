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
package org.apache.wicket.security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.models.ISecureModel;
import org.apache.wicket.security.strategies.ClassAuthorizationStrategy;
import org.apache.wicket.security.strategies.LoginException;

/**
 * Implementation of a strategy for test purposes.
 * 
 * @author marrink
 */
public class TestStrategy extends ClassAuthorizationStrategy
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean loggedin = false;

	private Map authorized = new HashMap();

	/**
	 * 
	 */
	public TestStrategy()
	{
		super();
	}

	/**
	 * @param secureClass
	 */
	public TestStrategy(Class secureClass)
	{
		super(secureClass);
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.ClassAuthorizationStrategy#destroy()
	 */
	public void destroy()
	{
		super.destroy();
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isClassAuthenticated(java.lang.Class)
	 */
	public boolean isClassAuthenticated(Class clazz)
	{
		return loggedin;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isClassAuthorized(java.lang.Class,
	 *      org.apache.wicket.security.actions.WaspAction)
	 */
	public boolean isClassAuthorized(Class clazz, WaspAction action)
	{
		return isAuthorized(clazz, action);
	}

	/**
	 * @param obj
	 * @param action
	 * @return
	 */
	private boolean isAuthorized(Object obj, WaspAction action)
	{
		WaspAction authorizedAction = (WaspAction)authorized.get(obj);
		if (authorizedAction == null)
			return false;
		return authorizedAction.implies(action);
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isComponentAuthenticated(org.apache.wicket.Component)
	 */
	public boolean isComponentAuthenticated(Component component)
	{
		return loggedin;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isComponentAuthorized(org.apache.wicket.Component,
	 *      org.apache.wicket.security.actions.WaspAction)
	 */
	public boolean isComponentAuthorized(Component component, WaspAction action)
	{
		if (!isAuthorized(component.getClass(), action))
			return isAuthorized(SecureComponentHelper.alias(component), action);
		return true;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isModelAuthenticated(org.apache.wicket.model.IModel,
	 *      org.apache.wicket.Component)
	 */
	public boolean isModelAuthenticated(IModel model, Component component)
	{
		return loggedin;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isModelAuthorized(org.apache.wicket.security.models.ISecureModel,
	 *      org.apache.wicket.Component,
	 *      org.apache.wicket.security.actions.WaspAction)
	 */
	public boolean isModelAuthorized(ISecureModel model, Component component, WaspAction action)
	{
		return isAuthorized("model:"
				+ (component instanceof Page ? component.getClass().getName() : component.getId()),
				action);
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#login(java.lang.Object)
	 */
	public void login(Object context) throws LoginException
	{
		if (context instanceof Map)
		{
			loggedin = true;
			authorized.putAll((Map)context);
		}
		else
			throw new LoginException(
					"Specify a map containing all the classes/components and what actions are authorized");
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#logoff(java.lang.Object)
	 */
	public boolean logoff(Object context)
	{
		if (context instanceof Map)
		{
			Map map = (Map)context;
			Iterator it = map.keySet().iterator();
			while (it.hasNext())
				authorized.remove(it.next());
		}
		else
			authorized.clear();
		loggedin = !authorized.isEmpty();
		return true;
	}
}