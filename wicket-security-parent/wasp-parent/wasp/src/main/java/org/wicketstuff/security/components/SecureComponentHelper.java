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
package org.wicketstuff.security.components;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.util.string.PrependingStringBuffer;
import org.wicketstuff.security.WaspApplication;
import org.wicketstuff.security.WaspSession;
import org.wicketstuff.security.actions.ActionFactory;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.checks.WaspKey;
import org.wicketstuff.security.models.ISecureModel;
import org.wicketstuff.security.strategies.SecurityException;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * Utility class for secure components.
 * 
 * @author marrink
 * @see ISecurityCheck
 * @see ISecureComponent
 * @see ISecureModel
 */
public final class SecureComponentHelper
{

	/**
	 * String used to concatenate the parts that make up alias.
	 * 
	 * @see #alias(Class)
	 * @see #alias(Component)
	 * @see #containerAlias(MarkupContainer)
	 * @see #containerAliasses(Component)
	 */
	public static final String PATH_SEPARATOR = "" + Component.PATH_SEPARATOR;

	/**
	 * The security check placed on the component or null. This uses the metadata of a component to
	 * store or retrieve the {@link ISecurityCheck}.
	 * 
	 * @param component
	 *            if null, null will be returned
	 * @return the security check or null if none was placed on the component
	 */
	public static ISecurityCheck getSecurityCheck(Component component)
	{
		if (component != null)
			return component.getMetaData(new WaspKey());
		return null;
	}

	/**
	 * Places a security check on a component. This uses the metadata of a component to store or
	 * retrieve the {@link ISecurityCheck}.
	 * 
	 * @param component
	 * @param securityCheck
	 * @return the component or null if the component was null to begin with.
	 */
	public static Component setSecurityCheck(Component component, ISecurityCheck securityCheck)
	{
		if (component == null)
			return null;
		component.setMetaData(new WaspKey(), securityCheck);
		return component;
	}

	/**
	 * We cannot assume everybody uses the here specified public methods to store the
	 * {@link ISecurityCheck}, so we check if the component is a {@link ISecureComponent} and if so
	 * use the {@link ISecureComponent#getSecurityCheck()} on the secure component else we fall back
	 * to the metadata.
	 * 
	 * @param component
	 * @return the security check or null if the component does not have one.
	 */
	private static ISecurityCheck saveGetSecurityCheck(Component component)
	{
		if (component instanceof ISecureComponent)
			return ((ISecureComponent)component).getSecurityCheck();
		return getSecurityCheck(component);
	}

	/**
	 * Checks if the component has a {@link ISecureModel}.
	 * 
	 * @param component
	 * @return true if the component has a securemodel, else false.
	 */
	public static boolean hasSecureModel(Component component)
	{
		return component != null && component.getDefaultModel() instanceof ISecureModel<?>;
	}

	/**
	 * Gets the {@link ActionFactory}.
	 * 
	 * @return the actionfactory
	 * @throws WicketRuntimeException
	 *             if the ActionFactory is not found.
	 */
	private static ActionFactory getActionFactory()
	{
		Application application = Application.get();
		if (application instanceof WaspApplication)
		{
			WaspApplication app = (WaspApplication)application;
			return app.getActionFactory();
		}
		throw new WicketRuntimeException(application + " is not a " + WaspApplication.class);
	}

	/**
	 * Gets the {@link WaspAuthorizationStrategy}.
	 * 
	 * @return the strategy
	 * @throws WicketRuntimeException
	 *             if a {@link WaspSession} is not found
	 * @throws ClassCastException
	 *             if the session does not contain a {@link WaspAuthorizationStrategy}
	 */
	private static WaspAuthorizationStrategy getStrategy()
	{
		Session session = Session.get();
		if (session instanceof WaspSession)
			return (WaspAuthorizationStrategy)session.getAuthorizationStrategy();
		throw new WicketRuntimeException(session + " is not a " + WaspSession.class);
	}

	/**
	 * Default implementation for {@link ISecureComponent#isActionAuthorized(String)} and
	 * {@link WaspAuthorizationStrategy#isActionAuthorized(Component, org.apache.wicket.authorization.Action)}
	 * . First tries to use the {@link ISecurityCheck} from the component if that is not available
	 * it tries the {@link ISecureModel} if neither is present the action is authorized on the
	 * component.
	 * 
	 * @param component
	 *            the component to check
	 * @param action
	 *            the required action(s)
	 * 
	 * @return true, if the component is authorized, false otherwise.
	 * @see ISecureComponent#isActionAuthorized(String)
	 * @see ISecurityCheck
	 * @see ISecureModel
	 */
	public static boolean isActionAuthorized(Component component, String action)
	{
		if (action == null)
			return true;
		ISecurityCheck check = saveGetSecurityCheck(component);
		if (check != null)
			return check.isActionAuthorized(getActionFactory().getAction(action));
		if (hasSecureModel(component))
			return ((ISecureModel<?>)component.getDefaultModel()).isAuthorized(component,
				getActionFactory().getAction(action));
		return true;
	}

	/**
	 * Default implementation for {@link ISecureComponent#isActionAuthorized(WaspAction)} and
	 * {@link WaspAuthorizationStrategy#isActionAuthorized(Component, org.apache.wicket.authorization.Action)}
	 * . First tries to use the {@link ISecurityCheck} from the component if that is not available
	 * it tries the {@link ISecureModel} if neither is present the action is authorized on the
	 * component.
	 * 
	 * @param component
	 *            the component to check
	 * @param action
	 *            the required action(s)
	 * 
	 * @return true, if the component is authorized, false otherwise.
	 * @see ISecureComponent#isActionAuthorized(WaspAction)
	 * @see ISecurityCheck
	 * @see ISecureModel
	 */
	public static boolean isActionAuthorized(Component component, WaspAction action)
	{
		if (action == null)
			return true;
		ISecurityCheck check = saveGetSecurityCheck(component);
		if (check != null)
			return check.isActionAuthorized(action);
		if (hasSecureModel(component))
			return ((ISecureModel<?>)component.getDefaultModel()).isAuthorized(component, action);
		return true;
	}

	/**
	 * Default implementation for {@link ISecureComponent#isAuthenticated()}. First tries to use the
	 * {@link ISecurityCheck} from the component if that is not available it tries the
	 * {@link ISecureModel} if neither is present the user is authenticated if
	 * {@link WaspAuthorizationStrategy#isUserAuthenticated()} returns true.
	 * 
	 * @param component
	 *            the component to check
	 * 
	 * @return true, if the user is authenticated, false otherwise.
	 * @see ISecureComponent#isAuthenticated()
	 * @see ISecurityCheck
	 * @see ISecureModel
	 */
	public static boolean isAuthenticated(Component component)
	{
		ISecurityCheck check = saveGetSecurityCheck(component);
		if (check != null)
			return check.isAuthenticated();
		if (hasSecureModel(component))
			return ((ISecureModel<?>)component.getDefaultModel()).isAuthenticated(component);
		return getStrategy().isUserAuthenticated();
	}

	/**
	 * Builds a 'unique' name for the component. The name is based on the page class alias and the
	 * relative path to the page (if not a page itself). Note that although it is unlikely, it is
	 * not impossible for two components to have the same alias.
	 * 
	 * @param component
	 * @return an alias.
	 * @throws SecurityException
	 *             if the component is null, or if the page of the component is not available.
	 */
	public static String alias(Component component)
	{
		// might be useful in wicket core itself
		if (component == null)
			throw new SecurityException("Specified component is null");
		Page page = null;
		try
		{
			page = component.getPage();
		}
		catch (IllegalStateException e)
		{
			throw new SecurityException("Unable to create alias for component: " + component, e);
		}
		String alias = alias(page.getClass());
		String relative = component.getPageRelativePath();
		if (relative == null || "".equals(relative))
			return alias;
		return alias + PATH_SEPARATOR + relative;
	}

	/**
	 * Builds an alias for a class.
	 * 
	 * @param class1
	 * @return an alias
	 */
	public static String alias(Class<?> class1)
	{
		if (class1 == null)
			throw new SecurityException("Specified class is null");
		return class1.getName();
	}

	/**
	 * Builds an alias string for {@link MarkupContainer}s.
	 * 
	 * @param container
	 * @return an alias
	 */
	public static String containerAlias(MarkupContainer container)
	{
		if (container == null)
			throw new SecurityException("specified markupcontainer is null");
		MarkupContainer parent = container;
		PrependingStringBuffer buffer = new PrependingStringBuffer(150);
		while (parent != null)
		{
			if (buffer.length() > 0)
				buffer.prepend(PATH_SEPARATOR);
			buffer.prepend(parent.getClass().getName());
			parent = parent.getParent();
		}
		return buffer.toString();
	}

	/**
	 * Builds a set of aliases for this component. Each alias can be used as name in Permission.
	 * 
	 * @param component
	 * @return an array with aliases for this component
	 */
	public static String[] containerAliasses(Component component)
	{
		if (component == null)
			throw new SecurityException("specified component is null");
		MarkupContainer parent = null;
		if (component instanceof MarkupContainer)
			parent = (MarkupContainer)component;
		else
			parent = component.getParent();
		if (parent == null)
			return new String[0];
		String alias = containerAlias(parent);
		String[] split = alias.split(PATH_SEPARATOR);
		String[] result = new String[split.length + (split.length - 1)];
		PrependingStringBuffer buffer = new PrependingStringBuffer(200);
		int index = result.length - 1;
		for (int i = split.length - 1; i >= 0; i--)
		{
			if (i < split.length - 1)
			{
				result[index] = split[i];
				index--;
				buffer.prepend(PATH_SEPARATOR);
			}
			buffer.prepend(split[i]);
			result[index] = buffer.toString();
			index--;
		}
		return result;
	}
}
