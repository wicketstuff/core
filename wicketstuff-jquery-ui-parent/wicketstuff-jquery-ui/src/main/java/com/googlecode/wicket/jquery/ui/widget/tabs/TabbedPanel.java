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

import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.JQueryGenericPanel;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides jQuery tabs based on a {@link JQueryGenericPanel}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class TabbedPanel extends JQueryGenericPanel<List<ITab>> implements ITabsListener
{
	private static final long serialVersionUID = 1L;

	private TabsBehavior widgetBehavior;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}{@code s}
	 */
	public TabbedPanel(String id, List<ITab> tabs)
	{
		this(id, Model.ofList(tabs), new Options());
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
	 * Activates the selected tab
	 *
	 * @param index the tab's index to activate
	 * @return this, for chaining
	 */
	public TabbedPanel setActiveTab(int index)
	{
		this.options.set("active", index);

		return this;
	}

	/**
	 * Activates the selected tab<br>
	 * <br>
	 * <b>Warning:</b> invoking this method results to a dual client-server round-trip. Use this method if you cannot use {@link #setActiveTab(int)} followed by {@code target.add(myTabbedPannel)}
	 *
	 * @param index the tab's index to activate
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void setActiveTab(int index, IPartialPageRequestHandler handler)
	{
		this.widgetBehavior.activate(index, handler); // sets 'active' option, that fires 'activate' event (best would be that is also fires a 'show' event)
	}

	/**
	 * Enables all tabs
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void enable(IPartialPageRequestHandler handler)
	{
		this.widgetBehavior.enable(handler);
	}

	/**
	 * Enables a tab, identified by its index
	 *
	 * @param index the tab's index to enable
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void enable(int index, IPartialPageRequestHandler handler)
	{
		this.widgetBehavior.enable(index, handler);
	}

	/**
	 * Disables all tabs
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void disable(IPartialPageRequestHandler handler)
	{
		this.widgetBehavior.disable(handler);
	}

	/**
	 * Disables a tab, identified by its index. The selected tab cannot be disabled.
	 *
	 * @param index the tab's index to disable
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void disable(int index, IPartialPageRequestHandler handler)
	{
		this.widgetBehavior.disable(index, handler);
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
	public boolean isCreateEventEnabled()
	{
		return true;
	}

	@Override
	public boolean isActivateEventEnabled()
	{
		return true;
	}

	@Override
	public boolean isActivatingEventEnabled()
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

	/**
	 * Reloads the {@link TabbedPanel}<br>
	 * <br>
	 * <b>Note:</b> This method should be used instead of {@code target.add(tabbedPanel)} if the underlying model is-a {@link TabListModel}
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void reload(IPartialPageRequestHandler handler)
	{
		IModel<?> model = this.getModel();

		if (model instanceof TabListModel)
		{
			((TabListModel) model).flush();
		}

		handler.add(this);
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
				return String.format("tab-%s-%s", this.getMarkupId(), super.newChildId()); // fixes issue #14
			}

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				this.removeAll(); // fixes issue #7
			}
		};

		this.add(panels);

		this.add(new ListView<ITab>("tabs", this.getModel()) {

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
					final String newId = panels.newChildId();

					// tab //
					item.add(TabbedPanel.this.newTabContainer("tab", newId, tab, item.getIndex()));

					// panel //
					panels.add(tab.getPanel(newId).setMarkupId(newId).setOutputMarkupId(true));
				}
			}
		});

		this.widgetBehavior = (TabsBehavior) JQueryWidget.newWidgetBehavior(this);
		this.add(this.widgetBehavior);
	}

	@Override
	public void onActivate(AjaxRequestTarget target, int index, ITab tab)
	{
		// noop
	}

	@Override
	public void onActivating(AjaxRequestTarget target, int index, ITab tab)
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

	/**
	 * Gets a new tab container that contains the tab's title<br>
	 * <b>Warning:</b> override with care!
	 * 
	 * @param id the container's markup-id
	 * @param tabId the tab html-id
	 * @param tab the {@link ITab}
	 * @param index the tab index
	 * @return a new {@link WebMarkupContainer}
	 */
	protected WebMarkupContainer newTabContainer(String id, String tabId, ITab tab, int index)
	{
		WebMarkupContainer container = new Fragment(id, "tab-fragment", TabbedPanel.this);

		// link //
		Component link = TabbedPanel.this.newTitleLabel("link", tab.getTitle());
		link.add(AttributeModifier.replace("href", "#" + tabId));
		container.add(link);

		return container;
	}

	// IJQueryWidget //

	@Override
	public TabsBehavior newWidgetBehavior(String selector)
	{
		return new TabsBehavior(selector, this.options, this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<ITab> getTabs()
			{
				return TabbedPanel.this.getModelObject();
			}
		};
	}
}
