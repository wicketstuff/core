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

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.form.button.ButtonBehavior;

/**
 * Provides a Kendo UI button based on a built-in <code>ExternalLink</code>
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.19.0
 * @since 7.0.0
 */
public class ExternalLink extends org.apache.wicket.markup.html.link.ExternalLink implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	private final String icon;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param href the href attribute to set
	 */
	public ExternalLink(String id, IModel<String> href)
	{
		this(id, href, KendoIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param href the href attribute to set
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 */
	public ExternalLink(String id, IModel<String> href, String icon)
	{
		super(id, href);

		this.icon = icon;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param href the href attribute to set
	 * @param label the label (body)
	 */
	public ExternalLink(String id, IModel<String> href, final IModel<?> label)
	{
		this(id, href, label, KendoIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param href the href attribute to set
	 * @param label the label (body)
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 */
	public ExternalLink(String id, IModel<String> href, final IModel<?> label, String icon)
	{
		super(id, href, label);

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
