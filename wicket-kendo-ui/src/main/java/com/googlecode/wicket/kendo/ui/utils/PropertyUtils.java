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

/**
 * Utility class for Kendo UI bean properties
 *
 * @author Sebastien Briquet - sebfz1
 */
public class PropertyUtils
{
	/**
	 * Escapes the property name (for not being understood as JSON complex type)
	 *
	 * @param property the property name (ie: "mybean.myproperty")
	 * @return the escaped property name
	 */
	public static String escape(String property)
	{
		return property.replace('.', '$');
	}

	/**
	 * Unescapes the property name
	 *
	 * @param property the escaped property name
	 * @return the unescaped property name
	 */
	public static String unescape(String property)
	{
		return property.replace('$', '.');
	}

	/**
	 * Utility class
	 */
	private PropertyUtils()
	{
	}
}
