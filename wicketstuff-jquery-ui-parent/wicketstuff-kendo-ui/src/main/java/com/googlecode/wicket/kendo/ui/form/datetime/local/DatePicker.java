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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.DateUtils;
import com.googlecode.wicket.jquery.core.utils.LocaleUtils;
import com.googlecode.wicket.kendo.ui.form.datetime.DatePickerBehavior;

/**
 * Provides a Kendo UI date-picker based on a {@link LocalTextField}<br>
 * The code is quite identical to the jQuery DatePicker
 *
 * @author Sebastien Briquet - sebfz1
 */
public class DatePicker extends LocalTextField<LocalDate> // NOSONAR
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public DatePicker(String id)
	{
		this(id, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public DatePicker(String id, Options options)
	{
		this(id, null, null, DateUtils.LOCAL_DATE_PATTERN, options);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 */
	public DatePicker(String id, Locale locale)
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
	public DatePicker(String id, Locale locale, Options options)
	{
		this(id, null, locale, LocaleUtils.getLocaleDatePattern(locale, DateUtils.LOCAL_DATE_PATTERN), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public DatePicker(String id, String pattern)
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
	public DatePicker(String id, String pattern, Options options)
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
	public DatePicker(String id, final Locale locale, String pattern)
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
	public DatePicker(String id, final Locale locale, final String pattern, Options options)
	{
		this(id, null, locale, pattern, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public DatePicker(String id, IModel<LocalDate> model)
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
	public DatePicker(String id, IModel<LocalDate> model, Options options)
	{
		this(id, model, null, DateUtils.LOCAL_DATE_PATTERN, options);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 */
	public DatePicker(String id, IModel<LocalDate> model, Locale locale)
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
	public DatePicker(String id, IModel<LocalDate> model, Locale locale, Options options)
	{
		this(id, model, locale, LocaleUtils.getLocaleDatePattern(locale, DateUtils.LOCAL_DATE_PATTERN), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public DatePicker(String id, IModel<LocalDate> model, String pattern)
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
	public DatePicker(String id, IModel<LocalDate> model, String pattern, Options options)
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
	public DatePicker(String id, IModel<LocalDate> model, final Locale locale, String pattern)
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
	public DatePicker(String id, IModel<LocalDate> model, final Locale locale, final String pattern, Options options)
	{
		super(id, model, locale, pattern, options, LocalDate.class, DatePicker.newConverter(pattern));
	}

	// Properties //

	@Override
	protected String getMethod()
	{
		return DatePickerBehavior.METHOD;
	}

	@Override
	protected String[] getInputTypes()
	{
		return new String[] { "text", "date" };
	}

	// Factories //
	
	/**
	 * Gets a new date {@link IConverter}.
	 * 
	 * @param format the date format
	 * @return the converter
	 */
	private static IConverter<LocalDate> newConverter(final String pattern)
	{
		final String corrected = correctPattern(pattern);

		return new IConverter<LocalDate>() { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public LocalDate convertToObject(String value, Locale locale)
			{
				try
				{
					return LocalDate.parse(value, DateTimeFormatter.ofPattern(corrected, locale));
				}
				catch (DateTimeParseException e)
				{
					throw new ConversionException(e.getMessage(), e);
				}
			}

			@Override
			public String convertToString(LocalDate date, Locale locale)
			{
				return date != null ? date.format(DateTimeFormatter.ofPattern(corrected, locale)) : null;
			}
		};
	}

	/**
	 * Correct the supplied pattern<br>
	 * Single 'y' is allowed in Java11, leads to a bad string conversion with {@link DateTimeFormatter}.<br>
	 * ie: LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern)) returns 0019-11-29
	 * 
	 * @param pattern the java date pattern
	 * @return the corrected pattern
	 */
	static String correctPattern(final String pattern) {
		String corrected = pattern;

		if (corrected.contains("y") && !corrected.contains("yy")) {
			corrected = corrected.replace("y", "yy");
		}

		return corrected;
	}
}
