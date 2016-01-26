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
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.datatable.DataSourceEvent.CreateEvent;
import com.googlecode.wicket.kendo.ui.datatable.DataSourceEvent.DeleteEvent;
import com.googlecode.wicket.kendo.ui.datatable.DataSourceEvent.UpdateEvent;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandAjaxBehavior;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandAjaxBehavior.ClickEvent;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandButton;
import com.googlecode.wicket.kendo.ui.datatable.button.ToolbarAjaxBehavior;
import com.googlecode.wicket.kendo.ui.datatable.button.ToolbarAjaxBehavior.ToolbarClickEvent;
import com.googlecode.wicket.kendo.ui.datatable.button.ToolbarButton;
import com.googlecode.wicket.kendo.ui.datatable.column.CommandColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IdPropertyColumn;

/**
 * Provides a {@value #METHOD} behavior<br/>
 * 
 * @author Sebastien Briquet - sebfz1
 */
public abstract class DataTableBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoGrid";

	private final IDataTableListener listener;
	private final IModel<List<IColumn>> columns;
	private final KendoDataSource dataSource;

	// TODO: private JQueryAjaxBehavior onEditAjaxBehavior;
	private JQueryAjaxBehavior onCancelAjaxBehavior;
	private JQueryAjaxBehavior onCreateAjaxBehavior;
	private JQueryAjaxBehavior onUpdateAjaxBehavior;
	private JQueryAjaxBehavior onDeleteAjaxBehavior;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param columns the list of {@link IColumn}
	 * @param listener the {@link IDataTableListener}
	 */
	public DataTableBehavior(String selector, IModel<List<IColumn>> columns, IDataTableListener listener)
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
	public DataTableBehavior(String selector, Options options, IModel<List<IColumn>> columns, IDataTableListener listener)
	{
		super(selector, METHOD, options);

		this.columns = columns;
		this.listener = Args.notNull(listener, "listener");

		// data source //
		this.dataSource = new KendoDataSource("datasource" + selector);
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

		// toolbar buttons //
		for (ToolbarButton button : this.getToolbarButtons())
		{
			if (!button.isBuiltIn())
			{
				component.add(this.newToolbarAjaxBehavior(this, button));
			}
		}

		// column buttons //
		for (CommandButton button : this.getCommandButtons())
		{
			if (!button.isBuiltIn())
			{
				component.add(this.newCommandAjaxBehavior(this, button));
			}
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
	 * Gets the {@code List} of {@link ToolbarButton}{@code s}
	 * 
	 * @return the {@code List} of {@code ToolbarButton}{@code s}
	 */
	protected List<ToolbarButton> getToolbarButtons()
	{
		return Collections.emptyList();
	}

	/**
	 * Indicates whether toolbar buttons are supplied.<br/>
	 * <b>Note:</b> if false, the {@code toolbar} option can be used (for built-in buttons only)
	 * 
	 * @return {@code true} or {@code false}
	 */
	private boolean hasVisibleToolbarButtons()
	{
		return !this.getToolbarButtons().isEmpty();
	}

	/**
	 * Gets the {@code List} of visible {@link ToolbarButton}{@code s}
	 * 
	 * @return the {@code List} of visible {@code ToolbarButton}{@code s}
	 */
	protected List<ToolbarButton> getVisibleToolbarButtons()
	{
		List<ToolbarButton> buttons = Generics.newArrayList();

		for (ToolbarButton button : this.getToolbarButtons())
		{
			if (button.isVisible())
			{
				buttons.add(button);
			}
		}

		return buttons;
	}

	/**
	 * Gets the read-only {@link List} of {@link CommandButton}
	 *
	 * @return the {@link List} of {@link CommandButton}
	 */
	private List<CommandButton> getCommandButtons()
	{
		for (IColumn column : this.columns.getObject())
		{
			if (column instanceof CommandColumn)
			{
				return ((CommandColumn) column).getButtons();
			}
		}

		return Collections.emptyList();
	}

	/**
	 * Gets the {@code List} of visible {@link CommandButton}{@code s} as json string
	 * 
	 * @param column the {@code CommandColumn}, containing the {@code CommandButton}{@code s}
	 * @param behaviors the {@code List} of {@code CommandAjaxBehavior}{@code s} which may be bound to buttons
	 * @return the {@code List} of visible {@code CommandButton} as json string
	 */
	private List<String> getCommandButtonsAsString(CommandColumn column, List<CommandAjaxBehavior> behaviors)
	{
		List<String> list = Generics.newArrayList();

		for (CommandButton button : column.getButtons())
		{
			if (button.isVisible())
			{
				JQueryAjaxBehavior behavior = this.getCommandAjaxBehavior(button, behaviors);

				list.add(button.toString(behavior));
			}
		}

		return list;
	}

	/**
	 * Gets the {@link CommandAjaxBehavior} associated to the {@link CommandButton}, if any
	 * 
	 * @param behaviors the {@code List} of {@code CommandAjaxBehavior}
	 * @param button the {@code CommandButton}
	 * @return {@code null} if no {@code CommandAjaxBehavior} if associated to the button
	 */
	private JQueryAjaxBehavior getCommandAjaxBehavior(CommandButton button, List<CommandAjaxBehavior> behaviors)
	{
		for (CommandAjaxBehavior behavior : behaviors)
		{
			if (button.equals(behavior.getButton()))
			{
				return behavior;
			}
		}

		return null;
	}

	/**
	 * Gets the {@code List} of {@link IColumn}{@code s} as json string
	 * 
	 * @param columns the {@code List} of {@link IColumn}{@code s}
	 * @param behaviors the the {@code List} of {@link CommandAjaxBehavior}{@code s} associated to {@link CommandButton}{@code s}
	 * @return the JSON string
	 */
	private String getColumnsAsString(List<IColumn> columns, List<CommandAjaxBehavior> behaviors)
	{
		StringBuilder builder = new StringBuilder("[ ");

		for (int i = 0; i < columns.size(); i++)
		{
			IColumn column = columns.get(i);

			if (i > 0)
			{
				builder.append(", ");
			}

			builder.append("{ ");
			builder.append(column.toString());

			if (column instanceof CommandColumn)
			{
				// buttons //
				builder.append(", ");
				builder.append(Options.QUOTE).append("command").append(Options.QUOTE).append(": ");
				builder.append(this.getCommandButtonsAsString((CommandColumn) column, behaviors));
			}

			builder.append(" }");
		}

		return builder.append(" ]").toString();
	}

	/**
	 * Gets the 'read' callback function<br/>
	 * As create, update and destroy need to be supplied as function, we should declare read as a function as well. Weird...
	 *
	 * @return the 'read' callback function
	 */
	private String getReadCallbackFunction()
	{
		return KendoDataSource.getReadCallbackFunction(this.getProviderCallbackUrl(), this.useCache());
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		List<IColumn> columns = this.columns.getObject();

		// this.setOption("edit", this.onEditAjaxBehavior.getCallbackFunction());
		this.setOption("cancel", this.onCancelAjaxBehavior.getCallbackFunction());

		// toolbar //
		if (this.hasVisibleToolbarButtons())
		{
			this.setOption("toolbar", this.getVisibleToolbarButtons());
		}

		// columns //
		this.setOption("columns", this.getColumnsAsString(columns, component.getBehaviors(CommandAjaxBehavior.class)));

		// schema //
		Options schema = new Options();
		schema.set("data", Options.asString("results"));
		schema.set("total", Options.asString("__count"));
		schema.set("model", this.newSchemaModelOptions(columns));

		// data-source //
		this.onConfigure(this.dataSource);
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

		// ajax //
		for (ToolbarAjaxBehavior behavior : component.getBehaviors(ToolbarAjaxBehavior.class))
		{
			String selector = String.format("%s .k-grid-toolbar .k-grid-%s", this.getSelector(), behavior.getButtonName());

			this.off(selector, "click");
			this.on(selector, "click", behavior.getCallbackFunction());
		}
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
	protected Options newSchemaModelOptions(List<IColumn> columns)
	{
		Options model = new Options();
		Options fields = new Options();

		for (IColumn column : columns)
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
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link ToolbarClickEvent}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newToolbarAjaxBehavior(DataTableBehavior source, final ToolbarButton button)
	{
		return new ToolbarAjaxBehavior(source, button);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called by a table's button. This method may be overridden to provide additional behaviors
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link ClickEvent}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected abstract JQueryAjaxBehavior newCommandAjaxBehavior(IJQueryAjaxAware source, CommandButton button);

	// Event object //

	protected static class CancelEvent extends JQueryEvent
	{
	}
}
