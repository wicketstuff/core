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

import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ComponentSecurityCheck;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.strategies.ClassAuthorizationStrategy;

/**
 * Basic implementation of a SecureWebPage. Note that any check added to this page using
 * {@link #setSecurityCheck(ISecurityCheck)} is too late to be considered for
 * {@link IAuthorizationStrategy#isInstantiationAuthorized(Class)} so please check your wasp
 * implementation for details on how to do that. Or see {@link ClassAuthorizationStrategy} for one
 * way of doing it.
 * 
 * @author marrink
 */
public class SecureWebPage extends WebPage implements ISecurePage
{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SecureWebPage()
	{
		super();
		setSecurityCheck(new ComponentSecurityCheck(this));
		// Note this check does not handle the right to instantiate this class,
		// we are to
		// late for that, please check your implementation for how that is
		// handled.
	}

	/**
	 * @param parameters
	 */
	public SecureWebPage(PageParameters parameters)
	{
		super(parameters);
		setSecurityCheck(new ComponentSecurityCheck(this));
		// Note this check does not handle the right to instantiate this class,
		// we are to
		// late for that, please check your implementation for how that is
		// handled.
	}

	/**
	 * @param model
	 */
	public SecureWebPage(IModel<?> model)
	{
		super(model);
		setSecurityCheck(new ComponentSecurityCheck(this));
		// Note this check does not handle the right to instantiate this class,
		// we are to
		// late for that, please check your implementation for how that is
		// handled.
	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#setSecurityCheck(org.wicketstuff.security.checks.ISecurityCheck)
	 */
	public final void setSecurityCheck(ISecurityCheck check)
	{
		SecureComponentHelper.setSecurityCheck(this, check);

	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#getSecurityCheck()
	 */
	public final ISecurityCheck getSecurityCheck()
	{
		return SecureComponentHelper.getSecurityCheck(this);
	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#isActionAuthorized(java.lang.String)
	 */
	public boolean isActionAuthorized(String action)
	{
		return SecureComponentHelper.isActionAuthorized(this, action);
	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#isActionAuthorized(WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		return SecureComponentHelper.isActionAuthorized(this, action);
	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#isAuthenticated()
	 */
	public boolean isAuthenticated()
	{
		return SecureComponentHelper.isAuthenticated(this);
	}
}
