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
package com.googlecode.wicket.kendo.ui.datatable.behavior;

import org.apache.wicket.Component;

import com.googlecode.wicket.jquery.core.JQueryAbstractBehavior;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.DataTableBehavior;

/**
 * Provides a {@link DataTable} {@code Behavior} that allows to register a 'dataBound' callback.<br>
 * <b>Warning:</b> In order to work correctly, you should use {@code DataTable#refresh} instead of {@code IPartialPageRequestHandler#add(datatable)}
 * 
 * @author Sebastien Briquet - sebfz1
 */
public abstract class DataBoundBehavior extends JQueryAbstractBehavior
{
	private static final long serialVersionUID = 1L;

	private String widget;

	public DataBoundBehavior()
	{
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		this.widget = KendoUIBehavior.widget(component, DataTableBehavior.METHOD);
	}

	/**
	 * Gets the Kendo UI widget
	 * 
	 * @return the Kendo UI widget
	 */
	protected String widget()
	{
		return this.widget;
	}

	@Override
	protected String $()
	{
		return String.format("var $w = %s; $w.bind('dataBound', %s); $w.dataSource.fetch();", this.widget(), this.getDataBoundCallback());
	}

	/**
	 * Gets the callback/handler to be triggered on 'dataBound' event
	 * 
	 * @return statement like function(e) {...}
	 */
	protected abstract String getDataBoundCallback();
}
