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
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.security.components.SecureWebPage;

/**
 * This Secure Linked Page is part of the demo on how to enable a link to a page The admin
 * user (admin principal) has the right to see this page. The link is only visible for
 * this user.
 * 
 * @author Olger Warnier
 * 
 */
public class MySecureLinkedPage extends SecureWebPage
{

	private static final long serialVersionUID = 1L;

	/**
	 * @param parameters
	 */
	public MySecureLinkedPage(PageParameters parameters)
	{
		super(parameters);

		// add the link back to the homepage
		add(new Link<Void>("toHomePage")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(HomePage.class);
			}
		});

		// add the link to the login page, it is possible to login as another user
		add(new Link<Void>("toLogin")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(LoginPage.class);
			}
		});
	}

}