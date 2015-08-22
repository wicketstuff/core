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
package org.apache.wicket.security.examples.pages.login;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.examples.multilogin.authentication.Level0Context;
import org.apache.wicket.security.hive.authentication.LoginContext;

/**
 * primary loginpage uses username and password.
 * 
 * @author marrink
 */
public class LoginPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public LoginPage()
	{
		// stateless so the login page will not throw a timeout exception
		// note that is only a hint we need to have stateless components on the
		// page for this to work, like a statelessform
		setStatelessHint(true);
		add(new FeedbackPanel("feedback")
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.Component#isVisible()
			 */
			@Override
			public boolean isVisible()
			{
				return anyMessage();
			}
		});
		String panelId = "signInPanel";
		newUserPasswordSignInPanel(panelId);
	}

	/**
	 * Creates a sign in panel with a username and a password field.
	 * 
	 * @param panelId
	 */
	protected void newUserPasswordSignInPanel(String panelId)
	{
		add(new UsernamePasswordSignInPanel(panelId)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean signIn(String username, String password)
			{
				LoginContext ctx = new Level0Context(username, password);
				try
				{
					((WaspSession) getSession()).login(ctx);
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
