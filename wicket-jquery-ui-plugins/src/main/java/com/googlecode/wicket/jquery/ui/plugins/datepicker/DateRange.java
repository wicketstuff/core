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
	 * Creates a new {@link DateRange}, UTC time, starting at 0:00:00.000 and ending at 23:59:59.999<br/>
	 * <b>Caution:</b> supplied dates should be local to the system (ie: <code>new Date()</code>).
	 * 
	 * @param start the local start date
	 * @param end the local end date
	 * @return a new {@link DateRange}
	 */
	public static DateRange of(Date start, Date end)
	{
		return DateRange.of(DateUtils.utc(start), DateUtils.utc(end));
	}

	/**
	 * Creates a new {@link DateRange}, starting at 0:00:00.000 and ending at 23:59:59.999<br/>
	 * <b>Caution:</b> supplied dates should be UTC.
	 * 
	 * @param start the UTC start date
	 * @param end the UTC end date
	 * @return a new {@link DateRange}
	 */
	public static DateRange of(long start, long end)
	{
		Calendar s = Calendar.getInstance(DateUtils.UTC);
		s.setTimeInMillis(start); // UTC
		s.set(Calendar.HOUR_OF_DAY, 0);
		s.set(Calendar.MINUTE, 0);
		s.set(Calendar.SECOND, 0);
		s.set(Calendar.MILLISECOND, 0);

		Calendar e = Calendar.getInstance(DateUtils.UTC);
		e.setTimeInMillis(end); // UTC
		e.set(Calendar.HOUR_OF_DAY, 23);
		e.set(Calendar.MINUTE, 59);
		e.set(Calendar.SECOND, 59);
		e.set(Calendar.MILLISECOND, 999);

		return new DateRange(s.getTimeInMillis(), e.getTimeInMillis());
	}

	/**
	 * Gets a default {@link DateRange} from today 0:00:00.000 to 23:59:59.999 (UTC).
	 *
	 * @return the {@link DateRange}
	 */
	public static DateRange today()
	{
		return DateRange.of(new Date(), new Date());
	}

	/**
	 * Gets a new UTC {@link DateFormat} using ISO8601 pattern, but timezone agnostic
	 * 
	 * @return a new {@link DateFormat}
	 */
	public static DateFormat newDateFormat()
	{
		DateFormat df = new SimpleDateFormat(PATTERN);
		df.setTimeZone(DateUtils.UTC);

		return df;
	}

	private long start;
	private long end;

	/**
	 * Constructor
	 *
	 * @param start the local start date
	 * @param end the local end date
	 */
	public DateRange(Date start, Date end)
	{
		this(DateUtils.utc(start), DateUtils.utc(end));
	}

	/**
	 * Constructor
	 *
	 * @param start the UTC start date
	 * @param end the UTC end date
	 */
	public DateRange(long start, long end)
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
		return new Date(this.start);
	}

	/**
	 * Sets the start date.<br/>
	 *
	 * @param date the start date
	 */
	public void setStart(Date date)
	{
		this.setStart(date.getTime());
	}

	/**
	 * Sets the start date.<br/>
	 *
	 * @param date the start date
	 */
	public void setStart(long date)
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
		return new Date(this.end);
	}

	/**
	 * Sets the end date.<br/>
	 *
	 * @param date the end date
	 */
	public void setEnd(Date date)
	{
		this.setEnd(date.getTime());
	}

	/**
	 * Sets the end date.<br/>
	 *
	 * @param date the end date
	 */
	public void setEnd(long date)
	{
		this.end = date;
	}

	@Override
	public String toString()
	{
		DateFormat df = DateRange.newDateFormat();

		return String.format("[new Date('%s'),new Date('%s')]", df.format(this.getStart()), df.format(this.getEnd()));
	}
}
