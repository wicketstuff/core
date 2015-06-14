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

import java.util.Locale;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.Strings;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.LocaleUtils;

/**
 * Provides a Kendo UI date-picker based on a {@link LocalTextField}<br/>
 * The code is quite identical to the jQuery DatePicker
 *
 * @author Sebastien Briquet - sebfz1
 */
public class DatePicker extends LocalTextField<LocalDate>
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoDatePicker";

	protected static final String DEFAULT_PATTERN = "MM/dd/yyyy"; // default java date pattern

	/**
	 * Gets a new date {@link IConverter}.
	 * 
	 * @param format the date format
	 * @return the converter
	 */
	private static IConverter<LocalDate> newConverter(final String pattern)
	{
		return new IConverter<LocalDate>() {

			private static final long serialVersionUID = 1L;

			@Override
			public LocalDate convertToObject(String value, Locale locale) throws ConversionException
			{
				try
				{
					return Strings.isEmpty(value) ? null : LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern));
				}
				catch (DateTimeParseException e)
				{
					throw new ConversionException(e.getMessage(), e);
				}
			}

			@Override
			public String convertToString(LocalDate date, Locale locale)
			{
				return date == null ? null : date.format(DateTimeFormatter.ofPattern(pattern));
			}
		};
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public DatePicker(String id)
	{
		this(id, DEFAULT_PATTERN, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public DatePicker(String id, Options options)
	{
		this(id, DEFAULT_PATTERN, options);
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
		this(id, null, pattern, options);
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
		this(id, null, LocaleUtils.getLocaleDatePattern(locale, DEFAULT_PATTERN), options.set("culture", Options.asString(LocaleUtils.getLangageCode(locale))));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public DatePicker(String id, IModel<LocalDate> model)
	{
		this(id, model, DEFAULT_PATTERN, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options {@link Options}
	 */
	public DatePicker(String id, IModel<LocalDate> model, Options options)
	{
		this(id, model, DEFAULT_PATTERN, options);
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
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options {@link Options}
	 */
	public DatePicker(String id, IModel<LocalDate> model, final String pattern, Options options)
	{
		super(id, model, pattern, options, LocalDate.class, DatePicker.newConverter(pattern));
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
		this(id, model, LocaleUtils.getLocaleDatePattern(locale, DEFAULT_PATTERN), options.set("culture", Options.asString(LocaleUtils.getLangageCode(locale))));
	}

	@Override
	protected String getMethod()
	{
		return METHOD;
	}
}
