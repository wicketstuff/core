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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.DateUtils;
import com.googlecode.wicket.jquery.core.utils.LocaleUtils;

/**
 * Provides a datetime-picker based on a {@link DatePicker} and a {@link TimePicker}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class DateTimePicker extends FormComponentPanel<LocalDateTime> implements ITextFormatProvider // NOSONAR
{
	private static final long serialVersionUID = 1L;

	protected DatePicker datePicker;
	protected TimePicker timePicker;

	private final Locale locale;
	private final String datePattern;
	private final String timePattern;

	private boolean timePickerEnabled = true;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public DateTimePicker(String id)
	{
		this(id, null, null, DateUtils.LOCAL_DATE_PATTERN, DateUtils.LOCAL_TIME_PATTERN);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param locale the {@code Locale}
	 */
	public DateTimePicker(String id, Locale locale)
	{
		this(id, null, locale, LocaleUtils.getLocaleDatePattern(locale, DateUtils.LOCAL_DATE_PATTERN), LocaleUtils.getLocaleTimePattern(locale, DateUtils.LOCAL_TIME_PATTERN));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param datePattern the SimpleDateFormat pattern for the date
	 * @param timePattern the SimpleDateFormat pattern for the time
	 */
	public DateTimePicker(String id, String datePattern, String timePattern)
	{
		this(id, null, null, datePattern, timePattern);
	}

	/**
	 * constructor
	 *
	 * @param id the markup id
	 * @param locale the {@code Locale}
	 * @param datePattern the SimpleDateFormat pattern for the date
	 * @param timePattern the SimpleDateFormat pattern for the time
	 */
	public DateTimePicker(String id, Locale locale, String datePattern, String timePattern)
	{
		this(id, null, locale, datePattern, timePattern);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the date {@code IModel}
	 */
	public DateTimePicker(String id, IModel<LocalDateTime> model)
	{
		this(id, model, null, DateUtils.LOCAL_DATE_PATTERN, DateUtils.LOCAL_TIME_PATTERN);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the date {@code IModel}
	 * @param locale the {@code LocalDate}
	 */
	public DateTimePicker(String id, IModel<LocalDateTime> model, Locale locale)
	{
		this(id, model, locale, LocaleUtils.getLocaleDatePattern(locale, DateUtils.LOCAL_DATE_PATTERN), LocaleUtils.getLocaleTimePattern(locale, DateUtils.LOCAL_TIME_PATTERN));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the date {@code IModel}
	 * @param datePattern the SimpleDateFormat pattern for the date
	 * @param timePattern the SimpleDateFormat pattern for the time
	 */
	public DateTimePicker(String id, IModel<LocalDateTime> model, String datePattern, String timePattern)
	{
		this(id, model, null, datePattern, timePattern);
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param model the date {@code IModel}
	 * @param locale the {@code Locale}
	 * @param datePattern the SimpleDateFormat pattern for the date
	 * @param timePattern the SimpleDateFormat pattern for the time
	 */
	public DateTimePicker(String id, IModel<LocalDateTime> model, Locale locale, String datePattern, String timePattern)
	{
		super(id, model);

		this.locale = locale;
		this.datePattern = datePattern;
		this.timePattern = timePattern;

		this.setType(LocalDateTime.class); // makes use of the converter
	}

	// Methods //

	@Override
	public String getInput()
	{
		String dateInput = this.datePicker.getInput();
		String timeInput = this.timePicker.getInput();

		return this.formatInput(dateInput, timeInput);
	}

	/**
	 * Gets a formated value of input(s)<br>
	 * This method is designed to provide the 'value' argument of {@link IConverter#convertToObject(String, Locale)}
	 *
	 * @param dateInput the date input
	 * @param timeInput the time input
	 * @return a formated value
	 */
	protected String formatInput(String dateInput, String timeInput)
	{
		if (this.isTimePickerEnabled())
		{
			return String.format("%s %s", dateInput, timeInput);
		}

		return dateInput;
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

	@Override
	@SuppressWarnings("unchecked")
	public <C> IConverter<C> getConverter(Class<C> type)
	{
		if (LocalDateTime.class.isAssignableFrom(type))
		{
			return (IConverter<C>) DateTimePicker.newConverter(this.getTextFormat());
		}

		return super.getConverter(type);
	}

	/**
	 * Returns the date-time pattern.
	 *
	 * @see org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider#getTextFormat()
	 */
	@Override
	public final String getTextFormat()
	{
		if (this.isTimePickerEnabled())
		{
			return String.format("%s %s", this.getDatePattern(), this.getTimePattern());
		}

		return this.getDatePattern();
	}

	/**
	 * Gets a (localized) string representation of the model object, given the date-time pattern in use.
	 *
	 * @return the model object as string
	 */
	public String getModelObjectAsString()
	{
		LocalDateTime date = this.getModelObject();

		if (date != null)
		{
			return date.format(DateTimeFormatter.ofPattern(this.getTextFormat(), this.getLocale()));
		}

		return "";
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
	 * Indicates whether the time-picker is enabled.<br>
	 * This method is marked final because an override will not change the time-picker 'enable' flag
	 *
	 * @return the enabled flag
	 */
	public final boolean isTimePickerEnabled()
	{
		return this.timePickerEnabled;
	}

	/**
	 * Sets the time-picker enabled flag
	 *
	 * @param enabled the enabled flag
	 * @return this, for chaining
	 */
	public final DateTimePicker setTimePickerEnabled(boolean enabled)
	{
		this.timePickerEnabled = enabled;

		return this;
	}

	/**
	 * Sets the time-picker enabled flag
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param enabled the enabled flag
	 */
	public final void setTimePickerEnabled(IPartialPageRequestHandler handler, boolean enabled)
	{
		this.timePickerEnabled = enabled;

		handler.add(this.timePicker);
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.datePicker = this.newDatePicker("datepicker", this.newDatePickerModel(), this.getLocale(), this.getDatePattern(), new Options());
		this.timePicker = this.newTimePicker("timepicker", this.newTimePickerModel(), this.getLocale(), this.getTimePattern(), new Options());

		this.add(this.datePicker);
		this.add(this.timePicker);
	}

	// Factories //

	/**
	 * Gets a new {@link LocalDateTime} {@link IConverter}.
	 * 
	 * @param format the time format
	 * @return the converter
	 */
	private static IConverter<LocalDateTime> newConverter(final String pattern)
	{
		final String corrected = DatePicker.correctPattern(pattern);

		return new IConverter<LocalDateTime>() { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public LocalDateTime convertToObject(String value, Locale locale)
			{
				try
				{
					return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(corrected, locale));
				}
				catch (DateTimeParseException e)
				{
					throw new ConversionException(e.getMessage(), e);
				}
			}

			@Override
			public String convertToString(LocalDateTime datetime, Locale locale)
			{
				return datetime != null ? datetime.format(DateTimeFormatter.ofPattern(corrected, locale)) : null;
			}
		};
	}

	/**
	 * Gets the {@link DatePicker} {@link Model}
	 * 
	 * @return the {@code DatePicker} {@code Model}
	 */
	private IModel<LocalDate> newDatePickerModel()
	{
		return new Model<LocalDate>() {

			private static final long serialVersionUID = 1L;

			@Override
			public LocalDate getObject()
			{
				LocalDateTime datetime = getModelObject();

				if (datetime != null)
				{
					return datetime.toLocalDate();
				}

				return null;
			}
		};
	}

	/**
	 * Gets the {@link DatePicker} {@link Model}
	 * 
	 * @return the {@code DatePicker} {@code Model}
	 */
	private IModel<LocalTime> newTimePickerModel()
	{
		return new Model<LocalTime>() {

			private static final long serialVersionUID = 1L;

			@Override
			public LocalTime getObject()
			{
				LocalDateTime datetime = getModelObject();

				if (datetime != null)
				{
					return datetime.toLocalTime();
				}

				return null;
			}
		};
	}

	/**
	 * Gets a new {@link DatePicker}
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 * @param datePattern the date pattern to be used
	 * @param options the {@code Options}
	 * @return the {@link DatePicker}
	 */
	protected DatePicker newDatePicker(String id, IModel<LocalDate> model, Locale locale, String datePattern, Options options)
	{
		return new DatePicker(id, model, locale, datePattern, options) { // NOSONAR

			private static final long serialVersionUID = 1L;

			// events //

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				this.setEnabled(DateTimePicker.this.isEnabled());
			}

			// methods //

			@Override
			public void convertInput()
			{
				// lets DateTimePicker handling the conversion
			}
		};
	}

	/**
	 * Gets a new {@link TimePicker}
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 * @param timePattern the time pattern to be used
	 * @param options the {@code Options}
	 * @return the {@link TimePicker}
	 */
	protected TimePicker newTimePicker(String id, IModel<LocalTime> model, Locale locale, String timePattern, Options options)
	{
		return new TimePicker(id, model, locale, timePattern, options) { // NOSONAR

			private static final long serialVersionUID = 1L;

			// events //

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				this.setEnabled(DateTimePicker.this.isEnabled() && DateTimePicker.this.isTimePickerEnabled());
			}

			// methods //

			@Override
			public void convertInput()
			{
				// lets DateTimePicker handling the conversion
			}
		};
	}
}
