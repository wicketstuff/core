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
package org.wicketstuff.shiro.page;

import jakarta.servlet.http.Cookie;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(LogoutPage.class);

	public static final String REDIRECTPAGE_PARAM = "redirectpage";

	/**
	 * Constructor. The page will immediately redirect to the given url.
	 * 
	 * @param url
	 *            The url to redirect to
	 */
	public LogoutPage(final CharSequence url)
	{
		doLogoutAndAddRedirect(url, getDelayTime());
	}


	public LogoutPage(final Class<? extends Page> pageClass)
	{
		doLogoutAndAddRedirect(urlFor(pageClass, null), getDelayTime());
	}

	@SuppressWarnings("unchecked")
	public LogoutPage(final PageParameters parameters)
	{
		final StringValue page = parameters.get(REDIRECTPAGE_PARAM);
		Class<? extends Page> pageClass;
		if (!page.isNull())
			try
			{
				pageClass = (Class<? extends Page>)Class.forName(page.toString());
			}
			catch (final ClassNotFoundException e)
			{
				throw new RuntimeException(e);
			}
		else
			pageClass = getApplication().getHomePage();


		setStatelessHint(true);
		setResponsePage(pageClass);

		// this should remove the cookie and invalidate session
		final Subject subject = SecurityUtils.getSubject();
		LOG.info("logout: " + subject);
		subject.logout();
		
		return;
	}


	/**
	 * Constructor. The page will redirect to the given url after waiting for the given number of
	 * seconds.
	 * 
	 * @param url
	 *            The url to redirect to
	 * @param waitBeforeRedirectInSeconds
	 *            The number of seconds the browser should wait before redirecting
	 */
	private void doLogoutAndAddRedirect(final CharSequence url,
		final int waitBeforeRedirectInSeconds)
	{
		setStatelessHint(true);

		// this should remove the cookie and invalidate session
		final Subject subject = SecurityUtils.getSubject();
		LOG.info("logout: " + subject);
		subject.logout();

		final WebMarkupContainer redirect = new WebMarkupContainer("redirect");
		final String content = waitBeforeRedirectInSeconds + ";URL=" + url;
		redirect.add(new AttributeModifier("content", new Model<String>(content)));
		add(redirect);

		// HYMMMM
		final Cookie c = new Cookie("rememberMe", "xxx");
		c.setMaxAge(0);
		((WebResponse)RequestCycle.get().getResponse()).addCookie(c);
	}

	public int getDelayTime()
	{
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isVersioned()
	{
		return false;
	}
}
