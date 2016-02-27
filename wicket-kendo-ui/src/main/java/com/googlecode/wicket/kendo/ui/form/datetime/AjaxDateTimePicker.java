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
package com.googlecode.wicket.kendo.ui.form.datetime;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxPostBehavior;
import com.googlecode.wicket.jquery.core.event.IValueChangedListener;
import com.googlecode.wicket.kendo.ui.ajax.OnChangeAjaxBehavior;

/**
 * Provides a datetime-picker based on a {@link AjaxDatePicker} and a {@link AjaxTimePicker}<br/>
 * This ajax version will post boht components, using a {@link JQueryAjaxPostBehavior}, when the 'change' javascript method is called.
 *
 * @author Sebastien Briquet - sebfz1
 */
public class AjaxDateTimePicker extends DateTimePicker implements IJQueryAjaxAware, IValueChangedListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public AjaxDateTimePicker(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param datePattern the SimpleDateFormat pattern for the date
	 * @param timePattern the SimpleDateFormat pattern for the time
	 */
	public AjaxDateTimePicker(String id, String datePattern, String timePattern)
	{
		super(id, datePattern, timePattern);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param date the initial date
	 */
	public AjaxDateTimePicker(String id, IModel<Date> date)
	{
		super(id, date);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param date the initial date
	 * @param datePattern the SimpleDateFormat pattern for the date
	 * @param timePattern the SimpleDateFormat pattern for the time
	 */
	public AjaxDateTimePicker(String id, IModel<Date> date, String datePattern, String timePattern)
	{
		super(id, date, datePattern, timePattern);
	}

	// Events //
	/**
	 * {@inheritDoc} <br/>
	 * <i>Not intended to be overridden</i>
	 */
	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		this.processInput();
		this.onValueChanged(target);
	}

	@Override
	public void onValueChanged(IPartialPageRequestHandler handler)
	{
		// noop
	}

	// Factories //

	@Override
	protected DatePicker newDatePicker(String id, IModel<Date> model, String datePattern, Options options)
	{
		return new AjaxDatePicker(id, model, datePattern, options) {

			private static final long serialVersionUID = 1L;

			@Override
			public JQueryBehavior newWidgetBehavior(String selector)
			{
				return new DatePickerBehavior(selector, this.options) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onAjax(AjaxRequestTarget target, JQueryEvent event)
					{
						AjaxDateTimePicker.this.onAjax(target, event);
					}

					@Override
					protected JQueryAjaxPostBehavior newOnChangeAjaxBehavior(IJQueryAjaxAware source)
					{
						return new OnChangeAjaxBehavior(source, AjaxDateTimePicker.this.datePicker, AjaxDateTimePicker.this.timePicker);
					}
				};
			}
		};
	}

	@Override
	protected TimePicker newTimePicker(String id, IModel<Date> model, String timePattern, Options options)
	{
		return new AjaxTimePicker(id, model, timePattern, options) {

			private static final long serialVersionUID = 1L;

			@Override
			public JQueryBehavior newWidgetBehavior(String selector)
			{
				return new TimePickerBehavior(selector, this.options) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onAjax(AjaxRequestTarget target, JQueryEvent event)
					{
						AjaxDateTimePicker.this.onAjax(target, event);
					}

					@Override
					protected JQueryAjaxPostBehavior newOnChangeAjaxBehavior(IJQueryAjaxAware source)
					{
						return new OnChangeAjaxBehavior(source, AjaxDateTimePicker.this.datePicker, AjaxDateTimePicker.this.timePicker);
					}
				};
			}
		};
	}
}
