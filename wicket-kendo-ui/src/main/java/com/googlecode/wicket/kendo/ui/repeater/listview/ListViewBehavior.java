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
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a {@value #METHOD} behavior
 * 
 * @author Sebastien Briquet - sebfz1
 */
public abstract class ListViewBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoListView";

	@SuppressWarnings("unused")
	private final IListViewListener listener;
	private final KendoDataSource dataSource;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IListViewListener}
	 */
	public ListViewBehavior(String selector, IListViewListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link IListViewListener}
	 */
	public ListViewBehavior(String selector, Options options, IListViewListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");

		// data source //
		this.dataSource = new KendoDataSource("datasource" + selector);
		this.add(this.dataSource);
	}

	// Events //

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		// noop
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

		// data-source //
		this.onConfigure(this.dataSource);
		this.setOption("dataSource", this.dataSource.getName());

		this.dataSource.set("pageSize", this.getRowCount());
		this.dataSource.setTransportRead(this.getReadCallbackFunction());
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
}
