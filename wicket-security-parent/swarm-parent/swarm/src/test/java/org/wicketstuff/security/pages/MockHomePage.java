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

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.wicketstuff.security.WaspSession;
import org.wicketstuff.security.components.SecureWebPage;
import org.wicketstuff.security.components.markup.html.links.SecurePageLink;

/**
 * @author marrink
 */
public class MockHomePage extends SecureWebPage
{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MockHomePage()
	{
		super();
		add(new Label("label", "this page is secured"));
		// In this test setup it is not possible to use a SecurePageLink, if you
		// do want
		// one you need to replace the securitycheck on the link with one that
		// does use
		// the target page.
		add(new BookmarkablePageLink<Void>("secret", VerySecurePage.class));

		add(new SecurePageLink<Void>("link", PageA.class));
		setStatelessHint(true);
	}

	/**
	 * shortcut to {@link WaspSession#logoff(Object)}
	 * 
	 * @param context
	 * @return true if the logoff was successful, false otherwise.
	 */
	public boolean logoff(Object context)
	{
		return ((WaspSession)Session.get()).logoff(context);
	}

}
