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
package org.apache.wicket.security.examples.pages;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ClassSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.examples.multilogin.authentication.LoginPage2;

/**
 * Tagging interface to differentiate between page where 1 login is sufficient and pages
 * where a secondary login is required.
 * 
 * @author marrink
 */
public interface TopSecretPage
{
	/**
	 * Custom check.
	 */
	// if required you can place a similar check on all implementations of this
	// page
	static final ISecurityCheck customcheck = new ClassSecurityCheck(TopSecretPage.class)
	{
		private static final long serialVersionUID = 1L;

		/**
		 * @see org.apache.wicket.security.checks.ClassSecurityCheck#isActionAuthorized(org.apache.wicket.security.actions.WaspAction)
		 */
		@Override
		public boolean isActionAuthorized(WaspAction action)
		{
			// if not authenticated for topsecret pages go to the secondary
			// login page.
			if (isAuthenticated())
				return getStrategy().isClassAuthorized(getClazz(), action);
			throw new RestartResponseAtInterceptPageException(LoginPage2.class);
		}
	};
}
