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
package com.googlecode.wicket.kendo.ui.form.datetime.local;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.DateUtils;
import com.googlecode.wicket.jquery.core.utils.LocaleUtils;
import com.googlecode.wicket.kendo.ui.form.datetime.TimePickerBehavior;

/**
 * Provides a Kendo UI time-picker based on a {@link LocalTextField}<br/>
 * It should be created on a HTML &lt;input type="text" /&gt; element
 *
 * @author Sebastien Briquet - sebfz1
 */
public class TimePicker extends LocalTextField<LocalTime>
{
	private static final long serialVersionUID = 1L;

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
		this(id, null, null, DateUtils.LOCAL_TIME_PATTERN, options);
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
		this(id, null, locale, LocaleUtils.getLocaleTimePattern(locale, DateUtils.LOCAL_TIME_PATTERN), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
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
	 * @param options the {@link Options}
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
	public TimePicker(String id, IModel<LocalTime> model)
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
	public TimePicker(String id, IModel<LocalTime> model, Options options)
	{
		this(id, model, null, DateUtils.LOCAL_TIME_PATTERN, options);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 */
	public TimePicker(String id, IModel<LocalTime> model, Locale locale)
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
	public TimePicker(String id, IModel<LocalTime> model, Locale locale, Options options)
	{
		this(id, model, locale, LocaleUtils.getLocaleTimePattern(locale, DateUtils.LOCAL_TIME_PATTERN), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public TimePicker(String id, IModel<LocalTime> model, String pattern)
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
	public TimePicker(String id, IModel<LocalTime> model, String pattern, Options options)
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
	 * @param options the {@link Options}
	 */
	public TimePicker(String id, IModel<LocalTime> model, final Locale locale, String pattern)
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
	public TimePicker(String id, IModel<LocalTime> model, final Locale locale, final String pattern, Options options)
	{
		super(id, model, locale, pattern, options, LocalTime.class, TimePicker.newConverter(pattern));
	}

	// Properties //

	@Override
	protected String getMethod()
	{
		return TimePickerBehavior.METHOD;
	}

	@Override
	protected String[] getInputTypes()
	{
		return new String[] { "text", "time" };
	}

	// Factories //

	/**
	 * Gets a new time {@link IConverter}.
	 * 
	 * @param format the time format
	 * @return the converter
	 */
	private static IConverter<LocalTime> newConverter(final String pattern)
	{
		return new IConverter<LocalTime>() {

			private static final long serialVersionUID = 1L;

			@Override
			public LocalTime convertToObject(String value, Locale locale) throws ConversionException
			{
				try
				{
					return LocalTime.parse(value, DateTimeFormatter.ofPattern(pattern, locale));
				}
				catch (DateTimeParseException e)
				{
					throw new ConversionException(e.getMessage(), e);
				}
			}

			@Override
			public String convertToString(LocalTime time, Locale locale)
			{
				return time != null ? time.format(DateTimeFormatter.ofPattern(pattern, locale)) : null;
			}
		};
	}
}
