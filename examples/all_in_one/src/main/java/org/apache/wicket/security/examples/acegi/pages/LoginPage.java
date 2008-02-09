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
package org.apache.wicket.security.examples.acegi.pages;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.providers.TestingAuthenticationToken;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.examples.acegi.authentication.AcegiLoginContext;
import org.apache.wicket.security.examples.pages.login.UsernamePasswordSignInPanel;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.util.lang.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marrink
 */
public class LoginPage extends org.apache.wicket.security.examples.pages.login.LoginPage
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

	/**
	 * @see org.apache.wicket.security.examples.pages.login.LoginPage#newUserPasswordSignInPanel(java.lang.String)
	 */
	protected void newUserPasswordSignInPanel(String panelId)
	{
		add(new UsernamePasswordSignInPanel(panelId)
		{

			private static final long serialVersionUID = 1L;

			public boolean signIn(final String username, final String password)
			{
				// authentication in swarm is handled by contexts, which are
				// disposed after use.
				LoginContext context = new AcegiLoginContext(new TestingAuthenticationToken(
						username, password, getAuthorities(username, password)));
				try
				{
					((WaspSession)getSession()).login(context);
				}
				catch (LoginException e)
				{
					log.error(e.getMessage(), e);
					return false;
				}
				return true;
			}

			/**
			 * This example uses the TestingAuthenticationToken therefore we
			 * need to provide the authorities up front. Other
			 * AuthenticationTokens will get this from a database or wherever
			 * they are designed to get it from.
			 * 
			 * @param username
			 * @param password
			 * @return
			 */
			private GrantedAuthority[] getAuthorities(String username, String password)
			{
				GrantedAuthority[] authorities = null;
				if (username != null && Objects.equal(username, password))
				{
					authorities = new GrantedAuthority[1];
					if ("ceo".equals(username))
					{
						authorities[0] = new GrantedAuthorityImpl("organisation.rights");
					}
					else
						authorities[0] = new GrantedAuthorityImpl("department.rights");
					// the subject returned in AcegiLoginContext knows how to
					// convert these names to principals
				}
				return authorities;
			}
		});
	}
}
