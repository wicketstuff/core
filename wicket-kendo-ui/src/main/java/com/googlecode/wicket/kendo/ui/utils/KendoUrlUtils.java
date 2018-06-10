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
package com.googlecode.wicket.kendo.ui.utils;

import java.util.regex.Pattern;

/**
 * Utility class for handling Url in Kendo UI templates
 *
 * @author Sebastien Briquet - sebfz1
 */
public class KendoUrlUtils
{
	private static final Pattern DATA_PATTERN = Pattern.compile("%23:(?<data>.*?)%23");
	private static final String DATA_REPLACE = "#:${data}#";

	/**
	 * Utility class
	 */
	private KendoUrlUtils()
	{
		// noop
	}

	/**
	 * Unescapes the URL ({@code %23} will be replaced by {@code #}
	 * 
	 * @param url the url/text to unescape
	 * @return the unescaped url
	 */
	public static String unescape(String url)
	{
		return DATA_PATTERN.matcher(url).replaceAll(DATA_REPLACE);
	}
}
