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
package com.googlecode.wicket.kendo.ui.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utility class for Kendo UI datetime formats
 *
 * @see <a href="http://docs.kendoui.com/api/framework/kendo">http://docs.kendoui.com/api/framework/kendo</a>
 * @author Sebastien Briquet - sebfz1
 */
public class KendoDateTimeUtils
{
	/** Time Zone */
	static final String PATTERN_TZ = "yyyy-MM-dd'T'HH:mm:ss.SSSzzz";

	static final String j_chars = "GyYMwWDdFEuaHkKhmsSzZX";
	static final String k_chars = "GyYMwWDdFdutHkKhmsfzZX"; // S > f, a > t, E > d
	static final int chars_lenth = j_chars.length();

	/**
	 * Utility class
	 */
	private KendoDateTimeUtils()
	{
		// noop
	}

	/**
	 * Converts the input value to handle discrepancies (like localized am/pm)
	 *
	 * @param input the input value
	 * @return the converted value
	 */
	public static String convert(String input)
	{
		String converted = input;

		if (converted.contains("a. m."))
		{
			converted = converted.replace("a. m.", "AM");
		}

		if (converted.contains("p. m."))
		{
			converted = converted.replace("p. m.", "PM");
		}

		return converted;
	}

	/**
	 * Converts a java datetime pattern to a kendo-ui datetime pattern
	 *
	 * @param pattern the java pattern
	 * @return the kendo-ui datetime pattern
	 */
	public static String toPattern(String pattern)
	{
		String converted = pattern;

		// realign kendo pattern:
		// single 't' is *not* allowed in kendo, whereas 'a' is allowed in java date and and 'aa' is *not* allowed in LocalTime pattern
		if (converted.contains("a") && !converted.contains("aa"))
		{
			converted = converted.replace("a", "aa");
		}
		// single 'y' is allowed in Java11, but *NOT* allowed in kendo
		if (converted.contains("y") && !converted.contains("yy"))
		{
			converted = converted.replace("y", "yy");
		}

		for (int i = 0; i < chars_lenth; i++)
		{
			char j = j_chars.charAt(i);
			char k = k_chars.charAt(i);

			if (j != k)
			{
				converted = converted.replace(j, k);
			}
		}

		return converted;
	}

	/**
	 * Converts a {@link Date} to a compatible kendo-ui date-string format (with timezone)
	 *
	 * @param date the date
	 * @return the compatible kendo ui date string
	 */
	public static String toString(Date date)
	{
		return new SimpleDateFormat(PATTERN_TZ).format(date);
	}

	/**
	 * Converts a {@link LocalDate} to a compatible kendo-ui date-string format (without timezone)
	 *
	 * @param date the date
	 * @return the compatible kendo ui date string
	 */
	public static String toString(LocalDate date)
	{
		return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	/**
	 * Converts a {@link LocalDateTime} to a compatible kendo-ui date-string format (without timezone)
	 *
	 * @param date the date
	 * @return the compatible kendo ui date string
	 */
	public static String toString(LocalDateTime date)
	{
		return date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
}
