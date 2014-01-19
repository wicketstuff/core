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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.kendo.ui.KendoAbstractBehavior;
import com.googlecode.wicket.kendo.ui.datatable.ColumnButtonAjaxBehavior.ClickEvent;
import com.googlecode.wicket.kendo.ui.datatable.column.CommandsColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;

/**
 * Provides the Kendo UI data-table behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class DataTableBehavior extends KendoAbstractBehavior implements IJQueryAjaxAware, IDataTableListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoGrid";

	protected final List<? extends IColumn> columns;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param columns the list of {@link IColumn}
	 */
	public DataTableBehavior(String selector, List<? extends IColumn> columns)
	{
		this(selector, new Options(), columns);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param columns the list of {@link IColumn}
	 */
	public DataTableBehavior(String selector, Options options, List<? extends IColumn> columns)
	{
		super(selector, METHOD, options);

		this.columns = columns;
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// buttons //
		for (ColumnButton button : this.getColumnButtons())
		{
			component.add(this.newButtonAjaxBehavior(this, button));
		}
	}

	// Properties //

	/**
	 * Gets the row count
	 *
	 * @return the row count
	 */
	protected abstract long getRowCount();

	/**
	 * Gets the data-source behavior's url
	 *
	 * @return the data-source behavior's url
	 */
	protected abstract CharSequence getSourceCallbackUrl();

	/**
	 * Get the JSON model of the datasource's schema
	 *
	 * @return the model, as JSON string
	 */
	protected Options getSchemaModel()
	{
		return new Options();
	}

	/**
	 * Gets the read-only {@link List} of {@link ColumnButton}
	 *
	 * @return the {@link List} of {@link ColumnButton}
	 */
	protected List<ColumnButton> getColumnButtons()
	{
		for (IColumn column : this.columns)
		{
			if (column instanceof CommandsColumn)
			{
				return ((CommandsColumn) column).getButtons();
			}
		}

		return Collections.emptyList();
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		Options source = new Options();
		Options schema = new Options();

		// schema //
		schema.set("data", Options.asString("results"));
		schema.set("total", Options.asString("__count"));
		schema.set("model", this.getSchemaModel());

		// source //
		source.set("type", Options.asString("jsonp"));
		source.set("pageSize", this.getRowCount());
		source.set("serverPaging", true);
		source.set("serverSorting", true);
		source.set("serverFiltering", true);
		source.set("transport", new Options("read", Options.asString(this.getSourceCallbackUrl())));
		source.set("schema", schema);

		this.setOption("dataSource", source);

		// columns //
		StringBuilder builder = new StringBuilder("[ ");

		for (int i = 0; i < this.columns.size(); i++)
		{
			IColumn column = this.columns.get(i);

			if (i > 0)
			{
				builder.append(", ");
			}

			builder.append("{ ");
			builder.append(column.toString());

			if (column instanceof CommandsColumn)
			{
				// buttons //
				builder.append(", ");
				builder.append(Options.QUOTE).append("command").append(Options.QUOTE).append(": ");
				builder.append("[ ");

				int n = 0;
				for (ColumnButtonAjaxBehavior behavior : component.getBehaviors(ColumnButtonAjaxBehavior.class))
				{
					ColumnButton button = behavior.getButton();

					if (n++ > 0)
					{
						builder.append(", ");
					}

					builder.append("{ ");
					builder.append("'name': '").append(button.getMarkupId()).append("', ");
					builder.append("'text': '").append(button.toString()).append("', ");
					// builder.append("'className': '").append(button.toString()).append("', ");
					builder.append("\n'click': ").append(behavior.getCallbackFunction());
					builder.append(" }");
				}

				builder.append(" ]");
			}

			builder.append(" }");
		}

		this.setOption("columns", builder.append(" ]").toString());
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ClickEvent)
		{
			ClickEvent e = (ClickEvent) event;
			// e.getButton().onClick(target, e.getValue()); //TODO: to implement?
			this.onClick(target, e.getButton(), e.getValue());
		}
	}

	// Factories //

	/**
	 * Gets a new {@link ColumnButtonAjaxBehavior} that will be called by the corresponding table's button.<br/>
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link ClickEvent}
	 * @return the {@link ColumnButtonAjaxBehavior}
	 */
	protected abstract ColumnButtonAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, ColumnButton button);
}
