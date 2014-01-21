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
package com.googlecode.wicket.kendo.ui.datatable;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.kendo.ui.KendoBehaviorFactory;
import com.googlecode.wicket.kendo.ui.datatable.ColumnButtonAjaxBehavior.ClickEvent;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;

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
	private final List<? extends IColumn> columns;
	private final IDataProvider<T> provider;
	private final long rows;

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param columns the list of {@link IColumn}
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 */
	public DataTable(String id, final List<? extends IColumn> columns, final IDataProvider<T> provider, final long rows)
	{
		this(id, columns, provider, rows, new Options());
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
	public DataTable(String id, final List<? extends IColumn> columns, final IDataProvider<T> provider, final long rows, Options options)
	{
		super(id);

		this.columns = columns;
		this.provider = provider;
		this.options = options;
		this.rows = rows;
	}

	// Methods //

	/**
	 * Shows the {@link DataTable}
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	public final void show(AjaxRequestTarget target)
	{
		this.onShow(target);

		KendoBehaviorFactory.show(target, this);
	}

	/**
	 * Hides the {@link DataTable}
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	public final void hide(AjaxRequestTarget target)
	{
		KendoBehaviorFactory.hide(target, this);

		this.onHide(target);
	}

	/**
	 * Reloads data and refreshes the {@link DataTable}
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void refresh(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("var grid = jQuery('%s').data('kendoGrid'); grid.dataSource.read(); grid.refresh();", JQueryWidget.getSelector(this)));
	}

	// Properties //

	/**
	 * Gets the {@link IDataProvider}
	 * 
	 * @return the {@link IDataProvider}
	 */
	public IDataProvider<T> getDataProvider()
	{
		return this.provider;
	}

	/**
	 * Gets the number of rows per page to be displayed
	 * 
	 * @return the number of rows per page to be displayed
	 */
	protected final long getRowCount()
	{
		return this.rows;
	}

	/**
	 * Gets the read-only {@link List} of {@link IColumn}<tt>s</tt>
	 * 
	 * @return the {@link List} of {@link IColumn}<tt>s</tt>
	 */
	public final List<? extends IColumn> getColumns()
	{
		return Collections.unmodifiableList(this.columns);
	}

	/**
	 * Get the JSON model of the datasource's schema
	 * 
	 * @return the model, as JSON object
	 */
	protected Options getSchemaModel()
	{
		Options fields = new Options();

		for (IColumn column : this.getColumns())
		{
			if (column.getType() != null)
			{
				fields.set(column.getField(), new Options("type", Options.asString(column.getType())));
			}
		}

		return new Options("fields", fields);
	}

	/**
	 * Gets the data-source behavior's url
	 * 
	 * @return the data-source behavior's url
	 */
	protected final CharSequence getSourceCallbackUrl()
	{
		return this.sourceBehavior.getCallbackUrl();
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.sourceBehavior = this.newDataSourceBehavior(this.columns, this.provider));
		this.add(JQueryWidget.newWidgetBehavior(this)); // cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
	{
		this.replaceComponentTagBody(markupStream, openTag, ""); // Empty tag body; Fixes #45
	}

	/**
	 * Triggered when the {@link DataTable} shows
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void onShow(AjaxRequestTarget target)
	{
		// noop
	}

	/**
	 * Triggered when the {@link DataTable} hides
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void onHide(AjaxRequestTarget target)
	{
		// noop
	}

	@Override
	public void onClick(AjaxRequestTarget target, ColumnButton button, String value)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new DataTableBehavior(selector, this.options, this.columns) {

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected long getRowCount()
			{
				return DataTable.this.getRowCount();
			}

			@Override
			protected Options getSchemaModel()
			{
				return DataTable.this.getSchemaModel();
			}

			@Override
			protected CharSequence getSourceCallbackUrl()
			{
				return DataTable.this.getSourceCallbackUrl();
			}

			// Events //

			@Override
			public void onClick(AjaxRequestTarget target, ColumnButton button, String value)
			{
				DataTable.this.onClick(target, button, value);
			}

			// Factories //
			@Override
			protected ColumnButtonAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, ColumnButton button)
			{
				return DataTable.this.newButtonAjaxBehavior(source, button);
			}
		};
	}

	// Factories //

	/**
	 * Gets a new {@link DataSourceBehavior}
	 * 
	 * @param columns the list of {@link IColumn}
	 * @param provider the {@link IDataProvider}
	 * @return the {@link AbstractAjaxBehavior}
	 */
	protected AbstractAjaxBehavior newDataSourceBehavior(final List<? extends IColumn> columns, final IDataProvider<T> provider)
	{
		return new DataSourceBehavior<T>(columns, provider);
	}

	/**
	 * Gets a new {@link ColumnButtonAjaxBehavior} that will be called by the corresponding {@link ColumnButton}.<br/>
	 * This method may be overridden to provide additional behaviors
	 * 
	 * @param source the {@link IJQueryAjaxAware} source
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link ClickEvent}
	 * @return the {@link ColumnButtonAjaxBehavior}
	 */
	protected ColumnButtonAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, ColumnButton button)
	{
		return new ColumnButtonAjaxBehavior(source, button);
	}
}
