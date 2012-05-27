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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;

/**
 * Provides a date-time-picker based on a {@link DatePicker} and a {@link TimePicker}
 * 
 * @author Sebastien Briquet - sebastien@7thweb.net
 */
public class DateTimePicker extends FormComponentPanel<Date>
{
	private static final long serialVersionUID = 1L;
	
	private DatePicker datePicker;
	private TimePicker timePicker;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public DateTimePicker(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public DateTimePicker(String id, IModel<Date> date)
	{
		super(id, date);
	}

	// Methods //
	@Override
	protected void convertInput()
	{
		Date date = this.datePicker.getConvertedInput();
		Date time = this.timePicker.getConvertedInput();

		if (date != null)
		{
			if (time == null)
			{
				this.setConvertedInput(new Date(date.getTime()));
			}
			else
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(date.getTime() + time.getTime());

				//FIXME: DateTimePicker: there is an issue with the time (it is always H-1 - at least in France). Maybe need to use the Locale...
//				CalendarConverter converter = new CalendarConverter();
//				new Date(date.getTime() + time.getTime());

				this.setConvertedInput(calendar.getTime());
			}
		}
	}

	/**
	 * Gets a string representation given the time pattern in use.
	 * TODO: Calendar: maybe use the CalendarConverter
	 * 
	 * @deprecated Experimental.
	 * @return the model object as string
	 */
	public String getModelObjectAsString()
	{
		Date date = this.getModelObject();

		if (date != null)
		{
			//maybe a little bit risky:
			String pattern = String.format("%s %s", this.datePicker.getTextFormat(), this.timePicker.getTextFormat());
			return new SimpleDateFormat(pattern).format(date);
		}

		return "";
	}

	/**
	 * Gets the underlying {@link DatePicker}
	 * 
	 * @return the {@link DatePicker}
	 */
	public final DatePicker getDatePicker()
	{
		return this.datePicker;
	}

	/**
	 * Gets the underlying {@link TimePicker}
	 * 
	 * @return the  {@link TimePicker}
	 */
	public final TimePicker getTimePicker()
	{
		return this.timePicker;
	}
	
	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.datePicker = new DatePicker("datepicker", this.getModel());
		this.timePicker = new TimePicker("timepicker", this.getModel());

		this.add(this.datePicker);
		this.add(this.timePicker);
	}
}
