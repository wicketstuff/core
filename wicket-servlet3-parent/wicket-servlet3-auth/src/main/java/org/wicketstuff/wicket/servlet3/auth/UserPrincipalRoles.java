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

import jakarta.servlet.http.HttpServletRequest;

import java.util.Iterator;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Implementation of Roles class that use the servlet 3 request object to
 * determine the current user roles.
 *
 * @author jsarman
 */
public class UserPrincipalRoles extends Roles
{

	private static final long serialVersionUID = 1L;

	public UserPrincipalRoles()
	{

	}

	@Override
	public final boolean hasAllRoles(Roles roles)
	{
		final HttpServletRequest request = getRequest();
		final Iterator<String> allRoles = roles.iterator();
		boolean result = true;
		while (allRoles.hasNext() && result)
		{
			result = request.isUserInRole(allRoles.next());
		}
		return result;
	}

	@Override
	public final boolean hasAnyRole(Roles roles)
	{
		final HttpServletRequest request = getRequest();
		final Iterator<String> allRoles = roles.iterator();
		boolean result = false;
		while (allRoles.hasNext() && !result)
		{
			result = request.isUserInRole(allRoles.next());
		}
		return result;
	}

	@Override
	public final boolean hasRole(String role)
	{
		return getRequest().isUserInRole(role);
	}

	private static HttpServletRequest getRequest()
	{
		return (HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest();
	}

}
