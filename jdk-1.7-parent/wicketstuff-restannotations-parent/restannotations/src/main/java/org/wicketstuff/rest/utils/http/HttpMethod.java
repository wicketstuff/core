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
package org.wicketstuff.rest.utils.http;

/***
 * Enum class that represents all the possible HTTP request methods.
 * 
 * @author andrea del bene
 * 
 */
public enum HttpMethod
{
	GET("GET"), POST("POST"), HEAD("HEAD"), OPTIONS("OPTIONS"), PUT("PUT"), PATCH("PATCH"), DELETE(
		"DELETE"), TRACE("TRACE");

	private String method;

	private HttpMethod(String method)
	{
		this.method = method;
	}

	/**
	 * Converts a string (like "put", "get", "post", etc...) to the corresponding HTTP method.
	 * 
	 * @param httpMethod
	 *            the string value we want to convert. The conversion mechanism is case-insensitive.
	 * @return
	 */
	public static HttpMethod toHttpMethod(String httpMethod)
	{
		HttpMethod[] values = HttpMethod.values();
		httpMethod = httpMethod.toUpperCase();

		for (int i = 0; i < values.length; i++)
		{
			if (values[i].method.equals(httpMethod))
				return values[i];
		}

		throw new RuntimeException("The string value '" + httpMethod +
			"' does not correspond to any valid HTTP request method");
	}

	public String getMethod()
	{
		return method;
	}
}
