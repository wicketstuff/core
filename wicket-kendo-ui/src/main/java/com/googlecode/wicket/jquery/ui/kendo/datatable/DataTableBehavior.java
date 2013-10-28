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

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.ui.kendo.KendoAbstractBehavior;
import com.googlecode.wicket.jquery.ui.kendo.datatable.ButtonAjaxBehavior.ClickEvent;
import com.googlecode.wicket.jquery.ui.kendo.datatable.column.CommandsColumn;
import com.googlecode.wicket.jquery.ui.kendo.datatable.column.IColumn;

/**
 * Provides the Kendo UI data-table behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class DataTableBehavior extends KendoAbstractBehavior implements IJQueryAjaxAware, IDataTableListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoGrid";

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public DataTableBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public DataTableBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}


	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// buttons //
		for (ColumnButton button : this.getButtons())
		{
			component.add(this.newButtonAjaxBehavior(this, button));
		}
	}


	// Properties //

	/**
	 * Gets the {@link List} of {@link IColumn}
	 * @return the {@link List} of {@link IColumn}
	 */
	protected abstract List<? extends IColumn> getColumns();

	/**
	 * Gets the row count
	 * @return the row count
	 */
	protected abstract long getRowCount();

	/**
	 * Gets the data-source behavior's url
	 * @return the data-source behavior's url
	 */
	protected abstract CharSequence getSourceCallbackUrl();

	/**
	 * Gets the {@link List} of {@link ColumnButton} that have may be supplied through a {@link CommandsColumn}
	 * @return the {@link List} of {@link ColumnButton}
	 */
	private List<ColumnButton> getButtons()
	{
		for (IColumn column : this.getColumns())
		{
			if (column instanceof CommandsColumn)
			{
				return ((CommandsColumn)column).getButtons();
			}
		}

		return Collections.emptyList();
	}


	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		// source //
		Options source = new Options();
		source.set("type", Options.asString("jsonp"));
		source.set("pageSize", this.getRowCount());
		source.set("serverPaging", true);
		source.set("transport", String.format("{ \"read\": \"%s\" }", this.getSourceCallbackUrl()));
		source.set("schema", "{ \"data\": \"results\", \"total\": \"__count\" }");

		this.setOption("dataSource", source);

		// columns //
		StringBuilder builder = new StringBuilder("[ ");

		{
			List<? extends IColumn> columns = this.getColumns();

			for (int i = 0; i < columns.size(); i++)
			{
				IColumn column = columns.get(i);

				if (i > 0)
				{
					builder.append(", ");
				}

				builder.append("{ ");
				builder.append(column.toString()); //toJSON(component)

				if (column instanceof CommandsColumn)
				{
					// buttons //
					builder.append(", ");
					builder.append(Options.QUOTE).append("command").append(Options.QUOTE).append(": ");
					builder.append("[ ");

					int n = 0;
					for(ButtonAjaxBehavior behavior : component.getBehaviors(ButtonAjaxBehavior.class))
					{
						ColumnButton button = behavior.getButton();

						if (n++ > 0) { builder.append(", "); }
						builder.append("{ ");
						builder.append("'name': '").append(button.getMarkupId()).append("', ");
						builder.append("'text': '").append(button.toString()).append("', ");
	//					builder.append("'className': '").append(button.toString()).append("', ");
						builder.append("\n'click': ").append(behavior.getCallbackFunction());
						builder.append(" }");
					}

					builder.append(" ]");
				}
//				else
//				{
//					builder.append("{ ").append(column.toString()).append(" }"); //toJSON
//				}

				builder.append(" }");
			}
		}

		builder.append(" ]");

		this.setOption("columns", builder.toString());
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ClickEvent)
		{
			ClickEvent e = (ClickEvent) event;
//			e.getButton().onClick(target, e.getValue()); //TODO: to implement?
			this.onClick(target, e.getButton(), e.getValue());
		}
	}


	// Factories //

	/**
	 * Gets a new {@link ButtonAjaxBehavior} that will be called by the corresponding table's button.<br/>
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link ClickEvent}
	 * @return the {@link ButtonAjaxBehavior}
	 */
	protected abstract ButtonAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, ColumnButton button);
}
