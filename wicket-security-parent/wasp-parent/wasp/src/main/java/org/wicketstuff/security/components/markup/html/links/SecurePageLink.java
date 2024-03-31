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
package org.wicketstuff.security.components.markup.html.links;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.checks.LinkSecurityCheck;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.SecureComponentHelper;

/**
 * Pagelink with visibility / clickability based on user rights. Requires render rights to be
 * visible, and enable rights to be clickable. This class is by default outfitted with a
 * {@link LinkSecurityCheck}, please see its documentation on how to enable the alternative security
 * check
 *
 * @author marrink
 * @see LinkSecurityCheck
 */
public class SecurePageLink<T> extends Link<T> implements ISecureComponent
{
	private static final long serialVersionUID = 1L;

	private IPageLink pageLink;

	/**
	 * @param id
	 * @param c
	 */
	public <C extends Page> SecurePageLink(String id, final Class<C> c)
	{
		super(id);
		setSecurityCheck(new LinkSecurityCheck(this, c));

		// Ensure that c is a subclass of Page
		if (!Page.class.isAssignableFrom(c))
		{
			throw new IllegalArgumentException("Class " + c + " is not a subclass of Page");
		}

		pageLink = new IPageLink()
		{
			private static final long serialVersionUID = 1L;

			public Page getPage()
			{
				// Create page using page factory
				return (Page)getSession().getPageFactory().newPage(c);
			}

			public Class<? extends Page> getPageIdentity()
			{
				return c;
			}
		};
	}

	/**
	 * @deprecated use {@link SecurePageLink#SecurePageLink(String, IPageLink)}
	 * @param id
	 * @param pageLink
	 */
	@Deprecated
	public SecurePageLink(String id, BookmarkablePageLink pageLink)
	{
		this(id, new OldPageLinkWrapper(pageLink));
	}

	/**
	 * @param id
	 * @param pageLink
	 */
	public SecurePageLink(String id, IPageLink pageLink)
	{
		super(id);
		this.pageLink = pageLink;
		setSecurityCheck(new LinkSecurityCheck(this, pageLink.getPageIdentity()));
	}

	/**
	 * Handles a link click by asking for a concrete Page instance through the IPageLink.getPage()
	 * delayed linking interface. This call will normally cause the destination page to be created.
	 *
	 * @see org.apache.wicket.markup.html.link.Link#onClick()
	 */
	@Override
	public void onClick()
	{
		setResponsePage(pageLink.getPage());
	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#getSecurityCheck()
	 */
	public ISecurityCheck getSecurityCheck()
	{
		return SecureComponentHelper.getSecurityCheck(this);
	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#isActionAuthorized(java.lang.String)
	 */
	public boolean isActionAuthorized(String waspAction)
	{
		return SecureComponentHelper.isActionAuthorized(this, waspAction);
	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#isActionAuthorized(WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		return SecureComponentHelper.isActionAuthorized(this, action);
	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#isAuthenticated()
	 */
	public boolean isAuthenticated()
	{
		return SecureComponentHelper.isAuthenticated(this);
	}

	/**
	 * @see org.wicketstuff.security.components.ISecureComponent#setSecurityCheck(org.wicketstuff.security.checks.ISecurityCheck)
	 */
	public void setSecurityCheck(ISecurityCheck check)
	{
		SecureComponentHelper.setSecurityCheck(this, check);
	}

}
