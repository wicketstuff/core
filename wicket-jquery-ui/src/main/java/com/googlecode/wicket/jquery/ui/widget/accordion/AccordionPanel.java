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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
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
	public AccordionPanel(String id, List<? extends ITab> tabs)
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
	public AccordionPanel(String id, List<? extends ITab> tabs, Options options)
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
	public AccordionPanel(String id, IModel<List<? extends ITab>> model, Options options)
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
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the tab's index to activate
	 */
	public void setActiveTab(int index, AjaxRequestTarget target)
	{
		this.widgetBehavior.activate(index, target); // sets 'active' option, that fires 'activate' event (best would be that is also fires a 'show' event)
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(new Loop("tabs", this.getCountModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(LoopItem item)
			{
				int index = item.getIndex();
				final ITab tab = AccordionPanel.this.getModelObject().get(index);

				if (tab.isVisible())
				{
					item.add(new Label("title", tab.getTitle()));
					item.add(tab.getPanel("panel"));
				}
			}
		});

		this.add(this.widgetBehavior = this.newWidgetBehavior(JQueryWidget.getSelector(this)));
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering cycle has begun, the behavior can modify the
	 * configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
	}

	@Override
	public void onActivate(AjaxRequestTarget target, int index, ITab tab)
	{
	}

	// IJQueryWidget //
	@Override
	public AccordionBehavior newWidgetBehavior(String selector)
	{
		return new AccordionBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<ITab> getTabs()
			{
				return AccordionPanel.this.getModelObject();
			}

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				AccordionPanel.this.onConfigure(this);
			}

			@Override
			public void onActivate(AjaxRequestTarget target, int index, ITab tab)
			{
				AccordionPanel.this.onActivate(target, index, tab);
			}
		};
	}
}
