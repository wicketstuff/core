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
package org.wicketstuff.security.pages.secure;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.components.markup.html.form.SecureTextField;
import org.wicketstuff.security.models.ISecureModel;
import org.wicketstuff.security.pages.SecureTestPage;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * @author marrink
 * 
 */
public class PageD extends SecureTestPage
{

	private final class SecureModel implements ISecureModel<String>
	{
		private static final long serialVersionUID = 1L;

		private String value = "foo";

		/**
		 * 
		 * @see org.wicketstuff.security.models.ISecureModel#isAuthenticated(org.apache.wicket.Component)
		 */
		public boolean isAuthenticated(Component component)
		{
			return getStrategy().isModelAuthenticated(this, component);
		}

		/**
		 * @return
		 */
		private WaspAuthorizationStrategy getStrategy()
		{
			return ((WaspAuthorizationStrategy)getSecureSession().getAuthorizationStrategy());
		}

		/**
		 * 
		 * @see org.wicketstuff.security.models.ISecureModel#isAuthorized(org.apache.wicket.Component,
		 *      org.wicketstuff.security.actions.WaspAction)
		 */
		public boolean isAuthorized(Component component, WaspAction action)
		{
			return getStrategy().isModelAuthorized(this, component, action);
		}

		/**
		 * 
		 * @see org.apache.wicket.model.IModel#getObject()
		 */
		public String getObject()
		{
			return value;
		}

		/**
		 * 
		 * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
		 */
		public void setObject(String object)
		{
			value = object;
		}

		/**
		 * 
		 * @see org.apache.wicket.model.IDetachable#detach()
		 */
		public void detach()
		{
			// noop
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public PageD()
	{
		add(new Label("welcome", "Welcome Only logged in users can see this page"));

		add(new SecureTextField<String>("componentcheck", new Model<String>("secure textfield")));
		add(new TextField<String>("modelcheck", new SecureModel()));
		add(new SecureTextField<String>("both", new SecureModel()));
		add(new SecureTextField<String>("bothcheck", new SecureModel(), true));
	}

}
