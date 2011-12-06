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
package org.wicketstuff.jquery.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.tabs.JQTabbedPanel;

@SuppressWarnings("serial")
public class Page4Tabs extends PageSupport
{
	public Page4Tabs() throws Exception
	{
		super();
		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new AbstractTab(Model.of("A"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new TextPanel(
					panelId,
					Model.of("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Phasellus nec leo. Duis ultricies. In id ipsum vitae ante fringilla"));
			}
		});
		tabs.add(new AbstractTab(Model.of("B"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new TextPanel(
					panelId,
					Model.of("volutpat. In pharetra. Ut ante. Vivamus tempus, leo a ullamcorper tincidunt, pede ipsum consectetuer nunc, at pellentesque"));
			}
		});
		tabs.add(new AbstractTab(Model.of("C"))
		{
			@Override
			public Panel getPanel(String panelId)
			{
				return new TextPanel(
					panelId,
					Model.of("libero felis in metus. Pellentesque sollicitudin neque. Nulla facilisi. Sed hendrerit tempus orci. Aenean a nulla quis risus molestie vehicula."));
			}
		});
		add(new TabbedPanel("tabs1", tabs));
		add(new JQTabbedPanel("tabs2", tabs));
		add(new JQTabbedPanel("tabs3", tabs, "{ fxFade: true }"));
	}
}
