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
package org.apache.wicket.security.components;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.checks.WaspKey;
import org.apache.wicket.security.models.ISecureModel;
import org.apache.wicket.security.strategies.SecurityException;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;


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
	 * The security check placed on the component or null. This uses the
	 * metadata of a component to store or retrieve the {@link ISecurityCheck}.
	 * 
	 * @param component
	 *            if null, null will be returned
	 * @return the security check or null if none was placed on the component
	 */
	public static ISecurityCheck getSecurityCheck(Component component)
	{
		if (component != null)
			return (ISecurityCheck)component.getMetaData(new WaspKey());
		return null;
	}

	/**
	 * Places a security check on a component. This uses the metadata of a
	 * component to store or retrieve the {@link ISecurityCheck}.
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
	 * We cannot assume everybody uses the here specified public methods to
	 * store the {@link ISecurityCheck}, so we check if the component is a
	 * {@link ISecureComponent} and if so use the
	 * {@link ISecureComponent#getSecurityCheck()} on the secure component else
	 * we fall back to the metadata.
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
		return component != null && component.getModel() instanceof ISecureModel;
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
		throw new WicketRuntimeException(application + " is not a WaspApplication");
	}

	/**
	 * Default implementation for
	 * {@link ISecureComponent#isActionAuthorized(String)} and
	 * {@link WaspAuthorizationStrategy#isActionAuthorized(Component, org.apache.wicket.authorization.Action)}.
	 * First tries to use the {@link ISecurityCheck} from the component if that
	 * is not available it tries the {@link ISecureModel} if neither is present
	 * the action is authorized on the component.
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
			return ((ISecureModel)component.getModel()).isAuthorized(component, getActionFactory()
					.getAction(action));
		return true;
	}

	/**
	 * Default implementation for
	 * {@link ISecureComponent#isActionAuthorized(WaspAction)} and
	 * {@link WaspAuthorizationStrategy#isActionAuthorized(Component, org.apache.wicket.authorization.Action)}.
	 * First tries to use the {@link ISecurityCheck} from the component if that
	 * is not available it tries the {@link ISecureModel} if neither is present
	 * the action is authorized on the component.
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
			return ((ISecureModel)component.getModel()).isAuthorized(component, action);
		return true;
	}

	/**
	 * Default implementation for {@link ISecureComponent#isAuthenticated()}.
	 * First tries to use the {@link ISecurityCheck} from the component if that
	 * is not available it tries the {@link ISecureModel} if neither is present
	 * the user is authenticated.
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
			return ((ISecureModel)component.getModel()).isAuthenticated(component);
		return true;
	}

	/**
	 * Builds a 'unique' name for the component. The name is based on the page
	 * class alias and the relative path to the page (if not a page itself).
	 * Note that although it is unlikely, it is not impossible for two
	 * components to have the same alias.
	 * 
	 * @param component
	 * @return an alias.
	 * @throws SecurityException
	 *             if the component is null, or if the page of the component is
	 *             not available.
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
		return alias + ":" + relative;
	}

	/**
	 * Builds an alias for a class.
	 * 
	 * @param class1
	 * @return an alias
	 */
	public static String alias(Class class1)
	{
		if (class1 == null)
			throw new SecurityException("Specified class is null");
		return class1.getName();
	}
}
