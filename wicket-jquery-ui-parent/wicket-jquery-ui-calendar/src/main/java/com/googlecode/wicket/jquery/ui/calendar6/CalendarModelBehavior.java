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
package com.googlecode.wicket.jquery.ui.calendar6;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.wicket.request.IRequestParameters;

import com.googlecode.wicket.jquery.core.behavior.AjaxCallbackBehavior;

/**
 * Provides the behavior that loads {@link CalendarEvent}{@code s} according to {@link CalendarModel} start &amp; end dates
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class CalendarModelBehavior extends AjaxCallbackBehavior
{
	private static final long serialVersionUID = 1L;

	private final CalendarModel model;

	/**
	 * Constructor
	 *
	 * @param model the {@link CalendarModel}
	 */
	public CalendarModelBehavior(final CalendarModel model)
	{
		this.model = model;
	}

	/**
	 * Sets the start date to the model<br>
	 * This can be overridden to perform additional operation on date before the assignment.
	 *
	 * @param model the {@link CalendarModel}
	 * @param date the {@link LocalDate}
	 */
	protected void setStartDate(CalendarModel model, LocalDate date)
	{
		model.setStart(date);
	}

	/**
	 * Sets the end date to the model<br>
	 * This can be overridden to perform additional operation on date before the assignment.
	 *
	 * @param model the {@link CalendarModel}
	 * @param date the {@link LocalDate}
	 */
	protected void setEndDate(CalendarModel model, LocalDate date)
	{
		model.setEnd(date);
	}

	@Override
	protected String getResponse(IRequestParameters parameters)
	{
		final String start = parameters.getParameterValue("start").toString();
		final String end = parameters.getParameterValue("end").toString();

		StringBuilder builder = new StringBuilder("[ ");

		if (this.model != null)
		{
			this.setStartDate(this.model, parse(start));
			this.setEndDate(this.model, parse(end));

			List<? extends CalendarEvent> list = this.model.getObject(); // calls load()

			if (list != null)
			{
				int count = 0;
				for (CalendarEvent event : list)
				{
					if (this.model instanceof ICalendarVisitor)
					{
						event.accept((ICalendarVisitor) this.model); // last chance to set options
					}

					if (count++ > 0)
					{
						builder.append(", ");
					}

					builder.append(event.toString());
				}
			}
		}

		return builder.append(" ]").toString();
	}

	protected static LocalDate parse(String dateStr) {
		for (DateTimeFormatter formatter : List.of(DateTimeFormatter.ISO_LOCAL_DATE, DateTimeFormatter.ISO_LOCAL_DATE_TIME, DateTimeFormatter.ISO_OFFSET_DATE_TIME)) {
			try {
				return LocalDate.parse(dateStr, formatter);
			} catch (Exception e) {
				// no-op
			}
		}
		throw new IllegalArgumentException("Unparsable LocalDate: " + dateStr);
	}
}
