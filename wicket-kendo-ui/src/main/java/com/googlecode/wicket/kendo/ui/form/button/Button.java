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

import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.KendoIcon;

/**
 * Provides a Kendo UI button based on the built-in Button
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Button extends org.apache.wicket.markup.html.form.Button  implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public Button(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Button(String id, IModel<String> model)
	{
		super(id, model);
	}

	// Properties //

	/**
	 * Gets the icon being displayed in the button
	 *
	 * @return {@link KendoIcon#NONE} by default
	 */
	protected String getIcon()
	{
		return KendoIcon.NONE; // used in #onConfigure
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this)); // cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		if (!KendoIcon.isNone(this.getIcon()))
		{
			behavior.setOption("icon", Options.asString(this.getIcon()));
		}
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public ButtonBehavior newWidgetBehavior(String selector)
	{
		return new ButtonBehavior(selector);
	}

	/**
	 * Provides a jQuery button {@link JQueryBehavior}
	 */
	public static class ButtonBehavior extends KendoUIBehavior
	{
		private static final long serialVersionUID = 1L;
		private static final String METHOD = "kendoButton";

		public ButtonBehavior(String selector)
		{
			super(selector, METHOD);
		}

		public ButtonBehavior(String selector, Options options)
		{
			super(selector, METHOD, options);
		}

		public ButtonBehavior(String selector, String icon)
		{
			super(selector, METHOD, new Options("icon", Options.asString(icon)));
		}
	}
}
