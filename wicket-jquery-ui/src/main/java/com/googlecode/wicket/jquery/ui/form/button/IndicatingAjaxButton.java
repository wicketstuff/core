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

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a jQuery button based on the {@link AjaxButton}, with an ajax indicator the time the {@link #onSubmit()} process.
 *
 * @since 6.0
 * @author Sebastien Briquet - sebfz1
 */
public abstract class IndicatingAjaxButton extends AjaxButton
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(IndicatingAjaxButton.class);

	public enum Position
	{
		LEFT, RIGHT
	}

	private Position position = Position.LEFT;

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 */
	public IndicatingAjaxButton(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param form the {@link Form}
	 */
	public IndicatingAjaxButton(String id, Form<?> form)
	{
		super(id, form);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public IndicatingAjaxButton(String id, IModel<String> model)
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
	public IndicatingAjaxButton(String id, IModel<String> model, Form<?> form)
	{
		super(id, model, form);
	}

	public IndicatingAjaxButton setPosition(Position position)
	{
		this.position = position;
		return this;
	}

	// Events //

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		if (!"button".equalsIgnoreCase(tag.getName()))
		{
			LOG.warn("IndicatingAjaxButton should be applied on a 'button' tag");
		}
	}

	// Properties //

	/**
	 * Indicates whether the button will be disabled on-click to prevent double submit
	 *
	 * @return false by default
	 */
	protected boolean isDisabledOnClick()
	{
		return false;
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new AjaxIndicatingButtonBehavior(selector, this.getIcon(), this.position) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Options newOnClickOptions()
			{
				Options options = super.newOnClickOptions();

				if (IndicatingAjaxButton.this.isDisabledOnClick())
				{
					options.set("disabled", true);
				}

				return options;
			}

			@Override
			protected Options newOnAjaxStopOptions()
			{
				Options options = super.newOnAjaxStopOptions();

				if (IndicatingAjaxButton.this.isDisabledOnClick())
				{
					options.set("disabled", !IndicatingAjaxButton.this.isEnabledInHierarchy());
				}

				return options;
			}
		};
	}
}
