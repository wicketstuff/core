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
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.JQueryPanel;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.widget.accordion.AccordionPanel.ChangeEvent.Step;
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
	private JQueryAjaxBehavior onChangingBehavior;
	private JQueryAjaxBehavior onChangedBehavior;

	/**
	 * Constructor
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}
	 */
	public AccordionPanel(String id, List<ITab> tabs)
	{
		this(id, tabs, new Options());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param tabs the list of {@link ITab}
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
		this.add(new ListView<ITab>("tabs", new ListModel<ITab>(this.tabs)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<ITab> item)
			{
				final ITab tab = item.getModelObject();

				if (tab.isVisible())
				{
					WebMarkupContainer link = this.newLink("link", tab);
					link.add(new Label("title", tab.getTitle()).setRenderBodyOnly(true));
					item.add(link);

					item.add(tab.getPanel("panel"));
				}
			}

			/**
			 * Provides the tab's link
			 *
			 * @param tab the ITab
			 * @return a WebMarkupContainer that represent the tab link
			 */
			private WebMarkupContainer newLink(String id, ITab tab)
			{
				if (tab instanceof AjaxTab)
				{
					return ((AjaxTab)tab).newLink(id);
				}

				return new WebMarkupContainer(id);
			}
		});
	}

	// Properties //
	/**
	 * Indicates whether the 'changestart' event is enabled
	 * If true, the {@link #onChanging(AjaxRequestTarget, int, ITab)} event will be triggered
	 *
	 * @return false by default
	 */
	protected boolean isChangingEventEnabled()
	{
		return false;
	}

	/**
	 * Indicates whether the 'change' event is enabled
	 * If true, the {@link #onChanging(AjaxRequestTarget, int, ITab)} event will be triggered
	 *
	 * @return false by default
	 */
	protected boolean isChangedEventEnabled()
	{
		return false;
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.onChangingBehavior = this.newOnChangingBehavior());
		this.add(this.onChangedBehavior = this.newOnChangedBehavior());
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
		behavior.setOptions(this.options);
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof ChangeEvent)
		{
			ChangeEvent payload = (ChangeEvent) event.getPayload();
			int index = payload.getIndex();
			ITab tab = tabs.get(index);

			//action
			if (payload.getStep() == ChangeEvent.Step.Start)
			{
				this.onChanging(payload.getTarget(), index, tab);
			}

			if (payload.getStep() == ChangeEvent.Step.Stop)
			{
				this.onChanged(payload.getTarget(), index, tab);
			}
		}
	}

	/**
	 * Triggered when the accordion state is changing ('hangestart' event).<br/>
	 * {@link #isChangingEventEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the accordion header that triggered this event
	 * @param tab the {@link ITab} that corresponds to the index
	 */
	protected void onChanging(AjaxRequestTarget target, int index, ITab tab)
	{
	}

	/**
	 * Triggered when the accordion state has changed ('hange' event).<br/>
	 * {@link #isChangingEventEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the accordion header that triggered this event
	 * @param tab the {@link ITab} that corresponds to the index
	 */
	protected void onChanged(AjaxRequestTarget target, int index, ITab tab)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new AccordionBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				AccordionPanel.this.onConfigure(this);

				if (AccordionPanel.this.isChangingEventEnabled())
				{
					this.setOption("changestart", AccordionPanel.this.onChangingBehavior.getCallbackFunction());
				}

				if (AccordionPanel.this.isChangedEventEnabled())
				{
					this.setOption("change", AccordionPanel.this.onChangedBehavior.getCallbackFunction());
				}
			}
		};
	}


	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'changestart' javascript callback
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnChangingBehavior()
	{
		return new OnChangeBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ChangeEvent(target, Step.Start);
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'change' javascript callback
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnChangedBehavior()
	{
		return new OnChangeBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ChangeEvent(target, Step.Stop);
			}
		};
	}

	// Event behaviors //
	/**
	 * Provides the base class for 'changestart' and 'change' events
	 */
	abstract class OnChangeBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnChangeBehavior(Component source)
		{
			super(source);
		}

		@Override
		public String getCallbackFunction()
		{
			return "function(event, ui) { " + this.getCallbackScript() + " }";
		}

		@Override
		public CharSequence getCallbackScript()
		{
			return this.generateCallbackScript("wicketAjaxGet('" + this.getCallbackUrl() + "&index=' + ui.options.active");
			//+ '&title=' + $(ui.newHeader.context).text()");
		}
	}

	// Event objects //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'change' callback
	 */
	static class ChangeEvent extends JQueryEvent
	{
		enum Step { Start, Stop };

		private final Step step;
		private final int index;

		/**
		 * Constructor
		 * @param target the {@link AjaxRequestTarget}
		 * @param step the {@link Step} (Start or Stop)
		 */
		public ChangeEvent(AjaxRequestTarget target, Step step)
		{
			super(target);

			this.step = step;
			this.index = RequestCycleUtils.getQueryParameterValue("index").toInt();
		}

		/**
		 * Gets the {@link Step} (Start or Stop)
		 * @return the {@link Step}
		 */
		public Step getStep()
		{
			return this.step;
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
