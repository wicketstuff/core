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

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.security.examples.tabs.components.navigation.ButtonContainer;
import org.apache.wicket.security.examples.tabs.components.tabs.SecureTab;
import org.apache.wicket.security.examples.tabs.panels.Gifkikker;
import org.apache.wicket.security.examples.tabs.panels.Grolsch;
import org.apache.wicket.security.examples.tabs.panels.Heineken;

/**
 * Basic page showing a tab bar. The tabs used have an additional feature of a
 * warningpanel. If you are not allowed to see the tab contents you will instead see a
 * nice message telling you that you can not see the contents.
 * 
 * @author marrink
 * 
 */
public class MasterPage extends SecurePage
{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MasterPage()
	{
		add(new ButtonContainer("buttoncontainer", getActiveMenuButton()));
		add(getTabBar("tabs"));
	}

	/**
	 * The component displaying all the tabs. like a {@link TabbedPanel}.
	 * 
	 * @param id
	 * @return the component
	 */
	protected Panel getTabBar(String id)
	{
		return new TabbedPanel(id, getTabs());
	}

	/**
	 * Return one of the buttons in {@link ButtonContainer}.
	 * 
	 * @return the active menu button.
	 */
	protected Integer getActiveMenuButton()
	{
		return ButtonContainer.BUTTON_SHOW_WARNING_PANEL;
	}

	/**
	 * A list containing {@link ITab}s representing all the tabs.
	 * 
	 * @return the tabs
	 */
	protected List<ITab> getTabs()
	{
		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new SecureTab(new ITab()
		{
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId)
			{
				return new Gifkikker(panelId);
			}

			public boolean isVisible()
			{
				// @TODO what todo with the new isVisible method ?
				return true;
			}

			public IModel<String> getTitle()
			{
				return new Model<String>("Gifkikker");
			}
		}));
		tabs.add(new SecureTab(new ITab()
		{
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId)
			{
				return new Heineken(panelId);
			}

			public boolean isVisible()
			{
				// @TODO what todo with the new isVisible method ?
				return true;
			}

			public IModel<String> getTitle()
			{
				return new Model<String>("Heineken");
			}
		}));
		tabs.add(new SecureTab(new ITab()
		{
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId)
			{
				return new Grolsch(panelId);
			}

			public boolean isVisible()
			{
				// @TODO what todo with the new isVisible method ?
				return true;
			}

			public IModel<String> getTitle()
			{
				return new Model<String>("Grolsch");
			}
		}));
		return tabs;
	}
}
