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
package org.wicketstuff.security.pages;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ComponentSecurityCheck;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.components.ISecurePage;
import org.wicketstuff.security.components.SecureComponentHelper;

/**
 * Secure version of {@link BasePage}. This class only allows logged in users with sufficient rights
 * to access the page.
 * 
 * @author marrink
 */
public class SecureTestPage extends BasePage implements ISecurePage
{
	private static final long serialVersionUID = 1L;

	public SecureTestPage()
	{
		super();
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param model
	 */
	public SecureTestPage(IModel<?> model)
	{
		super(model);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param parameters
	 */
	public SecureTestPage(PageParameters parameters)
	{
		super(parameters);
		setSecurityCheck(new ComponentSecurityCheck(this));
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
