/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.jeeweb.el;

import org.apache.wicket.Page;
import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.jeeweb.ajax.JEEWebGlobalAjaxHandler;

/**
 * This class is used for JSPs and JSFs to render a URLs with EL expressions:
 * 
 * <pre>
 * ${wicket:url('mypackage.MyPage')}
 * and 
 * ${wicket:urlWithQuery('mypackage.MyPage','param1=value1&param2=value2')}
 * </pre>
 * 
 * @author Tobias Soloschenko
 */
public class ELFunctions
{

	/**
	 * Creates a URL based on the given page and the given query
	 * 
	 * @param page
	 *            the page to be used within the link
	 * @param query
	 *            the query with all parameters
	 * @return the url
	 */
	public static String urlWithQuery(String page, String query)
	{
		PageParameters pageParameters = new PageParameters();
		if (query != null)
		{
			RequestUtils.decodeParameters(query, pageParameters);
		}
		Class<Page> resolveClass = WicketObjects.resolveClass(page);
		final CharSequence urlFor = RequestCycle.get().urlFor(resolveClass, pageParameters);
		return urlFor.toString();
	}

	/**
	 * Creates a URL based on the given page
	 * 
	 * @param page
	 *            the page to be used within the link
	 * @return the URL
	 */
	public static String url(String page)
	{
		PageParameters pageParameters = new PageParameters();
		Class<Page> resolveClass = WicketObjects.resolveClass(page);
		final CharSequence urlFor = RequestCycle.get().urlFor(resolveClass, pageParameters);
		return urlFor.toString();
	}

	/**
	 * Gets the plan callback URL to process a request to the current rendered page with pre
	 * rendered query arguments
	 * 
	 * @param query
	 *            the query
	 * @return the plane callback URL
	 */
	public static String ajaxCallbackUrlWithQuery(String query)
	{
		PageParameters pageParameters = new PageParameters();
		if (query != null)
		{
			RequestUtils.decodeParameters(query, pageParameters);
		}
		pageParameters.add("pageId", PageRequestHandlerTracker.getLastHandler(RequestCycle.get())
			.getPage().getPageId());
		final CharSequence urlFor = RequestCycle.get().urlFor(new JEEWebGlobalAjaxHandler(),
			pageParameters);
		return urlFor.toString();
	}

	/**
	 * Gets the plan callback URL to process a request to the current rendered page
	 * 
	 * @return the plane callback URL
	 */
	public static String ajaxCallbackUrl()
	{
		PageParameters pageParameters = new PageParameters();
		pageParameters.add("pageId", PageRequestHandlerTracker.getLastHandler(RequestCycle.get())
			.getPage().getPageId());
		final CharSequence urlFor = RequestCycle.get().urlFor(new JEEWebGlobalAjaxHandler(),
			pageParameters);
		return urlFor.toString();
	}

	/**
	 * Creates a ajax callback function that processes a request to the current rendered page
	 * 
	 * @return the callback function
	 */
	public static String ajaxGet()
	{
		return "Wicket.Ajax.get({'u':'" + ajaxCallbackUrl() + "'});";
	}

	/**
	 * Creates a ajax callback function that processes a request to the current rendered page.
	 * It takes in input a query string.
	 * 
	 * @param query
	 *            the query to be send to the function
	 * @return the callback function
	 */
	public static String ajaxGetWithQuery(String query)
	{
		return "Wicket.Ajax.get({'u':'" + ajaxCallbackUrlWithQuery(query) + "'});";
	}
	
	/**
	 * Creates a ajax callback function to send a form via AJAX to the current rendered page.
	 * 
	 * @param method
	 * 			the request method that must be used (GET or POST).
	 * @return the callback function
	 */
	public static String ajaxFormSubmit(String method)
	{
		return "Wicket.Ajax.ajax({'u':'" + ajaxCallbackUrl() + "', 'm':'" + method +"', 'f':this.id});return false;";
	}
}
