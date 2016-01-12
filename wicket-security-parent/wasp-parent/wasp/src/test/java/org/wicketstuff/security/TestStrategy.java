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
package org.wicketstuff.security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.models.ISecureModel;
import org.wicketstuff.security.strategies.ClassAuthorizationStrategy;

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

	private Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();

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
	public TestStrategy(Class<? extends ISecureComponent> secureClass)
	{
		super(secureClass);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.strategies.ClassAuthorizationStrategy#destroy()
	 */
	@Override
	public void destroy()
	{
		super.destroy();
	}

	/**
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#isClassAuthenticated(java.lang.Class)
	 */
	@Override
	public boolean isClassAuthenticated(Class<?> clazz)
	{
		return loggedin;
	}

	/**
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#isClassAuthorized(java.lang.Class,
	 *      org.wicketstuff.security.actions.WaspAction)
	 */
	@Override
	public boolean isClassAuthorized(Class<?> clazz, WaspAction action)
	{
		return isAuthorized(SecureComponentHelper.alias(clazz), action);
	}

	/**
	 * @param obj
	 * @param action
	 * @return
	 */
	private boolean isAuthorized(String alias, WaspAction action)
	{
		WaspAction authorizedAction = authorized.get(alias);
		if (authorizedAction == null)
			return false;
		return authorizedAction.implies(action);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#isComponentAuthenticated(org.apache.wicket.Component)
	 */
	@Override
	public boolean isComponentAuthenticated(Component component)
	{
		return loggedin;
	}

	/**
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#isComponentAuthorized(org.apache.wicket.Component,
	 *      org.wicketstuff.security.actions.WaspAction)
	 */
	@Override
	public boolean isComponentAuthorized(Component component, WaspAction action)
	{
		if (!isAuthorized(SecureComponentHelper.alias(component.getClass()), action))
			return isAuthorized(SecureComponentHelper.alias(component), action);
		return true;
	}

	/**
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#isModelAuthenticated(org.apache.wicket.model.IModel,
	 *      org.apache.wicket.Component)
	 */
	@Override
	public boolean isModelAuthenticated(IModel<?> model, Component component)
	{
		return loggedin;
	}

	/**
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#isModelAuthorized(org.wicketstuff.security.models.ISecureModel,
	 *      org.apache.wicket.Component, org.wicketstuff.security.actions.WaspAction)
	 */
	@Override
	public boolean isModelAuthorized(ISecureModel<?> model, Component component, WaspAction action)
	{
		return isAuthorized("model:" +
			(component instanceof Page ? component.getClass().getName() : component.getId()),
			action);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#login(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void login(Object context) throws LoginException
	{
		if (context instanceof Map<?, ?>)
		{
			loggedin = true;
			authorized.putAll((Map<String, WaspAction>)context);
		}
		else
			throw new LoginException(
				"Specify a map containing all the classes/components and what actions are authorized");
	}

	/**
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#logoff(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean logoff(Object context)
	{
		if (context instanceof Map<?, ?>)
		{
			Map<String, WaspAction> map = (Map<String, WaspAction>)context;
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext())
				authorized.remove(it.next());
		}
		else
			authorized.clear();
		loggedin = !authorized.isEmpty();
		return true;
	}

	/**
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#isUserAuthenticated()
	 */
	@Override
	public boolean isUserAuthenticated()
	{
		return loggedin;
	}

    @Override
    public boolean isResourceAuthorized(IResource resource, PageParameters parameters) {
        return true;
    }
}
