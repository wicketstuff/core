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
package org.apache.wicket.security.yahoo;

import java.io.Serializable;

/**
 * Simple pojo to contain the response for an authenticated user from a Yahoo webservice.
 * 
 * @author marrink
 */
public final class YahooResponse implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String sessionId;

	private String cookieContent;

	private long timeout;

	/**
	 * Constructor for Serialization purposes. Do not use. Construct.
	 */
	public YahooResponse()
	{
	}

	/**
	 * 
	 * Constructor.
	 * 
	 * @param sessionId
	 *            Yahoo session id
	 * @param timeout
	 *            session timeout in ??
	 * @param cookieContent
	 *            content of the cookie to be used for all authenticated webservice calls
	 *            to Yahoo
	 */
	public YahooResponse(String sessionId, long timeout, String cookieContent)
	{
		super();
		this.cookieContent = cookieContent;
		this.timeout = timeout;
		this.sessionId = sessionId;
	}

	/**
	 * The Yahoo session id.
	 * 
	 * @return session id
	 */
	public String getSessionId()
	{
		return sessionId;
	}

	/**
	 * Gets cookieContent.
	 * 
	 * @return cookieContent
	 */
	public String getCookieContent()
	{
		return cookieContent;
	}

	// TODO factory method to generate javax.servlet.http.Cookie

	/**
	 * Gets timeout of the session.
	 * 
	 * @return timeout in ??
	 */
	public long getTimeout()
	{
		return timeout;
	}
}
