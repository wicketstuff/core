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
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;

/**
 * Provides a jQuery accordion behavior.<br>
 * Note, this class has almost the same code as TabsBehavior
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.2.3
 * @since 6.0.1
 */
public abstract class AccordionBehavior extends JQueryUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "accordion";

	private final IAccordionListener listener;
	private JQueryAjaxBehavior onCreateAjaxBehavior = null;
	private JQueryAjaxBehavior onActivateAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IAccordionListener}
	 */
	public AccordionBehavior(String selector, IAccordionListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link IAccordionListener}
	 */
	public AccordionBehavior(String selector, Options options, IAccordionListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");
	}

	// Properties //

	/**
	 * Gets the reference {@link List} of {@link ITab}{@code s}.<br>
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
		List<ITab> list = Generics.newArrayList();

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

		if (this.listener.isCreateEventEnabled())
		{
			this.onCreateAjaxBehavior = this.newOnActivateAjaxBehavior(this);
			component.add(this.onCreateAjaxBehavior);
		}

		if (this.listener.isActivateEventEnabled())
		{
			this.onActivateAjaxBehavior = this.newOnActivateAjaxBehavior(this);
			component.add(this.onActivateAjaxBehavior);
		}
	}

	/**
	 * Activates the selected tab, identified by the index
	 *
	 * @param index the tab's index
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void activate(int index, IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(this.$("'option'", "'active'", index));
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		if (this.onCreateAjaxBehavior != null)
		{
			this.setOption("create", this.onCreateAjaxBehavior.getCallbackFunction());
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
		if (event instanceof ActivateEvent)
		{
			int index = ((ActivateEvent) event).getIndex();
			final List<ITab> tabs = this.getVisibleTabs();

			if (-1 < index && index < tabs.size()) /* index could be unknown depending on options and user action */
			{
				ITab tab = tabs.get(index);

				if (tab instanceof AjaxTab)
				{
					((AjaxTab) tab).load(target);
				}

				this.listener.onActivate(target, index, tab);
			}
		}
	}

	// Factories //

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
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'activate' event
	 */
	protected static class OnActivateAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnActivateAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.resolved("index", "jQuery(event.target).accordion('option', 'active')") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ActivateEvent();
		}
	}

	// Events classes //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnActivateAjaxBehavior} callback
	 */
	protected static class ActivateEvent extends JQueryEvent
	{
		private final int index;

		/**
		 * Constructor
		 */
		public ActivateEvent()
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
}
