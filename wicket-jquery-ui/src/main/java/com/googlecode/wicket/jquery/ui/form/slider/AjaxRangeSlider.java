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

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxPostBehavior;
import com.googlecode.wicket.jquery.core.event.IValueChangedListener;
import com.googlecode.wicket.jquery.core.event.JQueryAjaxChangeBehavior;
import com.googlecode.wicket.jquery.core.event.JQueryAjaxChangeBehavior.ChangeEvent;

/**
 * Provides a jQuery range slider based on a {@link FormComponentPanel}
 * This ajax version will post the {@link Component}, using a {@link JQueryAjaxPostBehavior}, when the 'change' javascript method is called.
 *
 * @author Sebastien Briquet - sebfz1
 */
public class AjaxRangeSlider extends RangeSlider implements IJQueryAjaxAware, IValueChangedListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public AjaxRangeSlider(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AjaxRangeSlider(String id, IModel<RangeValue> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param label {@link Label} on which the current slide value will be displayed
	 */
	public AjaxRangeSlider(String id, IModel<RangeValue> model, Label label)
	{
		super(id, model, label);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param lower the {@link TextField} that will host the lower value
	 * @param upper the {@link TextField} that will host the upper value
	 */
	public AjaxRangeSlider(String id, IModel<RangeValue> model, TextField<Integer> lower, TextField<Integer> upper)
	{
		super(id, model, lower, upper);
	}

	// Events //
	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ChangeEvent)
		{
			//In case of issue, consider copying code from AjaxFormComponentUpdatingBehavior.onEvent
			this.lower.processInput();
			this.upper.processInput();
			this.validate();

			if (this.isValid() && this.lower.isValid() && this.upper.isValid())
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
	 * Triggered when the validation failed (lower &gt; upper)
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onError(AjaxRequestTarget target)
	{
	}

	// Factories //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new AjaxSliderBehavior(selector, super.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				AjaxRangeSlider.this.onConfigure(this); //important, should call Slider#onConfigure(JQueryBehavior)
			}

			@Override
			public void onAjax(AjaxRequestTarget target, JQueryEvent event)
			{
				AjaxRangeSlider.this.onAjax(target, event);
			}

			@Override
			protected JQueryAjaxPostBehavior newOnChangeBehavior()
			{
				return new JQueryAjaxChangeBehavior(this, AjaxRangeSlider.this.lower, AjaxRangeSlider.this.upper);
			}
		};
	}
}
