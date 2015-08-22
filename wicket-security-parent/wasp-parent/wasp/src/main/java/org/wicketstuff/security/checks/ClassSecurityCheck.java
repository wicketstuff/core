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
package org.wicketstuff.security.checks;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.wicketstuff.security.WaspApplication;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.strategies.ClassAuthorizationStrategy;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * Default instantiation check for any type of class. This is used by
 * {@link ClassAuthorizationStrategy} to test for instantiation rights. But you can use it yourself
 * to for any kind of action. Note that errorpages should not be outfitted with a securitycheck such
 * as this that checks for instantiation.
 * 
 * @author marrink
 * @see ClassAuthorizationStrategy
 */
public class ClassSecurityCheck extends AbstractSecurityCheck
{
	private static final long serialVersionUID = 1L;

	private Class<?> clazz;

	/**
	 * Constructs a new securitycheck for a class
	 * 
	 * @param clazz
	 *            the class to use in the check
	 * @throws IllegalArgumentException
	 *             if the clazz is null
	 */
	public ClassSecurityCheck(Class<?> clazz)
	{
		this.clazz = clazz;
		if (clazz == null)
			throw new IllegalArgumentException("clazz is null");
	}

	/**
	 * The class to check against.
	 * 
	 * @return Returns the class.
	 */
	public Class<?> getClazz()
	{
		return clazz;
	}

	/**
	 * Checks if the user is authorized for the action. special permission is given to the
	 * loginpage, which is always authorized. If the user is not authenticated he is redirected to
	 * the login page. Redirects the authorization check to the strategy if the user is
	 * authenticated.
	 * 
	 * @return true if the user is authenticated and authorized, false otherwise.
	 * @see org.wicketstuff.security.checks.ISecurityCheck#isActionAuthorized(org.wicketstuff.security.actions.WaspAction)
	 * @see WaspApplication#getLoginPage()
	 * @see WaspAuthorizationStrategy#isClassAuthorized(Class, WaspAction)
	 * @throws RestartResponseAtInterceptPageException
	 *             if the user is not authenticated.
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		if (getClazz() == getLoginPage())
			return true;
		if (isAuthenticated())
			return getStrategy().isClassAuthorized(getClazz(), action);
		throw new RestartResponseAtInterceptPageException(getLoginPage());

	}

	/**
	 * Redirects to the {@link WaspAuthorizationStrategy#isClassAuthenticated(Class)}.
	 * 
	 * @see org.wicketstuff.security.checks.ISecurityCheck#isAuthenticated()
	 */
	public boolean isAuthenticated()
	{
		return getStrategy().isClassAuthenticated(getClazz());
	}
}
