/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.hugerte;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Writes configuration values as a JavaScript object literal. Strings are escaped so that the
 * output is safe inside an HTML {@code <script>} element; {@link RawJavaScript} is written as is.
 */
final class JavaScriptWriter
{
	private JavaScriptWriter()
	{
	}

	static String write(Object value)
	{
		var out = new StringBuilder();
		write(out, value);
		return out.toString();
	}

	static void write(StringBuilder out, Object value)
	{
		if (value == null)
		{
			out.append("null");
		}
		else if (value instanceof String s)
		{
			writeString(out, s);
		}
		else if (value instanceof Boolean || value instanceof Integer || value instanceof Long ||
			value instanceof Short || value instanceof Byte || value instanceof BigInteger ||
			value instanceof Double || value instanceof Float)
		{
			out.append(value);
		}
		else if (value instanceof BigDecimal d)
		{
			out.append(d.toString());
		}
		else if (value instanceof RawJavaScript raw)
		{
			out.append(raw.code());
		}
		else if (value instanceof List<?> list)
		{
			writeList(out, list);
		}
		else if (value instanceof Map<?, ?> map)
		{
			writeMap(out, map);
		}
		else
		{
			throw new IllegalArgumentException(
				"Unsupported configuration value of type " + value.getClass().getName());
		}
	}

	private static void writeList(StringBuilder out, List<?> list)
	{
		out.append('[');
		for (int i = 0; i < list.size(); i++)
		{
			if (i > 0)
			{
				out.append(',');
			}
			write(out, list.get(i));
		}
		out.append(']');
	}

	private static void writeMap(StringBuilder out, Map<?, ?> map)
	{
		out.append('{');
		boolean first = true;
		for (var entry : map.entrySet())
		{
			if (!first)
			{
				out.append(',');
			}
			first = false;
			writeString(out, (String)entry.getKey());
			out.append(':');
			write(out, entry.getValue());
		}
		out.append('}');
	}

	static void writeString(StringBuilder out, String s)
	{
		out.append('"');
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			switch (c)
			{
				case '"' -> out.append("\\\"");
				case '\\' -> out.append("\\\\");
				case '\n' -> out.append("\\n");
				case '\r' -> out.append("\\r");
				case '\t' -> out.append("\\t");
				case '\b' -> out.append("\\b");
				case '\f' -> out.append("\\f");
				// keep "</script>", "<!--" and "]]>" from ending or changing the script element
				case '<', '>', '&', '\'', '\u2028', '\u2029' -> unicodeEscape(out, c);
				default ->
				{
					if (c < 0x20)
					{
						unicodeEscape(out, c);
					}
					else
					{
						out.append(c);
					}
				}
			}
		}
		out.append('"');
	}

	private static void unicodeEscape(StringBuilder out, char c)
	{
		out.append(String.format("\\u%04x", (int)c));
	}
}
