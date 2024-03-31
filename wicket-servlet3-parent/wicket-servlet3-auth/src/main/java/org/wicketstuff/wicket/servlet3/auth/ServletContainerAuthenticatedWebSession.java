/*
 * Copyright 2014 WicketStuff.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.wicket.servlet3.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Principal;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Implementation class for AuthenticatedWebSession that uses servlet 3 request
 * to authenticate user.
 *
 * @author jsarman
 */
public class ServletContainerAuthenticatedWebSession extends AuthenticatedWebSession
{

	private static final long serialVersionUID = 1L;

	/**
	 * @return Current authenticated web session
	 */
	public static ServletContainerAuthenticatedWebSession get()
	{
		return (ServletContainerAuthenticatedWebSession) Session.get();
	}

	public ServletContainerAuthenticatedWebSession(Request request)
	{
		super(request);
	}

	/**
	 * Convenience method to retrieve authenticated users id.
	 *
	 * @return name member of Principal object in servlet 3 request
	 */
	public static String getUserName()
	{
		final Principal principal = getRequest().getUserPrincipal();
		if (principal == null)
		{
			return null;
		}
		return principal.getName();
	}

	@Override
	public final Roles getRoles()
	{
		if (isSignedIn())
		{
			return new UserPrincipalRoles();
		}
		return null;
	}

	@Override
	public final void signOut()
	{
		signIn(false);
		if (getRequest().getUserPrincipal() != null)
		{
			try
			{
				getRequest().logout();
			} catch (ServletException ex)
			{
				throw new RuntimeException(ex);
			}
		}
	}

	@Override
	public boolean authenticate(String username, String password)
	{
		try
		{
			//some user is already logged in so logout
			if (getRequest().getUserPrincipal() != null)
			{
				signOut();
			}
			//Login using the 3.0 servlet request call
			getRequest().login(username, password);

			return true;
		} catch (ServletException ex)
		{
			return false;
		}

	}

	private static HttpServletRequest getRequest()
	{
		return (HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest();
	}

}
