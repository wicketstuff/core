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
package com.googlecode.wicket.jquery.ui.kendo.datatable;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.kendo.KendoAbstractBehavior;
import com.googlecode.wicket.jquery.ui.kendo.datatable.column.IColumn;

/**
 * Provides a Kendo UI data-table
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
//TODO remove 6.8.2-SNAPSHOT in description
public class DataTable<T> extends WebMarkupContainer implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoGrid";

	/** The behavior that ajax-loads data */
	private AbstractAjaxBehavior sourceBehavior;

	private final Options options;
	private final List<? extends IColumn<T>> columns;
	private final IDataProvider<T> provider;
	private final long rows;

	/**
	 * Constructor
	 * @param id the markup id
	 * @param columns
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 */
	public DataTable(String id, final List<? extends IColumn<T>> columns, final IDataProvider<T> provider, final long rows)
	{
		this(id, columns, provider, rows, new Options());
	}

	/**
	 * Main constructor
	 * @param id the markup id
	 * @param columns
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 * @param options the {@link Options}
	 */
	public DataTable(String id, final List<? extends IColumn<T>> columns, final IDataProvider<T> provider, final long rows, Options options)
	{
		super(id);

		this.columns = columns;
		this.provider = provider;
		this.options = options;
		this.rows = rows;
	}


	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.sourceBehavior = this.newDataTableSourceBehavior(this.columns, this.provider, this.rows));
		this.add(JQueryWidget.newWidgetBehavior(this)); //cannot be in ctor as the markupId may be set manually afterward
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
	}


	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new KendoAbstractBehavior(selector, METHOD, this.options) {

			private static final long serialVersionUID = 1L;

			// Events //

			@Override
			public void onConfigure(Component component)
			{
				DataTable.this.onConfigure(this);

				this.options.set("columns", this.newDataColumnArray());
				this.options.set("dataSource", this.newDataSourceObject());
			}

			// Factories //

			/**
			 * Gets a new JSON dataSource object
			 * @return the JSON dataSource object as String
			 */
			private String newDataSourceObject()
			{
				Options options = new Options();
				options.set("type", Options.asString("jsonp"));
				options.set("pageSize", DataTable.this.rows);
				options.set("serverPaging", true);
				options.set("transport", String.format("{ \"read\": \"%s\" }", DataTable.this.sourceBehavior.getCallbackUrl()));
				options.set("schema", "{ \"data\": \"results\", \"total\": \"__count\" }");

				return options.toString();
			}

			/**
			 * Gets a new JSON columns array
			 * @return the JSON columns array as String
			 */
			private String newDataColumnArray()
			{
				StringBuilder builder = new StringBuilder("[ ");

				for (int i = 0; i < DataTable.this.columns.size(); i++)
				{
					if (i > 0) { builder.append(", "); }

					IColumn<?> column = DataTable.this.columns.get(i);

					builder.append("{ ");
					builder.append(Options.QUOTE).append("title").append(Options.QUOTE).append(": ").append(Options.QUOTE).append(column.getTitle()).append(Options.QUOTE);
					builder.append(", ");
					builder.append(Options.QUOTE).append("field").append(Options.QUOTE).append(": ").append(Options.QUOTE).append(column.getField()).append(Options.QUOTE);

					if (column.getWidth() > 0)
					{
						builder.append(", ");
						builder.append(Options.QUOTE).append("width").append(Options.QUOTE).append(": ").append(column.getWidth());
					}
//					builder.append(", ");
//					builder.append(Options.QUOTE).append("format").append(Options.QUOTE).append(": ").append(Options.QUOTE).append(column.getFormat()).append(Options.QUOTE);

					builder.append(" }");
				}

				return builder.append(" ]").toString();
			}
		};
	}


	// Factories //

	/**
	 * Gets a new {@link AutoCompleteSourceBehavior}
	 * @param columns TODO javadoc
	 * @param provider
	 * @param rows
	 * @return the {@link AutoCompleteSourceBehavior}
	 */
	protected AbstractAjaxBehavior newDataTableSourceBehavior(final List<? extends IColumn<T>> columns, final IDataProvider<T> provider, final long rows)
	{
		return new DataTableSourceBehavior<T>(columns, provider, rows);
	}
}
