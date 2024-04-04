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

import org.wicketstuff.security.actions.WaspAction;

/**
 * SecurityCheck that says you are authorized when in fact you are not and vice versa. The
 * authentication check always returns the correct result. This is convenient when you want to
 * display a component (like a message to the user) when the user is not authorized.
 * 
 * @author marrink
 */
public class InverseSecurityCheck implements ISecurityCheck
{
	private static final long serialVersionUID = 1L;

	private ISecurityCheck wrapped;

	/**
	 * Constructs a new SecurityCheck that will invert the result from the provided security check.
	 * 
	 * @param wrapped
	 */
	public InverseSecurityCheck(ISecurityCheck wrapped)
	{
		if (wrapped == null)
			throw new IllegalArgumentException("Need ISecurityCheck to invert");
		this.wrapped = wrapped;
	}

	/**
	 * Returns false if the user is authorized and true if the user is not authorized.
	 * 
	 * @see ISecurityCheck#isActionAuthorized(WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		return !wrapped.isActionAuthorized(action);
	}

	/**
	 * @see ISecurityCheck#isAuthenticated()
	 */
	public boolean isAuthenticated()
	{
		return wrapped.isAuthenticated();
	}

}
