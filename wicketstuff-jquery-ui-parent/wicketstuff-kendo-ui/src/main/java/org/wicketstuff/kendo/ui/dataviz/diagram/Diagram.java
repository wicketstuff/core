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
package org.wicketstuff.kendo.ui.dataviz.diagram;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.wicketstuff.jquery.core.JQueryBehavior;
import org.wicketstuff.jquery.core.JQueryGenericContainer;
import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.jquery.core.behavior.AjaxCallbackBehavior;
import org.wicketstuff.jquery.core.behavior.ListModelBehavior;
import org.wicketstuff.jquery.core.converter.IJsonConverter;
import org.wicketstuff.jquery.core.converter.JsonConverter;
import org.wicketstuff.kendo.ui.KendoUIBehavior;
import org.wicketstuff.kendo.ui.KendoDataSource.HierarchicalDataSource;

import com.github.openjson.JSONObject;
import com.github.openjson.JSONString;

/**
 * Provides a Kendo UI diagram
 *
 * @param <T> the model object type. It is recommended that the object type implements {@link JSONString}
 * @author Sebastien Briquet - sebfz1
 */
public class Diagram<T extends IDiagramNode<T>> extends JQueryGenericContainer<List<T>> implements IDiagramListener // NOSONAR
{
	private static final long serialVersionUID = 1L;

	protected final Options options;

	private final IJsonConverter<T> converter;
	private AjaxCallbackBehavior modelBehavior; // loads data

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public Diagram(String id)
	{
		this(id, new Options(), new JsonConverter<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public Diagram(String id, Options options)
	{
		this(id, options, new JsonConverter<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param converter the {@link IJsonConverter}
	 */
	public Diagram(String id, IJsonConverter<T> converter)
	{
		this(id, new Options(), converter);
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 * @param converter the {@link IJsonConverter}
	 */
	public Diagram(String id, Options options, IJsonConverter<T> converter)
	{
		super(id);

		this.options = options;
		this.converter = converter;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param data the list of data
	 */
	public Diagram(String id, List<T> data)
	{
		this(id, new ListModel<T>(data), new Options(), new JsonConverter<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param data the list of data
	 * @param converter the {@link IJsonConverter}
	 */
	public Diagram(String id, List<T> data, IJsonConverter<T> converter)
	{
		this(id, new ListModel<T>(data), new Options(), converter);
	}

	/**
	 * constructor
	 *
	 * @param id the markup id
	 * @param data the list of data
	 * @param options the {@link Options}
	 */
	public Diagram(String id, List<T> data, Options options)
	{
		this(id, new ListModel<T>(data), options, new JsonConverter<T>());
	}

	/**
	 * constructor
	 *
	 * @param id the markup id
	 * @param data the list of data
	 * @param options the {@link Options}
	 * @param converter the {@link IJsonConverter}
	 */
	public Diagram(String id, List<T> data, Options options, IJsonConverter<T> converter)
	{
		this(id, new ListModel<T>(data), options, converter);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of data
	 */
	public Diagram(String id, final IModel<List<T>> model)
	{
		this(id, model, new Options(), new JsonConverter<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of data
	 * @param converter the {@link IJsonConverter}
	 */
	public Diagram(String id, final IModel<List<T>> model, IJsonConverter<T> converter)
	{
		this(id, model, new Options(), converter);
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param model the list model of data
	 * @param options the {@link Options}
	 */
	public Diagram(String id, final IModel<List<T>> model, Options options)
	{
		this(id, model, options, new JsonConverter<T>());
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param model the list model of data
	 * @param options the {@link Options}
	 * @param converter the {@link IJsonConverter}
	 */
	public Diagram(String id, final IModel<List<T>> model, Options options, IJsonConverter<T> converter)
	{
		super(id, model);

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
		return KendoUIBehavior.widget(this, DiagramBehavior.METHOD);
	}

	/**
	 * Reloads the {@link Diagram}<br>
	 * Equivalent to {@code handler.add(table)}
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void reload(AjaxRequestTarget target)
	{
		target.add(this);
	}

	/**
	 * Refreshes the widget by reading from the datasource
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void refresh(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("var $w = %s; if ($w) { $w.dataSource.read(); }", this.widget()));
	}

	// Properties //

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
	public boolean isClickEventEnabled()
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
	 * Configure the {@link HierarchicalDataSource} with additional options
	 * 
	 * @param dataSource the {@link HierarchicalDataSource}
	 */
	protected void onConfigure(HierarchicalDataSource dataSource)
	{
		dataSource.set("schema", "{ model: { children: 'nodes' } }"); // IDiagramNode

		// show loading indicator //
		// TODO move into an Utils class
		String selector = JQueryWidget.getSelector(this);
		dataSource.set("requestStart", String.format("function () { kendo.ui.progress(jQuery('%s'), true); }", selector));
		dataSource.set("requestEnd", String.format("function () { kendo.ui.progress(jQuery('%s'), false); }", selector));
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onClick(AjaxRequestTarget target, JSONObject object)
	{
		this.onClick(target, this.getConverter().toObject(object));
	}

	/**
	 * Triggered when a series is clicked
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param object the converted object
	 */
	public void onClick(AjaxRequestTarget target, T object)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new DiagramBehavior(selector, this.options, this) { // NOSONAR

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected CharSequence getProviderUrl()
			{
				return Diagram.this.getCallbackUrl();
			}

			// Events //

			@Override
			protected void onConfigure(HierarchicalDataSource dataSource)
			{
				Diagram.this.onConfigure(dataSource);
			}
		};
	}

	// Factories //

	/**
	 * Gets a new {@link AbstractAjaxBehavior}
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
