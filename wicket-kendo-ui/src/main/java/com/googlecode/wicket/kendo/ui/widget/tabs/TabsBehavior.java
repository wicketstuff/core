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
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a {@value #METHOD} behavior<br>
 * Note, this class has almost the same code as AccordionBehavior
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.19.0
 */
public abstract class TabsBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoTabStrip";
	public static final int DEFAULT_TAB_INDEX = 0;

	int tabIndex = DEFAULT_TAB_INDEX;

	private final ITabsListener listener;
	private JQueryAjaxBehavior onSelectAjaxBehavior = null;
	private JQueryAjaxBehavior onShowAjaxBehavior = null;
	private JQueryAjaxBehavior onActivateAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the ITabsListener
	 */
	public TabsBehavior(String selector, ITabsListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the ITabsListener
	 */
	public TabsBehavior(String selector, Options options, ITabsListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");
	}

	// Properties //

	/**
	 * Gets the reference {@link List} of {@link ITab}{@code s}.<br>
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

		if (this.listener.isSelectEventEnabled())
		{
			this.onSelectAjaxBehavior = this.newOnSelectAjaxBehavior(this);
			component.add(this.onSelectAjaxBehavior);
		}

		if (this.listener.isShowEventEnabled())
		{
			this.onShowAjaxBehavior = this.newOnShowAjaxBehavior(this);
			component.add(this.onShowAjaxBehavior);
		}

		if (this.listener.isActivateEventEnabled())
		{
			this.onActivateAjaxBehavior = this.newOnActivateAjaxBehavior(this);
			component.add(this.onActivateAjaxBehavior);
		}
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		// selects (& expands) the active tab (not a default behavior)
		this.renderOnDomReadyScript(String.format("%s.select(%d);", this.widget(), this.tabIndex), response);
	}

	/**
	 * Selects (and activates) a tab, identified by the index
	 *
	 * @param index the tab's index
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void select(int index, IPartialPageRequestHandler handler)
	{
		this.tabIndex = index;

		handler.appendJavaScript(String.format("%s.select(%d);", this.widget(), this.tabIndex));
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
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

		super.onConfigure(component);
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof TabEvent)
		{
			int index = ((TabEvent) event).getIndex();
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
					this.listener.onSelect(target, index, tab);
				}

				if (event instanceof ShowEvent)
				{
					this.listener.onShow(target, index, tab);
				}

				if (event instanceof ActivateEvent)
				{
					this.listener.onActivate(target, index, tab);
				}
			}
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'select' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnSelectAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnSelectAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnSelectAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'show' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnShowAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnShowAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnShowAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'activate' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnActivateAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnActivateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnActivateAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides an abstract {@link JQueryAjaxBehavior} that aims to be wired to the {@code TabsBehavior} events
	 */
	abstract static class TabAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public TabAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), CallbackParameter.resolved("index", "jQuery(e.item).index()") };
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'select' event
	 */
	protected static class OnSelectAjaxBehavior extends TabAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnSelectAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new SelectEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'show' event
	 */
	protected static class OnShowAjaxBehavior extends TabAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnShowAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ShowEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'activate' event
	 */
	protected static class OnActivateAjaxBehavior extends TabAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnActivateAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new SelectEvent();
		}
	}

	// Event objects //

	/**
	 * Provides a base class for {@link TabsBehavior} event objects
	 */
	abstract static class TabEvent extends JQueryEvent
	{
		private final int index;

		/**
		 * Constructor
		 */
		public TabEvent()
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
	 * Provides an event object that will be broadcasted by the {@link OnSelectAjaxBehavior} callback
	 */
	protected static class SelectEvent extends TabEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnShowAjaxBehavior} callback
	 */
	protected static class ShowEvent extends TabEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnActivateAjaxBehavior} callback
	 */
	protected static class ActivateEvent extends TabEvent
	{
	}
}
