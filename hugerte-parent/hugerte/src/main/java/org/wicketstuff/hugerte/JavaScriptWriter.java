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
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Writes configuration values as a JavaScript object literal. Strings are escaped so that the
 * output is safe inside an HTML {@code <script>} element; {@link RawJavaScript} is written as is, and a
 * {@link ResourceReference} as the string of its URL: an absolute path including the context path,
 * because HugeRTE resolves some options, such as {@code external_plugins}, against its own script
 * directory rather than the page.
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
		switch (value)
		{
			case null -> out.append("null");
			case String s -> writeString(out, s);
			case Boolean b -> out.append(b);
			case Integer i -> out.append(i);
			case Long l -> out.append(l);
			case Short s -> out.append(s);
			case Byte b -> out.append(b);
			case BigInteger i -> out.append(i);
			case BigDecimal d -> out.append(d.toString());
			case Double d -> out.append(d);
			case Float f -> out.append(f);
			case RawJavaScript raw -> out.append(raw.code());
			case ResourceReference reference -> writeString(out, url(reference));
			case List<?> list -> writeList(out, list);
			case Map<?, ?> map -> writeMap(out, map);
			default -> throw new IllegalArgumentException(
				"Unsupported configuration value of type " + value.getClass().getName());
		}
	}

	private static String url(ResourceReference reference)
	{
		var cycle = RequestCycle.get();
		if (cycle == null)
		{
			throw new IllegalStateException(
				"Writing a ResourceReference as a URL needs an active request cycle: " + reference);
		}
		var renderer = cycle.getUrlRenderer();
		var request = cycle.getRequest();
		// Wicket renders the URL as the browser should request it from the current page, so
		// resolving it against the page's path, as the browser would, gives the absolute path, also
		// for URLs outside Wicket's filter path
		String root = "/" + request.getContextPath() + "/" + request.getFilterPath() + "/";
		return resolve(root.replaceAll("/+", "/"), renderer.getBaseUrl().toString(),
			renderer.renderUrl(cycle.mapUrlFor(reference, null)));
	}

	/**
	 * Resolves a URL the way a browser does on the page {@code root + page}.
	 *
	 * @param root
	 *            the absolute path of Wicket's filter, ending with a slash
	 * @param page
	 *            the page URL relative to {@code root}
	 * @param url
	 *            the URL to resolve
	 * @return {@code url} unchanged when it is absolute or starts with a slash, otherwise the
	 *         absolute path it resolves to, with query and fragment
	 */
	static String resolve(String root, String page, String url)
	{
		URI target = URI.create(url);
		if (target.isAbsolute() || target.getRawAuthority() != null ||
			target.getRawPath().startsWith("/"))
		{
			return url;
		}
		URI base = URI.create(root + page.replaceFirst("^/+", ""));
		if (target.getRawPath().isEmpty())
		{
			// java.net.URI drops the last path segment for a query-only reference; a browser
			// keeps the page path, and also its query for a fragment-only reference
			String query = target.getRawQuery() != null ? target.getRawQuery()
				: base.getRawQuery();
			return base.getRawPath() + (query == null ? "" : "?" + query) +
				(target.getRawFragment() == null ? "" : "#" + target.getRawFragment());
		}
		return base.resolve(target).toASCIIString();
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
