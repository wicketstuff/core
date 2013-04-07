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
package com.googlecode.wicket.jquery.ui.form.datepicker;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxPostBehavior;
import com.googlecode.wicket.jquery.core.event.IValueChangedListener;

/**
 * Provides a jQuery date-picker based on a {@link DateTextField}<br/>
 * This ajax version will post the {@link Component}, using a {@link JQueryAjaxPostBehavior}, when the 'onSelect' javascript method is called.
 *
 * @author Sebastien Briquet - sebfz1
 */
public class AjaxDatePicker extends DatePicker implements IValueChangedListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public AjaxDatePicker(String id)
	{
		super(id);
	}

	/**
	 * @param id
	 * @param options
	 */
	public AjaxDatePicker(String id, Options options)
	{
		super(id, options);
	}

	/**
	 * @param id
	 * @param pattern
	 * @param options
	 */
	public AjaxDatePicker(String id, String pattern, Options options)
	{
		super(id, pattern, options);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AjaxDatePicker(String id, IModel<Date> model)
	{
		super(id, model);
	}

	/**
	 * TODO javadoc
	 * @param id
	 * @param model
	 * @param options
	 */
	public AjaxDatePicker(String id, IModel<Date> model, Options options)
	{
		super(id, model, options);
	}

	/**
	 * @param id
	 * @param model
	 * @param pattern
	 * @param options
	 */
	public AjaxDatePicker(String id, IModel<Date> model, String pattern, Options options)
	{
		super(id, model, pattern, options);
	}

	// Events //
	@Override
	protected void onConfigure(JQueryBehavior behavior)
	{
	}

	@Override
	public final void onSelect(AjaxRequestTarget target, String date)
	{
		this.processInput();
		this.onValueChanged(target);
	}

	// IValueChangedListener //
	@Override
	public void onValueChanged(AjaxRequestTarget target)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new DatePickerBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isOnSelectEventEnabled()
			{
				return true;
			}

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				AjaxDatePicker.this.onConfigure(this);
			}

			@Override
			public void onSelect(AjaxRequestTarget target, String date)
			{
				AjaxDatePicker.this.onSelect(target, date);
			}
		};
	}
}
