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
package org.apache.wicket.security.examples.multilogin.authentication;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;

/**
 * Panel for user authentication. Uses a username and a token.
 * 
 * @author marrink
 */
public abstract class UsernamePasswordSignInPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            component id
	 */
	public UsernamePasswordSignInPanel(final String id)
	{
		super(id);
		add(new SignInForm("signInForm").setOutputMarkupId(false));
	}

	/**
	 * The actual login process.
	 * 
	 * @param username
	 * @param password
	 * @return true if the login was successful, false otherwise
	 */
	public abstract boolean signIn(String username, String password);

	/**
	 * Sign in form.
	 */
	public final class SignInForm extends StatelessForm<ValueMap>
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            id of the form component
		 */
		public SignInForm(final String id)
		{
			super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));

			// only remember username, not passwords
			add(new TextField<String>("username").setOutputMarkupId(false));
			// the token
			add(new PasswordTextField("password").setOutputMarkupId(false));
		}

		/**
		 * 
		 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public final void onSubmit()
		{
			ValueMap values = getModelObject();
			String username = values.getString("username");
			String password = values.getString("password");

			if (signIn(username, password))
			{
				// continue or homepage?
				if (!getPage().continueToOriginalDestination())
				{
					setResponsePage(Application.get().getHomePage());
				}
			}
			else
			{
				// Try the component based localizer first. If not found try the
				// application localizer. Else use the default
				error(getLocalizer().getString("exception.login", this,
					"Illegal username password combo"));
			}
		}
	}
}
