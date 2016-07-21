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
package org.apache.wicket.security.examples.secureform.pages;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.examples.secureform.MyLoginContext;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * primary loginpage uses username and password.
 * 
 * @author marrink
 * @author Olger Warnier
 */
public class LoginPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

	private static MyLoginContext loggedInUser;

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

		add(new SignInForm("signInForm")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean signIn(String username, String password)
			{
				WaspSession secureSession = (WaspSession) getSession();

				if (secureSession.isUserAuthenticated() && getLoggedInUser() != null)
				{
					secureSession.logoff(getLoggedInUser());
					secureSession.invalidateNow();
				}

				MyLoginContext userContext = new MyLoginContext(username, password);

				try
				{
					secureSession.login(userContext);
					setLoggedInUser(userContext);
				}
				catch (LoginException e)
				{
					log.error("Could not login " + username, e);
					error(getLocalizer().getString("exception.login", this, e.getMessage()));
					return false;
				}
				return true;
			}
		}).setOutputMarkupId(false);

	}

	private static void setLoggedInUser(MyLoginContext user)
	{
		loggedInUser = user;

	}

	private static MyLoginContext getLoggedInUser()
	{
		return loggedInUser;
	}

	public abstract class SignInForm extends StatelessForm<ValueMap>
	{
		private static final long serialVersionUID = 1L;

		/**
		 * remember username
		 */
		private boolean rememberMe = true;

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            id of the form component
		 */
		public SignInForm(final String id)
		{
			// sets a compound model on this form, every component without an
			// explicit model will use this model too
			super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));

			// only remember username, not passwords
			add(new TextField<String>("username").setPersistent(rememberMe)
				.setOutputMarkupId(false));
			add(new PasswordTextField("password").setOutputMarkupId(false));
			add(new CheckBox("rememberMe", new PropertyModel<Boolean>(this, "rememberMe")));
		}

		/**
		 * 
		 * @see org.apache.wicket.Component#getMarkupId()
		 */
		@Override
		public String getMarkupId()
		{
			// fix javascript id
			return getId();
		}

		/**
		 * 
		 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public final void onSubmit()
		{
			if (!rememberMe)
			{
				// delete persistent data
				getPage().removePersistedFormData(SignInForm.class, true);
			}

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

		/**
		 * @return true if formdata should be made persistent (cookie) for later logins.
		 */
		public boolean getRememberMe()
		{
			return rememberMe;
		}

		/**
		 * Remember form values for later logins?.
		 * 
		 * @param rememberMe
		 *            true if formdata should be remembered
		 */
		public void setRememberMe(boolean rememberMe)
		{
			this.rememberMe = rememberMe;
			((FormComponent< ? >) get("username")).setPersistent(rememberMe);
		}

		/**
		 * Implement this method in your extension class in order to actually login a
		 * user.
		 * 
		 * @param username
		 * @param password
		 * @return true for a succesfull login, false for a failed login
		 */
		public abstract boolean signIn(String username, String password);
	}

}