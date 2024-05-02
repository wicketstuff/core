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
package com.googlecode.wicket.kendo.ui.markup.html.link;

import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;

import com.googlecode.wicket.jquery.core.IJQuerySecurityProvider;
import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.form.button.ButtonBehavior;

/**
 * Provides a Kendo UI button based on a built-in {@code AjaxLink}
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 * @since 6.19.0
 * @since 7.0.0
 */
public abstract class AjaxLink<T> extends org.apache.wicket.ajax.markup.html.AjaxLink<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	private final String icon;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public AjaxLink(String id)
	{
		this(id, KendoIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 */
	public AjaxLink(String id, String icon)
	{
		super(id);

		this.icon = icon;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AjaxLink(String id, IModel<T> model)
	{
		this(id, model, KendoIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 */
	public AjaxLink(String id, IModel<T> model, String icon)
	{
		super(id, model);

		this.icon = icon;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("enable", this.isEnabledInHierarchy());

		// icon //
		final String icon = this.getIcon();

		if (!KendoIcon.isNone(icon))
		{
			behavior.setOption("icon", Options.asString(icon));
		}
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
	}

	// Properties //

	/**
	 * Gets the icon being displayed in the button
	 * 
	 * @return the icon
	 */
	protected String getIcon()
	{
		return this.icon;
	}

	// Factories //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ButtonBehavior(selector);
	}

	/**
	 * Provides a Kendo UI button based on an {@link AjaxLink}, protected by roles. Roles are checked against an {@link IJQuerySecurityProvider}<br>
	 * Assuming the {@link WebSession} is implementing {@link IJQuerySecurityProvider} if not provided.
	 *
	 * @param <T> the model object type
	 */
	public abstract static class SecuredAjaxLink<T> extends AjaxLink<T> // NOSONAR
	{
		private static final long serialVersionUID = 1L;

		private final String[] roles;
		private final IJQuerySecurityProvider provider;

		/**
		 * Constructor
		 *
		 * @param id the markup id
		 * @param roles list of roles allowed to enable the button
		 */
		public SecuredAjaxLink(String id, String[] roles)
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
		public SecuredAjaxLink(String id, IJQuerySecurityProvider provider, String[] roles)
		{
			super(id);

			this.roles = roles;
			this.provider = provider;
		}

		/**
		 * Constructor
		 *
		 * @param id the markup id
		 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
		 * @param roles list of roles allowed to enable the button
		 */
		public SecuredAjaxLink(String id, String icon, String[] roles)
		{
			this(id, icon, (IJQuerySecurityProvider) WebSession.get(), roles);
		}

		/**
		 * Constructor
		 *
		 * @param id the markup id
		 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
		 * @param provider the {@link IJQuerySecurityProvider} that will check roles
		 * @param roles list of roles allowed to enable the button
		 */
		public SecuredAjaxLink(String id, String icon, IJQuerySecurityProvider provider, String[] roles)
		{
			super(id, icon);

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
		public SecuredAjaxLink(String id, IModel<T> model, String[] roles)
		{
			this(id, model, (IJQuerySecurityProvider) WebSession.get(), roles);
		}

		/**
		 * Constructor
		 *
		 * @param id the markup id
		 * @param model the {@link IModel}
		 * @param provider the {@link IJQuerySecurityProvider} that will check roles
		 * @param roles list of roles allowed to enable the button
		 */
		public SecuredAjaxLink(String id, IModel<T> model, IJQuerySecurityProvider provider, String[] roles)
		{
			super(id, model);

			this.roles = roles;
			this.provider = provider;
		}

		/**
		 * Constructor
		 *
		 * @param id the markup id
		 * @param model the {@link IModel}
		 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
		 * @param roles list of roles allowed to enable the button
		 */
		public SecuredAjaxLink(String id, IModel<T> model, String icon, String[] roles)
		{
			this(id, model, icon, (IJQuerySecurityProvider) WebSession.get(), roles);
		}

		/**
		 * Constructor
		 *
		 * @param id the markup id
		 * @param model the {@link IModel}
		 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
		 * @param provider the {@link IJQuerySecurityProvider} that will check roles
		 * @param roles list of roles allowed to enable the button
		 */
		public SecuredAjaxLink(String id, IModel<T> model, String icon, IJQuerySecurityProvider provider, String[] roles)
		{
			super(id, model, icon);

			this.roles = roles;
			this.provider = provider;
		}

		// Properties //

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
			return this.isLocked() ? KendoIcon.LOCK : super.getIcon();
		}

		// Events //

		@Override
		protected void onConfigure()
		{
			super.onConfigure();

			this.setEnabled(!this.isLocked());
		}
	}
}
