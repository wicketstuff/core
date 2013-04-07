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

import java.util.List;

import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;

/**
 * Utility class for retrieving request parameters
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class RequestCycleUtils
{
	/**
	 * Gets the value of a query parameter
	 * @param name the name of the query parameter
	 * @return a {@link StringValue}
	 */
	public static StringValue getQueryParameterValue(String name)
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final IRequestParameters parameters = requestCycle.getRequest().getQueryParameters();

		return parameters.getParameterValue(name);
	}

	/**
	 * Gets the values of a query parameter
	 * @param name the name of the query parameter
	 * @return a {@link List} of {@link StringValue}
	 */
	public static List<StringValue> getQueryParameterValues(String name)
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final IRequestParameters parameters = requestCycle.getRequest().getQueryParameters();

		return parameters.getParameterValues(name);
	}

	/**
	 * Gets the value of a post parameter
	 * @param name the name of the query parameter
	 * @return a {@link StringValue}
	 */
	public static StringValue getPostParameterValue(String name)
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final IRequestParameters parameters = requestCycle.getRequest().getPostParameters();

		return parameters.getParameterValue(name);
	}

	/**
	 * Gets the values of a post parameter
	 * @param name the name of the query parameter
	 * @return a {@link List} of {@link StringValue}
	 */
	public static List<StringValue> getPostParameterValues(String name)
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final IRequestParameters parameters = requestCycle.getRequest().getPostParameters();

		return parameters.getParameterValues(name);
	}

	/**
	 * Utility class
	 */
	private RequestCycleUtils()
	{

	}
}
