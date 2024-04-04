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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.JQueryGenericPanel;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides Kendo UI tabs based on a {@link JQueryGenericPanel}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.19.0
 * @since 7.0.0
 */
public class TabbedPanel extends JQueryGenericPanel<List<ITab>> implements ITabsListener // NOSONAR
{
	private static final long serialVersionUID = 1L;

	private TabsBehavior widgetBehavior;

	/**
	 * Constructor with empty list of Tabs.<br>
	 *
	 * @param id the markup id
	 * @see #add(ITab)
	 */
	public TabbedPanel(String id)
	{
		this(id, new ArrayList<ITab>(), new Options());
	}

	/**
	 * Constructor with empty list of Tabs.<br>
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 * @see #add(ITab)
	 */
	public TabbedPanel(String id, Options options)
	{
		this(id, new ArrayList<ITab>(), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}{@code s}
	 */
	public TabbedPanel(String id, List<ITab> tabs)
	{
		this(id, tabs, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}{@code s}
	 * @param options the {@link Options}
	 */
	public TabbedPanel(String id, List<ITab> tabs, Options options)
	{
		this(id, Model.ofList(tabs), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link TabListModel}
	 */
	public TabbedPanel(String id, TabListModel model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link TabListModel}
	 * @param options the {@link Options}
	 */
	public TabbedPanel(String id, TabListModel model, Options options)
	{
		super(id, model, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of {@link ITab}{@code s}
	 */
	public TabbedPanel(String id, IModel<List<ITab>> model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of {@link ITab}{@code s}
	 * @param options the {@link Options}
	 */
	public TabbedPanel(String id, IModel<List<ITab>> model, Options options)
	{
		super(id, model, options);
	}

	// Properties //

	@Override
	public String getSelector()
	{
		return this.widgetBehavior.getSelector();
	}

	@Override
	public List<ITab> getModelObject()
	{
		List<ITab> list = super.getModelObject();

		if (list != null)
		{
			return list;
		}

		return Collections.emptyList();
	}

	/**
	 * Sets the current tab index<br>
	 * <b>Warning:</b> the index is relative to visible tabs only
	 *
	 * @param index the visible tab's index to activate
	 * @return this, for chaining
	 */
	public TabbedPanel setTabIndex(int index)
	{
		this.widgetBehavior.tabIndex = index;

		return this;
	}

	/**
	 * Sets and activates the current tab index<br>
	 * <b>Warning: </b> invoking this method results to a dual client-server round-trip.
	 *
	 * @param index the tab's index to activate
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void setTabIndex(int index, IPartialPageRequestHandler handler)
	{
		this.widgetBehavior.select(index, handler);
	}

	/**
	 * Gets the last <i>visible</i> tab index
	 *
	 * @return the tab index, or -1 if none
	 */
	public int getLastTabIndex()
	{
		int index = -1;

		for (ITab tab : this.getModelObject())
		{
			if (tab.isVisible())
			{
				index++;
			}
		}

		return index;
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

		final RepeatingView panels = new RepeatingView("panels") { // NOSONAR

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

		this.add(new ListView<ITab>("tabs", this.getModelObject()) { // NOSONAR

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

		this.widgetBehavior = (TabsBehavior) JQueryWidget.newWidgetBehavior(this);
		this.add(this.widgetBehavior);
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
	 * @return a new {@code Label}
	 */
	protected Component newTitleLabel(String id, IModel<String> title)
	{
		return new Label(id, title);
	}

	// IJQueryWidget //

	@Override
	public TabsBehavior newWidgetBehavior(String selector)
	{
		return new TabsBehavior(selector, this.options, this) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected List<ITab> getTabs()
			{
				return TabbedPanel.this.getModelObject();
			}
		};
	}
}
