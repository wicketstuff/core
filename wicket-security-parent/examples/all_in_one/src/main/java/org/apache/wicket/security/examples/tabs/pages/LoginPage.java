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
package org.apache.wicket.security.examples.tabs.pages;

import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.examples.pages.login.UsernamePasswordSignInPanel;
import org.apache.wicket.security.examples.tabs.authentication.MyLoginContext;
import org.apache.wicket.security.hive.authentication.LoginContext;

/**
 * Custom login page.
 * 
 * @author marrink
 */
public class LoginPage extends org.apache.wicket.security.examples.pages.login.LoginPage
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.apache.wicket.security.examples.pages.login.LoginPage#newUserPasswordSignInPanel(java.lang.String)
	 */
	@Override
	protected void newUserPasswordSignInPanel(String panelId)
	{
		add(new UsernamePasswordSignInPanel(panelId)
		{

			private static final long serialVersionUID = 1L;

			@Override
			public boolean signIn(final String username, final String password)
			{
				// authentication in swarm is handled by contexts, which are
				// disposed after use.
				LoginContext context = new MyLoginContext(username, password);
				try
				{
					((WaspSession) getSession()).login(context);
				}
				catch (LoginException e)
				{
					return false;
				}
				return true;
			}
		});
	}

}
