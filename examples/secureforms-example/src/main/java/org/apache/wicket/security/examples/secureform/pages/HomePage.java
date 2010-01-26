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

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.security.checks.InverseSecurityCheck;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.components.markup.html.links.SecurePageLink;

/**
 * Homepage
 * 
 * @author Olger Warnier
 */
public class HomePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters)
	{

		// Add the simplest type of label
		add(new Label("message", "Welcome this is a SWARM/WASP based Secure Forms example"));

		// add a link to the Secure Form page
		add(new Link<Void>("mySecurePageLink")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(MySecurePage.class);
			}
		});

		add(new Link<Void>("mySecureAnonymousPageLink")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(new MySecurePage(parameters)
				{
					// Override a method to mimic a real anonymous class.
					@Override
					protected void onBeforeRender()
					{
						super.onBeforeRender();
					}
				});
			}
		});

		/*
		 * setResponsePage(new CreateItemPage(getPage()) {
		 * @Override protected void onSuccess(final Serializable index) {
		 * setResponsePage(new ViewItemPage(getBackPage(), index)); } });
		 */
		// add a link to the login page
		add(new Link<Void>("toLogin")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(LoginPage.class);
			}
		});

		SecurePageLink<Void> securePageLink =
			new SecurePageLink<Void>("secureLink", MySecureLinkedPage.class);
		add(securePageLink);
		add(SecureComponentHelper.setSecurityCheck(new Label("sorry",
			"you are not allowed to go to MySecuredLinkedPage"), new InverseSecurityCheck(
			securePageLink.getSecurityCheck())));
	}
}
