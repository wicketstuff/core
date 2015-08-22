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
package org.apache.wicket.security.examples.customactions.authorization;

import org.apache.wicket.security.actions.Enable;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.AbstractSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;

/**
 * Shows how to chain securitychecks rather than overriding them. It enforces the "enable"
 * action on every check.
 * 
 * @author marrink
 */
public class EnableCheck extends AbstractSecurityCheck
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ISecurityCheck wrapped;

	/**
	 * Construct.
	 * 
	 * @param check
	 *            the check we are chaining
	 */
	public EnableCheck(ISecurityCheck check)
	{
		wrapped = check;
		if (check == null)
			throw new IllegalArgumentException("Must supplie a security check.");
	}

	/**
	 * @see org.apache.wicket.security.checks.ISecurityCheck#isActionAuthorized(org.apache.wicket.security.actions.WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		return wrapped.isActionAuthorized(action.add(getActionFactory().getAction(Enable.class)));
	}

	/**
	 * @see org.apache.wicket.security.checks.ISecurityCheck#isAuthenticated()
	 */
	public boolean isAuthenticated()
	{
		return wrapped.isAuthenticated();
	}

}
