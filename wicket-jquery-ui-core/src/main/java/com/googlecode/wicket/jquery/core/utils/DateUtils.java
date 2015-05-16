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
package com.googlecode.wicket.jquery.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class for {@link Date}({@code s})
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class DateUtils
{
	private static final String ISO8601 = "yyyy-MM-dd'T'HH:mmZ";

	public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

	/**
	 * Gets the current UTC date
	 * 
	 * @return the current UTC date
	 */
	public static long utc()
	{
		return DateUtils.utc(new Date());
	}

	/**
	 * Converts a local date to a UTC date
	 * 
	 * @param date the date to convert
	 * @return the UTC date
	 */
	public static long utc(Date date)
	{
		return DateUtils.utc(date.getTime());
	}

	/**
	 * Converts a local date to a UTC date
	 * 
	 * @param date the date to convert
	 * @return the UTC date
	 */
	public static long utc(long date)
	{
		return date - DateUtils.offset(date);
	}

	/**
	 * Gets the current timezone offset
	 * 
	 * @param time
	 * 
	 * @return the timezone offset
	 * @see TimeZone#getRawOffset()
	 */
	public static long offset(long time)
	{
		return TimeZone.getDefault().getOffset(time);
	}

	/**
	 * Converts a date to its ISO8601/javascript representation. ie: 2009-11-05T13:15:30+0200
	 *
	 * @param date the date to convert
	 * @return the ISO8601 date as string
	 */
	public static String toISO8601(Date date)
	{
		return new SimpleDateFormat(ISO8601).format(date);
	}

	/**
	 * Adds the specified amount of hours to a date
	 *
	 * @param date the actual date (UTC)
	 * @param hours the amount of hours to add
	 * @return a new date
	 */
	public static long addHours(long date, int hours)
	{
		Calendar calendar = Calendar.getInstance(UTC);
		calendar.setTimeInMillis(date);
		calendar.add(Calendar.HOUR, hours);

		return calendar.getTimeInMillis();
	}

	/**
	 * Adds the specified amount of hours to a date
	 *
	 * @param date the actual date (UTC)
	 * @param hours the amount of hours to add
	 * @return a new date
	 */
	public static Date addHours(Date date, int hours)
	{
		Calendar calendar = Calendar.getInstance(UTC);
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hours);

		return calendar.getTime();
	}

	/**
	 * Utility class
	 */
	private DateUtils()
	{
	}
}
