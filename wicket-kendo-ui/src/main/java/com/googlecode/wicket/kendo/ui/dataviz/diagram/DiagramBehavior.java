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
package com.googlecode.wicket.kendo.ui.dataviz.diagram;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.lang.Args;
import org.json.JSONObject;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoDataSource.HierarchicalDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.datatable.IDataTableListener;

/**
 * Provides a {@value #METHOD} behavior<br>
 * 
 * @author Sebastien Briquet - sebfz1
 */
public abstract class DiagramBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoDiagram";

	private final IDiagramListener listener;
	private HierarchicalDataSource dataSource;

	private JQueryAjaxBehavior onClickAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IDataTableListener}
	 */
	public DiagramBehavior(String selector, IDiagramListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link IDataTableListener}
	 */
	public DiagramBehavior(String selector, Options options, IDiagramListener listener)
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
		this.dataSource = new HierarchicalDataSource(component);
		this.add(this.dataSource);

		// events //
		if (this.listener.isClickEventEnabled())
		{
			this.onClickAjaxBehavior = this.newOnClickAjaxBehavior(this);
			component.add(this.onClickAjaxBehavior);
		}
	}

	// Properties //

	/**
	 * Gets the data-provider behavior's url
	 *
	 * @return the data-provider behavior's url
	 */
	protected abstract CharSequence getProviderUrl();

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		// events //
		if (this.onClickAjaxBehavior != null)
		{
			this.setOption("click", this.onClickAjaxBehavior.getCallbackFunction());
		}

		// data-source //
		this.onConfigure(this.dataSource);
		this.setOption("dataSource", this.dataSource.getName());

		this.dataSource.setTransportReadUrl(this.getProviderUrl());
	}

	/**
	 * Configure the {@link HierarchicalDataSource} with additional options
	 * 
	 * @param dataSource the {@link HierarchicalDataSource}
	 */
	protected void onConfigure(HierarchicalDataSource dataSource)
	{
		// noop
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ClickEvent)
		{
			this.listener.onClick(target, ((ClickEvent) event).getObject());
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'seriesClick' event
	 * 
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnSeriesClickAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnClickAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnClickAjaxBehavior(source);
	}

	// Classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'seriesClick' event
	 */
	protected static class OnClickAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnClickAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { // lf
					CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("data", "kendo.stringify(e.item.dataItem)") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ClickEvent();
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnClickAjaxBehavior} callback
	 */
	protected static class ClickEvent extends JQueryEvent
	{
		private JSONObject object;

		public ClickEvent()
		{
			String data = RequestCycleUtils.getQueryParameterValue("data").toString();

			this.object = new JSONObject(data);
		}

		public JSONObject getObject()
		{
			return this.object;
		}
	}
}
