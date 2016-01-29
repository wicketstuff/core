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

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a Kendo UI button with an ajax indicator
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class IndicatingButton extends Button
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public IndicatingButton(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public IndicatingButton(String id, IModel<String> model)
	{
		super(id, model);
	}

	// Properties //

	/**
	 * Indicates whether the button will be disabled on-click to prevent double submit<br/>
	 * <br/>
	 * <b>Warning:</b> the button will not be send as part of the post because of its disabled state. Therefore {@link Button#onSubmit()} will not be reached, {@link Form#onSubmit()} should be used instead.
	 *
	 * @return false by default
	 */
	protected boolean isDisabledOnClick()
	{
		return false;
	}

	// Event //
	
	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);

		behavior.setOption("enable", this.isEnabledInHierarchy());
	}

	// IJQueryWidget //

	@Override
	public ButtonBehavior newWidgetBehavior(String selector)
	{
		return new AjaxIndicatingButtonBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Options newOnClickOptions()
			{
				Options options = super.newOnClickOptions();

				if (IndicatingButton.this.isDisabledOnClick())
				{
					options.set("enable", false);
				}

				return options;
			}
		};
	}
}
