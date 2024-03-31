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
package org.wicketstuff.security.pages.insecure;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.security.checks.ComponentSecurityCheck;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.components.markup.html.links.SecureContainerLink;
import org.wicketstuff.security.pages.BasePage;

/**
 * Insecure page with secure components. Any logged in user can see this page.
 * 
 * @author marrink
 * 
 */
public class SecureComponentPage extends BasePage
{

	/**
	 * Simple container. This is not an anonymous inner class of the {@link SecureContainerLink}
	 * because that would make it an {@link ISecureComponent} and we do not want that for this test.
	 * 
	 * @author marrink
	 */
	public static final class MyReplacementContainer extends WebMarkupContainer
	{
		private static final long serialVersionUID = 1L;

		private MyReplacementContainer(String id)
		{
			super(id);
		}

		/**
		 * 
		 * @see org.apache.wicket.Component#onComponentTag(org.apache.wicket.markup.ComponentTag)
		 */
		@Override
		protected void onComponentTag(ComponentTag tag)
		{
			super.onComponentTag(tag);
			tag.setName("div"); // turn span into div, just do
			// anything to prove we really
			// changed the containers
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SecureComponentPage()
	{
		add(new Label("welcome", "Welcome Anyone can see this page as long as they are logged in"));
		Label secureLabel = new Label("secure", "this label is what forces you to login");
		add(SecureComponentHelper.setSecurityCheck(secureLabel, new ComponentSecurityCheck(
			secureLabel)));

		add(new WebMarkupContainer("replaceMe")); // content is irrelevant in
		// this example so i will
		// just use 2 containers
		// without there own markup.
		// this link will only show up if you have the enable action for a
		// WebMarkupContainer
		add(new SecureContainerLink<Void>("link", MyReplacementContainer.class, this, "replaceMe")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected MarkupContainer getReplacementFor(Component current, String id,
				Class<? extends MarkupContainer> replacementClass)
			{
				setVisible(false);
				return new MyReplacementContainer(id);
			}
		});
	}

}
