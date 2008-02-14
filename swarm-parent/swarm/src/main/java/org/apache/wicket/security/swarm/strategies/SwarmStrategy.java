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
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.components.ISecurePage;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.hive.Hive;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.authentication.LoginContainer;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authorization.Permission;
import org.apache.wicket.security.hive.authorization.permissions.ComponentPermission;
import org.apache.wicket.security.hive.authorization.permissions.DataPermission;
import org.apache.wicket.security.models.ISecureModel;
import org.apache.wicket.security.strategies.ClassAuthorizationStrategy;
import org.apache.wicket.security.strategies.SecurityException;
import org.apache.wicket.security.swarm.actions.SwarmAction;
import org.apache.wicket.security.swarm.models.SwarmModel;

/**
 * Implementation of a {@link ClassAuthorizationStrategy}. It allows for both
 * simple logins as well as multi level logins.
 * 
 * @author marrink
 */
public class SwarmStrategy extends ClassAuthorizationStrategy
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Key to the hive.
	 */
	private Object hiveQueen;

	private LoginContainer loginContainer;

	/**
	 * Constructs a new strategy linked to the specified hive.
	 * 
	 * @param hiveQueen
	 *            A key to retrieve the {@link Hive}
	 */
	public SwarmStrategy(Object hiveQueen)
	{
		this(ISecurePage.class, hiveQueen);
	}

	/**
	 * Constructs a new strategy linked to the specified hive.
	 * 
	 * @param secureClass
	 *            instances of this class will be required to have access
	 *            authorization.
	 * @param hiveQueen
	 *            A key to retrieve the {@link Hive}
	 */
	public SwarmStrategy(Class secureClass, Object hiveQueen)
	{
		super(secureClass);
		this.hiveQueen = hiveQueen;
		loginContainer = new LoginContainer();
	}

	/**
	 * Returns the hive.
	 * 
	 * @return the hive.
	 * @throws SecurityException
	 *             if no hive is registered.
	 */
	protected final Hive getHive()
	{
		Hive hive = HiveMind.getHive(hiveQueen);
		if (hive == null)
			throw new SecurityException("No hive registered for " + hiveQueen);
		return hive;
	}

	/**
	 * The currently logged in subject, note that at any time there is at most 1
	 * subject logged in.
	 * 
	 * @return the subject or null if no login has succeeded yet
	 */
	public final Subject getSubject()
	{
		return loginContainer.getSubject();
	}

	/**
	 * Performs the actual permission check at the {@link Hive}.
	 * 
	 * @param permission
	 *            the permission to verify
	 * @return true if the subject has or implies the permission, false
	 *         otherwise
	 * @throws SecurityException
	 *             if the permission is null
	 */
	public boolean hasPermission(Permission permission)
	{
		if (permission == null)
			throw new SecurityException("permission is not allowed to be null");
		return getHive().hasPermission(getSubject(), permission);
	}

	/**
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isClassAuthenticated(java.lang.Class)
	 */
	public boolean isClassAuthenticated(Class clazz)
	{
		return loginContainer.isClassAuthenticated(clazz);
	}

	/**
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isClassAuthorized(java.lang.Class,
	 *      org.apache.wicket.security.actions.WaspAction)
	 */
	public boolean isClassAuthorized(Class clazz, WaspAction action)
	{
		return hasPermission(new ComponentPermission(SecureComponentHelper.alias(clazz), action));
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isComponentAuthenticated(org.apache.wicket.Component)
	 */
	public boolean isComponentAuthenticated(Component component)
	{
		return loginContainer.isComponentAuthenticated(component);
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isComponentAuthorized(org.apache.wicket.Component,
	 *      org.apache.wicket.security.actions.WaspAction)
	 */
	public boolean isComponentAuthorized(Component component, WaspAction action)
	{
		return hasPermission(new ComponentPermission(component, action));
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isModelAuthenticated(org.apache.wicket.model.IModel,
	 *      org.apache.wicket.Component)
	 */
	public boolean isModelAuthenticated(IModel model, Component component)
	{
		return loginContainer.isModelAuthenticated(model, component);
	}

	/**
	 * Checks if some action is granted on the model. Although
	 * {@link SwarmModel}s are preferred any {@link ISecureModel} can be used,
	 * in that case it uses the {@link ISecureModel#toString()} method as the
	 * name of the {@link DataPermission}
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isModelAuthorized(ISecureModel,
	 *      Component, WaspAction)
	 */
	public boolean isModelAuthorized(ISecureModel model, Component component, WaspAction action)
	{
		DataPermission permission;
		if (model instanceof SwarmModel)
			permission = new DataPermission(component, (SwarmModel)model, (SwarmAction)action);
		else
			permission = new DataPermission(String.valueOf(model), action);
		return hasPermission(permission);

	}

	/**
	 * Logs a user in. Note that the context must be an instance of
	 * {@link LoginContext}.
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#login(java.lang.Object)
	 */
	public void login(Object context) throws LoginException
	{
		if (context instanceof LoginContext)
		{
			loginContainer.login((LoginContext)context);
		}
		else
			throw new SecurityException("Unable to process login with context: " + context);
	}

	/**
	 * Loggs a user off. Note that the context must be an instance of
	 * {@link LoginContext} and must be the same (or equal) to the logincontext
	 * used to log in.
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#logoff(Object)
	 */
	public boolean logoff(Object context)
	{
		if (context instanceof LoginContext)
		{
			return loginContainer.logoff((LoginContext)context);
		}
		throw new SecurityException("Unable to process logoff with context: " + context);
	}

	/**
	 * The {@link LoginContainer} keeps track of all Subjects for this session..
	 * 
	 * @return loginContainer
	 */
	protected final LoginContainer getLoginContainer()
	{
		return loginContainer;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.strategies.WaspAuthorizationStrategy#isUserAuthenticated()
	 */
	public boolean isUserAuthenticated()
	{
		return getSubject() != null;
	}
}
