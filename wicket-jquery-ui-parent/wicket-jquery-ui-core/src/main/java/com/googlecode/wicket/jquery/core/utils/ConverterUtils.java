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

import java.util.Date;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.util.convert.IConverter;

/**
 * Utility class for {@link Date}({@code s})
 * 
 * @author Sebastien Briquet - sebfz1
 * 
 */
public class ConverterUtils
{
	/**
	 * Utility class
	 */
	private ConverterUtils()
	{
		// noop
	}

	/**
	 * Converts the object to its string representation using the appropriate converter, if defined.
	 * 
	 * @param <T> the object type
	 * @param object the object
	 * @return the string representation using the appropriate converter, if defined. #toString() otherwise.
	 */
	public static <T> String toString(T object)
	{
		String value = null;

		@SuppressWarnings("unchecked")
		IConverter<T> converter = (IConverter<T>) Application.get().getConverterLocator().getConverter(object.getClass());

		if (converter != null)
		{
			value = converter.convertToString(object, Session.get().getLocale());
		}
		else
		{
			value = object.toString();
		}

		return value;
	}
}
