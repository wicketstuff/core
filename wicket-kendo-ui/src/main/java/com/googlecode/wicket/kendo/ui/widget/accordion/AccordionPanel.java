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
package com.googlecode.wicket.kendo.ui.widget.accordion;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.JQueryGenericPanel;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a Kendo UI accordion based on a {@link JQueryGenericPanel}, which takes {@link ITab}{@code s} as constructor's argument
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.19.0
 * @since 7.0.0
 */
public class AccordionPanel extends JQueryGenericPanel<List<ITab>> implements IAccordionListener
{
	private static final long serialVersionUID = 1L;

	private AccordionBehavior widgetBehavior;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}{@code s}
	 */
	public AccordionPanel(String id, List<ITab> tabs)
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
	public AccordionPanel(String id, List<ITab> tabs, Options options)
	{
		this(id, Model.ofList(tabs), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of {@link ITab}{@code s}
	 */
	public AccordionPanel(String id, IModel<List<ITab>> model)
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
	public AccordionPanel(String id, IModel<List<ITab>> model, Options options)
	{
		super(id, model, options);
	}

	// Properties //

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
	 * Sets the current tab index<br/>
	 * <b>Warning:</b> the index is relative to visible tabs only
	 *
	 * @param index the visible tab's index to activate
	 * @return this, for chaining
	 */
	public AccordionPanel setTabIndex(int index)
	{
		this.widgetBehavior.tabIndex = index;

		return this;
	}

	/**
	 * Sets (selects and activates) the current tab index<br/>
	 * <b>Warning:</b> the index is related to visible tabs only<br/>
	 * <b>Warning:</b> invoking this method results to a dual client-server round-trip.
	 *
	 * @param index the visible tab's index to activate
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
	public boolean isActivateEventEnabled()
	{
		return false;
	}

	@Override
	public boolean isExpandEventEnabled()
	{
		return false;
	}

	@Override
	public boolean isCollapseEventEnabled()
	{
		return false;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		final WebMarkupContainer root = new WebMarkupContainer("root");
		this.add(root);

		root.add(new Loop("tabs", this.getCountModel()) {

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

		this.widgetBehavior = JQueryWidget.newWidgetBehavior(this, root);
		this.add(this.widgetBehavior);
	}

	@Override
	public void onSelect(AjaxRequestTarget target, int index, ITab tab)
	{
		// noop
	}

	@Override
	public void onActivate(AjaxRequestTarget target, int index, ITab tab)
	{
		// noop
	}

	@Override
	public void onExpand(AjaxRequestTarget target, int index, ITab tab)
	{
		// noop
	}

	@Override
	public void onCollapse(AjaxRequestTarget target, int index, ITab tab)
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
	protected Label newTitleLabel(String id, IModel<String> title)
	{
		return new Label(id, title);
	}

	// IJQueryWidget //

	@Override
	public AccordionBehavior newWidgetBehavior(String selector)
	{
		return new AccordionBehavior(selector, this.options, this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<ITab> getTabs()
			{
				return AccordionPanel.this.getModelObject();
			}
		};
	}
}
