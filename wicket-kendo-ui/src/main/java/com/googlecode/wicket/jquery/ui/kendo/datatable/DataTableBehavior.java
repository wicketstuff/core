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
import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.kendo.KendoAbstractBehavior;
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
	protected abstract List<? extends IColumn<?>> getColumns();

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
		for (IColumn<?> column : this.getColumns())
		{
			if (column instanceof CommandsColumn)
			{
				return ((CommandsColumn<?>)column).getButtons();
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

		this.options.set("dataSource", source);

		// columns //
		StringBuilder builder = new StringBuilder("[ ");

		{
			List<? extends IColumn<?>> columns = this.getColumns();

			for (int i = 0; i < columns.size(); i++)
			{
				IColumn<?> column = columns.get(i);

				if (i > 0)
				{
					builder.append(", ");
				}

				if (column instanceof CommandsColumn)
				{
					builder.append("{ ");
					builder.append(column.toString()); //toJSON(component)

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
					builder.append(" }");
				}
				else
				{
					builder.append("{ ").append(column.toString()).append(" }"); //toJSON
				}
			}
		}

		builder.append(" ]");

		this.options.set("columns", builder.toString());
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
	protected ButtonAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, ColumnButton button)
	{
		return new ButtonAjaxBehavior(source, button);
	}


	// Ajax behaviors //

	/**
	 * Provides the {@link JQueryAjaxBehavior} being called by the button(s).
	 */
	protected static class ButtonAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		private final ColumnButton button;

		/**
		 * Constructor
		 * @param source the {@link IJQueryAjaxAware}
		 * @param button the {@link ColumnButton} to attach to the {@link ClickEvent}
		 */
		public ButtonAjaxBehavior(IJQueryAjaxAware source, ColumnButton button)
		{
			super(source);

			this.button = button;
		}


		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] {
					CallbackParameter.context("e"),
					CallbackParameter.resolved("value", String.format("this.dataItem($(e.currentTarget).closest('tr'))['%s']", this.button.getProperty()))
			};
		}

		/**
		 * Gets the {@link ColumnButton}
		 * @return the {@link ColumnButton}
		 */
		public ColumnButton getButton()
		{
			return this.button;
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ClickEvent(this.button);
		}
	}


	// Events classes //

	/**
	 * Provides a dialog event that will be transmitted to the dialog {@link AbstractDialog}
	 */
	protected static class ClickEvent extends JQueryEvent
	{
		private final ColumnButton button;
		private final String value;

		public ClickEvent(ColumnButton button)
		{
			super();

			this.button = button;
			this.value = RequestCycleUtils.getQueryParameterValue("value").toString();
		}

		/**
		 * Gets the button that has been attached to this event object
		 * @return the button
		 */
		public ColumnButton getButton()
		{
			return this.button;
		}

		/**
		 * Gets the value from the row
		 * @return the value from the row
		 */
		public String getValue()
		{
			return this.value;
		}
	}
}
