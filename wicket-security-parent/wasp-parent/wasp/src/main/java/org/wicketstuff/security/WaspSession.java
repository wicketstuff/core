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
package org.wicketstuff.security;

import org.apache.wicket.Session;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * Session for keeping the session scoped IAuthorizationStrategy and for providing easy access to
 * login, logoff and isAuthenticated.
 * 
 * @author marrink
 */
public class WaspSession extends WebSession
{
	private static final long serialVersionUID = 1L;

	private WaspAuthorizationStrategy securityStrategy;

	public WaspSession(WaspApplication application, Request request)
	{
		super(request);
		securityStrategy = application.getStrategyFactory().newStrategy();
	}

	/**
	 * Returns a session scoped WaspAuthorizationStrategy.
	 * 
	 * @see Session#getAuthorizationStrategy()
	 */
	@Override
	public IAuthorizationStrategy getAuthorizationStrategy()
	{
		return securityStrategy;
	}

	/**
	 * Attempts to login with the current login info.
	 * 
	 * @param context
	 *            any type of information required to login
	 * @throws LoginException
	 * @see WaspAuthorizationStrategy#login(Object)
	 */
	public void login(Object context) throws LoginException
	{
		securityStrategy.login(context);
		// make session permanent after login
		if (isTemporary())
			bind();
		else
			dirty(); // for cluster replication.
	}

	/**
	 * Attempts to log off the current user. Even though this call Already handles dirty flags. The
	 * {@link WaspAuthorizationStrategy} should also do the same as it is not guaranteed that every
	 * logoff comes from the session.
	 * 
	 * @param context
	 *            the context to use for logging off
	 * @return true, if the logoff was successful, false otherwise
	 * 
	 * @see WaspAuthorizationStrategy#logoff(Object)
	 */
	public boolean logoff(Object context)
	{
		if (securityStrategy != null && securityStrategy.logoff(context))
		{
			if (securityStrategy.isUserAuthenticated())
				dirty();
			else
				invalidate();
			return true;
		}
		return false;
	}

	/**
	 * Quick check if any user at all is currently authenticated.
	 * 
	 * @return true if an authenticated user is present, false otherwise
	 */
	public boolean isUserAuthenticated()
	{
		return securityStrategy.isUserAuthenticated();
	}

	/**
	 * Cleans up the WaspAuthorizationStrategy before killing this session. If you override this
	 * method you must call super.invalidateNow().
	 * 
	 * @see WebSession#invalidateNow()
	 */
	@Override
	public void invalidateNow()
	{
		securityStrategy.destroy();
		super.invalidateNow();
	}

	/**
	 * 
	 * @see org.apache.wicket.Session#detach()
	 */
	@Override
	public void detach()
	{
		if (isTemporary() && securityStrategy.isUserAuthenticated())
			bind();
		super.detach();
	}
}
