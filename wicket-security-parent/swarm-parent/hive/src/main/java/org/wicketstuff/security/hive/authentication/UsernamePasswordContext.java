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
package org.wicketstuff.security.hive.authentication;

import org.wicketstuff.security.authentication.LoginException;

/**
 * Simple {@link LoginContext} for a username and password, which may or may not be encrypted.
 * 
 * @author marrink
 */
public abstract class UsernamePasswordContext extends LoginContext
{
	/**
	 * The user attempting to login.
	 */
	private String username;

	/**
	 * The password for that user.
	 */
	private String password;

	/**
	 * Constructor for logoff purposes.
	 */
	public UsernamePasswordContext()
	{
		username = null;
		password = null;
	}

	/**
	 * 
	 * Constructor for login purposes.
	 * 
	 * @param username
	 *            the user attempting to login
	 * @param password
	 *            the password for the user
	 */
	public UsernamePasswordContext(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	/**
	 * Constructor for logoff purposes should you have chosen allow additional logins.
	 * 
	 * @param sortOrder
	 * @param allowAdditionalLogins
	 * @see LoginContext#LoginContext(int, boolean)
	 */
	protected UsernamePasswordContext(int sortOrder, boolean allowAdditionalLogins)
	{
		super(sortOrder, allowAdditionalLogins);
		username = null;
		password = null;
	}

	/**
	 * Constructor for login purposes should you have chosen allow additional logins.
	 * 
	 * @param username
	 *            the user attempting to login
	 * @param password
	 *            the password for the user
	 * @param sortOrder
	 * @param allowAdditionalLogins
	 * @see LoginContext#LoginContext(int, boolean)
	 */
	protected UsernamePasswordContext(String username, String password, int sortOrder,
		boolean allowAdditionalLogins)
	{
		super(sortOrder, allowAdditionalLogins);
		this.username = username;
		this.password = password;
	}

	/**
	 * The username and password are always null after this method returns.
	 * 
	 * @see org.wicketstuff.security.hive.authentication.LoginContext#login()
	 */
	@Override
	public final Subject login() throws LoginException
	{
		if (username == null || password == null)
			throw new LoginException("Insufficient information to login");
		Subject subject = getSubject(username, password);
		username = null;
		password = null;
		return subject;
	}

	/**
	 * Validates the username and password.
	 * 
	 * @param username
	 *            the user attempting to login
	 * @param password
	 *            the password for the user
	 * @return a {@link Subject} representing the user.
	 * @throws LoginException
	 *             if the user could not be validated
	 */
	protected abstract Subject getSubject(String username, String password) throws LoginException;
}
