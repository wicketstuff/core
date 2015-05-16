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
import com.googlecode.wicket.kendo.ui.widget.tabs.AjaxTab;

/**
 * Provides a Kendo UI kendoPanelBar behavior.
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.19.0
 * @since 7.0.0
 */
public abstract class AccordionBehavior extends KendoUIBehavior implements IJQueryAjaxAware, IAccordionListener
{
	private static final long serialVersionUID = 1L;

	static final String METHOD = "kendoPanelBar";
	private static final int TAB_NONE = -1;

	int tabIndex = TAB_NONE;

	private JQueryAjaxBehavior onSelectAjaxBehavior = null;
	private JQueryAjaxBehavior onActivateAjaxBehavior = null;
	private JQueryAjaxBehavior onExpandAjaxBehavior = null;
	private JQueryAjaxBehavior onCollapseAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public AccordionBehavior(String selector)
	{
		super(selector, METHOD);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public AccordionBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	// Properties //

	/**
	 * Gets the reference {@link List} of {@link ITab}{@code s}.<br/>
	 * Usually the model object of the component on which this {@link AccordionBehavior} is bound to.
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

	/**
	 * Gets the jQuery 'select' and 'expand' statement
	 *
	 * @param index the visible tab's index
	 * @return the jQuery statement
	 */
	private String getSelectStatement(int index)
	{
		return String.format("var $widget = %s, $item = jQuery('li:nth-child(%d)'); $widget.select($item); $widget.expand($item);", this.widget(), index + 1);
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

		if (this.isActivateEventEnabled())
		{
			this.onActivateAjaxBehavior = this.newOnActivateAjaxBehavior(this);
			component.add(this.onActivateAjaxBehavior);
		}

		if (this.isExpandEventEnabled())
		{
			this.onExpandAjaxBehavior = this.newOnExpandAjaxBehavior(this);
			component.add(this.onExpandAjaxBehavior);
		}

		if (this.isCollapseEventEnabled())
		{
			this.onCollapseAjaxBehavior = this.newOnCollapseAjaxBehavior(this);
			component.add(this.onCollapseAjaxBehavior);
		}
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		// selects (& expands) the active tab index
		if (this.tabIndex != TAB_NONE)
		{
			response.render(JavaScriptHeaderItem.forScript(String.format("jQuery(function() { %s } );", this.getSelectStatement(this.tabIndex)), this.getToken() + "-select"));
		}
	}

	/**
	 * Selects and expands a tab, identified by its index<br/>
	 * <b>Warning:</b> the index is related to visible tabs only
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the visible tab's index
	 */
	public void select(int index, AjaxRequestTarget target)
	{
		this.tabIndex = index;

		target.appendJavaScript(this.getSelectStatement(this.tabIndex));
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

		if (this.onActivateAjaxBehavior != null)
		{
			this.setOption("activate", this.onActivateAjaxBehavior.getCallbackFunction());
		}

		if (this.onExpandAjaxBehavior != null)
		{
			this.setOption("expand", this.onExpandAjaxBehavior.getCallbackFunction());
		}

		if (this.onCollapseAjaxBehavior != null)
		{
			this.setOption("collapse", this.onCollapseAjaxBehavior.getCallbackFunction());
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

				if (event instanceof ActivateEvent)
				{
					this.onActivate(target, index, tab);
				}

				if (event instanceof ExpandEvent)
				{
					this.onExpand(target, index, tab);
				}

				if (event instanceof CollapseEvent)
				{
					this.onCollapse(target, index, tab);
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
				return new ActivateEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'expand' javascript callback
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnExpandAjaxBehavior(IJQueryAjaxAware source)
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
				return new ExpandEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'collapse' javascript callback
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnCollapseAjaxBehavior(IJQueryAjaxAware source)
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
				return new CollapseEvent();
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
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'activate' callback
	 */
	protected static class ActivateEvent extends AbtractTabEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'expand' callback
	 */
	protected static class ExpandEvent extends AbtractTabEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'collapse' callback
	 */
	protected static class CollapseEvent extends AbtractTabEvent
	{
	}
}
