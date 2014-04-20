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
package com.googlecode.wicket.jquery.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.core.utils.DateUtils;

/**
 * Provides a wrapper on a {@link Map} that will contains jQuery behavior options (key/value).<br/>
 * the {@link #toString()} methods returns the JSON representation of the options.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Options implements IClusterable
{
	private static final long serialVersionUID = 1L;
	public static final String QUOTE = "\"";

	/**
	 * Converts a string representation of an object to its javascript representation. ie: "myvalue" (with the double quotes)<br/>
	 * If the supplied value is null, "null" is returned
	 *
	 * @param value the object
	 * @return the JSON value
	 */
	public static String asString(Object value)
	{
		return Options.asString(String.valueOf(value));
	}

	/**
	 * Converts a string to its javascript representation. ie: "myvalue" (with the double quotes)<br/>
	 * If the supplied value is null, "null" is returned
	 *
	 * @param value the object
	 * @return the JSON value
	 */
	public static String asString(String value)
	{
		return String.format("%s%s%s", QUOTE, String.valueOf(value).replace(QUOTE, "\\" + QUOTE), QUOTE);
	}

	/**
	 * Converts a date to its ISO8601/javascript representation. ie: "2009-11-05T13:15:30+0200" (with the double quotes)
	 *
	 * @param date the date to convert
	 * @return the JSON value
	 */
	public static String asDate(Date date)
	{
		return Options.asString(DateUtils.toISO8601(date));
	}

	/**
	 * Converts a list of options to a comma delimited string.
	 *
	 * @param objects options
	 * @return a comma delimited string
	 */
	public static String fromArray(Object... objects)
	{
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < objects.length; i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}

			builder.append(objects[i]);
		}

		return builder.toString();
	}

	private final Map<String, Serializable> map;

	/**
	 * Constructor.
	 */
	public Options()
	{
		this.map = new HashMap<String, Serializable>();
	}

	/**
	 * Constructor which adds an options defined by a key/value pair.
	 *
	 * @param key the option name
	 * @param value the option value
	 */
	public Options(String key, Serializable value)
	{
		this();
		this.set(key, value);
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
	 *
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
	 */
	public Serializable get(String key)
	{
		return this.map.get(key);
	}

	/**
	 * Adds or replace an options defined by a key/value pair.<br/>
	 * If for a given key, the value is null, then the pair is removed.
	 *
	 * @param key - key with which the specified value is to be associated
	 * @param value - value to be associated with the specified key
	 * @return this
	 */
	public final Options set(String key, Serializable value)
	{
		if (value != null)
		{
			this.map.put(key, value);
		}
		else
		{
			this.map.remove(key);
		}

		return this;
	}

	/**
	 * Gets a read-only entry set of options
	 *
	 * @return an unmodifiable set of internal map entries
	 */
	public Set<Entry<String, Serializable>> entries()
	{
		return Collections.unmodifiableSet(this.map.entrySet());
	}

	/**
	 * Gets the JSON representation of the Options<br/>
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder("{ ");

		int i = 0;
		for (Entry<String, Serializable> entry : this.map.entrySet())
		{
			if (i++ > 0)
			{
				builder.append(", ");
			}

			builder.append(QUOTE).append(entry.getKey()).append(QUOTE).append(": ").append(entry.getValue());
		}

		return builder.append(" }").toString();
	}
}
