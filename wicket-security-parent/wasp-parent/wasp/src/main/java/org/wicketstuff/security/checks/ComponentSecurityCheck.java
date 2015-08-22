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
package org.wicketstuff.security.checks;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.models.ISecureModel;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * Basic security check for components. Tries to authorize the component and optionally its
 * {@link ISecureModel} if it exists. Note that this check always authenticates the user, this is
 * only to make it easier to put a secure component on a non secure page and have it redirect to the
 * login. Usually the secure page will have checked authentication already. Both
 * {@link ISecureModel} and this check need to authenticate / authorize the user before an approval
 * is given.
 * 
 * @author marrink
 */
public class ComponentSecurityCheck extends AbstractSecurityCheck
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Component component;

	private boolean checkModel;

	/**
	 * Constructs a ComponentSecurityCheck that never checks the model. Note that the check still
	 * needs to be manually added to the component.
	 * 
	 * @param component
	 *            the target component for this security check.
	 * @see ISecureComponent#setSecurityCheck(ISecurityCheck)
	 * @see SecureComponentHelper#setSecurityCheck(Component, ISecurityCheck)
	 */
	public ComponentSecurityCheck(Component component)
	{
		this(component, false);
	}

	/**
	 * Constructs a ComponentSecurityCheck that optionally checks the model. Note that the check
	 * still needs to be manually added to the component.
	 * 
	 * @param component
	 *            the target component for this security check.
	 * @param checkSecureModelIfExists
	 *            forces the model to be checked after this check is fired
	 * @see ISecureComponent#setSecurityCheck(ISecurityCheck)
	 * @see SecureComponentHelper#setSecurityCheck(Component, ISecurityCheck)
	 */
	public ComponentSecurityCheck(Component component, boolean checkSecureModelIfExists)
	{
		super();
		checkModel = checkSecureModelIfExists;
		if (component == null)
			throw new IllegalArgumentException("component must be specified.");
		this.component = component;
	}

	/**
	 * Checks if the user is authenticated for this component. if the model is also checked both the
	 * model and the component need to be authenticated before we return true.
	 * 
	 * @see ISecurityCheck#isAuthenticated()
	 * @see WaspAuthorizationStrategy#isComponentAuthenticated(Component)
	 */
	public boolean isAuthenticated()
	{
		boolean result = getStrategy().isComponentAuthenticated(getComponent());
		if (result && checkSecureModel() && SecureComponentHelper.hasSecureModel(getComponent()))
			return ((ISecureModel<?>)getComponent().getDefaultModel()).isAuthenticated(getComponent());
		return result;
	}

	/**
	 * Returns the target component for this securitycheck.
	 * 
	 * @return the component
	 */
	protected final Component getComponent()
	{
		return component;
	}

	/**
	 * Checks if the user is authorized for this component. if the model is also checked both the
	 * model and the component need to be authorized before we return true.
	 * 
	 * @return true if the component (and optionally the model) are authorized, false otherwise.
	 * @see ISecurityCheck#isActionAuthorized(WaspAction)
	 * @see WaspAuthorizationStrategy#isComponentAuthorized(Component, WaspAction)
	 * @see WaspAuthorizationStrategy#isModelAuthorized(ISecureModel, Component, WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		if (!isAuthenticated())
			throw new RestartResponseAtInterceptPageException(getLoginPage());
		boolean result = getStrategy().isComponentAuthorized(getComponent(), action);
		if (result && checkSecureModel() && SecureComponentHelper.hasSecureModel(getComponent()))
			return ((ISecureModel<?>)getComponent().getDefaultModel()).isAuthorized(getComponent(),
				action);
		return result;
	}

	/**
	 * Flags if we need to check the {@link ISecureModel} of a component if it exists at all.
	 * 
	 * @return true if we must check the model, false otherwise.
	 */
	protected final boolean checkSecureModel()
	{
		return checkModel;
	}

}
