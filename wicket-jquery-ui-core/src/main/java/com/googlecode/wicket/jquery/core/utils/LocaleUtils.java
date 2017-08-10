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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.wicket.util.string.Strings;

/**
 * Utility class for {@link Locale}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class LocaleUtils
{
	public static final int DEFAULT_STYLE = DateFormat.SHORT;

	/**
	 * Utility class
	 */
	private LocaleUtils()
	{
		// noop
	}

	/**
	 * Gets the language-code (eg: xx-XX) from a given {@link Locale}
	 * @param locale the {@link Locale}
	 * @return the language-code
	 */
	public static String getLangageCode(Locale locale)
	{
		if (!Strings.isEmpty(locale.getCountry()))
		{
			return String.format("%s-%s", locale.getLanguage(), locale.getCountry());
		}

		return locale.getLanguage();
	}

	/**
	 * Gets the date pattern for the given {@link Locale}
	 * @param locale the {@link Locale}
	 * @return the pattern
	 */
	public static String getLocaleDatePattern(Locale locale)
	{
		return LocaleUtils.getLocaleDatePattern(locale, DEFAULT_STYLE, null);
	}

	/**
	 * Gets the date pattern for the given {@link Locale} and style
	 * @param locale the {@link Locale}
	 * @param style the {@link DateFormat} style (eg: {@link DateFormat#SHORT}, {@link DateFormat#MEDIUM}, {@link DateFormat#LONG})
	 * @return the pattern
	 */
	public static String getLocaleDatePattern(Locale locale, int style)
	{
		return LocaleUtils.getLocaleDatePattern(locale, style, null);
	}

	/**
	 * Gets the date pattern for the given {@link Locale}
	 * @param locale the {@link Locale}
	 * @param defaultPattern default pattern to be returned if the found {@link DateFormat} is not an instance of {@link SimpleDateFormat}
	 * @return the pattern
	 */
	public static String getLocaleDatePattern(Locale locale, String defaultPattern)
	{
		return LocaleUtils.getLocaleDatePattern(locale, DEFAULT_STYLE, defaultPattern);
	}

	/**
	 * Gets the date pattern for the given {@link Locale} and style
	 * @param locale the {@link Locale}
	 * @param style the {@link DateFormat} style (eg: {@link DateFormat#SHORT}, {@link DateFormat#MEDIUM}, {@link DateFormat#LONG})
	 * @param defaultPattern default pattern to be returned if the found {@link DateFormat} is not an instance of {@link SimpleDateFormat}
	 * @return the pattern
	 */
	public static String getLocaleDatePattern(Locale locale, int style, String defaultPattern)
	{
		DateFormat format = DateFormat.getDateInstance(style, locale);

		if (format instanceof SimpleDateFormat)
		{
			return ((SimpleDateFormat)format).toPattern();
		}

		return defaultPattern;
	}


	/**
	 * Gets the time pattern for the given {@link Locale}
	 * @param locale the {@link Locale}
	 * @return the pattern
	 */
	public static String getLocaleTimePattern(Locale locale)
	{
		return LocaleUtils.getLocaleTimePattern(locale, DEFAULT_STYLE, null);
	}

	/**
	 * Gets the time pattern for the given {@link Locale} and style
	 * @param locale the {@link Locale}
	 * @param style the {@link DateFormat} style (eg: {@link DateFormat#SHORT}, {@link DateFormat#MEDIUM}, {@link DateFormat#LONG})
	 * @return the pattern
	 */
	public static String getLocaleTimePattern(Locale locale, int style)
	{
		return LocaleUtils.getLocaleTimePattern(locale, style, null);
	}

	/**
	 * Gets the time pattern for the given {@link Locale}
	 * @param locale the {@link Locale}
	 * @param defaultPattern default pattern to be returned if the found {@link DateFormat} is not an instance of {@link SimpleDateFormat}
	 * @return the pattern
	 */
	public static String getLocaleTimePattern(Locale locale, String defaultPattern)
	{
		return LocaleUtils.getLocaleTimePattern(locale, DEFAULT_STYLE, defaultPattern);
	}

	/**
	 * Gets the time pattern for the given {@link Locale} and style
	 * @param locale the {@link Locale}
	 * @param style the {@link DateFormat} style (eg: {@link DateFormat#SHORT}, {@link DateFormat#MEDIUM}, {@link DateFormat#LONG})
	 * @param defaultPattern default pattern to be returned if the found {@link DateFormat} is not an instance of {@link SimpleDateFormat}
	 * @return the pattern
	 */
	public static String getLocaleTimePattern(Locale locale, int style, String defaultPattern)
	{
		DateFormat format = DateFormat.getTimeInstance(style, locale);

		if (format instanceof SimpleDateFormat)
		{
			return ((SimpleDateFormat)format).toPattern();
		}

		return defaultPattern;
	}
}
