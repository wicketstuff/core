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
import java.util.Locale;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.event.IValueChangedListener;

/**
 * Provides a Kendo UI ajax date-picker<br>
 * {@code AjaxTimePicker} &#38; {@code local.AjaxTimePicker} share the same code
 *
 * @author Sebastien Briquet - sebfz1
 */
public class AjaxTimePicker extends TimePicker implements IValueChangedListener // NOSONAR
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public AjaxTimePicker(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, Options options)
	{
		super(id, options);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 */
	public AjaxTimePicker(String id, Locale locale)
	{
		super(id, locale);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, Locale locale, Options options)
	{
		super(id, locale, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public AjaxTimePicker(String id, String pattern)
	{
		super(id, pattern);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, String pattern, Options options)
	{
		super(id, pattern, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public AjaxTimePicker(String id, final Locale locale, String pattern)
	{
		super(id, locale, pattern);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, final Locale locale, final String pattern, Options options)
	{
		super(id, locale, pattern, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AjaxTimePicker(String id, IModel<Date> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, IModel<Date> model, Options options)
	{
		super(id, model, options);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 */
	public AjaxTimePicker(String id, IModel<Date> model, Locale locale)
	{
		super(id, model, locale);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, IModel<Date> model, Locale locale, Options options)
	{
		super(id, model, locale, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public AjaxTimePicker(String id, IModel<Date> model, String pattern)
	{
		super(id, model, pattern);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, IModel<Date> model, String pattern, Options options)
	{
		super(id, model, pattern, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public AjaxTimePicker(String id, IModel<Date> model, final Locale locale, String pattern)
	{
		super(id, model, locale, pattern);
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, IModel<Date> model, final Locale locale, final String pattern, Options options)
	{
		super(id, model, locale, pattern, options);
	}

	// Events //

	@Override
	public void onValueChanged(IPartialPageRequestHandler handler)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		IValueChangedListener listener = new IValueChangedListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(IPartialPageRequestHandler handler)
			{
				AjaxTimePicker.this.processInput();
				AjaxTimePicker.this.onValueChanged(handler);
			}
		};

		return new TimePickerBehavior(selector, this.options, listener);
	}
}
