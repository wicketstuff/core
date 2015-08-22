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

import org.apache.wicket.security.authentication.LoginException;

/**
 * Login exception with additional information obtained from the Yahoo webservice.
 * 
 * @author marrink
 */
public class YahooLoginException extends LoginException
{

	private static final long serialVersionUID = 1L;

	private final String errorCode;

	/**
	 * Construct.
	 */
	public YahooLoginException()
	{
		errorCode = null;
	}

	/**
	 * Construct.
	 * 
	 * @param message
	 */
	public YahooLoginException(String message)
	{
		super(message);
		errorCode = null;
	}

	/**
	 * Construct.
	 * 
	 * @param errorCode
	 * @param message
	 */
	public YahooLoginException(String errorCode, String message)
	{
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * Construct.
	 * 
	 * @param cause
	 */
	public YahooLoginException(Throwable cause)
	{
		super(cause);
		errorCode = null;
	}

	/**
	 * Construct.
	 * 
	 * @param message
	 * @param cause
	 */
	public YahooLoginException(String message, Throwable cause)
	{
		super(message, cause);
		errorCode = null;
	}

	/**
	 * Construct.
	 * 
	 * @param errorCode
	 * @param message
	 * @param cause
	 */
	public YahooLoginException(String errorCode, String message, Throwable cause)
	{
		super(message, cause);
		this.errorCode = errorCode;
	}

	/**
	 * The errorCode from Yahoo, if available.
	 * 
	 * @return code or null if not available.
	 */
	public final String getErrorCode()
	{
		return errorCode;
	}

}
