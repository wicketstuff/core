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
package com.googlecode.wicket.kendo.ui.scheduler.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.core.Options;

/**
 * Represents a resource Id. The underlying Object T can either be a String or a Number (only Integer is supported)
 *
 * @param <T> the type of the underlying Object
 * @author Sebastien Briquet - sebfz1
 */
public class Id<T> implements IClusterable
{
	private static final long serialVersionUID = 1L;

	/** Regex pattern used to identify a string-number */
	private static final Pattern PATTERN = Pattern.compile("(\\d+)");

	private final T id;

	/**
	 * Constructor
	 *
	 * @param id - the id (String or Number/Integer)
	 */
	private Id(T id)
	{
		this.id = id;
	}

	/**
	 * Gets the underlying id
	 *
	 * @return the underlying id
	 */
	public T get()
	{
		return this.id;
	}

	/**
	 * Gets the string representation of the id-value
	 *
	 * @return the id-value, as string
	 */
	public String getValue()
	{
		return this.toString();
	}

	/**
	 * Converts an id of type I to its corresponding {@link Id} type
	 *
	 * @param id the id-value
	 * @return a new {@link Id}
	 */
	public static <I> Id<?> valueOf(I id)
	{
		if (id instanceof String)
		{
			String value = String.valueOf(id);
			Matcher matcher = PATTERN.matcher(value);

			if (matcher.find() && value.equals(matcher.group()))
			{
				return new NumberId(value);
			}

			return new StringId(value);
		}

		if (id instanceof Number)
		{
			return new NumberId((Number) id);
		}

		throw new UnsupportedOperationException("argument 'id' should either be a String or a Number");
	}

	/**
	 * Converts a list of ids of type I to its corresponding <tt>List</tt> of {@link Id}
	 *
	 * @param ids the id-values
	 * @return a new {@link Id}
	 */
	public static <I> List<Id<?>> valueOf(List<I> ids)
	{
		List<Id<?>> list = new ArrayList<Id<?>>();

		for (I id : ids)
		{
			list.add(Id.valueOf(id));
		}

		return list;
	}

	/**
	 * Internal <tt>String</tt> {@link Id}
	 */
	private static class StringId extends Id<String>
	{
		private static final long serialVersionUID = 1L;

		public StringId(String id)
		{
			super(id);
		}

		@Override
		public String toString()
		{
			return Options.asString(this.get());
		}
	}

	/**
	 * Internal <tt>Number</tt> {@link Id}
	 */
	private static class NumberId extends Id<Number>
	{
		private static final long serialVersionUID = 1L;

		public NumberId(Number id)
		{
			super(id);
		}

		public NumberId(String id)
		{
			super(Integer.valueOf(id));
		}

		@Override
		public String toString()
		{
			return String.valueOf(this.get());
		}
	}
}
