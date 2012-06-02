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
package com.googlecode.wicket.jquery.ui.widget.tabs;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryPanel;
import com.googlecode.wicket.jquery.ui.Options;

/**
 * Provides jQuery tabs based on a {@link JQueryPanel}
 * 
 * @author Sebastien Briquet - sebastien@7thweb.net
 */
public class TabbedPanel extends JQueryPanel
{
	private static final long serialVersionUID = 1L;

	private final Options options;
	private final List<ITab> tabs;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public TabbedPanel(String id, List<ITab> tabs)
	{
		this(id, tabs, new Options());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options {@link Options}
	 */
	public TabbedPanel(String id, List<ITab> tabs, Options options)
	{
		super(id);
		
		this.tabs = tabs;
		this.options = options;
		
		this.init();
	}
	
	/**
	 * Initialization
	 */
	private void init()
	{
		final RepeatingView panels = new RepeatingView("panels");
		this.add(panels);
		
		this.add(new ListView<ITab>("tabs", new ListModel<ITab>(this.tabs)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<ITab> item)
			{
				final ITab tab = item.getModelObject();
				final String newId = panels.newChildId();

				// link (tab) //
				WebMarkupContainer link = this.newLink(tab);
				link.add(AttributeModifier.replace("href", "#" + newId)); 
				link.add(new Label("title", tab.getTitle()).setRenderBodyOnly(true));
				item.add(link);

				// panel //
				panels.add(tab.getPanel(newId).setMarkupId(newId).setOutputMarkupId(true));
			}
			
			/**
			 * Provides the tab's link
			 * 
			 * @param tab the ITab
			 * @return a WebMarkupContainer that represent the tab link
			 */
			private WebMarkupContainer newLink(ITab tab)
			{
				if (tab instanceof AjaxTab)
				{
					return ((AjaxTab)tab).newLink("link");
				}

				return new WebMarkupContainer("link");
			}			
		});
	}


	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.newWidgetBehavior(JQueryWidget.getSelector(this)));
	}
	
	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new TabsBehavior(selector, this.options);
	}
}
