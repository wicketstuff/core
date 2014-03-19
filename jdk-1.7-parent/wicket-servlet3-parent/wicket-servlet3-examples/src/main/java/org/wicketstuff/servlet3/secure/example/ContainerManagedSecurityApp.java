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
package org.wicketstuff.servlet3.secure.example;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.pages.SignInPage;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.servlet3.secure.example.ui.HomePage;
import org.wicketstuff.wicket.servlet3.auth.ServletContainerAuthenticatedWebApplication;
import org.wicketstuff.wicket.servlet3.auth.ServletContainerAuthenticatedWebSession;

/**
 * @author jsarman
 */
public class ContainerManagedSecurityApp extends ServletContainerAuthenticatedWebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected Class<? extends ServletContainerAuthenticatedWebSession> getContainerManagedWebSessionClass()
	{
		return ServletContainerAuthenticatedWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass()
	{
		return SignInPage.class;
	}

}
