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
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.BuilderUtils;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.datatable.ColumnAjaxBehavior.ClickEvent;
import com.googlecode.wicket.kendo.ui.datatable.ToolbarAjaxBehavior.ToolbarClickEvent;
import com.googlecode.wicket.kendo.ui.datatable.column.CommandsColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IdPropertyColumn;
import com.googlecode.wicket.kendo.ui.scheduler.SchedulerBehavior;

/**
 * Provides the Kendo UI data-table behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class DataTableBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoGrid";

	private final IDataTableListener listener;
	private final KendoDataSource dataSource;

	// TODO: private JQueryAjaxBehavior onEditAjaxBehavior;
	private JQueryAjaxBehavior onCancelAjaxBehavior;
	private JQueryAjaxBehavior onCreateAjaxBehavior;
	private JQueryAjaxBehavior onUpdateAjaxBehavior;
	private JQueryAjaxBehavior onDeleteAjaxBehavior;
	private JQueryAjaxBehavior onToolbarClickAjaxBehavior; // toolbar buttons

	protected final List<? extends IColumn> columns;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param columns the list of {@link IColumn}
	 * @param listener the {@link IDataTableListener}
	 */
	public DataTableBehavior(String selector, List<? extends IColumn> columns, IDataTableListener listener)
	{
		this(selector, new Options(), columns, listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param columns the list of {@link IColumn}
	 * @param listener the {@link IDataTableListener}
	 */
	public DataTableBehavior(String selector, Options options, List<? extends IColumn> columns, IDataTableListener listener)
	{
		super(selector, METHOD, options);

		this.columns = columns;
		this.listener = Args.notNull(listener, "listener");

		this.dataSource = new KendoDataSource("gridDataSource");
		this.add(this.dataSource);
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// grid events //
		this.onCancelAjaxBehavior = this.newOnCancelAjaxBehavior(this);
		component.add(this.onCancelAjaxBehavior);

		// data source //
		this.onCreateAjaxBehavior = this.newOnCreateAjaxBehavior(this);
		component.add(this.onCreateAjaxBehavior);

		this.onUpdateAjaxBehavior = this.newOnUpdateAjaxBehavior(this);
		component.add(this.onUpdateAjaxBehavior);

		this.onDeleteAjaxBehavior = this.newOnDeleteAjaxBehavior(this);
		component.add(this.onDeleteAjaxBehavior);

		// column buttons //
		for (ColumnButton button : this.getColumnButtons())
		{
			component.add(this.newButtonAjaxBehavior(this, button));
		}

		// toolbar buttons //
		this.onToolbarClickAjaxBehavior = this.newToolbarClickAjaxBehavior(this);

		if (this.onToolbarClickAjaxBehavior != null)
		{
			component.add(this.onToolbarClickAjaxBehavior);
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
	 * Gets the data-provider behavior's url
	 *
	 * @return the data-provider behavior's url
	 */
	protected abstract CharSequence getProviderCallbackUrl();

	/**
	 * Indicates whether the read function should use cache
	 * 
	 * @return false by default
	 * @see <a href="http://docs.telerik.com/kendo-ui/api/javascript/data/datasource#configuration-transport.read.cache">configuration-transport.read.cache</a>
	 */
	protected boolean useCache()
	{
		return false;
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

	/**
	 * Gets the 'read' callback function<br/>
	 * As create, update and destroy need to be supplied as function, we should declare read as a function as well. Weird...
	 *
	 * @return the 'read' callback function
	 */
	private String getReadCallbackFunction()
	{
		return "function(options) {" // lf
				+ " jQuery.ajax({" // lf
				+ "		url: '" + this.getProviderCallbackUrl() + "'," // lf
				+ "		data: options.data," // lf
				+ "		cache: " + this.useCache() + "," // lf
				+ "		dataType: 'json'," // lf
				+ "		success: function(result) {" // lf
				+ "			options.success(result);" // lf
				+ "		}," // lf
				+ "		error: function(result) {" // lf
				+ "			options.error(result);" // lf
				+ "		}" // lf
				+ "	});" // lf
				+ "}";
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		// events //
		if (this.onToolbarClickAjaxBehavior != null)
		{
			// FIXME: registered on each reload (target.add)
			this.on(this.selector + " .k-grid-toolbar .k-button", "click", this.onToolbarClickAjaxBehavior.getCallbackFunction());
		}

		// this.setOption("edit", this.onEditAjaxBehavior.getCallbackFunction());
		this.setOption("cancel", this.onCancelAjaxBehavior.getCallbackFunction());

		// options //
		Options schema = new Options();

		// schema //
		schema.set("data", Options.asString("results"));
		schema.set("total", Options.asString("__count"));
		schema.set("model", this.newSchemaModel());

		// data-source //
		this.setOption("dataSource", this.dataSource.getName());

		this.dataSource.set("pageSize", this.getRowCount());
		this.dataSource.set("serverPaging", true);
		this.dataSource.set("serverSorting", true);
		this.dataSource.set("serverFiltering", true);
		this.dataSource.set("schema", schema);
		this.dataSource.setTransportRead(this.getReadCallbackFunction());
		this.dataSource.setTransportCreate(this.onCreateAjaxBehavior.getCallbackFunction());
		this.dataSource.setTransportUpdate(this.onUpdateAjaxBehavior.getCallbackFunction());
		this.dataSource.setTransportDelete(this.onDeleteAjaxBehavior.getCallbackFunction());

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
				for (ColumnAjaxBehavior behavior : component.getBehaviors(ColumnAjaxBehavior.class))
				{
					ColumnButton button = behavior.getButton();
					String css = button.getCSSClass();

					if (n++ > 0)
					{
						builder.append(", ");
					}

					builder.append("{ ");
					BuilderUtils.append(builder, "name", button.getName());
					builder.append(", ");
					BuilderUtils.append(builder, "text", button.toString());
					builder.append(", ");

					if (!Strings.isEmpty(css)) /* important */
					{
						BuilderUtils.append(builder, "className", css);
						builder.append(", ");
					}

					String function = behavior.getCallbackFunction();

					if (function != null)
					{
						builder.append("'click': ").append(function);
					}

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
			e.getButton().onClick(target, e.getValue());

			this.listener.onClick(target, e.getButton(), e.getValue());
		}

		if (event instanceof ToolbarClickEvent)
		{
			ToolbarClickEvent e = (ToolbarClickEvent) event;
			this.listener.onClick(target, e.getButton(), e.getValues());
		}

		if (event instanceof CancelEvent)
		{
			this.listener.onCancel(target);
		}

		if (event instanceof CreateEvent)
		{
			this.listener.onCreate(target, ((CreateEvent) event).getObject());
		}

		if (event instanceof UpdateEvent)
		{
			this.listener.onUpdate(target, ((UpdateEvent) event).getObject());
		}

		if (event instanceof DeleteEvent)
		{
			this.listener.onDelete(target, ((DeleteEvent) event).getObject());
		}
	}

	// Factories //

	/**
	 * Get the JSON model of the datasource's schema
	 *
	 * @return the model, as JSON object
	 */
	protected Options newSchemaModel()
	{
		Options model = new Options();
		Options fields = new Options();

		for (IColumn column : this.columns)
		{
			if (column.getField() != null)
			{
				Options field = new Options();

				if (column instanceof IdPropertyColumn)
				{
					model.set("id", Options.asString(column.getField()));
				}

				if (column.isEditable() != null)
				{
					field.set("editable", column.isEditable());
				}

				if (column.isNullable() != null)
				{
					field.set("nullable", column.isNullable());
				}

				if (column.getType() != null)
				{
					field.set("type", Options.asString(column.getType()));
				}

				fields.set(column.getField(), field);
			}
		}

		model.set("fields", fields);

		return model;
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'update' event
	 * 
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link JQueryAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnCancelAjaxBehavior(IJQueryAjaxAware source)
	{
		return new JQueryAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("e") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new CancelEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'create' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnCreateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new CreateEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'update' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnUpdateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new UpdateEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'delete' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDeleteAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new DeleteEvent();
			}
		};
	}

	/**
	 * Gets the {@link JQueryAjaxBehavior} that will be called when the user clicks a toolbar button
	 *
	 * @param source {@link IJQueryAjaxAware}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected abstract JQueryAjaxBehavior newToolbarClickAjaxBehavior(IJQueryAjaxAware source);

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called by a table's button. This method may be overridden to provide additional behaviors
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link ClickEvent}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected abstract JQueryAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, ColumnButton button);

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} for handling datasource operations
	 */
	protected abstract class DataSourceAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public DataSourceAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), CallbackParameter.resolved("data", "kendo.stringify(e.data)") };
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			return super.getCallbackFunctionBody(parameters) + " e.success();";
		}
	}

	// Event object //

	protected static class CancelEvent extends JQueryEvent
	{
	}

	/**
	 * Provides a base class for {@link SchedulerBehavior} event objects
	 */
	protected static class DataSourceEvent extends JQueryEvent
	{
		private final JSONObject object;

		public DataSourceEvent()
		{
			String data = RequestCycleUtils.getQueryParameterValue("data").toString();
			this.object = new JSONObject(data);
		}

		public JSONObject getObject()
		{
			return this.object;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link DataSourceAjaxBehavior} 'create' callback
	 */
	protected static class CreateEvent extends DataSourceEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link DataSourceAjaxBehavior} 'update' callback
	 */
	protected static class UpdateEvent extends DataSourceEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link DataSourceAjaxBehavior} 'delete' callback
	 */
	protected static class DeleteEvent extends DataSourceEvent
	{
	}
}
