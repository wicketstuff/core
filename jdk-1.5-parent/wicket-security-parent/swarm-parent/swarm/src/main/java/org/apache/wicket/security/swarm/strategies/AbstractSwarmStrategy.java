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
package org.apache.wicket.security.swarm.strategies;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.hive.Hive;
import org.apache.wicket.security.hive.authentication.LoginContainer;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authorization.Permission;
import org.apache.wicket.security.hive.authorization.permissions.ComponentPermission;
import org.apache.wicket.security.hive.authorization.permissions.DataPermission;
import org.apache.wicket.security.log.IAuthorizationMessageSource;
import org.apache.wicket.security.models.ISecureModel;
import org.apache.wicket.security.strategies.ClassAuthorizationStrategy;
import org.apache.wicket.security.strategies.SecurityException;
import org.apache.wicket.security.swarm.actions.SwarmAction;
import org.apache.wicket.security.swarm.models.SwarmModel;

public abstract class AbstractSwarmStrategy extends ClassAuthorizationStrategy
{
	private static final long serialVersionUID = 1L;

	protected LoginContainer loginContainer;

	public AbstractSwarmStrategy()
	{
	}

	public AbstractSwarmStrategy(Class< ? extends ISecureComponent> secureClass)
	{
		super(secureClass);
	}

	public abstract boolean hasPermission(Permission permission, Subject subject);

	/**
	 * The currently logged in subject, note that at any time there is at most 1 subject
	 * logged in.
	 * 
	 * @return the subject or null if no login has succeeded yet
	 */
	public Subject getSubject()
	{
		return loginContainer.getSubject();
	}

	/**
	 * Performs the actual permission check at the {@link Hive}. This is equal to using
	 * {@link #hasPermission(Permission, Subject)} with {@link #getSubject()}
	 * 
	 * @param permission
	 *            the permission to verify
	 * @return true if the subject has or implies the permission, false otherwise
	 * @throws SecurityException
	 *             if the permission is null
	 * @see #hasPermission(Permission, Subject)
	 */
	public boolean hasPermission(Permission permission)
	{
		return hasPermission(permission, getSubject());
	}

	/**
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isClassAuthenticated(java.lang.Class)
	 */
	@Override
	public boolean isClassAuthenticated(Class< ? > clazz)
	{
		return loginContainer.isClassAuthenticated(clazz);
	}

	/**
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isClassAuthorized(java.lang.Class,
	 *      org.apache.wicket.security.actions.WaspAction)
	 */
	@Override
	public boolean isClassAuthorized(Class< ? > clazz, WaspAction action)
	{
		if (hasPermission(new ComponentPermission(SecureComponentHelper.alias(clazz), action)))
			return true;
		logMessage(getMessageSource());
		return false;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isComponentAuthenticated(org.apache.wicket.Component)
	 */
	@Override
	public boolean isComponentAuthenticated(Component component)
	{
		return loginContainer.isComponentAuthenticated(component);
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isComponentAuthorized(org.apache.wicket.Component,
	 *      org.apache.wicket.security.actions.WaspAction)
	 */
	@Override
	public boolean isComponentAuthorized(Component component, WaspAction action)
	{
		if (hasPermission(new ComponentPermission(component, action)))
			return true;
		IAuthorizationMessageSource message = getMessageSource();
		if (message != null)
		{
			message.setComponent(component);
			logMessage(message);
		}
		return false;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isModelAuthenticated(org.apache.wicket.model.IModel,
	 *      org.apache.wicket.Component)
	 */
	@Override
	public boolean isModelAuthenticated(IModel< ? > model, Component component)
	{
		return loginContainer.isModelAuthenticated(model, component);
	}

	/**
	 * Checks if some action is granted on the model. Although {@link SwarmModel}s are
	 * preferred any {@link ISecureModel} can be used, in that case it uses the
	 * {@link ISecureModel#toString()} method as the name of the {@link DataPermission}
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isModelAuthorized(ISecureModel,
	 *      Component, WaspAction)
	 */
	@Override
	public boolean isModelAuthorized(ISecureModel< ? > model, Component component, WaspAction action)
	{
		DataPermission permission;
		if (model instanceof SwarmModel< ? >)
			permission =
				new DataPermission(component, (SwarmModel< ? >) model, (SwarmAction) action);
		else
			permission = new DataPermission(String.valueOf(model), action);
		if (hasPermission(permission))
			return true;
		IAuthorizationMessageSource message = getMessageSource();
		if (message != null)
		{
			message.setComponent(component);
			logMessage(message);
		}
		return false;

	}
}
