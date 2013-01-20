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
package com.googlecode.wicket.jquery.ui.kendo.datetime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.DateConverter;
import org.apache.wicket.validation.ValidationError;

/**
 * Provides a date-time-picker based on a {@link DatePicker} and a {@link TimePicker}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class DateTimePicker extends FormComponentPanel<Date> implements ITextFormatProvider
{
	private static final long serialVersionUID = 1L;

	private DatePicker datePicker;
	private TimePicker timePicker;

	private final String datePattern;
	private final String timePattern;

	private IConverter<Date> converter;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public DateTimePicker(String id)
	{
		this(id, DatePicker.DEFAULT_PATTERN, TimePicker.DEFAULT_PATTERN);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param datePattern the SimpleDateFormat pattern for the date
	 * @param timePattern the SimpleDateFormat pattern for the time
	 */
	public DateTimePicker(String id, String datePattern, String timePattern)
	{
		super(id);

		this.datePattern = datePattern;
		this.timePattern = timePattern;
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param date the initial date
	 */
	public DateTimePicker(String id, IModel<Date> date)
	{
		this(id, date, DatePicker.DEFAULT_PATTERN, TimePicker.DEFAULT_PATTERN);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param date the initial date
	 * @param datePattern the SimpleDateFormat pattern for the date
	 * @param timePattern the SimpleDateFormat pattern for the time
	 */
	public DateTimePicker(String id, IModel<Date> date, String datePattern, String timePattern)
	{
		super(id, date);

		this.datePattern = datePattern;
		this.timePattern = timePattern;
	}

	// Methods //
	@Override
	protected void convertInput()
	{
		final IConverter<Date> converter = this.getConverter(Date.class);
		String dateInput = this.datePicker.getInput();
		String timeInput = this.timePicker.getInput();

		try
		{
			Date date = converter.convertToObject(String.format("%s %s", dateInput, timeInput), this.getLocale());
			this.setConvertedInput(date);
		}
		catch (ConversionException e)
		{
			ValidationError error = new ValidationError();
			error.addMessageKey("DateTimePicker.ConversionError");
			error.setVariable("date", dateInput);
			error.setVariable("time", timeInput);

			this.error(error);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> IConverter<C> getConverter(Class<C> type)
	{
		if (Date.class.isAssignableFrom(type))
		{
			if (this.converter == null)
			{
				this.converter = this.newDateConverter();
			}

			return (IConverter<C>) this.converter;
		}

		return super.getConverter(type);
	}

	/**
	 * Gets a new date converter.
	 *
	 * @return the converter
	 */
	private IConverter<Date> newDateConverter()
	{
		return new DateConverter() {

			private static final long serialVersionUID = 1L;

			@Override
			public DateFormat getDateFormat(Locale locale)
			{
				if (locale == null)
				{
					locale = Locale.getDefault();
				}

				return new SimpleDateFormat(getTextFormat(), locale);
			}
		};
	}


	/**
	 * Gets a new {@link DatePicker}
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param datePattern the date pattern to be used
	 *
	 * @return the {@link DatePicker}
	 */
	protected DatePicker newDatePicker(String id, IModel<Date> model, String datePattern)
	{
		return new DatePicker(id, model, datePattern);
	}

	/**
	 * Gets a new {@link TimePicker}
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param timePattern the date pattern to be used
	 *
	 * @return the {@link TimePicker}
	 */
	protected TimePicker newTimePicker(String id, IModel<Date> model, String timePattern)
	{
		return new TimePicker(id, model, timePattern);
	}

	/**
	 * Gets the date pattern in use
	 *
	 * @return the pattern
	 */
	public final String getDatePattern()
	{
		return this.datePattern;
	}

	/**
	 * Gets the time pattern in use
	 *
	 * @return the pattern
	 */
	public final String getTimePattern()
	{
		return this.timePattern;
	}

	/**
	 * Returns the date-time pattern.
	 *
	 * @see org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider#getTextFormat()
	 */
	@Override
	public final String getTextFormat()
	{
		return String.format("%s %s", this.getDatePattern(), this.getTimePattern());
	}

	/**
	 * Gets a string representation of the model object, given the date-time pattern in use.
	 *
	 * @return the model object as string
	 */
	public String getModelObjectAsString()
	{
		Date date = this.getModelObject();

		if (date != null)
		{
			return new SimpleDateFormat(this.getTextFormat()).format(date);
		}

		return "";
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.datePicker = this.newDatePicker("datepicker", this.getModel(), this.getDatePattern());
		this.timePicker = this.newTimePicker("timepicker", this.getModel(), this.getTimePattern());

		this.add(this.datePicker);
		this.add(this.timePicker);
	}
}
