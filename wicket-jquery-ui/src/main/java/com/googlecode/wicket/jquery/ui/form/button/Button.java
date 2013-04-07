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
package com.googlecode.wicket.jquery.ui.form.button;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.JQueryIcon;

/**
 * Provides a jQuery button based on the built-in Button
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Button extends org.apache.wicket.markup.html.form.Button implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public Button(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Button(String id, IModel<String> model)
	{
		super(id, model);
	}

	/**
	 * Gets the icon being displayed in the button
	 */
	protected String getIcon()
	{
		return JQueryIcon.NONE; // used in #onConfigure
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this)); //cannot be in ctor as the markupId may be set manually afterward
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(ButtonBehavior behavior)
	{
		if (!JQueryIcon.NONE.equals(this.getIcon()))
		{
			behavior.setOption("icons", String.format("{ primary: '%s' }", this.getIcon()));
		}
	}

	// IJQueryWidget //
	@Override
	public ButtonBehavior newWidgetBehavior(String selector)
	{
		return new ButtonBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				Button.this.onConfigure(this);
			}
		};
	}

	/**
	 * TODO: javadoc
	 */
	public static class ButtonBehavior extends JQueryBehavior
	{
		private static final long serialVersionUID = 1L;
		private static final String METHOD = "button";

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
			super(selector, METHOD, new Options("icons", String.format("{ primary: '%s' }", icon)));
		}
	}
}
