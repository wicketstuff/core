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
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryPanel;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides jQuery tabs based on a {@link JQueryPanel}
 *
 * @author Sebastien Briquet - sebfz1
 */
//XXX: report as changed - getTabs() > getModelObject()
public class TabbedPanel extends JQueryPanel implements ITabsListener
{
	private static final long serialVersionUID = 1L;

	private TabsBehavior widgetBehavior;

	/**
	 * Constructor
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}<code>s</code>
	 */
	public TabbedPanel(String id, List<ITab> tabs)
	{
		this(id, tabs, new Options());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}<code>s</code>
	 * @param options {@link Options}
	 */
	public TabbedPanel(String id, List<? extends ITab> tabs, Options options)
	{
		this(id, Model.ofList(tabs), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of {@link ITab}<code>s</code>
	 * @param options {@link Options}
	 */
	public TabbedPanel(String id, IModel<List<? extends ITab>> model, Options options)
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
	 * Activates the selected tab
	 * @param index the tab's index to activate
	 * @return this, for chaining
	 */
	public TabbedPanel setActiveTab(int index)
	{
		this.options.set("active", index);

		return this;
	}

	/**
	 * Activates the selected tab<br/>
	 * <b>Warning: </b> invoking this method results to a dual client-server round-trip. Use this method if you cannot use {@link #setActiveTab(int)} followed by <code>target.add(myTabbedPannel)</code>
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the tab's index to activate
	 */
	public void setActiveTab(int index, AjaxRequestTarget target)
	{
		this.widgetBehavior.activate(index, target); //sets 'active' option, that fires 'activate' event (best would be that is also fires a 'show' event)
	}

	@Override
	public boolean isOnActivatingEventEnabled()
	{
		return false;
	}


	// Methods //
	/**
	 * Helper method. Adds an {@link ITab} to the list of tabs.
	 * @param tab the {@link ITab} to be added
	 * @return true (as specified by Collection.add)
	 */
	public boolean add(ITab tab)
	{
		return this.getModelObject().add(tab); //will throw an UnsupportedOperationException if null is supplied to the constructor
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
				return String.format("tab-%s-%s", this.getMarkupId(), super.newChildId()); //fixes issue #14
			}

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				this.removeAll(); //fixes issue #7
			}
		};

		this.add(panels);

		this.add(new ListView<ITab>("tabs", this.getModelObject()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<ITab> item)
			{
				final ITab tab = item.getModelObject();

				if (tab.isVisible())
				{
					final String newId = panels.newChildId();

					// link (tab) //
					Label link = new Label("link", tab.getTitle());
					link.add(AttributeModifier.replace("href", "#" + newId));
					item.add(link);

					// panel //
					panels.add(tab.getPanel(newId).setMarkupId(newId).setOutputMarkupId(true));
				}
			}
		});

		this.add(this.widgetBehavior = this.newWidgetBehavior(JQueryWidget.getSelector(this)));
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
	}

	@Override
	public void onActivating(AjaxRequestTarget target, int index, ITab tab)
	{
	}
	@Override
	public void onActivate(AjaxRequestTarget target, int index, ITab tab)
	{
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
			public boolean isOnActivatingEventEnabled()
			{
				return TabbedPanel.this.isOnActivatingEventEnabled();
			}

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				TabbedPanel.this.onConfigure(this);
			}

			@Override
			public void onActivating(AjaxRequestTarget target, int index, ITab tab)
			{
				TabbedPanel.this.onActivating(target, index, tab);
			}

			@Override
			public void onActivate(AjaxRequestTarget target, int index, ITab tab)
			{
				TabbedPanel.this.onActivate(target, index, tab);
			}
		};
	}
}
