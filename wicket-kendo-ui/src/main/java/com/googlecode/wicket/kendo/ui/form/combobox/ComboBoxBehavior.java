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
package com.googlecode.wicket.kendo.ui.form.combobox;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.event.ISelectionChangedListener;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.ajax.OnChangeAjaxBehavior;
import com.googlecode.wicket.kendo.ui.ajax.OnChangeAjaxBehavior.ChangeEvent;

/**
 * Provides a {@value #METHOD} behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class ComboBoxBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "kendoComboBox";

	private final ISelectionChangedListener listener;
	private KendoDataSource dataSource;

	private JQueryAjaxBehavior onChangeAjaxBehavior = null;

	public ComboBoxBehavior(String selector, ISelectionChangedListener listener)
	{
		super(selector, METHOD);
		
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

		// events //
		if (this.listener.isSelectionChangedEventEnabled())
		{
			this.onChangeAjaxBehavior = new OnChangeAjaxBehavior(this);
			component.add(this.onChangeAjaxBehavior);
		}
	}

	// Properties //

	@Override
	public boolean isEnabled(Component component)
	{
		return component.isEnabledInHierarchy();
	}

	protected abstract CharSequence getDataSourceUrl();

	/**
	 * Gets the datasource name
	 * 
	 * @return the datasource name
	 */
	public String getDataSourceName()
	{
		if (this.dataSource != null)
		{
			return this.dataSource.getName();
		}

		return null;
	}

	/**
	 * Gets the datasource token
	 * 
	 * @return the datasource token
	 */
	public String getDataSourceToken()
	{
		if (this.dataSource != null)
		{
			return this.dataSource.getToken();
		}

		return null;
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("autoBind", true); // immutable

		// events //
		if (this.onChangeAjaxBehavior != null)
		{
			this.setOption("change", this.onChangeAjaxBehavior.getCallbackFunction());
		}

		// data-source //
		// TODO onConfigure (see MultiSelectBehavior)
		this.setOption("dataSource", this.getDataSourceName());

		if (this.isEnabled(component))
		{
			this.dataSource.setTransportReadUrl(this.getDataSourceUrl());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ChangeEvent)
		{
			this.listener.onSelectionChanged(target);
		}
	}
}
