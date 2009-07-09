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
package org.apache.wicket.security.examples.tabs.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.security.checks.ContainerSecurityCheck;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.examples.tabs.components.navigation.ButtonContainer;
import org.apache.wicket.security.examples.tabs.panels.Gifkikker;
import org.apache.wicket.security.examples.tabs.panels.Grolsch;
import org.apache.wicket.security.examples.tabs.panels.Heineken;
import org.apache.wicket.security.extensions.markup.html.tabs.ISecureTab;
import org.apache.wicket.security.extensions.markup.html.tabs.SecureTabbedPanel;

/**
 * Basic page showing a tab bar. Only tabs you are allowed to see will be shown.
 * 
 * @author marrink
 */
public class MasterPageHiddenTabs extends MasterPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public MasterPageHiddenTabs()
	{
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.pages.MasterPage#getActiveMenuButton()
	 */
	protected Integer getActiveMenuButton()
	{
		return ButtonContainer.BUTTON_HIDE_TAB;
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.pages.MasterPage#getTabBar(java.lang.String)
	 */
	protected Panel getTabBar(String id)
	{
		return new SecureTabbedPanel(id, getTabs());
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.pages.MasterPage#getTabs()
	 */
	protected List getTabs()
	{
		List tabs = new ArrayList();
		tabs.add(new ISecureTab()
		{
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId)
			{
				Gifkikker panel = new Gifkikker(panelId);
				SecureComponentHelper.setSecurityCheck(panel, new ContainerSecurityCheck(panel));
				return panel;
			}

            @Override
            public boolean isVisible() {
                //@TODO what todo with the new isVisible method ?
                return true;
            }

			public IModel getTitle()
			{
				return new Model("Gifkikker");
			}

			public Class getPanel()
			{
				return Gifkikker.class;
			}
		});
		tabs.add(new ISecureTab()
		{
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId)
			{
				Heineken panel = new Heineken(panelId);
				SecureComponentHelper.setSecurityCheck(panel, new ContainerSecurityCheck(panel));
				return panel;
			}

            @Override
            public boolean isVisible() {
                //@TODO what todo with the new isVisible method ?
                return true;
            }

			public IModel getTitle()
			{
				return new Model("Heineken");
			}

			public Class getPanel()
			{
				return Heineken.class;
			}
		});
		tabs.add(new ISecureTab()
		{
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId)
			{
				Grolsch panel = new Grolsch(panelId);
				SecureComponentHelper.setSecurityCheck(panel, new ContainerSecurityCheck(panel));
				return panel;
			}

            @Override
            public boolean isVisible() {
                //@TODO what todo with the new isVisible method ?
                return true;
            }
            
			public IModel getTitle()
			{
				return new Model("Grolsch");
			}

			public Class getPanel()
			{
				return Grolsch.class;
			}
		});
		return tabs;
	}
}
