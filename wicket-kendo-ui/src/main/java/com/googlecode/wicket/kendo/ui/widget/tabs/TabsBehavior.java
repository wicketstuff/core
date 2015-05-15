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
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a Kendo UI kendoTabStrip behavior.<br/>
 * Note, this class has almost the same code as AccordionBehavior
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.19.0
 */
public abstract class TabsBehavior extends KendoUIBehavior implements IJQueryAjaxAware, ITabsListener
{
	private static final long serialVersionUID = 1L;

	static final String METHOD = "kendoTabStrip";
	private static final int DEFAULT_TAB = 0;

	int activeTab = DEFAULT_TAB;

	private JQueryAjaxBehavior onSelectAjaxBehavior = null;
	private JQueryAjaxBehavior onShowAjaxBehavior = null;
	private JQueryAjaxBehavior onActivateAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public TabsBehavior(String selector)
	{
		super(selector, METHOD);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public TabsBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	// Properties //

	/**
	 * Gets the reference {@link List} of {@link ITab}{@code s}.<br/>
	 * Usually the model object of the component on which this {@link TabsBehavior} is bound to.
	 *
	 * @return a non-null {@link List}
	 */
	protected abstract List<ITab> getTabs();

	/**
	 * Gets a read-only {@link ITab} {@link List} having its visible flag set to true.
	 *
	 * @return a {@link List} of {@link ITab}{@code s}
	 */
	protected List<ITab> getVisibleTabs()
	{
		List<ITab> list = new ArrayList<ITab>();

		for (ITab tab : this.getTabs())
		{
			if (tab.isVisible())
			{
				list.add(tab);
			}
		}

		return Collections.unmodifiableList(list);
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (this.isSelectEventEnabled())
		{
			this.onSelectAjaxBehavior = this.newOnSelectAjaxBehavior(this);
			component.add(this.onSelectAjaxBehavior);
		}

		if (this.isShowEventEnabled())
		{
			this.onShowAjaxBehavior = this.newOnShowAjaxBehavior(this);
			component.add(this.onShowAjaxBehavior);
		}

		if (this.isActivateEventEnabled())
		{
			this.onActivateAjaxBehavior = this.newOnActivateAjaxBehavior(this);
			component.add(this.onActivateAjaxBehavior);
		}
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		// selects (& expands) the active tab (this is not a default behavior)
		response.render(JavaScriptHeaderItem.forScript(String.format("jQuery(function() { %s.select(%d); } );", this.widget(), this.activeTab), this.getToken() + "-select"));
	}

	/**
	 * Selects (and activates) a tab, identified by the index
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the tab's index
	 */
	public void select(int index, AjaxRequestTarget target)
	{
		this.activeTab = index;

		target.appendJavaScript(String.format("%s.select(%d);", this.widget(), this.activeTab));
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		if (this.onSelectAjaxBehavior != null)
		{
			this.setOption("select", this.onSelectAjaxBehavior.getCallbackFunction());
		}

		if (this.onShowAjaxBehavior != null)
		{
			this.setOption("show", this.onShowAjaxBehavior.getCallbackFunction());
		}

		if (this.onActivateAjaxBehavior != null)
		{
			this.setOption("activate", this.onActivateAjaxBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof AbtractTabEvent)
		{
			int index = ((AbtractTabEvent) event).getIndex();
			final List<ITab> tabs = this.getVisibleTabs();

			if (-1 < index && index < tabs.size()) /* index could be unknown depending on options and user action */
			{
				ITab tab = tabs.get(index);

				if (tab instanceof AjaxTab)
				{
					((AjaxTab) tab).load(target);
				}

				if (event instanceof SelectEvent)
				{
					this.onSelect(target, index, tab);
				}

				if (event instanceof ShowEvent)
				{
					this.onShow(target, index, tab);
				}

				if (event instanceof ActivateEvent)
				{
					this.onActivate(target, index, tab);
				}
			}
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'select' javascript callback
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnSelectAjaxBehavior(IJQueryAjaxAware source)
	{
		return new JQueryAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("e"), CallbackParameter.resolved("index", "jQuery(e.item).index()") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new SelectEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'show' javascript callback
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnShowAjaxBehavior(IJQueryAjaxAware source)
	{
		return new JQueryAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("e"), CallbackParameter.resolved("index", "jQuery(e.item).index()") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new ShowEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'activate' javascript callback
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnActivateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new JQueryAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("e"), CallbackParameter.resolved("index", "jQuery(e.item).index()") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new SelectEvent();
			}
		};
	}

	// Event objects //

	/**
	 * Provides a base event object that will be broadcasted by the {@link JQueryAjaxBehavior} callbacks
	 */
	protected abstract static class AbtractTabEvent extends JQueryEvent
	{
		private final int index;

		/**
		 * Constructor
		 */
		public AbtractTabEvent()
		{
			super();

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

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'select' callback
	 */
	protected static class SelectEvent extends AbtractTabEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'show' callback
	 */
	protected static class ShowEvent extends AbtractTabEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'activate' callback
	 */
	protected static class ActivateEvent extends AbtractTabEvent
	{
	}
}
