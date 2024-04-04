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
package com.googlecode.wicket.kendo.ui.dataviz.chart;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.github.openjson.JSONString;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryGenericContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.behavior.AjaxCallbackBehavior;
import com.googlecode.wicket.jquery.core.behavior.ListModelBehavior;
import com.googlecode.wicket.jquery.core.converter.IJsonConverter;
import com.googlecode.wicket.jquery.core.converter.JsonConverter;
import com.googlecode.wicket.kendo.ui.KendoBehaviorFactory;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.dataviz.chart.series.Series;

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

	private final IJsonConverter<T> converter;
	private AjaxCallbackBehavior modelBehavior; // loads data

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param series the {@code List} of {@link Series}
	 */
	public Chart(String id, final List<Series> series)
	{
		this(id, series, new Options(), new JsonConverter<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param series the {@code List} of {@link Series}
	 * @param options the {@link Options}
	 */
	public Chart(String id, final List<Series> series, Options options)
	{
		this(id, series, options, new JsonConverter<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param series the {@code List} of {@link Series}
	 * @param converter the {@link IJsonConverter}
	 */
	public Chart(String id, final List<Series> series, IJsonConverter<T> converter)
	{
		this(id, series, new Options(), converter);
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param series the {@code List} of {@link Series}
	 * @param options the {@link Options}
	 * @param converter the {@link IJsonConverter}
	 */
	public Chart(String id, final List<Series> series, Options options, IJsonConverter<T> converter)
	{
		super(id);

		this.series = series;
		this.options = options;
		this.converter = converter;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param data the list of data
	 * @param series the {@code List} of {@link Series}
	 */
	public Chart(String id, List<T> data, final List<Series> series)
	{
		this(id, Model.ofList(data), series, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param data the list of data
	 * @param series the {@code List} of {@link Series}
	 * @param options the {@link Options}
	 */
	public Chart(String id, List<T> data, final List<Series> series, Options options)
	{
		this(id, Model.ofList(data), series, options, new JsonConverter<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param data the list of data
	 * @param series the {@code List} of {@link Series}
	 * @param converter the {@link IJsonConverter}
	 */
	public Chart(String id, List<T> data, final List<Series> series, IJsonConverter<T> converter)
	{
		this(id, Model.ofList(data), series, new Options(), converter);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param data the list of data
	 * @param series the {@code List} of {@link Series}
	 * @param options the {@link Options}
	 * @param converter the {@link IJsonConverter}
	 */
	public Chart(String id, List<T> data, final List<Series> series, Options options, IJsonConverter<T> converter)
	{
		this(id, Model.ofList(data), series, options, converter);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of data
	 * @param series the {@code List} of {@link Series}
	 */
	public Chart(String id, final IModel<List<T>> model, final List<Series> series)
	{
		this(id, model, series, new Options(), new JsonConverter<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of data
	 * @param series the {@code List} of {@link Series}
	 * @param options the {@link Options}
	 */
	public Chart(String id, final IModel<List<T>> model, final List<Series> series, Options options)
	{
		this(id, model, series, options, new JsonConverter<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of data
	 * @param series the {@code List} of {@link Series}
	 * @param converter the {@link IJsonConverter}
	 */
	public Chart(String id, final IModel<List<T>> model, final List<Series> series, IJsonConverter<T> converter)
	{
		this(id, model, series, new Options(), converter);
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param model the list model of data
	 * @param series the {@code List} of {@link Series}
	 * @param options the {@link Options}
	 * @param converter the {@link IJsonConverter}
	 */
	public Chart(String id, final IModel<List<T>> model, final List<Series> series, Options options, IJsonConverter<T> converter)
	{
		super(id, model);

		this.series = series;
		this.options = options;
		this.converter = converter;
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

	/**
	 * Gets the {@link IJsonConverter}
	 * 
	 * @return the {@link IJsonConverter}
	 */
	protected final IJsonConverter<T> getConverter()
	{
		return this.converter;
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

		this.modelBehavior = this.newListModelBehavior(this.getModel(), this.getConverter());
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
	 * Gets a new {@link AjaxCallbackBehavior}
	 *
	 * @param model the @{@code List} {@link Model}
	 * @param converter the {@link IJsonConverter}
	 * @return a new {@link ListModelBehavior}, by default
	 */
	protected AjaxCallbackBehavior newListModelBehavior(final IModel<List<T>> model, IJsonConverter<T> converter)
	{
		return new ListModelBehavior<T>(model, converter);
	}
}
