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
package org.wicketstuff.security.strategies;

import java.io.Serializable;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.WaspApplication;
import org.wicketstuff.security.WaspSession;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.actions.WaspActionFactory;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.log.AuthorizationErrorKey;
import org.wicketstuff.security.log.AuthorizationMessageSource;
import org.wicketstuff.security.log.IAuthorizationMessageSource;
import org.wicketstuff.security.models.ISecureModel;

/**
 * Base class for every strategy. Checks Authorization and authentication at the class, component
 * and model levels.
 * 
 * @author marrink
 */
public abstract class WaspAuthorizationStrategy implements IAuthorizationStrategy, Serializable
{
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(WaspAuthorizationStrategy.class);

	/**
	 * Key used to store the {@link IAuthorizationMessageSource} in the {@link RequestCycle}
	 * metadata.
	 */
	protected static final AuthorizationErrorKey MESSAGE_KEY = new AuthorizationErrorKey();

	private static final ThreadLocal<StrategyResolver> resolver = new ThreadLocal<StrategyResolver>();

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
	public abstract boolean isModelAuthorized(ISecureModel<?> model, Component component,
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
	public abstract boolean isClassAuthorized(Class<?> clazz, WaspAction action);

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
	public abstract boolean isModelAuthenticated(IModel<?> model, Component component);

	/**
	 * Performs the authentication check.
	 * 
	 * @param clazz
	 *            the class of the wicket component
	 * 
	 * @return true if the user is authenticated, false otherwise
	 */
	public abstract boolean isClassAuthenticated(Class<?> clazz);

	/**
	 * Checks if there is a user logged in at all. This will return true after a successful
	 * {@link #login(Object)} and false after a successful {@link #logoff(Object)}. Note that in a
	 * multi-login scenario this method returns true until all successful logins are countered with
	 * a successful logoff.
	 * 
	 * @return true while a user is logged in, false otherwise
	 */
	public abstract boolean isUserAuthenticated();

	/**
	 * Attempts to log the user in. Note to implementations: It is generally considered a bad idea
	 * to store the context if it contains sensitive data (like a plaintext password).
	 * 
	 * @param context
	 *            a not further specified object that provides all the information to log the user
	 *            on
	 * @throws LoginException
	 *             if the login is unsuccessful
	 */
	public abstract void login(Object context) throws LoginException;

	/**
	 * Log the user off. With the help of a context implementations might facilitate multi level
	 * login / logoff.
	 * 
	 * @param context
	 *            a not further specified object, might be null
	 * @return true if the logoff was successful, false otherwise
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
			{
				if (check.isActionAuthorized(getActionFactory().getAction(action)))
					return true;
				IAuthorizationMessageSource message = getMessageSource();
				if (message != null)
				{
					message.setComponent(component);
					message.addVariable("wicket.action", action);
					message.addVariable("wasp.action", getActionFactory().getAction(action));
					logMessage(message);
				}
				return false;

			}
			IModel<?> model = component.getDefaultModel();
			if (model instanceof ISecureModel<?>)
			{
				if (((ISecureModel<?>)model).isAuthorized(component,
					getActionFactory().getAction(action)))
					return true;
				IAuthorizationMessageSource message = getMessageSource();
				if (message != null)
				{
					message.setComponent(component);
					message.addVariable("wicket.action", action);
					message.addVariable("wasp.action", getActionFactory().getAction(action));
					logMessage(message);
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * Logs a message indication an action was denied. The message is retrieved through localization
	 * with the following resource key:"authorization.denied" (no quotes). This method removes the
	 * messagesource from the requestcycle.
	 * 
	 * @param message
	 *            messagesource
	 * @see #logMessage(String, Map, IAuthorizationMessageSource, boolean)
	 */
	protected final void logMessage(IAuthorizationMessageSource message)
	{
		logMessage("authorization.denied", null, message, true);
	}

	/**
	 * Logs a message indication an action was denied. The message is retrieved through localization
	 * with the specified resource key. Optionally the message can be removed from the requestcycle,
	 * if it is not removed the message might be processed multiple times (which might be what you
	 * want if you want to use different resource keys). This invokes
	 * {@link #logMessage(String, Map, IAuthorizationMessageSource)}
	 * 
	 * 
	 * @param key
	 *            the resource key to lookup the message
	 * @param variables
	 *            optional map containing additional variables that can be used during the message
	 *            lookup
	 * @param message
	 *            messagesource
	 * @param remove
	 *            flag indicating if the message should be removed or not
	 * @see #removeMessageSource()
	 * @see #logMessage(String, Map, IAuthorizationMessageSource)
	 */
	protected final void logMessage(String key, Map<String, Object> variables,
		IAuthorizationMessageSource message, boolean remove)
	{
		if (message == null || Strings.isEmpty(key))
		{
			if (remove)
				removeMessageSource();
			return;
		}
		logMessage(key, variables, message);
		if (remove)
			removeMessageSource();
	}

	/**
	 * Logs a message indication an action was denied. The message is retrieved through localization
	 * with the specified resource key. This default implementation simply does something like
	 * <code>log.debug(...)</code> Overwrite this method if you want for example wicket to print
	 * feedback messages with something like <code>Session.get().error(...)</code>
	 * 
	 * @param key
	 *            the resource key for the message
	 * @param variables
	 *            optional map containing additional variables that can be used during message
	 *            construction
	 * @param message
	 *            the messagesource
	 */
	protected void logMessage(String key, Map<String, Object> variables,
		IAuthorizationMessageSource message)
	{
		String msg = message.getMessage(key, variables);
		if (!Strings.isEmpty(msg))
                    log.debug(msg);
	}

	/**
	 * Removes the message from the {@link RequestCycle}'s metadata.
	 */
	protected final void removeMessageSource()
	{
		RequestCycle.get().setMetaData(MESSAGE_KEY, null);
	}

	/**
	 * Retrieves the messagesource from the {@link RequestCycle}'s metadata.
	 * 
	 * @return the messagesource or null if there is none
	 */
	protected final IAuthorizationMessageSource getMessageSource()
	{
		return getMessageSource(false);
	}

	/**
	 * Retrieves the messagesource from the {@link RequestCycle}'s metadata. optionally creating a
	 * new one if there is not already one.
	 * 
	 * @param create
	 * @return the messagesource or null if there is none and the create flag was false
	 */
	protected final IAuthorizationMessageSource getMessageSource(boolean create)
	{
		IAuthorizationMessageSource resource = null;
		if (RequestCycle.get() != null)
			resource = RequestCycle.get().getMetaData(MESSAGE_KEY);
		if (resource == null && create)
		{
			resource = createMessageSource();
			RequestCycle.get().setMetaData(MESSAGE_KEY, resource);
		}
		return resource;
	}

	/**
	 * Creates a new {@link IAuthorizationMessageSource}. Subclasses can override this to return
	 * there own implementation.
	 * 
	 * @return a new IErrorMessageSource, never null
	 */
	protected IAuthorizationMessageSource createMessageSource()
	{
		return new AuthorizationMessageSource();
	}

	/**
	 * Indicates if messages about denied actions should be logged. Default is to use the slf4 log
	 * implementation checking if debug messages should be printed. for example using log4j as the
	 * logging implementation for slf4j logging could be turned on by putting the following line in
	 * your log4.properties</br> <code>
	 * log4j.category.org.wicketstuff.security.strategies.WaspAuthorizationStrategy=DEBUG
	 * </code>
	 * 
	 * @return true if messages should be logged, false otherwise
	 */
	protected boolean logMessages()
	{
		return log.isDebugEnabled();
	}

	/**
	 * We cannot assume everybody uses the here specified public methods to store the securitycheck,
	 * so we check if the component is a ISecureComponent and if so use the getSecurityCheck on the
	 * secure component else we fall back to the SecureComponentHelper.
	 * 
	 * @param component
	 * @return the security check of the component or null if the component does not have one
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
	protected final WaspActionFactory getActionFactory()
	{
		return ((WaspApplication)Application.get()).getActionFactory();
	}

	/**
	 * Returns the WaspAuthorizationStrategy. This defaults to
	 * {@link WaspSession#getAuthorizationStrategy()}, but a different implementation can be
	 * registered via a {@link StrategyResolver}.
	 * 
	 * @return
	 */
	public static WaspAuthorizationStrategy get()
	{
		StrategyResolver threadResolver = resolver.get();
		return threadResolver == null
			? (WaspAuthorizationStrategy)((WaspSession)Session.get()).getAuthorizationStrategy()
			: threadResolver.getStrategy();
	}

	/**
	 * Sets the StrategyResolver for the current thread
	 * 
	 * @param threadResolver
	 */
	public static void setStrategyResolver(StrategyResolver threadResolver)
	{
		resolver.set(threadResolver);
	}
}
