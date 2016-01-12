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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.checks.LinkSecurityCheck;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.SecureComponentHelper;

/**
 * BookmarkablePagelink with visibility / clickability based on user rights. Requires render rights
 * to be visible, and enable rights to be clickable. This class is by default outfitted with a
 * {@link LinkSecurityCheck}, please see its documentation on how to enable the alternative security
 * check
 * 
 * @author marrink
 * @see LinkSecurityCheck
 */
public class SecureBookmarkablePageLink<T> extends BookmarkablePageLink<T> implements
	ISecureComponent
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 * @param pageClass
	 */
	public SecureBookmarkablePageLink(String id, Class<? extends Page> pageClass)
	{
		super(id, pageClass);
		setSecurityCheck(new LinkSecurityCheck(this, pageClass));
	}

	/**
	 * @param id
	 * @param pageClass
	 * @param parameters
	 */
	public SecureBookmarkablePageLink(String id, Class<? extends Page> pageClass,
		PageParameters parameters)
	{
		super(id, pageClass, parameters);
		setSecurityCheck(new LinkSecurityCheck(this, pageClass));
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
