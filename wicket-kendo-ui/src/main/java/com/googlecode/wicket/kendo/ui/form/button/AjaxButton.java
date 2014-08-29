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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.form.button.Button.ButtonBehavior;

/**
 * Provides a Kendo UI button based on the built-in AjaxButton
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class AjaxButton extends org.apache.wicket.ajax.markup.html.form.AjaxButton implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 */
	public AjaxButton(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param form the {@link Form}
	 */
	public AjaxButton(String id, Form<?> form)
	{
		super(id, form);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AjaxButton(String id, IModel<String> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param form the {@link Form}
	 */
	public AjaxButton(String id, IModel<String> model, Form<?> form)
	{
		super(id, model, form);
	}

	// Events //
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

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public ButtonBehavior newWidgetBehavior(String selector)
	{
		return new ButtonBehavior(selector);
	}
}
