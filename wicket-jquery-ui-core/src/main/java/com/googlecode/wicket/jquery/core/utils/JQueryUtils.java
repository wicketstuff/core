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
package com.googlecode.wicket.jquery.core.utils;

/**
 * Utility class for javascript / jQuery statements
 * 
 * @author Sebastien Briquet - sebfz1
 * 
 */
public class JQueryUtils
{
	/**
	 * Utility class
	 */
	private JQueryUtils()
	{
	}

	/**
	 * Wraps a js statement into a try-catch block
	 * 
	 * @param statement the js statement
	 * @return the wrapped statement
	 */
	public static String trycatch(String statement)
	{
		return JQueryUtils.trycatch(statement, false);
	}

	/**
	 * Wraps a js statement into a try-catch block
	 * 
	 * @param statement the js statement
	 * @param warn whether a warn should be logged to the console
	 * @return the wrapped statement
	 */
	public static String trycatch(String statement, boolean warn)
	{
		return "try { " + statement + " } catch (e) { " + (warn ? "if (console) { console.warn(e); }" : "") + " }";
	}

	/**
	 * Gets the statement that detaches the element matching the current selector from the DOM.<br>
	 * The {@code #detach} method is the same as {@code #remove}, except that {@code #detach} keeps all jQuery data associated with the removed elements.<br>
	 * This method is useful when removed elements are to be reinserted into the DOM at a later time.
	 * 
	 * @param selector the jQuery selector
	 * @return the statement
	 * @see #remove(AjaxRequestTarget)
	 */
	public static String detach(String selector)
	{
		return String.format("var $w = jQuery('%s'); if($w) { $w.detach(); }", selector);
	}

	/**
	 * Gets the statement that removes the element matching the current selector from the DOM.<br>
	 * Use {@code #remove} when you want to remove the element itself, as well as everything inside it.<br>
	 * In addition to the elements themselves, all bound events and jQuery data associated with the elements are removed.<br>
	 * To remove the elements without removing data and events, use {@code #detach} instead.
	 * 
	 * @param selector the jQuery selector
	 * @return the statement
	 * @see #detach(AjaxRequestTarget)
	 */
	public static String remove(String selector)
	{
		return String.format("var $w = jQuery('%s'); if($w) { $w.remove(); }", selector);
	}
}
