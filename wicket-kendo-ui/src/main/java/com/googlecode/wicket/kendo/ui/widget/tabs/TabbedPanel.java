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
package com.googlecode.wicket.kendo.ui.widget.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.jquery.core.JQueryPanel;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides Kendo UI tabs based on a {@link JQueryPanel}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.19.0
 * @since 7.0.0
 */
// FIXME: target.add(myTabbedPannel) does not work
public class TabbedPanel extends JQueryPanel implements ITabsListener
{
	private static final long serialVersionUID = 1L;

	private TabsBehavior widgetBehavior;

	/**
	 * Constructor with empty list of Tabs.<br/>
	 *
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}<code>s</code>
	 * @see {@link #add(ITab)} to add tabs.
	 */
	public TabbedPanel(String id)
	{
		this(id, new ArrayList<ITab>(), new Options());
	}

	/**
	 * Constructor with empty list of Tabs.<br/>
	 *
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}<code>s</code>
	 * @see {@link #add(ITab)} to add tabs.
	 */
	public TabbedPanel(String id, Options options)
	{
		this(id, new ArrayList<ITab>(), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}<code>s</code>
	 */
	public TabbedPanel(String id, List<ITab> tabs)
	{
		this(id, tabs, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}<code>s</code>
	 * @param options {@link Options}
	 */
	public TabbedPanel(String id, List<ITab> tabs, Options options)
	{
		this(id, new ListModel<ITab>(tabs), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of {@link ITab}<code>s</code>
	 */
	public TabbedPanel(String id, IModel<List<ITab>> model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of {@link ITab}<code>s</code>
	 * @param options {@link Options}
	 */
	public TabbedPanel(String id, IModel<List<ITab>> model, Options options)
	{
		super(id, model, options);
	}

	// Properties //

	@SuppressWarnings("unchecked")
	public List<ITab> getModelObject()
	{
		List<ITab> list = (List<ITab>) this.getDefaultModelObject();

		if (list != null)
		{
			return list;
		}

		return Collections.emptyList();
	}

	/**
	 * Sets the selected tab
	 *
	 * @param index the tab's index to activate
	 * @return this, for chaining
	 */
	public TabbedPanel setActiveTab(int index)
	{
		this.widgetBehavior.activeTab = index;

		return this;
	}

	/**
	 * Selects (and activates) a tab, identified by the index<br/>
	 * <br/>
	 * <b>Warning: </b> invoking this method results to a dual client-server round-trip.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the tab's index to activate
	 */
	public void setActiveTab(int index, AjaxRequestTarget target)
	{
		this.widgetBehavior.select(index, target);
	}

	@Override
	public boolean isSelectEventEnabled()
	{
		return true;
	}

	@Override
	public boolean isShowEventEnabled()
	{
		return false;
	}

	@Override
	public boolean isActivateEventEnabled()
	{
		return false;
	}

	// Methods //

	/**
	 * Helper method. Adds an {@link ITab} to the list of tabs.
	 *
	 * @param tab the {@link ITab} to be added
	 * @return true (as specified by Collection.add)
	 */
	public boolean add(ITab tab)
	{
		return this.getModelObject().add(tab); // will throw an UnsupportedOperationException if null is supplied to the constructor
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		final RepeatingView panels = new RepeatingView("panels") {

			private static final long serialVersionUID = 1L;

			@Override
			public String newChildId()
			{
				return String.format("tab-%s-%s", this.getMarkupId(), super.newChildId());
			}

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				this.removeAll();
			}
		};

		this.add(panels);

		this.add(new ListView<ITab>("tabs", this.getModelObject()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected ListItem<ITab> newItem(int index, IModel<ITab> model)
			{
				ListItem<ITab> item = super.newItem(index, model);
				item.setVisible(model.getObject().isVisible());

				return item;
			}

			@Override
			protected void populateItem(ListItem<ITab> item)
			{
				final ITab tab = item.getModelObject();

				if (tab.isVisible())
				{
					// link (tab) //
					item.add(TabbedPanel.this.newTitleLabel("title", tab.getTitle()));

					// panel //
					final String newId = panels.newChildId();
					panels.add(tab.getPanel(newId).setMarkupId(newId).setOutputMarkupId(true));
				}
			}
		});

		this.add(this.widgetBehavior = JQueryWidget.newWidgetBehavior(this));
	}

	@Override
	public void onSelect(AjaxRequestTarget target, int index, ITab tab)
	{
		// noop
	}

	@Override
	public void onShow(AjaxRequestTarget target, int index, ITab tab)
	{
		// noop
	}

	@Override
	public void onActivate(AjaxRequestTarget target, int index, ITab tab)
	{
		// noop
	}

	// Factories //

	/**
	 * Gets a new {@link Label} for the tab's title
	 *
	 * @param id the markup id
	 * @param title the tab's title model
	 * @return a new {@link Label}
	 */
	protected Label newTitleLabel(String id, IModel<String> title)
	{
		return new Label(id, title);
	}

	// IJQueryWidget //

	@Override
	public TabsBehavior newWidgetBehavior(String selector)
	{
		return new TabsBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<ITab> getTabs()
			{
				return TabbedPanel.this.getModelObject();
			}

			@Override
			public boolean isSelectEventEnabled()
			{
				return TabbedPanel.this.isSelectEventEnabled();
			}

			@Override
			public boolean isShowEventEnabled()
			{
				return TabbedPanel.this.isShowEventEnabled();
			}

			@Override
			public boolean isActivateEventEnabled()
			{
				return TabbedPanel.this.isActivateEventEnabled();
			}

			@Override
			public void onSelect(AjaxRequestTarget target, int index, ITab tab)
			{
				TabbedPanel.this.onSelect(target, index, tab);
			}

			@Override
			public void onShow(AjaxRequestTarget target, int index, ITab tab)
			{
				TabbedPanel.this.onShow(target, index, tab);
			}

			@Override
			public void onActivate(AjaxRequestTarget target, int index, ITab tab)
			{
				TabbedPanel.this.onActivate(target, index, tab);
			}
		};
	}
}
