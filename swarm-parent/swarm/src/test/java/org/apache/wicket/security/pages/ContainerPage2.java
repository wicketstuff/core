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
package org.apache.wicket.security.pages;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ContainerSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.components.SecureWebPage;

/**
 * @author marrink
 */
public class ContainerPage2 extends SecureWebPage
{

	/**
	 * Construct.
	 */
	public ContainerPage2()
	{
		add(new Label("label", "always visible"));
		SecureMarkupContainer container = new SecureMarkupContainer("secure");
		container.add(new Label("hidden", "hidden label"));
		add(container);
	}

	/**
	 * Simple secure container.
	 * 
	 * @author marrink
	 */
	private static final class SecureMarkupContainer extends WebMarkupContainer implements
			ISecureComponent
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 * Construct.
		 * 
		 * @param id
		 */
		public SecureMarkupContainer(String id)
		{
			super(id);
			setSecurityCheck(new ContainerSecurityCheck(this));
		}

		/**
		 * 
		 * @see org.apache.wicket.security.components.ISecureComponent#getSecurityCheck()
		 */
		public ISecurityCheck getSecurityCheck()
		{
			return SecureComponentHelper.getSecurityCheck(this);
		}

		/**
		 * 
		 * @see org.apache.wicket.security.components.ISecureComponent#isActionAuthorized(java.lang.String)
		 */
		public boolean isActionAuthorized(String waspAction)
		{
			return SecureComponentHelper.isActionAuthorized(this, waspAction);
		}

		/**
		 * 
		 * @see org.apache.wicket.security.components.ISecureComponent#isActionAuthorized(org.apache.wicket.security.actions.WaspAction)
		 */
		public boolean isActionAuthorized(WaspAction action)
		{
			return SecureComponentHelper.isActionAuthorized(this, action);
		}

		/**
		 * 
		 * @see org.apache.wicket.security.components.ISecureComponent#isAuthenticated()
		 */
		public boolean isAuthenticated()
		{
			return SecureComponentHelper.isAuthenticated(this);
		}

		/**
		 * 
		 * @see org.apache.wicket.security.components.ISecureComponent#setSecurityCheck(org.apache.wicket.security.checks.ISecurityCheck)
		 */
		public void setSecurityCheck(ISecurityCheck check)
		{
			SecureComponentHelper.setSecurityCheck(this, check);
		}

	}

}
