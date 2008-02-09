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
package org.apache.wicket.security.examples.acegi.authentication;

import org.acegisecurity.AcegiSecurityException;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.AbstractAuthenticationToken;
import org.apache.wicket.Application;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.examples.acegi.AcegiApplication;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.Subject;

/**
 * A general purpose wrapper to authenticate a user with Wasp through Acegi. It
 * does not support multi-login. Provided as is without warranty or
 * responsibility for damage.
 * 
 * @author marrink
 */
public final class AcegiLoginContext extends LoginContext
{
	private AbstractAuthenticationToken token;

	/**
	 * 
	 * Constructor for logoff purposes.
	 */
	public AcegiLoginContext()
	{

	}

	/**
	 * Constructs a new LoginContext with the provided Acegi
	 * AuthenticationToken.
	 * 
	 * @param token
	 *            contains credentials like username and password
	 */
	public AcegiLoginContext(AbstractAuthenticationToken token)
	{
		this.token = token;
	}

	/**
	 * @see org.apache.wicket.security.hive.authentication.LoginContext#login()
	 */
	public Subject login() throws LoginException
	{
		if (token == null)
			throw new LoginException("Insufficient information to login");
		// Attempt authentication.
		try
		{
			AuthenticationManager authenticationManager = ((AcegiApplication)Application.get())
					.getAuthenticationManager();
			if (authenticationManager == null)
				throw new LoginException(
						"AuthenticationManager is not available, check if your spring config contains a property for the authenticationManager in your wicketApplication bean.");
			Authentication authResult = authenticationManager.authenticate(token);
			setAuthentication(authResult);
		}
		catch (AcegiSecurityException e)
		{
			setAuthentication(null);
			throw new LoginException(e);

		}

		catch (RuntimeException e)
		{
			setAuthentication(null);
			throw new LoginException(e);
		}
		// cleanup
		token = null;
		// return result
		return new AcegiSubject();
	}

	/**
	 * Sets the acegi authentication.
	 * 
	 * @param authentication
	 *            the authentication or null to clear
	 */
	private void setAuthentication(Authentication authentication)
	{
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * Notify Acegi.
	 * 
	 * @see org.apache.wicket.security.hive.authentication.LoginContext#notifyLogoff(org.apache.wicket.security.hive.authentication.Subject)
	 */
	public void notifyLogoff(Subject subject)
	{
		setAuthentication(null);
	}
}
