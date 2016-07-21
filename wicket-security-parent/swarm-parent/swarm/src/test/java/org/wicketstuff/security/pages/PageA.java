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
import org.apache.wicket.markup.html.form.TextField;
import org.wicketstuff.security.WaspSession;
import org.wicketstuff.security.components.SecureWebPage;
import org.wicketstuff.security.components.markup.html.form.SecureTextField;
import org.wicketstuff.security.components.markup.html.links.SecurePageLink;

/**
 * @author marrink
 */
public class PageA extends SecureWebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public PageA()
	{
		super();
		add(new Label("label", "this page shows security inheritance"));
		add(new SecurePageLink<Void>("link", MockHomePage.class));
		add(new SecureTextField<String>("invisible"));
		add(new SecureTextField<String>("readonly"));
		add(new TextField<String>("unchecked"));
	}

	/**
	 * Shortcut to {@link WaspSession#logoff(Object)}
	 * 
	 * @param context
	 * @return true if the logoff was successful, false otherwise
	 */
	public boolean logoff(Object context)
	{
		return ((WaspSession)Session.get()).logoff(context);
	}
}
