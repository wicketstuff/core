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
import com.googlecode.wicket.kendo.ui.ajax.OnChangeAjaxBehavior;
import com.googlecode.wicket.kendo.ui.ajax.OnChangeAjaxBehavior.ChangeEvent;

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
	private JQueryAjaxBehavior onSelectAjaxBehavior;
	private JQueryAjaxBehavior onChangeAjaxBehavior = null;

	private KendoDataSource dataSource;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IAutoCompleteListener}
	 */
	protected AutoCompleteBehavior(String selector, IAutoCompleteListener listener)
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
	protected AutoCompleteBehavior(String selector, Options options, IAutoCompleteListener listener)
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
		this.dataSource = new KendoDataSource(component);
		this.add(this.dataSource);

		// ajax behaviors //
		this.onSelectAjaxBehavior = this.newOnSelectAjaxBehavior(this);
		component.add(this.onSelectAjaxBehavior);

		if (this.listener.isChangeEventEnabled())
		{
			this.onChangeAjaxBehavior = new OnChangeAjaxBehavior(this);
			component.add(this.onChangeAjaxBehavior);
		}
	}

	// Properties //

	@Override
	public boolean isEnabled(Component component)
	{
		return component.isEnabledInHierarchy();
	}

	protected abstract CharSequence getDataSourceUrl();

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		this.setOption("autoBind", true); // immutable

		this.setOption("select", this.onSelectAjaxBehavior.getCallbackFunction());

		if (this.onChangeAjaxBehavior != null)
		{
			this.setOption("change", this.onChangeAjaxBehavior.getCallbackFunction());
		}

		// data source //
		this.setOption("dataSource", this.dataSource.getName());

		if (this.isEnabled(component))
		{
			this.dataSource.set("serverFiltering", true); // important
			this.dataSource.setTransportReadUrl(this.getDataSourceUrl());
		}

		this.onConfigure(this.dataSource); // last chance to set options
		super.onConfigure(component);
	}

	/**
	 * Configure the {@link KendoDataSource} with additional options
	 * 
	 * @param dataSource the {@link KendoDataSource}
	 */
	protected void onConfigure(KendoDataSource dataSource)
	{
		// noop
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ChangeEvent)
		{
			this.listener.onChange(target, ((ChangeEvent) event).getValue());
		}

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
	 * @return a new {@code OnSelectAjaxBehavior} by default
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
					CallbackParameter.resolved("index", "e.item.index()") };
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

		public SelectEvent()
		{
			this.index = RequestCycleUtils.getQueryParameterValue("index").toInt(-1);
		}

		public int getIndex()
		{
			return this.index;
		}
	}
}
