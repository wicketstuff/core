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
package com.googlecode.wicket.jquery.ui.plugins.datepicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import com.googlecode.wicket.jquery.ui.JQueryAbstractBehavior;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.event.IValueChangedListener;

/**
 * Provides a {@link FormComponentPanel} based on a {@link TextField} and a {@link RangeDatePicker}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class RangeDatePickerTextField extends FormComponentPanel<DateRange> implements IValueChangedListener
{
	private static final long serialVersionUID = 1L;
	private static final String NULL = "?";
	private static final String SEPARATOR = " - ";

	private TextField<DateRange> input;
	private RangeDatePicker datepicker;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public RangeDatePickerTextField(String id)
	{
		this(id, new Options("calendars", 3));
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options {@link Options}
	 */
	public RangeDatePickerTextField(String id, final Options options)
	{
		super(id);

		this.init(options);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public RangeDatePickerTextField(String id, IModel<DateRange> model)
	{
		this(id, model, new Options("calendars", 3));
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options {@link Options}
	 */
	public RangeDatePickerTextField(String id, IModel<DateRange> model, final Options options)
	{
		super(id, model);

		this.init(options);
	}

	/**
	 * Initialization
	 * @param options the {@link Options}
	 */
	private void init(final Options options)
	{
		// TextField //
		this.input = this.newTextField();
		this.input.setOutputMarkupId(true);
		this.input.add(this.newToggleBehavior());
		this.add(this.input);

		// DatePicker //
		this.datepicker = new RangeDatePicker("datepicker", this.getModel(), options) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onValueChanged(AjaxRequestTarget target)
			{
				RangeDatePickerTextField.this.input.modelChanged();
				target.add(RangeDatePickerTextField.this.input);

				RangeDatePickerTextField.this.onValueChanged(target, RangeDatePickerTextField.this.getForm());
			}
		};

		this.add(this.datepicker);
	}

	// Methods //
	@Override
	protected void convertInput()
	{
		this.setConvertedInput(this.input.getConvertedInput());
	}

	// Properties //
	/**
	 * Gets the separator to be displayed in the {@link TextField}, between the two dates.
	 * @return the text separator. Default to {@link #SEPARATOR}
	 */
	protected String getSeparator()
	{
		return SEPARATOR;
	}

	/**
	 * Gets the text to be displayed in the {@link TextField}, in place of a date which is null.
	 * @return the null representation. Default to {@link #NULL}
	 */
	protected String getNullString()
	{
		return NULL;
	}

	// Events //
	@Override
	public void onValueChanged(AjaxRequestTarget target, Form<?> form)
	{
	}

	// Factories //
	/**
	 * Gets a new {@link DateFormat} to be used by the {@link TextField}'s {@link IConverter}
	 * @param locale the {@link Locale}
	 * @return the {@link DateFormat}
	 */
	protected DateFormat newDateFormat(Locale locale)
	{
		return new SimpleDateFormat("dd MMM yyyy", locale);
	}

	/**
	 * Gets a new {@link TextField}.
	 * @return the {@link TextField}
	 */
	private TextField<DateRange> newTextField()
	{
		return new TextField<DateRange>("text", this.getModel(), DateRange.class) {

			private static final long serialVersionUID = 1L;

			@Override
			public <C> IConverter<C> getConverter(Class<C> type)
			{
				if (DateRange.class.isAssignableFrom(type))
				{
					return newConverter();
				}

				return super.getConverter(type);
			}
		};
	}

	@SuppressWarnings("unchecked")
	protected <C> IConverter<C> newConverter()
	{
		return (IConverter<C>) new IConverter<DateRange>() {

			private static final long serialVersionUID = 1L;

			@Override
			public DateRange convertToObject(String value, Locale locale)
			{
				DateFormat dateFormat = RangeDatePickerTextField.this.newDateFormat(locale);
				String[] dates = value.split(RangeDatePickerTextField.this.getSeparator());

				try
				{
					return new DateRange(dateFormat.parse(dates[0]), dateFormat.parse(dates[1]));
				}
				catch (ParseException e)
				{
					throw new ConversionException(e.getMessage());
				}
				catch (IndexOutOfBoundsException e)
				{
					throw new ConversionException(e.getMessage());
				}
			}

			@Override
			public String convertToString(DateRange value, Locale locale)
			{
				DateFormat dateFormat = RangeDatePickerTextField.this.newDateFormat(locale);
				Date start = value.getStart();
				Date end = value.getEnd();

				return String.format("%s%s%s", start != null ? dateFormat.format(start) : getNullString(), RangeDatePickerTextField.this.getSeparator(), end != null ? dateFormat.format(end) : getNullString());
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAbstractBehavior} to show the {@link RangeDatePicker} on {@link TextField}'s click event.
	 * @return the {@link JQueryAbstractBehavior}
	 */
	private JQueryAbstractBehavior newToggleBehavior()
	{
		return new JQueryAbstractBehavior("DatePicker-toggle") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String $() {

				StringBuilder statements = new StringBuilder();

				statements.append("jQuery('#").append(input.getMarkupId()).append("').on('click', function() { ");
				statements.append("jQuery('#").append(datepicker.getMarkupId()).append("').DatePickerShow(); ");
				statements.append("} );");

				return String.format("jQuery(function() { %s });", statements);
			}
		};
	}
}
