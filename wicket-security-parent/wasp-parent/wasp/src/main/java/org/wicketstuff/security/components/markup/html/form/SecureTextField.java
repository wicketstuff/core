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
package org.wicketstuff.security.components.markup.html.form;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ComponentSecurityCheck;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.models.ISecureModel;

/**
 * Textfield which automatically switches between read and write mode based on the user rights. By
 * default it does not consider {@link ISecureModel}, unless told to do so by either removing the
 * securitycheck or by using the specialized constructor.
 * 
 * @author marrink
 */
public class SecureTextField<T> extends TextField<T> implements ISecureComponent
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 */
	public SecureTextField(String id)
	{
		super(id);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param id
	 * @param type
	 */
	public SecureTextField(String id, Class<T> type)
	{
		super(id, type);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param id
	 * @param object
	 */
	public SecureTextField(String id, IModel<T> object)
	{
		super(id, object);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param id
	 * @param model
	 * @param checkModel
	 *            tells the {@link ComponentSecurityCheck} to also check this model.
	 */
	public SecureTextField(String id, ISecureModel<T> model, boolean checkModel)
	{
		super(id, model);
		setSecurityCheck(new ComponentSecurityCheck(this, checkModel));
	}

	/**
	 * @param id
	 * @param model
	 * @param type
	 */
	public SecureTextField(String id, IModel<T> model, Class<T> type)
	{
		super(id, model, type);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param id
	 * @param model
	 * @param checkModel
	 *            tells the {@link ComponentSecurityCheck} to also check this model.
	 * @param type
	 */
	public SecureTextField(String id, ISecureModel<T> model, boolean checkModel, Class<T> type)
	{
		super(id, model, type);
		setSecurityCheck(new ComponentSecurityCheck(this, checkModel));
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
