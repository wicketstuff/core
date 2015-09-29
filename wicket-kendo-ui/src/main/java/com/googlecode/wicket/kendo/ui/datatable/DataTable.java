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
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.kendo.ui.KendoBehaviorFactory;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.datatable.CommandAjaxBehavior.ClickEvent;
import com.googlecode.wicket.kendo.ui.datatable.behavior.DataBoundBehavior;
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
	private AbstractAjaxBehavior providerBehavior;

	private final Options options;
	private final IModel<List<IColumn>> columns;
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
	public DataTable(String id, final List<IColumn> columns, final IDataProvider<T> provider, final long rows)
	{
		this(id, Model.ofList(columns), provider, rows, new Options());
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
	public DataTable(String id, final List<IColumn> columns, final IDataProvider<T> provider, final long rows, Options options)
	{
		this(id, Model.ofList(columns), provider, rows, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param columns the list of {@link IColumn}
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 */
	public DataTable(String id, final IModel<List<IColumn>> columns, final IDataProvider<T> provider, final long rows)
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
	public DataTable(String id, final IModel<List<IColumn>> columns, final IDataProvider<T> provider, final long rows, Options options)
	{
		super(id);

		this.columns = columns;
		this.provider = provider;
		this.options = options;
		this.rows = rows;
	}

	// Methods //

	/**
	 * Gets the Kendo UI widget
	 *
	 * @return the jQuery object
	 */
	protected String widget()
	{	
		return KendoUIBehavior.widget(this, DataTableBehavior.METHOD);
	}

	/**
	 * Shows the {@link DataTable}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public final void show(IPartialPageRequestHandler handler)
	{
		this.onShow(handler);

		KendoBehaviorFactory.show(handler, this);
	}

	/**
	 * Hides the {@link DataTable}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public final void hide(IPartialPageRequestHandler handler)
	{
		KendoBehaviorFactory.hide(handler, this);

		this.onHide(handler);
	}

	/**
	 * Resets the dataSource to the first page
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void reset(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("var $w = %s; if ($w) { $w.dataSource.page(1); }", this.widget()));
	}

	/**
	 * Resets & reloads the {@link DataTable}<br/>
	 * Equivalent to {@code handler.add(table);}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void reload(IPartialPageRequestHandler handler)
	{
		this.reset(handler);

		handler.add(this);
	}

	/**
	 * Reloads current data and refreshes the {@link DataTable}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void refresh(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("var $w = %s; if ($w) { $w.dataSource.read(); }", this.widget()));
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
	 * Gets the read-only {@link List} of {@link IColumn}{@code s}
	 *
	 * @return the {@link List} of {@link IColumn}{@code s}
	 */
	public final List<IColumn> getColumns()
	{
		return Collections.unmodifiableList(this.columns.getObject());
	}

	/**
	 * Gets the data-provider behavior's url
	 *
	 * @return the data-provider behavior's url
	 */
	protected final CharSequence getProviderCallbackUrl()
	{
		return this.providerBehavior.getCallbackUrl();
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.providerBehavior = this.newDataProviderBehavior(this.columns, this.provider);
		this.add(this.providerBehavior);

		this.add(JQueryWidget.newWidgetBehavior(this)); // cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("sortable", this.provider instanceof ISortStateLocator<?>);
		behavior.setOption("autoBind", this.getBehaviors(DataBoundBehavior.class).isEmpty()); // false if DataBoundBehavior is added
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
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void onShow(IPartialPageRequestHandler handler)
	{
		// noop
	}

	/**
	 * Triggered when the {@link DataTable} hides
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void onHide(IPartialPageRequestHandler handler)
	{
		// noop
	}

	@Override
	public void onClick(AjaxRequestTarget target, String button, List<String> values)
	{
		// noop
	}

	@Override
	public void onClick(AjaxRequestTarget target, CommandButton button, String value)
	{
		// noop
	}

	@Override
	public void onCancel(AjaxRequestTarget target)
	{
		// noop
	}

	@Override
	public void onCreate(AjaxRequestTarget target, JSONObject object)
	{
		// noop
	}

	@Override
	public void onUpdate(AjaxRequestTarget target, JSONObject object)
	{
		// noop
	}

	@Override
	public void onDelete(AjaxRequestTarget target, JSONObject object)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new DataTableBehavior(selector, this.options, this.columns, this) {

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected long getRowCount()
			{
				return DataTable.this.getRowCount();
			}

			@Override
			protected CharSequence getProviderCallbackUrl()
			{
				return DataTable.this.getProviderCallbackUrl();
			}

			// Factories //

			@Override
			protected JQueryAjaxBehavior newToolbarClickAjaxBehavior(IJQueryAjaxAware source)
			{
				return DataTable.this.newToolbarAjaxBehavior(source);
			}

			@Override
			protected JQueryAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, CommandButton button)
			{
				return DataTable.this.newColumnAjaxBehavior(source, button);
			}
		};
	}

	// Factories //

	/**
	 * Gets a new {@link DataProviderBehavior}
	 *
	 * @param columns the list of {@link IColumn}
	 * @param provider the {@link IDataProvider}
	 * @return the {@link AbstractAjaxBehavior}
	 */
	protected AbstractAjaxBehavior newDataProviderBehavior(final IModel<List<IColumn>> columns, final IDataProvider<T> provider)
	{
		return new DataProviderBehavior<T>(columns, provider);
	}

	/**
	 * Gets the {@link JQueryAjaxBehavior} that will be called when the user clicks a toolbar button
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newToolbarAjaxBehavior(IJQueryAjaxAware source)
	{
		return null;
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called by the corresponding table's button.<br/>
	 * This method may be overridden to provide additional behaviors
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link ClickEvent}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newColumnAjaxBehavior(IJQueryAjaxAware source, CommandButton button)
	{
		return new CommandAjaxBehavior(source, button);
	}
}
