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
package org.wicketstuff.security.authentication;

/**
 * Thrown when an exception arrises during the login.
 * 
 * @author marrink
 */
public class LoginException extends Exception
{
	private Object loginContext;

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LoginException()
	{
	}

	/**
	 * @param message
	 */
	public LoginException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public LoginException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LoginException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * The login context that cause the exception.
	 * 
	 * @return login context
	 */
	public Object getLoginContext()
	{
		return loginContext;
	}

	/**
	 * Set the login context that caused the problem.
	 * 
	 * @param loginContext
	 * @return this exception
	 */
	public LoginException setLoginContext(Object loginContext)
	{
		this.loginContext = loginContext;
		return this;
	}

}
