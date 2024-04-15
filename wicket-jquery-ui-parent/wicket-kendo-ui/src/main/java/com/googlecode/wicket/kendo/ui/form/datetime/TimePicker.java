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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.DateUtils;
import com.googlecode.wicket.jquery.core.utils.LocaleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.utils.KendoDateTimeUtils;

/**
 * Provides a Kendo UI time-picker<br>
 * It should be created on a HTML &lt;input type="text" /&gt; element
 *
 * @author Sebastien Briquet - sebfz1
 */
public class TimePicker extends DateTextField implements IJQueryWidget // NOSONAR
{
	private static final long serialVersionUID = 1L;

	private final Locale locale;

	protected final Options options;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public TimePicker(String id)
	{
		this(id, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public TimePicker(String id, Options options)
	{
		this(id, null, null, DateUtils.TIME_PATTERN, options);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 */
	public TimePicker(String id, Locale locale)
	{
		this(id, locale, new Options());
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 * @param options the {@link Options}
	 */
	public TimePicker(String id, Locale locale, Options options)
	{
		this(id, null, locale, LocaleUtils.getLocaleTimePattern(locale, DateUtils.TIME_PATTERN), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public TimePicker(String id, String pattern)
	{
		this(id, pattern, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options the {@link Options}
	 */
	public TimePicker(String id, String pattern, Options options)
	{
		this(id, null, null, pattern, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public TimePicker(String id, final Locale locale, String pattern)
	{
		this(id, locale, pattern, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options the {@link Options}
	 */
	public TimePicker(String id, final Locale locale, final String pattern, Options options)
	{
		this(id, null, locale, pattern, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public TimePicker(String id, IModel<Date> model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public TimePicker(String id, IModel<Date> model, Options options)
	{
		this(id, model, null, DateUtils.TIME_PATTERN, options);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 */
	public TimePicker(String id, IModel<Date> model, Locale locale)
	{
		this(id, model, locale, new Options());
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 * @param options the {@link Options}
	 */
	public TimePicker(String id, IModel<Date> model, Locale locale, Options options)
	{
		this(id, model, locale, LocaleUtils.getLocaleTimePattern(locale, DateUtils.TIME_PATTERN), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public TimePicker(String id, IModel<Date> model, String pattern)
	{
		this(id, model, pattern, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options the {@link Options}
	 */
	public TimePicker(String id, IModel<Date> model, String pattern, Options options)
	{
		this(id, model, null, pattern, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public TimePicker(String id, IModel<Date> model, final Locale locale, String pattern)
	{
		this(id, model, locale, pattern, new Options());
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
	public TimePicker(String id, IModel<Date> model, final Locale locale, final String pattern, Options options)
	{
		super(id, model, pattern);

		this.locale = locale;
		this.options = options;
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
		if (behavior.getOption("culture") == null)
		{
			behavior.setOption("culture", Options.asString(LocaleUtils.getLangageCode(this.getLocale())));
		}

		if (behavior.getOption("format") == null)
		{
			behavior.setOption("format", Options.asString(KendoDateTimeUtils.toPattern(this.getTextFormat())));
		}
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// Properties //

	@Override
	public Locale getLocale()
	{
		if (this.locale != null)
		{
			return this.locale;
		}

		return super.getLocale();
	}

	/**
	 * Marked as final. It is - probably - not consistent to have a pattern different from the display
	 */
	@Override
	public final String getTextFormat()
	{
		return super.getTextFormat();
	}

	/**
	 * Gets a string representation given the time pattern in use.
	 *
	 * @return the model object as string
	 */
	public String getModelObjectAsString()
	{
		Date date = this.getModelObject();

		if (date != null)
		{
			return new SimpleDateFormat(this.getTextFormat(), this.getLocale()).format(date);
		}

		return "";
	}

	@Override
	protected String[] getInputTypes()
	{
		return new String[] { "text", "time" };
	}

	// Methods //

	@Override
	public String getInput()
	{
		String input = super.getInput();

		if (input != null)
		{
			return KendoDateTimeUtils.convert(input);
		}

		return null;
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new KendoUIBehavior(selector, TimePickerBehavior.METHOD, this.options);
	}
}
