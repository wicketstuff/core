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
package org.wicketstuff.security.pages;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.WaspSession;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.hive.authentication.LoginContext;
import org.wicketstuff.security.hive.authentication.SecondaryLoginContext;

/**
 * @author marrink
 * 
 */
public class SecondaryLoginPage extends WebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(SecondaryLoginPage.class);

	private Form<Void> form;

	private TextField<String> textField;

	/**
	 * 
	 */
	public SecondaryLoginPage()
	{
		super();
		add(new Label("label", "welcome please login to continue to the secret"));
		add(form = new Form<Void>("form")
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
	 * Login using a username.
	 * 
	 * @param username
	 * @return true if the login was successful, false otherwise
	 */
	public boolean login(String username)
	{
		try
		{
			LoginContext context = new SecondaryLoginContext();
			((WaspSession)Session.get()).login(context);
			continueToOriginalDestination();

			// or
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
	 * 
	 * @return the form
	 */
	public final Form<Void> getForm()
	{
		return form;
	}

	/**
	 * 
	 * @return the username textfield
	 */
	public final TextField<String> getTextField()
	{
		return textField;
	}

}
