/*
 * Copyright 2014 WicketStuff.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.servlet3.secure.example.ui;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.servlet3.secure.example.ui.admin.AdminPage;
import org.wicketstuff.servlet3.secure.example.ui.common.AdminLink;
import org.wicketstuff.servlet3.secure.example.ui.common.SecureLink;
import org.wicketstuff.servlet3.secure.example.ui.common.UserInfoPanel;

/**
 * @author jsarman
 */
public class BasePage extends WebPage
{

	public BasePage()
	{
		this(null);
	}

	public BasePage(PageParameters parameters)
	{
		super(parameters);
		add(new UserInfoPanel("userInfo"));
		add(new StatelessLink("home")
		{

			@Override
			public void onClick()
			{
				setResponsePage(Application.get().getHomePage());
			}
		}.setVisible(this.getClass() != Application.get().getHomePage()));

		add(new AdminLink("admin")
		{

			@Override
			public void onClick()
			{
				setResponsePage(AdminPage.class);
			}

		}.setVisible(this.getClass() != AdminPage.class));
		add(new SecureLink("invalidate")
		{

			@Override
			public void onClick()
			{
				Session.get().invalidate(); //This logs out user and removes the rememberMe cookie
				setResponsePage(Application.get().getHomePage());
			}
		});
		add(new SecureLink("logout")
		{

			@Override
			public void onClick()
			{
				AuthenticatedWebSession.get().signOut(); //log out and do not delete cookie
				setResponsePage(Application.get().getHomePage());
			}
		});
	}
}
