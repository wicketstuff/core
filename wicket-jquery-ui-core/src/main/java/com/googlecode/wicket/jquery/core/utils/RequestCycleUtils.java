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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPageClassRequestHandler;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;

/**
 * Utility class for {@link RequestCycle}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class RequestCycleUtils
{
	/**
	 * Utility class
	 */
	private RequestCycleUtils()
	{
		// noop
	}

	/**
	 * Gets the current {@link AjaxRequestTarget}
	 *
	 * @return the target or {@code null} if not in an ajax request
	 */
	public static AjaxRequestTarget getAjaxRequestTarget()
	{
		return RequestCycle.get().find(AjaxRequestTarget.class).orElse(null);
	}

	/**
	 * Gets the current {@link IPartialPageRequestHandler}
	 *
	 * @return the handler or {@code null} if not in an ajax request / websocket notification
	 */
	public static IPartialPageRequestHandler getRequestHandler()
	{
		return RequestCycle.get().find(IPartialPageRequestHandler.class).orElse(null);
	}

	/**
	 * Gets the page class of the current request cycle
	 * 
	 * @return the {@link IRequestablePage} class
	 */
	public static Class<? extends IRequestablePage> getPageClass()
	{
		IPageClassRequestHandler handler = RequestCycle.get().find(IPageClassRequestHandler.class).orElse(null);

		if (handler != null)
		{
			return handler.getPageClass();
		}

		return null;
	}

	/**
	 * Indicates whether the query contains the specified parameter
	 *
	 * @param name the name of the query parameter
	 * @return {@code true} if the parameter is found
	 */
	public static boolean hasQueryParameter(String name)
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final IRequestParameters parameters = requestCycle.getRequest().getQueryParameters();

		return parameters.getParameterNames().stream().anyMatch(name::equals);
	}

	/**
	 * Gets the value of a query parameter
	 *
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
	 *
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
	 *
	 * @param name the name of the post parameter
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
	 *
	 * @param name the name of the post parameter
	 * @return a {@link List} of {@link StringValue}
	 */
	public static List<StringValue> getPostParameterValues(String name)
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final IRequestParameters parameters = requestCycle.getRequest().getPostParameters();

		return parameters.getParameterValues(name);
	}

	/**
	 * Gets the value of a request parameter
	 *
	 * @param name the name of the parameter
	 * @return a {@link StringValue}
	 */
	public static StringValue getRequestParameterValue(String name)
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final IRequestParameters parameters = requestCycle.getRequest().getRequestParameters();

		return parameters.getParameterValue(name);
	}

	/**
	 * Gets the values of a request parameter
	 *
	 * @param name the name of the parameter
	 * @return a {@link List} of {@link StringValue}
	 */
	public static List<StringValue> getRequestParameterValues(String name)
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final IRequestParameters parameters = requestCycle.getRequest().getRequestParameters();

		return parameters.getParameterValues(name);
	}
}
