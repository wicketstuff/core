/*
 * Copyright 2008 Stichting JoiningTracks, The Netherlands
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
package org.apache.wicket.security.examples.springsecurity.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.util.lang.Objects;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * MockLoginPage creates a Spring Security logincontext based on the
 * TestingAuthenticationToken This context is used by the wicket security components to
 * create the right session.
 * 
 * When logged on with any name except 'admin' the user is logged on with ROLE_USER when
 * logged on with the name 'admin' the user is logged on with ROLE_USER and ROLE_ADMIN.
 * 
 * @author Olger Warnier
 */
public class MockLoginPage extends WebPage
{
	/**
     *
     */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MockLoginPage.class);

	private Form<Void> form;

	private TextField<String> textField;

	/**
     *
     */
	public MockLoginPage()
	{
		super();
		setStatelessHint(true);
		add(new Label("label", "welcome please login"));
		add(form = new StatelessForm<Void>("form")
		{

			/**
             *
             */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit()
			{
				login(get("username").getDefaultModelObjectAsString());
			}
		});
		form.add(textField = new TextField<String>("username", new Model<String>()));
	}

	/**
	 * @param username
	 * @return true if the login was successful, false otherwise
	 */
	public boolean login(String username)
	{
		try
		{
			LoginContext context;

			context =
				new SpringSecureLoginContext(new TestingAuthenticationToken(username, username,
					getAuthorities(username, username)));
			((WaspSession) Session.get()).login(context);
			if (!continueToOriginalDestination())
				setResponsePage(Application.get().getHomePage());
			return true;
		}
		catch (LoginException e)
		{
			log.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * @return the form
	 */
	public final Form<Void> getForm()
	{
		return form;
	}

	/**
	 * @return the username textfield
	 */
	public final TextField<String> getTextField()
	{
		return textField;
	}

	/**
	 * This Login Page uses the TestingAuthenticationToken therefore we need to provide
	 * the authorities up front. Other AuthenticationTokens will get this from a database
	 * or wherever they are designed to get it from.
	 * <p/>
	 * Thanks to Marrik and his ACEGI examples.
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

			if ("admin".equals(username))
			{
				authorities = new GrantedAuthority[2];
				authorities[0] = new GrantedAuthorityImpl("ROLE_ADMIN");
				authorities[1] = new GrantedAuthorityImpl("ROLE_USER");
			}
			else
			{
				authorities = new GrantedAuthority[1];
				authorities[0] = new GrantedAuthorityImpl("ROLE_USER");
			}
			// the subject returned in AcegiLoginContext knows how to
			// convert these names to principals
		}
		return authorities;
	}

}
