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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONString;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryGenericContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.behavior.AjaxCallbackBehavior;
import com.googlecode.wicket.kendo.ui.KendoBehaviorFactory;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.dataviz.series.Series;

/**
 * Provides a Kendo UI chart
 *
 * @param <T> the model object type. It is recommended that the object type implements {@link JSONString}
 * @author Sebastien Briquet - sebfz1
 */
public class Chart<T> extends JQueryGenericContainer<List<T>> implements IChartListener // NOSONAR
{
	private static final long serialVersionUID = 1L;

	protected final Options options;
	protected final List<Series> series;

	/** The behavior that ajax-loads data */
	private AjaxCallbackBehavior modelBehavior;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param provider the {@link IDataProvider}
	 */
	public Chart(String id, final List<Series> series)
	{
		this(id, series, new Options());
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param provider the {@link IDataProvider}
	 * @param options the {@link Options}
	 */
	public Chart(String id, final List<Series> series, Options options)
	{
		super(id);

		this.series = series;
		this.options = options;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param columns the list of {@link IColumn}
	 * @param provider the {@link IDataProvider}
	 */
	public Chart(String id, List<T> data, final List<Series> series)
	{
		this(id, Model.ofList(data), series, new Options());
	}

	/**
	 * constructor
	 *
	 * @param id the markup id
	 * @param columns the list of {@link IColumn}
	 * @param provider the {@link IDataProvider}
	 * @param options the {@link Options}
	 */
	public Chart(String id, List<T> data, final List<Series> series, Options options)
	{
		this(id, Model.ofList(data), series, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param columns the list of {@link IColumn}
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 */
	public Chart(String id, final IModel<List<T>> model, final List<Series> series)
	{
		this(id, model, series, new Options());
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param columns the list of {@link IColumn}
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 * @param options the {@link Options}
	 */
	public Chart(String id, final IModel<List<T>> model, final List<Series> series, Options options)
	{
		super(id, model);

		this.series = series;
		this.options = options;
	}

	// Methods //

	/**
	 * Gets the Kendo UI widget
	 *
	 * @return the jQuery object
	 */
	public String widget()
	{
		return KendoUIBehavior.widget(this, ChartBehavior.METHOD);
	}

	/**
	 * Shows the {@link Chart}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public final void show(IPartialPageRequestHandler handler)
	{
		this.onShow(handler);

		KendoBehaviorFactory.show(handler, this);
	}

	/**
	 * Hides the {@link Chart}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public final void hide(IPartialPageRequestHandler handler)
	{
		KendoBehaviorFactory.hide(handler, this);

		this.onHide(handler);
	}

	/**
	 * Reloads the {@link Chart}<br>
	 * Equivalent to {@code handler.add(table)}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void reload(IPartialPageRequestHandler handler)
	{
		handler.add(this);
	}

	/**
	 * Refreshes the widget by reading from the datasource
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void refresh(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("var $w = %s; if ($w) { $w.dataSource.read(); }", this.widget()));
	}

	// Properties //

	/**
	 * Gets the read-only {@link List} of {@link Series}
	 *
	 * @return the {@code List} of {@code Series}
	 */
	public final List<Series> getSeries()
	{
		return this.series;
	}

	/**
	 * Gets the data-provider behavior's url
	 *
	 * @return the data-provider behavior's url
	 */
	protected final CharSequence getCallbackUrl()
	{
		return this.modelBehavior.getCallbackUrl();
	}

	@Override
	public boolean isSeriesClickEventEnabled()
	{
		return false;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.modelBehavior = this.newChartModelBehavior(this.getModel());
		this.add(this.modelBehavior);

		this.add(JQueryWidget.newWidgetBehavior(this)); // cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// noop
	}

	/**
	 * Configure the {@link KendoDataSource} with additional options
	 * 
	 * @param dataSource the {@link KendoDataSource}
	 */
	protected void onConfigure(KendoDataSource dataSource)
	{
		// show loading indicator //
		String selector = JQueryWidget.getSelector(this);
		dataSource.set("requestStart", String.format("function () { kendo.ui.progress(jQuery('%s'), true); }", selector));
		dataSource.set("requestEnd", String.format("function () { kendo.ui.progress(jQuery('%s'), false); }", selector));
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	/**
	 * Triggered when the {@link Chart} shows
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void onShow(IPartialPageRequestHandler handler)
	{
		// noop
	}

	/**
	 * Triggered when the {@link Chart} hides
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void onHide(IPartialPageRequestHandler handler)
	{
		// noop
	}

	@Override
	public void onSeriesClick(AjaxRequestTarget target, String seriesName, String seriesField, String category, long value)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ChartBehavior(selector, this.options, this.series, this) { // NOSONAR

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected CharSequence getProviderUrl()
			{
				return Chart.this.getCallbackUrl();
			}

			// Events //

			@Override
			protected void onConfigure(KendoDataSource dataSource)
			{
				Chart.this.onConfigure(dataSource);
			}
		};
	}

	// Factories //

	/**
	 * Gets a new {@link ChartModelBehavior}
	 *
	 * @param columns the list of {@link IColumn}
	 * @param provider the {@link IDataProvider}
	 * @return the {@link AbstractAjaxBehavior}
	 */
	protected AjaxCallbackBehavior newChartModelBehavior(final IModel<List<T>> model)
	{
		return new ChartModelBehavior<T>(model);
	}
}
