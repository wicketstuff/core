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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Immutable configuration of a HugeRTE editor.
 * <p>
 * Plugins and toolbar have typed accessors; every other
 * <a href="https://hugerte.org">HugeRTE option</a> is set with {@link #with(String, Object)}.
 * Each {@code with...} method returns a new configuration:
 *
 * <pre>{@code
 * HugeRteConfig config = HugeRteConfig.defaults()
 * 	.withPlugins(HugeRtePlugin.LISTS, HugeRtePlugin.LINK)
 * 	.withToolbar("undo redo | bold italic | bullist numlist | link")
 * 	.withMenubar(false)
 * 	.with("height", 300);
 * }</pre>
 *
 * Option values may be strings, booleans, numbers, {@link RawJavaScript}, and lists or maps (with
 * string keys) of those. The editor's target element is set by {@link HugeRteBehavior}.
 *
 * @param plugins
 *            the plugins to load
 * @param toolbar
 *            toolbar rows; empty means the HugeRTE default toolbar
 * @param options
 *            all other options, in insertion order
 */
public record HugeRteConfig(Set<HugeRtePlugin> plugins, List<String> toolbar,
	Map<String, Object> options) implements Serializable
{
	/** Options that this class or {@link HugeRteBehavior} manage. */
	private static final Set<String> RESERVED = Set.of("selector", "target", "plugins", "toolbar");

	private static final HugeRteConfig DEFAULTS = new HugeRteConfig(Set.of(), List.of(), Map.of());

	public HugeRteConfig
	{
		plugins = plugins.isEmpty() ? Set.of()
			: Collections.unmodifiableSet(EnumSet.copyOf(plugins));
		toolbar = List.copyOf(toolbar);
		var copy = new LinkedHashMap<String, Object>();
		options.forEach((name, value) -> copy.put(checkOption(name), normalize(name, value)));
		options = Collections.unmodifiableMap(copy);
	}

	/**
	 * @return a configuration without plugins, toolbar or options, which gives the HugeRTE
	 *         defaults
	 */
	public static HugeRteConfig defaults()
	{
		return DEFAULTS;
	}

	/**
	 * @return a copy with the given plugins added
	 */
	public HugeRteConfig withPlugins(HugeRtePlugin... plugins)
	{
		var all = EnumSet.noneOf(HugeRtePlugin.class);
		all.addAll(this.plugins);
		all.addAll(Arrays.asList(plugins));
		return new HugeRteConfig(all, toolbar, options);
	}

	/**
	 * @param rows
	 *            one string per toolbar row, with button names separated by spaces and groups
	 *            separated by {@code |}
	 * @return a copy with the toolbar replaced
	 */
	public HugeRteConfig withToolbar(String... rows)
	{
		return new HugeRteConfig(plugins, List.of(rows), options);
	}

	/**
	 * @return a copy with the option set to the value, replacing an earlier value
	 * @throws IllegalArgumentException
	 *             for a reserved option ({@code selector}, {@code target}, {@code plugins},
	 *             {@code toolbar}) or an unsupported value type
	 */
	public HugeRteConfig with(String option, Object value)
	{
		var copy = new LinkedHashMap<>(options);
		copy.put(option, value);
		return new HugeRteConfig(plugins, toolbar, copy);
	}

	/**
	 * @return a copy without the option
	 */
	public HugeRteConfig without(String option)
	{
		var copy = new LinkedHashMap<>(options);
		copy.remove(option);
		return new HugeRteConfig(plugins, toolbar, copy);
	}

	/** Shows or hides the menu bar. */
	public HugeRteConfig withMenubar(boolean menubar)
	{
		return with("menubar", menubar);
	}

	/** Shows or hides the status bar. */
	public HugeRteConfig withStatusbar(boolean statusbar)
	{
		return with("statusbar", statusbar);
	}

	/** Makes the editor read-only. */
	public HugeRteConfig withReadonly(boolean readonly)
	{
		return with("readonly", readonly);
	}

	/**
	 * @param contentCss
	 *            a built-in content style ({@code default}, {@code dark}, {@code document},
	 *            {@code writer}) or the URL of a style sheet
	 */
	public HugeRteConfig withContentCss(String contentCss)
	{
		return with("content_css", contentCss);
	}

	/**
	 * @return the configuration as a JavaScript object literal, without a target element
	 */
	public String toJavaScript()
	{
		var all = new LinkedHashMap<String, Object>();
		if (!plugins.isEmpty())
		{
			all.put("plugins", plugins.stream().map(HugeRtePlugin::id).toList());
		}
		if (toolbar.size() == 1)
		{
			all.put("toolbar", toolbar.get(0));
		}
		else if (!toolbar.isEmpty())
		{
			all.put("toolbar", toolbar);
		}
		all.putAll(options);
		return JavaScriptWriter.write(all);
	}

	private static String checkOption(String name)
	{
		Objects.requireNonNull(name, "option");
		if (RESERVED.contains(name))
		{
			throw new IllegalArgumentException("Option '" + name +
				"' is managed by HugeRteConfig or HugeRteBehavior and cannot be set directly");
		}
		return name;
	}

	/**
	 * Validates a value and returns an immutable, serializable copy of it.
	 */
	private static Object normalize(String name, Object value)
	{
		if (value == null)
		{
			throw new IllegalArgumentException(
				"Option '" + name + "' has no value; use without(\"" + name + "\") to remove it");
		}
		if (value instanceof CharSequence s)
		{
			return s.toString();
		}
		if (value instanceof Boolean || value instanceof Integer || value instanceof Long ||
			value instanceof Short || value instanceof Byte || value instanceof BigInteger ||
			value instanceof BigDecimal || value instanceof RawJavaScript)
		{
			return value;
		}
		if (value instanceof Double d && Double.isFinite(d))
		{
			return d;
		}
		if (value instanceof Float f && Float.isFinite(f))
		{
			return f;
		}
		if (value instanceof Collection<?> collection)
		{
			var list = new ArrayList<Object>(collection.size());
			collection.forEach(item -> list.add(normalize(name, item)));
			return Collections.unmodifiableList(list);
		}
		if (value instanceof Map<?, ?> map)
		{
			var copy = new LinkedHashMap<String, Object>();
			map.forEach((key, item) -> {
				if (!(key instanceof String s))
				{
					throw new IllegalArgumentException(
						"Option '" + name + "' has a map with a non-string key: " + key);
				}
				copy.put(s, normalize(name, item));
			});
			return Collections.unmodifiableMap(copy);
		}
		throw new IllegalArgumentException("Option '" + name +
			"' has an unsupported value of type " + value.getClass().getName());
	}
}
