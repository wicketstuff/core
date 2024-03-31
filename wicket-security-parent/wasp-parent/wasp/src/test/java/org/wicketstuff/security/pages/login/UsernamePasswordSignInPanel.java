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
package org.wicketstuff.security.pages.login;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.WaspSession;
import org.wicketstuff.security.WaspWebApplication;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.components.markup.html.form.SecureForm;
import org.wicketstuff.security.components.markup.html.form.SecureTextField;
import org.wicketstuff.security.components.markup.html.links.SecurePageLink;
import org.wicketstuff.security.pages.container.MySecurePanel;
import org.wicketstuff.security.pages.secure.FormPage;
import org.wicketstuff.security.pages.secure.HomePage;
import org.wicketstuff.security.pages.secure.PageB;
import org.wicketstuff.security.pages.secure.PageC;
import org.wicketstuff.security.pages.secure.PageC2;
import org.wicketstuff.security.pages.secure.PageD;

/**
 * 
 * @author marrink
 * 
 */
public class UsernamePasswordSignInPanel extends Panel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(UsernamePasswordSignInPanel.class);

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            component id
	 */
	public UsernamePasswordSignInPanel(final String id)
	{
		super(id);

		add(new FeedbackPanel("feedback"));
		add(new Label("naam"));
		add(new SignInForm("signInForm"));
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return true if the user signed in successfully
	 */
	public boolean signIn(String username, String password)
	{
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper.alias(HomePage.class),
			getWaspApplication().getActionFactory().getAction("access render enable"));
		authorized.put(SecureComponentHelper.alias(PageB.class),
			getWaspApplication().getActionFactory().getAction("access render"));
		authorized.put(SecureComponentHelper.alias(PageC.class),
			getWaspApplication().getActionFactory().getAction("access"));
		authorized.put(SecureComponentHelper.alias(PageC2.class),
			getWaspApplication().getActionFactory().getAction("access render foo"));
		authorized.put(SecureComponentHelper.alias(PageD.class),
			getWaspApplication().getActionFactory().getAction("access render"));
		authorized.put(SecureComponentHelper.alias(FormPage.class),
			getWaspApplication().getActionFactory().getAction("access render"));
		// because this test uses the ISecureComponent class as base class for
		// instantiation checks we need to grant all ISecureComponents access
		authorized.put(SecureComponentHelper.alias(SecurePageLink.class),
			getWaspApplication().getActionFactory().getAction("access"));
		authorized.put(SecureComponentHelper.alias(SecureTextField.class),
			getWaspApplication().getActionFactory().getAction("access"));
		authorized.put(SecureComponentHelper.alias(SecureForm.class),
			getWaspApplication().getActionFactory().getAction("access"));
		// grant models rights Page D
		authorized.put("model:modelcheck",
			getWaspApplication().getActionFactory().getAction("access render"));
		authorized.put("model:bothcheck",
			getWaspApplication().getActionFactory().getAction("access render"));
		// panels
		authorized.put(SecureComponentHelper.alias(MySecurePanel.class),
			getWaspApplication().getActionFactory().getAction("access"));
		WaspSession session = getSecureSession();
		try
		{
			session.login(authorized);
			return true;
		}
		catch (LoginException e)
		{
			log.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * Shortcut to the session.
	 * 
	 * @return the wasp session
	 */
	protected final WaspSession getSecureSession()
	{
		return (WaspSession)Session.get();
	}

	/**
	 * Shortcut to the application.
	 * 
	 * @return the wasp application
	 */
	protected final WaspWebApplication getWaspApplication()
	{
		return (WaspWebApplication)Application.get();
	}

	/**
	 * Sign in form.
	 */
	public final class SignInForm extends StatelessForm<ValueMap>
	{
		private static final long serialVersionUID = 1L;

		private boolean rememberMe = true;

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            id of the form component
		 */
		public SignInForm(final String id)
		{
			super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));

			// only save username, not passwords
			add(new TextField<String>("username"));// .setPersistent(rememberMe));
			add(new PasswordTextField("password"));
			// MarkupContainer row for remember me checkbox
			WebMarkupContainer rememberMeRow = new WebMarkupContainer("rememberMeRow");
			add(rememberMeRow);

			// Add rememberMe checkbox
			rememberMeRow.add(new CheckBox("rememberMe", new PropertyModel<Boolean>(this,
				"rememberMe")));
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
				// getPage().removePersistedFormData(SignInForm.class, true);
			}

			ValueMap values = getModelObject();
			String username = values.getString("username");
			String password = values.getString("password");

			if (signIn(username, password))
			{
				getPage().continueToOriginalDestination();
				// or
				setResponsePage(Application.get().getHomePage());
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
		 * Should form values be persisted.
		 * 
		 * @return true if form values should be persisted, false otherwise
		 */
		public boolean getRememberMe()
		{
			return rememberMe;
		}

		/**
		 * 
		 * @param rememberMe
		 *            persist form values
		 */
		public void setRememberMe(boolean rememberMe)
		{
			this.rememberMe = rememberMe;
			// ((FormComponent< ? >) get("username")).setPersistent(rememberMe);
		}
	}
}
