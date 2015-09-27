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
package com.googlecode.wicket.kendo.ui.form.autocomplete;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a {@value #METHOD} behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class AutoCompleteBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "kendoAutoComplete";

	private final IAutoCompleteListener listener;
	private JQueryAjaxBehavior onSelectAjaxBehavior = null;

	private KendoDataSource dataSource;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IAutoCompleteListener}
	 */
	public AutoCompleteBehavior(String selector, IAutoCompleteListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link IAutoCompleteListener}
	 */
	public AutoCompleteBehavior(String selector, Options options, IAutoCompleteListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// data source //
		this.dataSource = new KendoDataSource("datasource" + this.selector);
		this.dataSource.set("serverFiltering", true); // important
		this.add(this.dataSource);

		// ajax behaviors //
		this.onSelectAjaxBehavior = this.newOnSelectAjaxBehavior(this);
		component.add(this.onSelectAjaxBehavior);
	}

	// Properties //

	protected abstract CharSequence getChoiceCallbackUrl();

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		// options //
		this.setOption("autoBind", true); // immutable
		this.setOption("dataSource", this.dataSource.getName());
		this.setOption("select", this.onSelectAjaxBehavior.getCallbackFunction());

		// data source //
		this.dataSource.setTransportRead(Options.asString(this.getChoiceCallbackUrl()));
	}

	// IJQueryAjaxAware //

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof SelectEvent)
		{
			this.listener.onSelect(target, ((SelectEvent) event).getIndex());
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'select' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnSelectAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnSelectAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnSelectAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'select' event
	 */
	protected static class OnSelectAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnSelectAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("index", "e.item.index()"), // lf
					CallbackParameter.resolved("value", "e.item.text") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new SelectEvent();
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnSelectAjaxBehavior} callback
	 */
	protected static class SelectEvent extends JQueryEvent
	{
		private final int index;
		private final String value;

		public SelectEvent()
		{
			this.index = RequestCycleUtils.getQueryParameterValue("index").toInt(-1);
			this.value = RequestCycleUtils.getQueryParameterValue("value").toString();
		}

		public int getIndex()
		{
			return this.index;
		}

		public String getValue()
		{
			return this.value;
		}
	}
}
