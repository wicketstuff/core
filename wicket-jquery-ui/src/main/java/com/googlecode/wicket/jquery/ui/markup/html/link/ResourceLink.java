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
package com.googlecode.wicket.jquery.ui.markup.html.link;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.form.button.ButtonBehavior;

/**
 * Provides a Kendo UI button based on a built-in {@code ResourceLink}
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 * @since 6.19.0
 * @since 7.0.0
 */
public class ResourceLink<T> extends org.apache.wicket.markup.html.link.ResourceLink<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	private final String icon;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param reference the shared resource to link to
	 */
	public ResourceLink(String id, ResourceReference reference)
	{
		this(id, reference, JQueryIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param reference the shared resource to link to
	 * @param icon either a {@link JQueryIcon} constant or a 'ui-icon-xxx' css class
	 */
	public ResourceLink(String id, ResourceReference reference, String icon)
	{
		super(id, reference);

		this.icon = icon;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param reference the shared resource to link to
	 * @param parameters the resource parameters
	 */
	public ResourceLink(String id, ResourceReference reference, PageParameters parameters)
	{
		this(id, reference, parameters, JQueryIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param reference the shared resource to link to
	 * @param parameters the resource parameters
	 * @param icon either a {@link JQueryIcon} constant or a 'ui-icon-xxx' css class
	 */
	public ResourceLink(String id, ResourceReference reference, PageParameters parameters, String icon)
	{
		super(id, reference, parameters);

		this.icon = icon;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param resource the resource
	 */
	public ResourceLink(String id, IResource resource)
	{
		this(id, resource, JQueryIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param resource the resource
	 * @param icon either a {@link JQueryIcon} constant or a 'ui-icon-xxx' css class
	 */
	public ResourceLink(String id, IResource resource, String icon)
	{
		super(id, resource);

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
		behavior.setOption("disabled", !this.isEnabledInHierarchy());
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
	}

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ButtonBehavior(selector, this.icon);
	}
}
