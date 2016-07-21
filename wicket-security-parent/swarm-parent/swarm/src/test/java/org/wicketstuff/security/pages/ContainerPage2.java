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

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ContainerSecurityCheck;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.components.SecureWebPage;

/**
 * @author marrink
 */
public class ContainerPage2 extends SecureWebPage
{
	private static final long serialVersionUID = 1L;

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
		private static final long serialVersionUID = 1L;

		public SecureMarkupContainer(String id)
		{
			super(id);
			setSecurityCheck(new ContainerSecurityCheck(this));
		}

		public ISecurityCheck getSecurityCheck()
		{
			return SecureComponentHelper.getSecurityCheck(this);
		}

		public boolean isActionAuthorized(String waspAction)
		{
			return SecureComponentHelper.isActionAuthorized(this, waspAction);
		}

		public boolean isActionAuthorized(WaspAction action)
		{
			return SecureComponentHelper.isActionAuthorized(this, action);
		}

		public boolean isAuthenticated()
		{
			return SecureComponentHelper.isAuthenticated(this);
		}

		public void setSecurityCheck(ISecurityCheck check)
		{
			SecureComponentHelper.setSecurityCheck(this, check);
		}
	}
}
