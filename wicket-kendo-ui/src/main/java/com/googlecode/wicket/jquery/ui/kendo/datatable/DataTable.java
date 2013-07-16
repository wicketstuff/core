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

import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.kendo.datatable.column.IColumn;

/**
 * Provides a Kendo UI data-table
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public class DataTable<T> extends WebComponent implements IJQueryWidget, IDataTableListener
{
	private static final long serialVersionUID = 1L;

	/** The behavior that ajax-loads data */
	private AbstractAjaxBehavior sourceBehavior;

	private final Options options;
	private final List<? extends IColumn<T>> columns;
	private final IDataProvider<T> provider;
	private final long rows;

	/**
	 * Constructor
	 * @param id the markup id
	 * @param columns the list of {@link IColumn}
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
	 * @param columns the list of {@link IColumn}
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


	// Properties //
	protected List<ColumnButton> getButtons()
	{
		return Collections.emptyList();
	}


	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.sourceBehavior = this.newDataSourceBehavior(this.columns, this.provider, this.rows));
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

	@Override
	public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
	{
		this.replaceComponentTagBody(markupStream, openTag, ""); //Empty tag body; Fixes #45
	}

	@Override
	public void onClick(AjaxRequestTarget target, ColumnButton button, String value)
	{
	}


	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new DataTableBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<? extends IColumn<T>> getColumns()
			{
				return DataTable.this.columns;
			}

			@Override
			protected long getRowCount()
			{
				return DataTable.this.rows;
			}

			@Override
			protected CharSequence getSourceCallbackUrl()
			{
				return DataTable.this.sourceBehavior.getCallbackUrl();
			}

			// Events //

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				DataTable.this.onConfigure(this);
			}

			@Override
			public void onClick(AjaxRequestTarget target, ColumnButton button, String value)
			{
				DataTable.this.onClick(target, button, value);
			}
		};
	}


	// Factories //
	/**
	 * Gets a new {@link DataSourceBehavior}
	 *
	 * @param columns the list of {@link IColumn}
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 * @return the {@link AbstractAjaxBehavior}
	 */
	protected AbstractAjaxBehavior newDataSourceBehavior(final List<? extends IColumn<T>> columns, final IDataProvider<T> provider, final long rows)
	{
		return new DataSourceBehavior<T>(columns, provider, rows);
	}
}
