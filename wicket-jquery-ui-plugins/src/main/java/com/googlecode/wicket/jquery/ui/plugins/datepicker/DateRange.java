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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.core.utils.DateUtils;

/**
 * Provides the value type to be used as model object for {@link RangeDatePicker} and {@link RangeDatePickerTextField}<br/>
 * <br/>
 * <tt>start</tt> and <tt>end</tt> dates are UTC based, the JSON array ({@link #toString()}) is timezone agnostic
 *
 * @author Sebastien Briquet - sebfz1
 */
public class DateRange implements IClusterable
{
	private static final long serialVersionUID = 1L;

	public static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * Gets a default {@link DateRange} with start-date and end-date are set to today (UTC).
	 *
	 * @return the {@link DateRange}
	 */
	public static DateRange today()
	{
		final Date date = new Date();

		Calendar start = Calendar.getInstance(DateUtils.UTC);
		start.setTime(date);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);

		Calendar end = Calendar.getInstance(DateUtils.UTC);
		end.setTime(date);
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		end.set(Calendar.MILLISECOND, 999);

		return new DateRange(start.getTime(), end.getTime());
	}

	private Date start;
	private Date end;

	/**
	 * Constructor
	 *
	 * @param start the start date
	 * @param end the end date
	 */
	public DateRange(Date start, Date end)
	{
		this.start = start;
		this.end = end;
	}

	/**
	 * Gets the start date
	 *
	 * @return the start date
	 */
	public final Date getStart()
	{
		return this.start;
	}

	/**
	 * Sets the start date.<br/>
	 *
	 * @param date the start date
	 */
	public void setStart(Date date)
	{
		this.start = date;
	}

	/**
	 * Gets the end date
	 *
	 * @return the end date
	 */
	public Date getEnd()
	{
		return this.end;
	}

	/**
	 * Sets the end date.<br/>
	 *
	 * @param date the end date
	 */
	public void setEnd(Date date)
	{
		this.end = date;
	}

	@Override
	public String toString()
	{
		DateFormat df = new SimpleDateFormat(PATTERN); // ISO8601, no time zone
		df.setTimeZone(DateUtils.UTC);

		return String.format("[new Date('%s'),new Date('%s')]", df.format(this.getStart()), df.format(this.getEnd()));
	}
}
