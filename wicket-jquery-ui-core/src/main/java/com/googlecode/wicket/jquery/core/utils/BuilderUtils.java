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

import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.core.util.lang.PropertyResolver;

/**
 * Utility class for {@link StringBuilder}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class BuilderUtils
{
	/**
	 * Helper method that appends a key/value JSON pair to the specified builder<br/>
	 * The value will *not* be quoted, except if the value is {@code null}, {@code "null"} will be returned.
	 *
	 * @param builder the {@link StringBuilder}
	 * @param key the key
	 * @param value the object
	 */
	public static void append(StringBuilder builder, String key, Object value)
	{
		builder.append(JSONObject.quote(key)).append(": ").append(String.valueOf(value));
	}

	/**
	 * Helper method that appends a key/value JSON pair to the specified builder. The value will be quoted
	 *
	 * @param builder the {@link StringBuilder}
	 * @param key the key
	 * @param value the value
	 */
	public static void append(StringBuilder builder, String key, String value)
	{
		builder.append(JSONObject.quote(key)).append(": ").append(JSONObject.quote(value));
	}

	/**
	 * Helper method that appends a key/value JSON pair to the specified builder. The value will be resolved according to the supplied property
	 * 
	 * @param builder the {@link StringBuilder}
	 * @param object the object
	 * @param property the object's property
	 */
	public static <T> void resolve(StringBuilder builder, final T object, final String property)
	{
		String value = String.valueOf(PropertyResolver.getValue(property, object)); // convert to String to quote it

		BuilderUtils.append(builder, property, value);
	}

	/**
	 * Utility class
	 */
	private BuilderUtils()
	{
	}
}
