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

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.JQueryPanel;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;

/**
 * Provides a jQuery accordion based on a {@link JQueryPanel}, which takes {@link ITab}<code>s</code> as contructor's argument
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.2.3
 * @since 6.0.1
 */
public class AccordionPanel extends JQueryPanel
{
	private static final long serialVersionUID = 1L;

	private final List<ITab> tabs;
	private final Options options;
	private AccordionBehavior widgetBehavior;
	private JQueryAjaxBehavior activateEventBehavior;

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
		this.add(new Loop("tabs", this.getCountModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(LoopItem item)
			{
				int index = item.getIndex();
				final ITab tab = AccordionPanel.this.tabs.get(index);

				if (tab.isVisible())
				{
					item.add(new Label("title", tab.getTitle()));
					item.add(tab.getPanel("panel"));
				}
			}
		});
	}

	// Properties //
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
		this.widgetBehavior.activate(index, target); //sets 'active' option, that fires 'activate' event (best would be that is also fires a 'show' event)
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
				return AccordionPanel.this.tabs.size();
			}
		};
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.activateEventBehavior = this.newActivateEventBehavior());
		this.add(this.widgetBehavior = (AccordionBehavior) JQueryWidget.newWidgetBehavior(this));
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
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof ActivateEvent)
		{
			ActivateEvent payload = (ActivateEvent) event.getPayload();
			AjaxRequestTarget target = payload.getTarget();

			int index = payload.getIndex();

			if (index > -1) /* index could be not known depending on options and user action */
			{
				ITab tab = this.tabs.get(index);

				if (tab instanceof AjaxTab)
				{
					((AjaxTab) tab).load(target);
				}

				this.onActivate(target, index, tab);
			}
		}
	}

	/**
	 * Triggered when an accordion tab has been activated ('activate' event).<br/>
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the accordion header that triggered this event
	 * @param tab the {@link ITab} that corresponds to the index
	 */
	protected void onActivate(AjaxRequestTarget target, int index, ITab tab)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new AccordionBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				AccordionPanel.this.onConfigure(this);

				this.setOption("create", activateEventBehavior.getCallbackFunction());
				this.setOption("activate", activateEventBehavior.getCallbackFunction());
			}
		};
	}

	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'activate' callback
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newActivateEventBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(event, ui) { " + this.getCallbackScript() + " }";
			}

			@Override
			public CharSequence getCallbackScript()
			{
				return this.generateCallbackScript("wicketAjaxGet('" + this.getCallbackUrl() + "&index=' + jQuery(event.target).accordion('option', 'active')");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ActivateEvent(target);
			}
		};
	}

	// Event objects //
	/**
	 * Base class for accordion event objects
	 */
	private class ActivateEvent extends JQueryEvent
	{
		private final int index;

		/**
		 * Constructor
		 *
		 * @param target the {@link AjaxRequestTarget}
		 */
		public ActivateEvent(AjaxRequestTarget target)
		{
			super(target);

			this.index = RequestCycleUtils.getQueryParameterValue("index").toInt(-1);
		}

		/**
		 * Gets the tab's index
		 *
		 * @return the index
		 */
		public int getIndex()
		{
			return this.index;
		}
	}
}
