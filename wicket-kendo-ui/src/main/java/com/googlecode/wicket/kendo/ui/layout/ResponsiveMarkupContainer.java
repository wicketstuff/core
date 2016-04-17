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
package com.googlecode.wicket.kendo.ui.layout;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a Kendo UI responsive panel based on a {@link WebMarkupContainer}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.21.0
 * @since 7.1.0
 */
public class ResponsiveMarkupContainer extends WebMarkupContainer implements IJQueryWidget, IResponsiveListener
{
	private static final long serialVersionUID = 1L;

	private final Options options;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public ResponsiveMarkupContainer(String id)
	{
		this(id, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public ResponsiveMarkupContainer(String id, Options options)
	{
		super(id);

		this.options = options;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the model
	 */
	public ResponsiveMarkupContainer(String id, IModel<?> model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the model
	 * @param options the {@link Options}
	 */
	public ResponsiveMarkupContainer(String id, IModel<?> model, Options options)
	{
		super(id, model);

		this.options = options;
	}

	// Properties //

	@Override
	public boolean isOpenEventEnabled()
	{
		return false;
	}

	@Override
	public boolean isCloseEventEnabled()
	{
		return false;
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
		// noop
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onOpen(AjaxRequestTarget target)
	{
		// noop
	}

	@Override
	public void onClose(AjaxRequestTarget target)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ResponsiveBehavior(selector, this.options, this);
	}
}
