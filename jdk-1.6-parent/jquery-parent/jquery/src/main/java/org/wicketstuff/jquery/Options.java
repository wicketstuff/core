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
package org.wicketstuff.jquery;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Options implements Serializable
{

	protected Map<String, Object> options_ = new HashMap<String, Object>();

	public Object get(String name)
	{
		return options_.get(name);
	}

	public Object get(String name, Object defaultValue)
	{
		Object back = options_.get(name);
		if (back == null)
		{
			back = defaultValue;
		}
		return back;
	}

	/**
	 * shortcut method, call set with overwrite = true.
	 * 
	 * @param name
	 *            name of the option
	 * @param value
	 *            new value of the option (if null, then remove the option)
	 * @return this
	 * @see #set(String,Object,boolean)
	 */
	public Options set(String name, Object value)
	{
		return set(name, value, true);
	}

	/**
	 * set an option.
	 * 
	 * @param name
	 *            name of the option
	 * @param value
	 *            new value of the option (if null, then remove the option)
	 * @param overwrite
	 *            if false and the value is already set, then the option is unchanged
	 * @return this
	 */
	public Options set(String name, Object value, boolean overwrite)
	{
		if (!overwrite && options_.containsKey(name))
		{
			return this;
		}
		if ((value == null) && options_.containsKey(name))
		{
			options_.remove(name);
		}
		options_.put(name, value);
		return this;
	}

	@Override
	public String toString()
	{
		return toString(false).toString();
	}

	public CharSequence toString(boolean asFragment)
	{
		if (options_.isEmpty())
		{
			return "";
		}
		StringBuilder str = new StringBuilder();
		if (!asFragment)
		{
			str.append("{\n");
		}
		for (Map.Entry<String, Object> entry : options_.entrySet())
		{
			str.append("\t'").append(escape(entry.getKey())).append("':");

			// Don't surround function-strings in quotes
			Object val = entry.getValue();
			if (val instanceof FunctionString)
			{
				str.append(val.toString());
			}
			else if ((val instanceof Boolean) || (val instanceof Number))
			{
				str.append(val.toString());
			}
			else
			{
				str.append("'" + escape(val.toString()) + "'");
			}

			str.append(",\n");
		}
		if (!asFragment)
		{
			str.setLength(str.length() - 2);
			str.append("\n}\n");
		}
		return str;
	}

	/**
	 * Escapes any occurrence of '
	 * 
	 * @param input
	 * @return escaped input
	 */
	private String escape(final String input)
	{
		final StringBuilder output = new StringBuilder();

		for (final char ch : input.toCharArray())
		{
			if (ch == '\'')
			{
				output.append('\\');
			}
			output.append(ch);
		}

		return output.toString();
	}

}
