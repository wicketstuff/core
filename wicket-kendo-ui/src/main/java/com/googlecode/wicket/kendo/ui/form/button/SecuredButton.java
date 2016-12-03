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
package com.googlecode.wicket.kendo.ui.form.button;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.IJQuerySecurityProvider;
import com.googlecode.wicket.kendo.ui.KendoIcon;

/**
 * Provides a Kendo UI button based on the built-in Button, protected by roles. Roles are checked against an {@link IJQuerySecurityProvider}<br>
 * Assuming the {@link WebSession} is implementing {@link IJQuerySecurityProvider} if not provided.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class SecuredButton extends Button
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(SecuredButton.class);

	private final IJQuerySecurityProvider provider;
	private String[] roles;

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param roles list of roles allowed to enable the button
	 */
	public SecuredButton(String id, String... roles)
	{
		this(id, (IJQuerySecurityProvider) WebSession.get(), roles);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param provider the {@link IJQuerySecurityProvider} that will check roles
	 * @param roles list of roles allowed to enable the button
	 */
	public SecuredButton(String id, IJQuerySecurityProvider provider, String... roles)
	{
		super(id);

		this.roles = roles;
		this.provider = provider;
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param roles list of roles allowed to enable the button
	 */
	public SecuredButton(String id, IModel<String> model, String... roles)
	{
		this(id, model, (IJQuerySecurityProvider) WebSession.get(), roles);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param provider the {@link IJQuerySecurityProvider}
	 * @param roles list of roles allowed to enable the button
	 */
	public SecuredButton(String id, IModel<String> model, IJQuerySecurityProvider provider, String... roles)
	{
		super(id, model);

		this.roles = roles;
		this.provider = provider;
	}

	/**
	 * Sets the roles allowed to enable the button
	 * 
	 * @param roles list of roles allowed to enable the button
	 */
	public void setRoles(String[] roles)
	{
		this.roles = roles.clone();
	}

	/**
	 * Indicates whether the button is locked.
	 * 
	 * @return the result of {@link IJQuerySecurityProvider#hasRole(String...)}
	 */
	public final boolean isLocked()
	{
		return !this.provider.hasRole(this.roles);
	}

	@Override
	protected String getIcon()
	{
		return this.isLocked() ? KendoIcon.LOCK : KendoIcon.UNLOCK;
	}

	// Events //
	@Override
	protected void onConfigure()
	{
		super.onConfigure();

		this.setEnabled(!this.isLocked());
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		if (!"button".equalsIgnoreCase(tag.getName()))
		{
			LOG.warn("SecuredButton should be applied on a 'button' tag");
		}
	}
}
