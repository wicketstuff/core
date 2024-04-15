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

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.resource.JavaScriptPackageHeaderItem;
import com.googlecode.wicket.kendo.ui.KendoBehaviorFactory;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.datatable.behavior.DataBoundBehavior;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandAjaxBehavior;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandAjaxBehavior.CommandClickEvent;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandButton;
import com.googlecode.wicket.kendo.ui.datatable.button.ToolbarButton;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;

/**
 * Provides a Kendo UI data-table
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public class DataTable<T> extends WebComponent implements IGenericComponent<List<IColumn>, DataTable<T>>, IJQueryWidget, IDataTableListener
{
	private static final long serialVersionUID = 1L;

	private final long rows;

	private final IDataProvider<T> provider;
	private AbstractAjaxBehavior providerBehavior;

	protected final Options options;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param provider the {@link IDataProvider}
	 * @param rows the number of rows per page to be displayed
	 */
	public DataTable(String id, final IDataProvider<T> provider, final long rows)
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
	public DataTable(String id, final IDataProvider<T> provider, final long rows, Options options)
	{
		super(id);

		this.provider = provider;
		this.options = options;
		this.rows = rows;
	}

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
		super(id, columns);

		this.provider = provider;
		this.options = options;
		this.rows = rows;
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new JavaScriptPackageHeaderItem(DataTable.class)); // DataTable.js
	}

	/**
	 * Gets the Kendo UI widget
	 *
	 * @return the jQuery object
	 */
	public String widget()
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
	 * Reloads the {@link DataTable}<br>
	 * Equivalent to {@code handler.add(table)}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @see DataTable#reset(IPartialPageRequestHandler)
	 */
	public void reload(IPartialPageRequestHandler handler)
	{
		this.reload(handler, false);
	}

	/**
	 * Reloads the {@link DataTable}<br>
	 * If {@code reset} is {@code true}, equivalent to {@code #reset(IPartialPageRequestHandler)} + {@code handler.add(table)}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param reset whether to call reset or not
	 * @see DataTable#reset(IPartialPageRequestHandler)
	 */
	public void reload(IPartialPageRequestHandler handler, boolean reset)
	{
		if (reset)
		{
			this.reset(handler);
		}

		handler.add(this);
	}

	/**
	 * Refreshes the widget by reading from the datasource
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void refresh(IPartialPageRequestHandler handler)
	{
		this.refresh(handler, false);
	}

	/**
	 * Refreshes the widget by reading from the datasource
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param reset whether to call reset or not
	 * @see DataTable#reset(IPartialPageRequestHandler)
	 */
	public void refresh(IPartialPageRequestHandler handler, boolean reset)
	{
		if (reset)
		{
			this.reset(handler);
		}

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
		if (this.getModelObject() != null)
		{
			return Collections.unmodifiableList(this.getModelObject());
		}

		return Collections.emptyList();
	}

	/**
	 * Gets the data-provider behavior's url
	 *
	 * @return the data-provider behavior's url
	 */
	protected final CharSequence getCallbackUrl()
	{
		return this.providerBehavior.getCallbackUrl();
	}

	/**
	 * Gets the {@code List} of {@link ToolbarButton}{@code s}
	 * 
	 * @return the {@code List} of {@code ToolbarButton}{@code s}
	 */
	protected List<ToolbarButton> getToolbarButtons()
	{
		return Collections.emptyList();
	}
	
	@Override
	public boolean isSelectable()
	{
		return this.options.get("selectable") != null;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.providerBehavior = this.newDataProviderBehavior(this.getModel(), this.getDataProvider());
		this.add(this.providerBehavior);

		this.add(JQueryWidget.newWidgetBehavior(this)); // cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("sortable", this.getDataProvider() instanceof ISortStateLocator<?>);
		behavior.setOption("autoBind", this.getBehaviors(DataBoundBehavior.class).isEmpty()); // false if DataBoundBehavior is added
		behavior.setOption("dataBound", "datatable_dataBound"); // DataTable.js
		behavior.setOption("edit", "datatable_edit"); // DataTable.js
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
	public void onClick(AjaxRequestTarget target, ToolbarButton button, List<String> values)
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
	public void onChange(AjaxRequestTarget target, JSONArray items)
	{
		// noop
	}

	@Override
	public void onChecked(AjaxRequestTarget target, List<String> selectedKeys) {
		// noop
	}

	@Override
	public void onColumnReorder(AjaxRequestTarget target, int oldIndex, int newIndex, JSONObject column)
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
		return new DataTableBehavior(selector, this.options, this.getModel(), this) {

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected long getRowCount()
			{
				return DataTable.this.getRowCount();
			}

			@Override
			protected CharSequence getProviderUrl()
			{
				return DataTable.this.getCallbackUrl();
			}

			@Override
			protected List<ToolbarButton> getToolbarButtons()
			{
				return DataTable.this.getToolbarButtons();
			}

			// Events //

			@Override
			protected void onConfigure(KendoDataSource dataSource)
			{
				DataTable.this.onConfigure(dataSource);
			}

			// Factories //

			@Override
			protected JQueryAjaxBehavior newCommandAjaxBehavior(IJQueryAjaxAware source, CommandButton button)
			{
				return DataTable.this.newCommandAjaxBehavior(source, button);
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
	 * Gets a new {@link JQueryAjaxBehavior} that will be called by the corresponding table's button.<br>
	 * This method may be overridden to provide additional behaviors
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link CommandClickEvent}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newCommandAjaxBehavior(IJQueryAjaxAware source, CommandButton button)
	{
		return new CommandAjaxBehavior(source, button);
	}
}
