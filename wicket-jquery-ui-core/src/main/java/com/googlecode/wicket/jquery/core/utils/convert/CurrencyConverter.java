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
package com.googlecode.wicket.jquery.core.utils.convert;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.AbstractConverter;

import com.googlecode.wicket.jquery.core.IJQueryCultureWidget;

/**
 * Provides a base class for converting currency numbers to {@link Object}
 *
 * @param <N> The number object type
 * @author Sebastien Briquet - sebfz1
 *
 * @deprecated there is a bug in java currency converter...
 *
 */
@Deprecated
public abstract class CurrencyConverter<N extends Number> extends AbstractConverter<N>
{
	private static final long serialVersionUID = 1L;
	private Locale locale;
	private final String culture;

	public CurrencyConverter(IJQueryCultureWidget widget)
	{
		this.culture = widget.getCulture();
	}

	@Override
	protected abstract Class<N> getTargetType();

	@Override
	public N convertToObject(String value, Locale locale)
	{
		return this.parse(value, Double.MIN_VALUE, Double.MAX_VALUE, this.getLocale());
	}

	/**
	 * Parses a value as a String and returns a Number.<br/>
	 * @param value the string to parse
	 * @param min the minimum allowed value
	 * @param max the maximum allowed value
	 * @param locale the {@link Locale}
	 * @return the converted number
	 */
	// code from AbstractNumberConverter#parse(Object, double, double, Locale)
	protected N parse(String value, final double min, final double max, Locale locale)
	{
		if (value == null)
		{
			return null;
		}

		final NumberFormat format = NumberFormat.getCurrencyInstance(locale);
		final N number = this.parse(format, value, locale);

		if (number == null)
		{
			return null;
		}

		if (number.doubleValue() < min)
		{
			throw newConversionException("Value cannot be less than " + min, value, locale).setFormat(format);
		}

		if (number.doubleValue() > max)
		{
			throw newConversionException("Value cannot be greater than " + max, value, locale).setFormat(format);
		}

		return number;
	}

	@Override
	public String convertToString(N value, Locale locale)
	{
		return value.toString(); //the rendering is assumed by the IJQueryCultureWidget
	}

	/**
	 * Gets the {@link Locale} from the {@link IJQueryCultureWidget} current culture
	 * @return the {@link Locale}
	 */
	public Locale getLocale()
	{
		if (this.locale == null)
		{
			if (this.culture != null)
			{
				String[] inputs = culture.split("-");
				this.locale = new Locale(inputs[0], inputs.length > 1 ? inputs[1] : "");
			}
			else
			{
				this.locale = Locale.getDefault();
			}
		}

		return this.locale;
	}
}
