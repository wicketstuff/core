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
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Generics;

import com.github.openjson.JSONObject;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.datatable.DataSourceEvent.CreateEvent;
import com.googlecode.wicket.kendo.ui.datatable.DataSourceEvent.DeleteEvent;
import com.googlecode.wicket.kendo.ui.datatable.DataSourceEvent.UpdateEvent;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandAjaxBehavior;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandAjaxBehavior.CommandClickEvent;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandButton;
import com.googlecode.wicket.kendo.ui.datatable.button.ToolbarAjaxBehavior;
import com.googlecode.wicket.kendo.ui.datatable.button.ToolbarAjaxBehavior.ToolbarClickEvent;
import com.googlecode.wicket.kendo.ui.datatable.button.ToolbarButton;
import com.googlecode.wicket.kendo.ui.datatable.column.CheckboxColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.CommandColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IdPropertyColumn;
import com.googlecode.wicket.kendo.ui.repeater.ChangeEvent;

/**
 * Provides a {@value #METHOD} behavior<br>
 * 
 * @author Sebastien Briquet - sebfz1
 */
public abstract class DataTableBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoGrid";

	private final IDataTableListener listener;
	private final IModel<List<IColumn>> columns;

	private KendoDataSource dataSource;

	// TODO: private JQueryAjaxBehavior onEditAjaxBehavior;
	private JQueryAjaxBehavior onCancelAjaxBehavior;
	 private JQueryAjaxBehavior onChangeAjaxBehavior = null;
	private JQueryAjaxBehavior onCheckedAjaxBehavior = null;
	private JQueryAjaxBehavior onColumnReorderAjaxBehavior = null;
	private DataSourceAjaxBehavior onCreateAjaxBehavior;
	private DataSourceAjaxBehavior onUpdateAjaxBehavior;
	private DataSourceAjaxBehavior onDeleteAjaxBehavior;

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
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// data source //
		this.dataSource = new KendoDataSource(component);
		this.add(this.dataSource);

		// grid events //
		this.onCancelAjaxBehavior = this.newOnCancelAjaxBehavior(this);
		component.add(this.onCancelAjaxBehavior);

		if (this.listener.isSelectable())
		{
			this.onChangeAjaxBehavior = this.newOnChangeAjaxBehavior(this);
			component.add(this.onChangeAjaxBehavior);
		}

		if (this.hasCheckboxColumn())
		{
			this.onCheckedAjaxBehavior = this.newOnCheckedAjaxBehavior(this);
			component.add(this.onCheckedAjaxBehavior);
		}

		if (this.isColumnReorderEnabled())
		{
			this.onColumnReorderAjaxBehavior = this.newColumnReorderAjaxBehavior(this);
			component.add(this.onColumnReorderAjaxBehavior);
		}

		// data events //
		this.onCreateAjaxBehavior = this.newOnCreateAjaxBehavior(this);
		component.add(this.onCreateAjaxBehavior);

		this.onUpdateAjaxBehavior = this.newOnUpdateAjaxBehavior(this);
		component.add(this.onUpdateAjaxBehavior);

		this.onDeleteAjaxBehavior = this.newOnDeleteAjaxBehavior(this);
		component.add(this.onDeleteAjaxBehavior);

		// toolbar buttons //
		for (ToolbarButton button : this.getVisibleToolbarButtons())
		{
			if (!button.isBuiltIn())
			{
				component.add(this.newToolbarAjaxBehavior(this, button));
			}
		}

		// column buttons //
		for (CommandButton button : this.getVisibleCommandButtons())
		{
			if (!button.isBuiltIn())
			{
				component.add(this.newCommandAjaxBehavior(this, button));
			}
		}
	}

	// Properties //

	/**
	 * Indicates whether the {@code reorderable} option is set
	 * 
	 * @return {@code false} by default
	 */
	public boolean isColumnReorderEnabled()
	{
		Boolean value = this.options.get("reorderable");

		return value != null && value;
	}

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
	protected abstract CharSequence getProviderUrl();

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
	 * Indicates whether toolbar buttons are supplied.<br>
	 * <b>Note:</b> if false, the {@code toolbar} option can be used (for built-in buttons only)
	 * 
	 * @return {@code true} or {@code false}
	 */
	protected boolean hasVisibleToolbarButtons()
	{
		return !this.getVisibleToolbarButtons().isEmpty();
	}

	/**
	 * Indicates whether the columns {@link IModel} contains a {@link CheckboxColumn}
	 * 
	 * @return {@code true} if a {@link CheckboxColumn} is found
	 */
	protected boolean hasCheckboxColumn()
	{
		return this.columns.getObject().stream().anyMatch(column -> column instanceof CheckboxColumn);
	}

	/**
	 * Gets the {@link List} of {@link CommandButton}
	 *
	 * @return the {@link List} of {@link CommandButton}
	 */
	protected List<CommandButton> getCommandButtons()
	{
		List<CommandButton> buttons = Generics.newArrayList();

		for (IColumn column : this.columns.getObject())
		{
			if (column instanceof CommandColumn)
			{
				buttons.addAll(((CommandColumn) column).getButtons());
			}
		}

		return buttons;
	}

	/**
	 * Gets the {@code List} of visible {@link CommandButton}{@code s}
	 * 
	 * @return the {@code List} of visible {@code CommandButton}{@code s}
	 */
	protected List<CommandButton> getVisibleCommandButtons()
	{
		List<CommandButton> buttons = Generics.newArrayList();

		for (CommandButton button : this.getCommandButtons())
		{
			if (button.isVisible())
			{
				buttons.add(button);
			}
		}

		return buttons;
	}

	/**
	 * Gets the {@code List} of visible {@link CommandButton}{@code s} as json string
	 * 
	 * @param column the {@code CommandColumn}, containing the {@code CommandButton}{@code s}
	 * @param behaviors the {@code List} of {@code CommandAjaxBehavior}{@code s} which may be bound to buttons
	 * @return the {@code List} of visible {@code CommandButton} as json string
	 */
	private static List<String> getCommandButtonsAsString(CommandColumn column, List<CommandAjaxBehavior> behaviors)
	{
		List<String> list = Generics.newArrayList();

		for (CommandButton button : column.getButtons())
		{
			if (button.isVisible())
			{
				JQueryAjaxBehavior behavior = getCommandAjaxBehavior(button, behaviors);

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
	 * @return {@code null} if no {@code CommandAjaxBehavior} is associated to the button
	 */
	public static JQueryAjaxBehavior getCommandAjaxBehavior(CommandButton button, List<CommandAjaxBehavior> behaviors)
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
	 * @param behaviors the {@code List} of {@link CommandAjaxBehavior}{@code s} associated to {@link CommandButton}{@code s}
	 * @return the JSON string
	 */
	private static String getColumnsAsString(List<IColumn> columns, List<CommandAjaxBehavior> behaviors)
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
				builder.append(getCommandButtonsAsString((CommandColumn) column, behaviors));
			}

			builder.append(" }");
		}

		return builder.append(" ]").toString();
	}

	/**
	 * Gets the 'read' callback function<br>
	 * As create, update and destroy need to be supplied as function, we should declare read as a function as well. Weird...
	 *
	 * @return the 'read' callback function
	 */
	private String getReadCallbackFunction()
	{
		return KendoDataSource.getReadCallbackFunction(this.getProviderUrl(), this.useCache());
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		final List<IColumn> columns = this.columns.getObject();

		// this.setOption("edit", this.onEditAjaxBehavior.getCallbackFunction());
		this.setOption("cancel", this.onCancelAjaxBehavior.getCallbackFunction());

		if (this.onChangeAjaxBehavior != null)
		{
			this.setOption("change", this.onChangeAjaxBehavior.getCallbackFunction());
		}

		if (this.onCheckedAjaxBehavior != null)
		{
			this.setOption("change", this.onCheckedAjaxBehavior.getCallbackFunction());
		}

		if (this.onColumnReorderAjaxBehavior != null)
		{
			this.setOption("columnReorder", this.onColumnReorderAjaxBehavior.getCallbackFunction());
		}

		// toolbar //
		if (this.hasVisibleToolbarButtons())
		{
			this.setOption("toolbar", this.getVisibleToolbarButtons());
		}

		// columns (+ column buttons) //
		this.setOption("columns", getColumnsAsString(columns, component.getBehaviors(CommandAjaxBehavior.class)));

		// schema //
		Options schema = new Options();
		schema.set("data", Options.asString("results"));
		schema.set("total", Options.asString("__count"));
		schema.set("model", this.newSchemaModelOptions(columns));

		// data-source //
		this.setOption("dataSource", this.dataSource.getName());

		this.dataSource.set("schema", schema);
		this.dataSource.set("pageSize", this.getRowCount());
		this.dataSource.set("serverPaging", true);
		this.dataSource.set("serverSorting", true);
		this.dataSource.set("serverFiltering", true);
		this.dataSource.setTransportRead(this.getReadCallbackFunction());
		this.dataSource.setTransportCreate(this.onCreateAjaxBehavior.getCallbackFunction());
		this.dataSource.setTransportUpdate(this.onUpdateAjaxBehavior.getCallbackFunction());
		this.dataSource.setTransportDelete(this.onDeleteAjaxBehavior.getCallbackFunction());

		this.onConfigure(this.dataSource); // last chance to set options

		// ajax //
		for (ToolbarAjaxBehavior behavior : component.getBehaviors(ToolbarAjaxBehavior.class))
		{
			String selector = String.format("%s .k-grid-toolbar .k-grid-%s", this.getSelector(), behavior.getButtonName());

			this.off(selector, "click");
			this.on(selector, "click", behavior.getCallbackFunction());
		}

		super.onConfigure(component);
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
		if (event instanceof ToolbarClickEvent)
		{
			ToolbarClickEvent e = (ToolbarClickEvent) event;

			e.getButton().onClick(target, e.getValues());
			this.listener.onClick(target, e.getButton(), e.getValues());
		}

		if (event instanceof CommandClickEvent)
		{
			CommandClickEvent e = (CommandClickEvent) event;

			e.getButton().onClick(target, e.getValue());
			this.listener.onClick(target, e.getButton(), e.getValue());
		}

		if (event instanceof CancelEvent)
		{
			this.listener.onCancel(target);
		}

		if (event instanceof ChangeEvent)
		{
			this.listener.onChange(target, ((ChangeEvent) event).getItems());
		}

		if (event instanceof CheckboxEvent)
		{
			this.listener.onChecked(target, ((CheckboxEvent) event).getSelectedKeys());
		}

		if (event instanceof ColumnReorderEvent)
		{
			ColumnReorderEvent e = (ColumnReorderEvent) event;
			this.listener.onColumnReorder(target, e.getOldIndex(), e.getNewIndex(), e.getObject());
		}

		if (event instanceof CreateEvent)
		{
			this.listener.onCreate(target, ((DataSourceEvent) event).getObject());
		}

		if (event instanceof UpdateEvent)
		{
			this.listener.onUpdate(target, ((DataSourceEvent) event).getObject());
		}

		if (event instanceof DeleteEvent)
		{
			this.listener.onDelete(target, ((DataSourceEvent) event).getObject());
		}
	}

	// Factories //

	/**
	 * Get the JSON model of the datasource's schema
	 * 
	 * @param columns the {@code List} of {@link IColumn}{@code s}
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
				if (column instanceof IdPropertyColumn)
				{
					model.set("id", Options.asString(column.getField()));
				}

				Options field = new Options();

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
	 * @return a new {@code JQueryAjaxBehavior} by default
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
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'change' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnChangeAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnChangeAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnChangeAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'change' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnCheckedAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnCheckedAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnCheckedAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'columnReorder' event
	 * 
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code JQueryAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newColumnReorderAjaxBehavior(IJQueryAjaxAware source)
	{
		return new JQueryAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("e"), // lf
						CallbackParameter.resolved("oldIndex", "e.oldIndex"), // lf
						CallbackParameter.resolved("newIndex", "e.newIndex"), // lf
						CallbackParameter.resolved("data", "kendo.stringify(e.column)") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new ColumnReorderEvent();
			}
		};
	}

	/**
	 * Gets a new {@link DataSourceAjaxBehavior} that will be wired to the datasource's 'create' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code DataSourceAjaxBehavior}
	 */
	protected DataSourceAjaxBehavior newOnCreateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new CreateEvent();
			}
		};
	}

	/**
	 * Gets a new {@link DataSourceAjaxBehavior} that will be wired to the datasource's 'update' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code DataSourceAjaxBehavior}
	 */
	protected DataSourceAjaxBehavior newOnUpdateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new UpdateEvent();
			}
		};
	}

	/**
	 * Gets a new {@link DataSourceAjaxBehavior} that will be wired to the datasource's 'delete' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code DataSourceAjaxBehavior}
	 */
	protected DataSourceAjaxBehavior newOnDeleteAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) { // NOSONAR

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
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link CommandClickEvent}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected abstract JQueryAjaxBehavior newCommandAjaxBehavior(IJQueryAjaxAware source, CommandButton button);

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'change' event
	 */
	protected static class OnChangeAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnChangeAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), CallbackParameter.resolved("items", "items") };
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			String statement = "";
			statement += "var $grid = e.sender;\n";
			statement += "var _rows = jQuery.map(this.select(), function(row) { return $grid.dataItem(row); });\n"; // TODO REMOVE
			statement += "var items = kendo.stringify(_rows);\n";

			return statement + super.getCallbackFunctionBody(parameters);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ChangeEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'change' event (for checkboxes)
	 */
	protected static class OnCheckedAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor
		 *
		 * @param source the {@link Behavior} that will broadcast the event.
		 */
		public OnCheckedAjaxBehavior(final IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), CallbackParameter.resolved("values", "this.selectedKeyNames()") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new CheckboxEvent();
		}
	}

	// Event object //

	/**
	 * Provides an event object that will be broadcasted by the {@link #newOnCancelAjaxBehavior} callback
	 */
	protected static class CancelEvent extends JQueryEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnCheckedAjaxBehavior} callback
	 */
	protected static class CheckboxEvent extends JQueryEvent
	{

		private final List<String> selectedKeys;

		public CheckboxEvent()
		{
			this.selectedKeys = RequestCycleUtils.toStringList(RequestCycleUtils.getQueryParameterValues("values"));
		}

		public List<String> getSelectedKeys()
		{
			return selectedKeys;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@linkplain #newColumnReorderAjaxBehavior} callback
	 */
	protected static class ColumnReorderEvent extends JQueryEvent
	{
		private final int oldIndex;
		private final int newIndex;
		private final JSONObject object;

		public ColumnReorderEvent()
		{
			this.oldIndex = RequestCycleUtils.getQueryParameterValue("oldIndex").toInt(0);
			this.newIndex = RequestCycleUtils.getQueryParameterValue("newIndex").toInt(0);

			String data = RequestCycleUtils.getQueryParameterValue("data").toString();
			this.object = new JSONObject(data);
		}

		public int getOldIndex()
		{
			return this.oldIndex;
		}

		public int getNewIndex()
		{
			return this.newIndex;
		}

		public JSONObject getObject()
		{
			return this.object;
		}
	}
}
