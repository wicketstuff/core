package com.googlecode.wicket.jquery.core.utils;

import java.util.List;

import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.util.lang.Generics;

/**
 * Utility class for json objects
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JsonUtils
{
	/**
	 * Converts an array of {@code String} to a {@link JSONArray} of (quoted) strings
	 * 
	 * @param values the array of strings
	 * @return a {@link JSONArray}
	 */
	public static JSONArray toArray(String[] values)
	{
		List<String> list = Generics.newArrayList(values.length);

		for (String value : values)
		{
			list.add(value);
		}

		return new JSONArray(list);
	}

	/**
	 * Converts an array of enums to a {@link JSONArray} of (quoted) strings
	 * 
	 * @param values the array of strings
	 * @return a {@link JSONArray}
	 */
	public static JSONArray toArray(Enum<?>[] values)
	{
		List<String> list = Generics.newArrayList(values.length);

		for (Enum<?> value : values)
		{
			list.add(value.name());
		}

		return new JSONArray(list);
	}

	/**
	 * Utility class
	 */
	private JsonUtils()
	{
	}
}
