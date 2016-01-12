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

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.models.ISecureModel;

/**
 * Security check for when you replace panels on a page instead of using new pages. This is done by
 * checking if the panel / container class is authorized just like it does with pages.
 * 
 * @author marrink
 */
public class ContainerSecurityCheck extends ComponentSecurityCheck
{

	private static final long serialVersionUID = 1L;

	private final boolean enableAuthentication;

	/**
	 * Constructs a new check on the container. By default neither the model or the authentication
	 * is checked.
	 * 
	 * @param component
	 *            the container
	 */
	public ContainerSecurityCheck(MarkupContainer component)
	{
		super(component);
		enableAuthentication = false;
	}

	/**
	 * Constructs a new check on the container. By default authentication is not checked.
	 * 
	 * @param component
	 *            the container
	 * @param checkSecureModelIfExists
	 *            flag for enabling or disabling the securitycheck on the model
	 */
	public ContainerSecurityCheck(MarkupContainer component, boolean checkSecureModelIfExists)
	{
		this(component, checkSecureModelIfExists, false);
	}

	/**
	 * Constructs a new check on the container with the given flags for model and authentication
	 * checks.
	 * 
	 * @param component
	 * @param checkSecureModelIfExists
	 *            flag for enabling or disabling the securitycheck on the model
	 * @param enableAuthenticationCheck
	 *            flag if authentication should be checked for this component
	 */
	public ContainerSecurityCheck(MarkupContainer component, boolean checkSecureModelIfExists,
		boolean enableAuthenticationCheck)
	{
		super(component, checkSecureModelIfExists);
		enableAuthentication = enableAuthenticationCheck;
	}

	/**
	 * Checks the container class.
	 * 
	 * @see org.wicketstuff.security.checks.ComponentSecurityCheck#isActionAuthorized(org.wicketstuff.security.actions.WaspAction)
	 */
	@Override
	public boolean isActionAuthorized(WaspAction action)
	{
		if (enableAuthentication && !isAuthenticated())
			throw new RestartResponseAtInterceptPageException(getLoginPage());
		boolean result = getStrategy().isComponentAuthorized(getComponent(), action) ||
			getStrategy().isClassAuthorized(getComponent().getClass(), action);
		if (result && checkSecureModel() && SecureComponentHelper.hasSecureModel(getComponent()))
			return ((ISecureModel<?>)getComponent().getDefaultModel()).isAuthorized(getComponent(),
				action);
		return result;
	}
}
