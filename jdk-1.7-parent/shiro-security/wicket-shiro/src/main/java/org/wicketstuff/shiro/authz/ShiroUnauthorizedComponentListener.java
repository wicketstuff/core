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
package org.wicketstuff.shiro.authz;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

public class ShiroUnauthorizedComponentListener implements
	IUnauthorizedComponentInstantiationListener
{
	private final Class<? extends Page> loginPage;
	private final Class<? extends Page> unauthorizedPage;
	private AnnotationsShiroAuthorizationStrategy annotationStrategy = null;

	public ShiroUnauthorizedComponentListener(final Class<? extends Page> loginPage,
		final Class<? extends Page> unauthorizedPage, final AnnotationsShiroAuthorizationStrategy s)
	{
		this.loginPage = loginPage;
		this.unauthorizedPage = unauthorizedPage;
		annotationStrategy = s;
	}

	protected Class<? extends Page> addLoginMessagesAndGetPage(
		final ShiroSecurityConstraint constraint, final Component component,
		Class<? extends Page> page)
	{
		if (constraint.loginMessage().length() > 0)
			Session.get().info(getMessage(constraint.loginMessage(), constraint, component));
		if (constraint.loginPage() != Page.class)
			page = constraint.loginPage();
		return page;
	}

	protected Class<? extends Page> addUnauthorizedMessagesAndGetPage(
		final ShiroSecurityConstraint constraint, final Component component,
		Class<? extends Page> page)
	{
		if (constraint.unauthorizedMessage().length() > 0)
			Session.get().info(getMessage(constraint.unauthorizedMessage(), constraint, component));
		if (constraint.unauthorizedPage() != Page.class)
			page = constraint.unauthorizedPage();
		return page;
	}

	public AnnotationsShiroAuthorizationStrategy getAnnotationStrategy()
	{
		return annotationStrategy;
	}

	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------

	protected String getMessage(final String key, final ShiroSecurityConstraint anno,
		final Component comp)
	{
		return key; // TODO, this could be more complicated....
	}

	/**
	 * {@inheritDoc}
	 */
	public void onUnauthorizedInstantiation(final Component component)
	{
		final Subject subject = SecurityUtils.getSubject();
		final boolean notLoggedIn = !subject.isAuthenticated();
		final Class<? extends Page> page = notLoggedIn ? loginPage : unauthorizedPage;

		if (annotationStrategy != null)
		{
			final ShiroSecurityConstraint fail = annotationStrategy.checkInvalidInstantiation(component.getClass());
			if (fail != null)
				if (notLoggedIn)
					addLoginMessagesAndGetPage(fail, component, page);
				else
					addUnauthorizedMessagesAndGetPage(fail, component, page);
		}

		if (notLoggedIn)
			// the login page
			throw new RestartResponseAtInterceptPageException(page);
		// the unauthorized page
		throw new RestartResponseException(page);
	}

	public void setAnnotationStrategy(final AnnotationsShiroAuthorizationStrategy annotationStrategy)
	{
		this.annotationStrategy = annotationStrategy;
	}
}
