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
package com.googlecode.wicket.kendo.ui.repeater.dataview;

import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.renderer.JsonRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.template.JQueryTemplate;
import com.googlecode.wicket.kendo.ui.KendoDataSource;

/**
 * Provides a simple read-only {@link DataView} with a {@link Pager} panel
 * 
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public class DataViewPanel<T> extends GenericPanel<T> // NOSONAR
{
	private static final long serialVersionUID = 1L;

	private final Options options;

	/** the number of rows to display */
	protected final long rows;

	/** the data-source provider */
	private final IDataProvider<T> provider;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 */
	public DataViewPanel(String id, final IDataProvider<T> provider, final long rows)
	{
		this(id, provider, rows, new Options());
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 * @param options the {@link Options}
	 */
	public DataViewPanel(String id, final IDataProvider<T> provider, final long rows, Options options)
	{
		super(id);

		this.rows = rows;
		this.options = options;
		this.provider = provider;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		final KendoDataSource dataSource = new KendoDataSource("datasource_" + this.getMarkupId());

		this.add(this.newDataView("listView", dataSource, this.provider, this.options));
		this.add(this.newPager("pager", dataSource));
	}

	// Factories //

	/**
	 * Gets a new {@link IJQueryTemplate} to customize the rendering<br/>
	 * The properties used in the template text (ie: ${data.name}) should be of the prefixed by "data."<br/>
	 * <br/>
	 * <b>Note:</b> {@link DataView} uses a {@link JsonRenderer} by default, making {@link IJQueryTemplate#getTextProperties()} not required to override (see {@link JQueryTemplate})
	 *
	 * @return null by default
	 * @see JQueryTemplate
	 */
	protected IJQueryTemplate newTemplate()
	{
		return null;
	}

	/**
	 * Gets a new {@link DataView}
	 * 
	 * @param id the markup id
	 * @param dataSource the {@link KendoDataSource}
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 * @param options the {@link Options}
	 * @return a new {@code DataView}
	 */
	protected DataView<T> newDataView(String id, final KendoDataSource dataSource, IDataProvider<T> provider, Options options)
	{
		return new DataView<T>(id, provider, options) {

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected long getRowCount()
			{
				return DataViewPanel.this.rows;
			}

			// Factories //

			@Override
			protected IJQueryTemplate newTemplate()
			{
				return DataViewPanel.this.newTemplate();
			}

			@Override
			protected KendoDataSource newDataSource(String selector)
			{
				return dataSource;
			}
		};
	}

	/**
	 * Gets a new {@link Pager}
	 * 
	 * @param id the markup id
	 * @param dataSource the {@link KendoDataSource}
	 * @return a new {@code Pager}
	 */
	protected Pager newPager(String id, KendoDataSource dataSource)
	{
		return new Pager(id, dataSource);
	}
}
