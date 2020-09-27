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
package com.googlecode.wicket.kendo.ui.repeater.listview;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.datatable.DataSourceAjaxBehavior;
import com.googlecode.wicket.kendo.ui.datatable.DataSourceEvent.CreateEvent;
import com.googlecode.wicket.kendo.ui.datatable.DataSourceEvent.DeleteEvent;
import com.googlecode.wicket.kendo.ui.datatable.DataSourceEvent.UpdateEvent;
import com.googlecode.wicket.kendo.ui.repeater.ChangeEvent;

/**
 * Provides a {@value #METHOD} behavior
 * 
 * @author Sebastien Briquet - sebfz1
 */
public abstract class ListViewBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoListView";

	private final IListViewListener listener;
	private final KendoDataSource dataSource;

	private JQueryAjaxBehavior onCreateAjaxBehavior;
	private JQueryAjaxBehavior onUpdateAjaxBehavior;
	private JQueryAjaxBehavior onDeleteAjaxBehavior;

	private JQueryAjaxBehavior onChangeAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IListViewListener}
	 * @param dataSource the {@link KendoDataSource}
	 */
	public ListViewBehavior(String selector, KendoDataSource dataSource, IListViewListener listener)
	{
		this(selector, dataSource, listener, new Options());
	}

	/**
	 * Main Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IListViewListener}
	 * @param options the {@link Options}
	 * @param dataSource the {@link KendoDataSource}
	 */
	public ListViewBehavior(String selector, KendoDataSource dataSource, IListViewListener listener, Options options)
	{
		super(selector, METHOD, options);

		// listener //
		this.listener = Args.notNull(listener, "listener");

		// datasource //
		this.dataSource = dataSource;
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// data source //
		this.add(this.dataSource);

		// events //
		this.onCreateAjaxBehavior = this.newOnCreateAjaxBehavior(this);
		component.add(this.onCreateAjaxBehavior);

		this.onUpdateAjaxBehavior = this.newOnUpdateAjaxBehavior(this);
		component.add(this.onUpdateAjaxBehavior);

		this.onDeleteAjaxBehavior = this.newOnDeleteAjaxBehavior(this);
		component.add(this.onDeleteAjaxBehavior);

		if (this.listener.isSelectable())
		{
			this.onChangeAjaxBehavior = this.newOnChangeAjaxBehavior(this, this.getDataSourceName());
			component.add(this.onChangeAjaxBehavior);
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
	protected abstract CharSequence getProviderUrl();

	/**
	 * Gets the datasource name
	 * 
	 * @return the datasource name
	 */
	public String getDataSourceName()
	{
		return this.dataSource.getName();
	}

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
		// schema //
		Options schema = new Options();
		schema.set("data", Options.asString("results"));
		schema.set("total", Options.asString("__count"));

		// data-source //
		this.setOption("dataSource", this.getDataSourceName());

		this.dataSource.set("schema", schema);
		this.dataSource.set("pageSize", this.getRowCount());
		this.dataSource.set("serverPaging", true);
		this.dataSource.setTransportRead(this.getReadCallbackFunction());
		this.dataSource.setTransportCreate(this.onCreateAjaxBehavior.getCallbackFunction());
		this.dataSource.setTransportUpdate(this.onUpdateAjaxBehavior.getCallbackFunction());
		this.dataSource.setTransportDelete(this.onDeleteAjaxBehavior.getCallbackFunction());

		this.onConfigure(this.dataSource); // last chance to set options

		// events //
		if (this.onChangeAjaxBehavior != null)
		{
			this.setOption("change", this.onChangeAjaxBehavior.getCallbackFunction());
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

		if (event instanceof ChangeEvent)
		{
			this.listener.onChange(target, ((ChangeEvent) event).getItems());
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'create' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnCreateAjaxBehavior(IJQueryAjaxAware source)
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
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'update' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnUpdateAjaxBehavior(IJQueryAjaxAware source)
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
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'delete' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDeleteAjaxBehavior(IJQueryAjaxAware source)
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
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'select' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param datasource the datasource name
	 * @return a new {@code OnChangeAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnChangeAjaxBehavior(IJQueryAjaxAware source, String datasource)
	{
		return new OnChangeAjaxBehavior(source, datasource);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'select' event
	 */
	protected static class OnChangeAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		private final String datasource;

		public OnChangeAjaxBehavior(IJQueryAjaxAware source, String datasource)
		{
			super(source);

			this.datasource = datasource;
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.resolved("items", "items") };
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			String statement = "";
			statement += "var $view = " + this.datasource + ".view();";
			statement += "var _rows = jQuery.map(this.select(), function(item) { var index = jQuery(item).index(); return $view[index]; });";
			statement += "var items = kendo.stringify(_rows);\n";

			return statement + super.getCallbackFunctionBody(parameters);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ChangeEvent();
		}
	}
}
