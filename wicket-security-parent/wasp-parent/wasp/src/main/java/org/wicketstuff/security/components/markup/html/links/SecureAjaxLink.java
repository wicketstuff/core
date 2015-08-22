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

package org.wicketstuff.security.components.markup.html.links;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ComponentSecurityCheck;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.SecureComponentHelper;

/**
 * AjaxLink with visibility / clickability based on user rights. Requires render rights to be
 * visible, and enable rights to be clickable.
 * 
 * @author Olger Warnier
 */
abstract public class SecureAjaxLink<T> extends AjaxLink<T> implements ISecureComponent
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            wicket id as found in the HTML
	 * @param model
	 *            the model
	 */
	public SecureAjaxLink(final String id, final IModel<T> model)
	{
		super(id, model);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param id
	 *            wicket id as found in the HTML
	 */
	public SecureAjaxLink(final String id)
	{
		super(id);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#getSecurityCheck()
	 */
	public ISecurityCheck getSecurityCheck()
	{
		return SecureComponentHelper.getSecurityCheck(this);
	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#isActionAuthorized(java.lang.String)
	 */
	public boolean isActionAuthorized(String waspAction)
	{
		return SecureComponentHelper.isActionAuthorized(this, waspAction);
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

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#setSecurityCheck(org.wicketstuff.security.checks.ISecurityCheck)
	 */
	public void setSecurityCheck(ISecurityCheck check)
	{
		SecureComponentHelper.setSecurityCheck(this, check);
	}

}
