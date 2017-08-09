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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
	public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	public static final String ISO8601_TZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static final String ISO8601_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	/** default java date pattern */
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	/** default java time pattern */
	public static final String TIME_PATTERN = "h:mm aa";

	/** default java 8 date pattern */
	public static final String LOCAL_DATE_PATTERN = DATE_PATTERN;

	/** default java 8 time pattern */
	public static final String LOCAL_TIME_PATTERN = "h:mm a";

	/** UTC timezone */
	public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

	/**
	 * Utility class
	 */
	private DateUtils()
	{
		// noop
	}

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
	 * Converts a ISO8601 string date to an {@code UTC} {@link Date}
	 * 
	 * @param date the date to convert
	 * @return the UTC date
	 * @throws ParseException if the string cannot be parsed
	 */
	public static long utc(String date) throws ParseException
	{
		return DateUtils.utc(DateUtils.parse(date).getTime());
	}

	/**
	 * Gets the current timezone offset
	 * 
	 * @param time the timestamp
	 * @return the timezone offset
	 * @see TimeZone#getRawOffset()
	 */
	public static long offset(long time)
	{
		return TimeZone.getDefault().getOffset(time);
	}

	/**
	 * Converts a ISO8601 string date (without timezone) to a {@link Date}
	 *
	 * @param date ISO8601 string date
	 * @return the {@code Date}
	 * @throws ParseException if the string cannot be parsed
	 */
	public static Date parse(String date) throws ParseException
	{
		return new SimpleDateFormat(ISO8601).parse(date);
	}

	/**
	 * Converts a date to its ISO8601/javascript representation (with timezone). ie: 2009-11-05T13:15:00.000+0200
	 *
	 * @param date the date to convert
	 * @return the ISO8601 date as string
	 */
	public static String toString(Date date)
	{
		return new SimpleDateFormat(ISO8601_TZ).format(date);
	}

	/**
	 * Converts a date to its ISO8601/javascript representation (UTC). ie: 2009-11-05T13:15:00.000Z
	 *
	 * @param date the date to convert
	 * @return the ISO8601 date as string
	 */
	public static String toUTCString(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(ISO8601_UTC);
		formatter.setTimeZone(DateUtils.UTC);

		return formatter.format(date);
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

	// java8 date handling & conversion //

	/**
	 * Gets a new {@link Date} according to the first date (which represents the date) and the second date (which represents the time)
	 * 
	 * @param date the date
	 * @param time the time
	 * @return a new {@code Date}
	 */
	public static Date dateOf(Date date, Date time)
	{
		return DateUtils.toDate(DateUtils.toLocalDate(date), DateUtils.toLocalTime(time));
	}

	/**
	 * Gets a new {@link Date} according to the supplied {@link LocalDate} &#38; {@link LocalTime}
	 * 
	 * @param date the {@code LocalDate}
	 * @param time the {@code LocalTime}. If {@code null}, {@link LocalTime#MIDNIGHT} is returned
	 * @return a new {@code Date}
	 */
	public static Date toDate(LocalDate date, LocalTime time)
	{
		return DateUtils.toDate(DateUtils.toLocalDateTime(date, time));
	}

	/**
	 * Gets a new {@link Date} according to the supplied {@link LocalDateTime}
	 * 
	 * @param datetime the {@code LocalDateTime}
	 * @return a new {@code Date}
	 */
	public static Date toDate(LocalDateTime datetime)
	{
		return DateUtils.toDate(datetime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Gets a new {@link Date} according to the supplied {@link Instant}
	 * 
	 * @param instant the {@code Instant}
	 * @return a new {@code Date}
	 */
	public static Date toDate(Instant instant)
	{
		return new Date(instant.toEpochMilli());
	}

	/**
	 * Converts an {@link Date} to a {@link LocalDate}
	 * 
	 * @param date the {@code Date}
	 * @return a new {@code LocalDate}
	 */
	public static LocalDate toLocalDate(Date date)
	{
		if (date != null)
		{
			return DateUtils.toLocalDate(date.getTime());
		}

		return null;
	}

	/**
	 * Converts a timestamp to a {@link LocalDate}
	 * 
	 * @param timestamp the timestamp
	 * @return a new {@code LocalDate}
	 */
	public static LocalDate toLocalDate(long timestamp)
	{
		return DateUtils.toLocalDate(Instant.ofEpochMilli(timestamp));
	}

	/**
	 * Converts an {@link Instant} to a {@link LocalDate}
	 * 
	 * @param instant the {@code Instant}
	 * @return a new {@code LocalDate}
	 */
	public static LocalDate toLocalDate(Instant instant)
	{
		return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * Converts an {@link Date} to a {@link LocalTime}
	 * 
	 * @param time the {@code Date}
	 * @return a new {@code LocalTime}
	 */
	public static LocalTime toLocalTime(Date time)
	{
		if (time != null)
		{
			return DateUtils.toLocalTime(time.getTime());
		}

		return null;
	}

	/**
	 * Converts a timestamp to a {@link LocalTime}
	 * 
	 * @param timestamp the timestamp
	 * @return a new {@code LocalTime}
	 */
	public static LocalTime toLocalTime(long timestamp)
	{
		return DateUtils.toLocalTime(Instant.ofEpochMilli(timestamp));
	}

	/**
	 * Converts an {@link Instant} to a {@link LocalTime}
	 * 
	 * @param instant the {@code Instant}
	 * @return a new {@code LocalTime}
	 */
	public static LocalTime toLocalTime(Instant instant)
	{
		return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
	}

	/**
	 * Gets a new {@link LocalDateTime} according to the supplied {@link LocalDate} &#38; {@link LocalTime}
	 * 
	 * @param date the {@code LocalDate}
	 * @param time the {@code LocalTime}. If {@code null}, {@link LocalTime#MIDNIGHT} is returned
	 * @return a new {@code LocalDateTime}
	 */
	public static LocalDateTime toLocalDateTime(LocalDate date, LocalTime time)
	{
		return LocalDateTime.of(date, time != null ? time : LocalTime.MIDNIGHT);
	}
}
