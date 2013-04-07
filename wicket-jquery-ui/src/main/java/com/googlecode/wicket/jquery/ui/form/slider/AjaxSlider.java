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
package com.googlecode.wicket.jquery.ui.form.slider;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxPostBehavior;
import com.googlecode.wicket.jquery.core.event.IValueChangedListener;
import com.googlecode.wicket.jquery.core.event.JQueryAjaxChangeBehavior;
import com.googlecode.wicket.jquery.core.event.JQueryAjaxChangeBehavior.ChangeEvent;

/**
 * Provides a jQuery range slider based on a {@link FormComponentPanel}
 * This ajax version will post the {@link Component}, using a {@link JQueryAjaxChangeBehavior}, when the 'change' javascript method is called.
 *
 * @author Sebastien Briquet - sebfz1
 */
public class AjaxSlider extends Slider implements IJQueryAjaxAware, IValueChangedListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public AjaxSlider(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AjaxSlider(String id, IModel<Integer> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param label {@link Label} on which the current slide value will be displayed
	 */
	public AjaxSlider(String id, IModel<Integer> model, Label label)
	{
		super(id, model, label);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param input the {@link TextField} that will host the value
	 */
	public AjaxSlider(String id, Model<Integer> model, TextField<Integer> input)
	{
		super(id, model, input);
	}

	// Events //
	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ChangeEvent)
		{
			//In case of issue, consider copying code from AjaxFormComponentUpdatingBehavior.onEvent
			super.input.processInput();
			this.validate();

			if (this.isValid() && super.input.isValid())
			{
				this.onValueChanged(target);
			}
			else
			{
				this.onError(target);
			}
		}
	}

	@Override
	public void onValueChanged(AjaxRequestTarget target)
	{
	}

	/**
	 * Triggered when the validation failed (ie, not input provided)
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onError(AjaxRequestTarget target)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new AjaxSliderBehavior(selector, super.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				AjaxSlider.this.onConfigure(this); //important, should call Slider#onConfigure(JQueryBehavior)
			}

			@Override
			public void onAjax(AjaxRequestTarget target, JQueryEvent event)
			{
				AjaxSlider.this.onAjax(target, event);
			}

			@Override
			protected JQueryAjaxPostBehavior newOnChangeBehavior()
			{
				return new JQueryAjaxChangeBehavior(this, AjaxSlider.this.input);
			}
		};
	}
}
