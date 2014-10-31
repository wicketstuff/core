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
package com.googlecode.wicket.jquery.ui.widget.accordion;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.JQueryPanel;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a jQuery accordion based on a {@link JQueryPanel}, which takes {@link ITab}<code>s</code> as contructor's argument
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.2.3
 * @since 6.0.1
 */
public class AccordionPanel extends JQueryPanel implements IAccordionListener
{
	private static final long serialVersionUID = 1L;

	private AccordionBehavior widgetBehavior;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}<code>s</code>
	 */
	public AccordionPanel(String id, List<ITab> tabs)
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
	public AccordionPanel(String id, List<ITab> tabs, Options options)
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
	public AccordionPanel(String id, IModel<List<ITab>> model, Options options)
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
	 * Gets the model of tab's count
	 *
	 * @return the {@link Model}
	 */
	private Model<Integer> getCountModel()
	{
		return new Model<Integer>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Integer getObject()
			{
				return AccordionPanel.this.getModelObject().size();
			}
		};
	}

	/**
	 * Activates the selected tab
	 *
	 * @param index the tab's index to activate
	 * @return this, for chaining
	 */
	public AccordionPanel setActiveTab(int index)
	{
		this.options.set("active", index);

		return this;
	}

	/**
	 * Activates the selected tab<br/>
	 * <b>Warning: </b> invoking this method results to a dual client-server round-trip. Use this method if you cannot use {@link #setActiveTab(int)} followed by <code>target.add(myTabbedPannel)</code>
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the tab's index to activate
	 */
	public void setActiveTab(int index, AjaxRequestTarget target)
	{
		this.widgetBehavior.activate(index, target); // sets 'active' option, that fires 'activate' event (best would be that it also fires a 'show' event)
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

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(new Loop("tabs", this.getCountModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected LoopItem newItem(final int index)
			{
				ITab tab = AccordionPanel.this.getModelObject().get(index);

				LoopItem item = super.newItem(index);
				item.setVisible(tab.isVisible());

				return item;
			}

			@Override
			protected void populateItem(LoopItem item)
			{
				int index = item.getIndex();
				final ITab tab = AccordionPanel.this.getModelObject().get(index);

				item.add(AccordionPanel.this.newTitleLabel("title", tab.getTitle()));
				item.add(tab.getPanel("panel"));
			}
		});

		this.add(this.widgetBehavior = this.newWidgetBehavior(JQueryWidget.getSelector(this)));
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
	public AccordionBehavior newWidgetBehavior(String selector)
	{
		return new AccordionBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCreateEventEnabled()
			{
				return AccordionPanel.this.isCreateEventEnabled();
			}

			@Override
			public boolean isActivateEventEnabled()
			{
				return AccordionPanel.this.isActivateEventEnabled();
			}

			@Override
			protected List<ITab> getTabs()
			{
				return AccordionPanel.this.getModelObject();
			}

			@Override
			public void onActivate(AjaxRequestTarget target, int index, ITab tab)
			{
				AccordionPanel.this.onActivate(target, index, tab);
			}
		};
	}
}
