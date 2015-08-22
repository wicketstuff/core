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
package org.wicketstuff.security.extensions.markup.html.tabs;

import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.checks.LinkSecurityCheck;
import org.wicketstuff.security.components.SecureComponentHelper;

/**
 * A Tab bar that hides the tabs if the user has insufficient rights to see the contents. Note that
 * tabs not implementing the {@link ISecureTab} interface will always show up.
 * 
 * @param <T>
 *            The type of panel to be used for this component's tabs. Just use {@link ITab} if you
 *            have no special needs here.
 * @author marrink
 */
public class SecureTabbedPanel<T extends ITab> extends TabbedPanel<T>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param tabs
	 * @see TabbedPanel#TabbedPanel(String, List)
	 */
	public SecureTabbedPanel(String id, List<T> tabs)
	{
		super(id, tabs);
	}

	/**
	 * Adds an {@link ISecurityCheck} to {@link ISecureTab}s
	 * 
	 * @see org.apache.wicket.extensions.markup.html.tabs.TabbedPanel#newLink(java.lang.String, int)
	 */
	@Override
	protected WebMarkupContainer newLink(String linkId, int index)
	{
		Link<?> link = (Link<?>)super.newLink(linkId, index);
		Class<? extends Panel> panelClass = getTabClass(index);
		// We are using a LinkSecurityCheck instead of a ContainerSecurityCheck
		// because it operates on classes instead of instances.
		if (panelClass != null){
			SecureComponentHelper.setSecurityCheck(link, new LinkSecurityCheck(link, panelClass));
                }
		// we can only set the check if we have a class, we could use
		// getPanel("dummy") and get the class from that but then we would
		// create some serious overhead instantiating panels twice.
		return link;
	}

	/**
	 * Gets the class of the panel. Note that it needs {@link ISecureTab}s for this.
	 * 
	 * @param tabIndex
	 *            the index of the tab
	 * @return the class or null if the tab at the index is not an ISecureTab
	 */
	protected Class<? extends Panel> getTabClass(int tabIndex)
	{
		ITab tab = getTabs().get(tabIndex);
		if (tab instanceof ISecureTab){
			return ((ISecureTab)tab).getPanel();
                }
		return null;
	}
}
