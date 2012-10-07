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
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.JQueryPanel;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;

/**
 * Provides jQuery tabs based on a {@link JQueryPanel}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class TabbedPanel extends JQueryPanel
{
	private static final long serialVersionUID = 1L;

	private final Options options;
	private final List<ITab> tabs;

	private JQueryAjaxBehavior onShowBehavior;

	/**
	 * Constructor
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}
	 */
	public TabbedPanel(String id, List<ITab> tabs)
	{
		this(id, tabs, new Options());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}
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
		final RepeatingView panels = new RepeatingView("panels") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				this.removeAll(); //fixes issue #7
			}
		};

		this.add(panels);

		this.add(new ListView<ITab>("tabs", new ListModel<ITab>(this.tabs)) {

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
	}


	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.onShowBehavior = this.newOnShowBehavior());
		this.add(JQueryWidget.newWidgetBehavior(this));
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
		if (event.getPayload() instanceof ShowEvent)
		{
			ShowEvent payload = (ShowEvent) event.getPayload();

			AjaxRequestTarget target = payload.getTarget();
			int index = payload.getIndex();
			ITab tab = tabs.get(index);

			if (tab instanceof AjaxTab)
			{
				((AjaxTab)tab).load(target);
			}
		}
	}


	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new TabsBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				TabbedPanel.this.onConfigure(this);

				this.setOption("show", TabbedPanel.this.onShowBehavior.getCallbackFunction()); //'show' is used instead of 'select' for an AjaxTab to be loaded event if it is the first tab
			}
		};
	}


	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'show' javascript callback
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnShowBehavior()
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
				return this.generateCallbackScript("wicketAjaxGet('" + this.getCallbackUrl() + "&index=' + ui.index");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ShowEvent(target);
			}
		};
	}


	// Event objects //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'show' callback
	 */
	static class ShowEvent extends JQueryEvent
	{
		private final int index;

		/**
		 * Constructor
		 * @param target the {@link AjaxRequestTarget}
		 * @param step the {@link Step} (Start or Stop)
		 */
		public ShowEvent(AjaxRequestTarget target)
		{
			super(target);

			this.index = RequestCycleUtils.getQueryParameterValue("index").toInt();
		}

		/**
		 * Gets the tab's index
		 * @return the index
		 */
		public int getIndex()
		{
			return this.index;
		}
	}
}
