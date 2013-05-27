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
package com.googlecode.wicket.jquery.ui.kendo.utils;

/**
 * Utility class for Kendo UI datetime formats
 *
 * @see <a href="http://docs.kendoui.com/api/framework/kendo">http://docs.kendoui.com/api/framework/kendo</a>
 * @author Sebastien Briquet - sebfz1
 */
public class KendoDateTimeUtils
{
	static final String j_chars = "GyMdkHmsSEDFwWahKzZ";
	static final String k_chars = "GyMdkHmsfEDFwWthKzZ"; //'S' > 'f', 'a' > 't'
	static final int chars_lenth = 19;

	/**
	 * Converts a java datetime pattern to a kendo-ui datetime pattern
	 *
	 * @param pattern the java pattern
	 * @return the kendo-ui datetime pattern
	 */
	public static String toPattern(String pattern)
	{
		String converted = pattern;

		for (int i = 0; i < chars_lenth; i++)
		{
			char j = j_chars.charAt(i);
			char k = k_chars.charAt(i);

			if (j != k)
			{
				converted = converted.replace(j, k);
			}
		}

		return converted;
	}

	/**
	 * Utility class
	 */
	private KendoDateTimeUtils()
	{
	}
}
