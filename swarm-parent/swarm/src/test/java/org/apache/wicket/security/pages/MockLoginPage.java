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
package org.apache.wicket.security.pages;

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
import org.apache.wicket.security.hive.authentication.CustomLoginContext;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.PrimaryLoginContext;
import org.apache.wicket.security.hive.authorization.SimplePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author marrink
 * 
 */
public class MockLoginPage extends WebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(MockLoginPage.class);

	private Form form;
	private TextField textField;

	/**
	 * 
	 */
	public MockLoginPage()
	{
		super();
		setStatelessHint(true);
		add(new Label("label", "welcome please login"));
		add(form = new StatelessForm("form")
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void onSubmit()
			{
				login(get("username").getModelObjectAsString());
			}
		});
		form.add(textField = new TextField("username", new Model()));
	}

	/**
	 * 
	 * @param username
	 * @return true if the login was successful, false otherwise
	 */
	public boolean login(String username)
	{
		try
		{
			LoginContext context;
			if ("test".equals(username))
			{
				context = new PrimaryLoginContext();
			}
			else if ("all".equals(username))
			{
				context = new CustomLoginContext(new SimplePrincipal("all permissions"));
			}
			else
			{
				context = new CustomLoginContext(new SimplePrincipal(username));
			}
			((WaspSession)Session.get()).login(context);
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
	 * 
	 * @return the form
	 */
	public final Form getForm()
	{
		return form;
	}

	/**
	 * 
	 * @return the username textfield
	 */
	public final TextField getTextField()
	{
		return textField;
	}

}
