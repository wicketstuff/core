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
package org.wicketstuff.restutils.http;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.http.WebRequest;

/**
 * Utility class for HTTP-related operations.
 * 
 * @author andrea del bene
 * 
 */
public class HttpUtils
{
	/**
	 * Read the string content of the current request.
	 * 
	 * @param request
	 *            the current request
	 * @return the string inside body request.
	 * @throws IOException
	 */
	public static String readStringFromRequest(WebRequest request) throws IOException
	{
		HttpServletRequest httpRequest = (HttpServletRequest)request.getContainerRequest();
		return IOUtils.toString(httpRequest.getReader());
	}

	/**
	 * Utility method to extract the HTTP request method.
	 * 
	 * @param request
	 *            the current request object
	 * @return the HTTP method used for this request
	 * @see HttpMethod
	 */
	public static HttpMethod getHttpMethod(Request request)
	{
		return getHttpMethod((WebRequest)request);
	}

	/**
	 * Utility method to extract the HTTP request method.
	 * 
	 * @param request
	 *            the current request object
	 * @return the HTTP method used for this request
	 * @see HttpMethod
	 */
	public static HttpMethod getHttpMethod(WebRequest request)
	{
		HttpServletRequest httpRequest = (HttpServletRequest)request.getContainerRequest();
		return HttpMethod.toHttpMethod((httpRequest.getMethod()));
	}
}
