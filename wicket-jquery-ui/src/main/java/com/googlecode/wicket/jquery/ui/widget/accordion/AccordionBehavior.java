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
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.extensions.markup.html.tabs.ITab;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;

/**
 * Provides a jQuery accordion behavior.<br/>
 * Note, this class has almost the same code as TabsBehavior
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.2.3
 * @since 6.0.1
 */
public abstract class AccordionBehavior extends JQueryBehavior implements IJQueryAjaxAware, IAccordionListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "accordion";

	private JQueryAjaxBehavior activateEventBehavior;

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
	 * Gets the reference list of tabs.<br/>
	 * Usually the model object of the component on which this {@link AccordionBehavior} is bound to.
	 *
	 * @return a non-null {@link List}
	 */
	protected abstract List<ITab> getTabs();


	// Methods //
	@Override
	public void bind(Component component)
	{
		super.bind(component);

		component.add(this.activateEventBehavior = this.newActivateEventBehavior());
	}

	/**
	 * Activates the selected tab, identified by the index
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the tab's index
	 */
	public void activate(int index, AjaxRequestTarget target)
	{
		target.appendJavaScript(this.$("'option'", "'active'", index));
	}

	// Events //
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("create", this.activateEventBehavior.getCallbackFunction());
		this.setOption("activate", this.activateEventBehavior.getCallbackFunction());
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ActivateEvent)
		{
			int index = ((ActivateEvent)event).getIndex();

			if (-1 < index && index < this.getTabs().size()) /* index could be unknown depending on options and user action */
			{
				ITab tab = this.getTabs().get(index);

				if (tab instanceof AjaxTab)
				{
					((AjaxTab) tab).load(target);
				}

				this.onActivate(target, index, tab);
			}
		}
	}

	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'activate' callback
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newActivateEventBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] {
						CallbackParameter.context("event"),
						CallbackParameter.context("ui"),
						CallbackParameter.resolved("index", "jQuery(event.target).accordion('option', 'active')")
						};
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new ActivateEvent();
			}
		};
	}


	// Events classes //
	/**
	 * Base class for accordion event objects
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
