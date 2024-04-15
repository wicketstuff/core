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
package com.googlecode.wicket.kendo.ui.datatable.button;

import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;

import com.googlecode.wicket.jquery.core.IJQuerySecurityProvider;
import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;

/**
 * Provides a secured command button object that can be used in {@link DataTable} column
 *
 * @author Sebastien Briquet - sebfz1
 */
public class SecuredToolbarButton extends ToolbarButton
{
	private static final long serialVersionUID = 1L;

	private final IJQuerySecurityProvider provider;
	private final String[] roles;

	/**
	 * Constructor for built-in commands (no property supplied)
	 *
	 * @param name the button's name
	 * @param roles list of roles allowed to enable the button
	 */
	public SecuredToolbarButton(String name, String[] roles)
	{
		this(name, roles, (IJQuerySecurityProvider) WebSession.get());
	}

	/**
	 * Constructor for built-in commands (no property supplied)
	 *
	 * @param name the button's name
	 * @param roles list of roles allowed to enable the button
	 * @param provider the {@link IJQuerySecurityProvider}
	 */
	public SecuredToolbarButton(String name, String[] roles, IJQuerySecurityProvider provider)
	{
		super(name);

		this.roles = roles;
		this.provider = provider;
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param property the property used to retrieve the row's object value
	 * @param roles list of roles allowed to enable the button
	 */
	public SecuredToolbarButton(String name, String property, String[] roles)
	{
		this(name, property, roles, (IJQuerySecurityProvider) WebSession.get());
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param property the property used to retrieve the row's object value
	 * @param roles list of roles allowed to enable the button
	 * @param provider the {@link IJQuerySecurityProvider}
	 */
	public SecuredToolbarButton(String name, String property, String[] roles, IJQuerySecurityProvider provider)
	{
		super(name, property);

		this.roles = roles;
		this.provider = provider;
	}

	/**
	 * Constructor for built-in commands (no property supplied)
	 *
	 * @param name the button's name
	 * @param text the button's text
	 * @param roles list of roles allowed to enable the button
	 */
	public SecuredToolbarButton(String name, IModel<String> text, String[] roles)
	{
		this(name, text, roles, (IJQuerySecurityProvider) WebSession.get());
	}

	/**
	 * Constructor for built-in commands (no property supplied)
	 *
	 * @param name the button's name
	 * @param text the button's text
	 * @param roles list of roles allowed to enable the button
	 * @param provider the {@link IJQuerySecurityProvider}
	 */
	public SecuredToolbarButton(String name, IModel<String> text, String[] roles, IJQuerySecurityProvider provider)
	{
		super(name, text);

		this.roles = roles;
		this.provider = provider;
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param text the button's text
	 * @param property the property used to retrieve the row's object value
	 * @param roles list of roles allowed to enable the button
	 */
	public SecuredToolbarButton(String name, IModel<String> text, String property, String[] roles)
	{
		this(name, text, property, roles, (IJQuerySecurityProvider) WebSession.get());
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param text the button's text
	 * @param property the property used to retrieve the row's object value
	 * @param roles list of roles allowed to enable the button
	 * @param provider the {@link IJQuerySecurityProvider}
	 */
	public SecuredToolbarButton(String name, IModel<String> text, String property, String[] roles, IJQuerySecurityProvider provider)
	{
		super(name, text, property);

		this.roles = roles;
		this.provider = provider;
	}

	// Properties //

	/**
	 * Gets the roles<br>
	 * <b>Caution:</b> to be overridden with care!
	 * 
	 * @return the roles
	 */
	public String[] getRoles()
	{
		return this.roles;
	}

	/**
	 * Indicates whether the button is locked.
	 * 
	 * @return the result of {@link IJQuerySecurityProvider#hasRole(String...)}
	 */
	public final boolean isLocked()
	{
		return !this.provider.hasRole(this.getRoles());
	}

	@Override
	public boolean isEnabled()
	{
		return !this.isLocked();
	}

	@Override
	public String getIconClass()
	{
		return this.isLocked() ? KendoIcon.getCssClass(KendoIcon.LOCK) : super.getIconClass();
	}
}
