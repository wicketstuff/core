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
package org.apache.wicket.security.strategies;

import java.io.Serializable;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.models.ISecureModel;


/**
 * Base class for every strategy. Checks Authorization and authentication at the
 * class, component and model levels.
 * 
 * @author marrink
 */
public abstract class WaspAuthorizationStrategy implements IAuthorizationStrategy, Serializable
{
	/**
	 * Performs the actual authorization check on the component.
	 * 
	 * @param component
	 * @param action
	 * @return true if authorized, false otherwise
	 */
	public abstract boolean isComponentAuthorized(Component component, WaspAction action);

	/**
	 * Performs the actual authorization check on the model of the component.
	 * 
	 * @param model
	 *            the model
	 * @param component
	 *            component 'owning' the model if available
	 * @param action
	 *            the action to check
	 * @return true if authorized, false otherwise
	 */
	public abstract boolean isModelAuthorized(ISecureModel model, Component component,
			WaspAction action);

	/**
	 * Performs the actual authorization check on the component class.
	 * 
	 * @param clazz
	 *            typically a component
	 * @param action
	 *            the action to check
	 * @return true if authorized, false otherwise
	 */
	public abstract boolean isClassAuthorized(Class clazz, WaspAction action);

	/**
	 * Performs the authentication check.
	 * 
	 * @param component
	 *            the wicket component
	 * 
	 * @return true if the user is authenticated, false otherwise
	 */
	public abstract boolean isComponentAuthenticated(Component component);

	/**
	 * Performs the authentication check.
	 * 
	 * @param model
	 *            the (secure) model
	 * @param component
	 *            the component owning the model
	 * 
	 * @return true if the user is authenticated, false otherwise
	 */
	public abstract boolean isModelAuthenticated(IModel model, Component component);

	/**
	 * Performs the authentication check.
	 * 
	 * @param clazz
	 *            the class of the wicket component
	 * 
	 * @return true if the user is authenticated, false otherwise
	 */
	public abstract boolean isClassAuthenticated(Class clazz);

	/**
	 * Attempts to log the user in. Note to implementations: It is generally
	 * considered a bad idea to store the context if it contains sensitive data
	 * (like a plaintext password). Implementations should handle temporary
	 * sessions and dirty flags for clustering.
	 * 
	 * @param context
	 *            a not further specified object that provides all the
	 *            information to log the user on
	 * @throws LoginException
	 *             if the login is unsuccessful
	 */
	public abstract void login(Object context) throws LoginException;

	/**
	 * Log the user off. With the help of a context implementations might
	 * facilitate multi level login / logoff. Implementations should handle
	 * dirty flags for clustering.
	 * 
	 * @param context
	 *            a not further specified object, might be null
	 * @return true if the logoff was successfull, false otherwise
	 */
	public abstract boolean logoff(Object context);

	/**
	 * Called at the end of the sessions lifecycle to perform some clean up.
	 */
	public abstract void destroy();

	/**
	 * 
	 * @see org.apache.wicket.authorization.IAuthorizationStrategy#isActionAuthorized(org.apache.wicket.Component,
	 *      org.apache.wicket.authorization.Action)
	 */
	public boolean isActionAuthorized(Component component, Action action)
	{
		if (component != null)
		{
			ISecurityCheck check = getSecurityCheck(component);
			if (check != null)
				return check.isActionAuthorized(getActionFactory().getAction(action));
			IModel model = component.getModel();
			if (model instanceof ISecureModel)
				return ((ISecureModel)model).isAuthorized(component, getActionFactory().getAction(
						action));
		}
		return true;
	}

	/**
	 * We cannot assume everybody uses the here specified public methods to
	 * store the securitycheck, so we check if the component is a
	 * ISecureComponent and if so use the getSecurityCheck on the secure
	 * component else we fall back to the SecureComponentHelper.
	 * 
	 * @param component
	 * @return the security check of the component or null if the component does
	 *         not have one
	 * @see SecureComponentHelper#getSecurityCheck(Component)
	 */
	protected final ISecurityCheck getSecurityCheck(Component component)
	{
		if (component instanceof ISecureComponent)
			return ((ISecureComponent)component).getSecurityCheck();
		return SecureComponentHelper.getSecurityCheck(component);
	}

	/**
	 * Shortcut to the actionfactory.
	 * 
	 * @return the actionfactory from the application
	 */
	protected final ActionFactory getActionFactory()
	{
		return ((WaspApplication)Application.get()).getActionFactory();
	}
}
