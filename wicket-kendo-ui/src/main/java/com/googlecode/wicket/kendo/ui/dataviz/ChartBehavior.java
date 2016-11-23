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
package com.googlecode.wicket.kendo.ui.dataviz;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.datatable.IDataTableListener;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.dataviz.series.Series;

/**
 * Provides a {@value #METHOD} behavior<br>
 * 
 * @author Sebastien Briquet - sebfz1
 */
public abstract class ChartBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoChart";

	private final IChartListener listener;
	private KendoDataSource dataSource;

	private JQueryAjaxBehavior onSeriesClickAjaxBehavior = null;
	private final List<Series> series;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param series the list of {@link IColumn}
	 * @param listener the {@link IDataTableListener}
	 */
	public ChartBehavior(String selector, List<Series> series, IChartListener listener)
	{
		this(selector, new Options(), series, listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param series the list of {@link IColumn}
	 * @param listener the {@link IDataTableListener}
	 */
	public ChartBehavior(String selector, Options options, List<Series> series, IChartListener listener)
	{
		super(selector, METHOD, options);

		this.series = series;
		this.listener = Args.notNull(listener, "listener");
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// data source //
		this.dataSource = new ChartDataSource(component);
		this.add(this.dataSource);

		// events //
		if (this.listener.isSeriesClickEventEnabled())
		{
			this.onSeriesClickAjaxBehavior = this.newOnSeriesClickAjaxBehavior(this);
			component.add(this.onSeriesClickAjaxBehavior);
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
		if (this.onSeriesClickAjaxBehavior != null)
		{
			this.setOption("seriesClick", this.onSeriesClickAjaxBehavior.getCallbackFunction());
		}

		// series //
		this.setOption("series", new JSONArray(this.series).toString());

		// data-source //
		this.onConfigure(this.dataSource);
		this.setOption("dataSource", this.dataSource.getName());

		this.dataSource.setTransportReadUrl(this.getProviderUrl());
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
		if (event instanceof SeriesClickEvent)
		{
			SeriesClickEvent e = (SeriesClickEvent) event;
			this.listener.onSeriesClick(target, e.getSeriesField(), e.getSeriesName(), e.getCategory(), e.getValue());
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'seriesClick' event
	 * 
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnSeriesClickAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnSeriesClickAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnSeriesClickAjaxBehavior(source);
	}

	// Classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'seriesClick' event
	 */
	protected static class OnSeriesClickAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnSeriesClickAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("value", "e.value"), // lf
					CallbackParameter.resolved("seriesName", "e.series.name"), // lf
					CallbackParameter.resolved("seriesField", "e.series.field"), // lf
					CallbackParameter.resolved("category", "e.category") // lf
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new SeriesClickEvent();
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnSeriesClickAjaxBehavior} callback
	 */
	protected static class SeriesClickEvent extends JQueryEvent
	{
		private final long value;
		private final String category;
		private final String seriesName;
		private final String seriesField;

		public SeriesClickEvent()
		{
			this.value = RequestCycleUtils.getQueryParameterValue("value").toOptionalLong();
			this.category = RequestCycleUtils.getQueryParameterValue("category").toString();
			this.seriesField = RequestCycleUtils.getQueryParameterValue("seriesField").toString();
			this.seriesName = RequestCycleUtils.getQueryParameterValue("seriesName").toString();
		}

		public long getValue()
		{
			return this.value;
		}

		public String getCategory()
		{
			return this.category;
		}

		public String getSeriesName()
		{
			return this.seriesName;
		}

		public String getSeriesField()
		{
			return this.seriesField;
		}
	}
}
