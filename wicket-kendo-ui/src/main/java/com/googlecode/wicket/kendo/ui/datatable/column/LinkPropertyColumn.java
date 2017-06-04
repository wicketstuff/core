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
package com.googlecode.wicket.kendo.ui.datatable.column;

import java.util.List;

import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.DataTableBehavior;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandAjaxBehavior;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandButton;

/**
 * Provides a link property column for a {@link DataTable} bound to a {@link CommandButton} action/url
 *
 * @author Sebastien Briquet - sebfz1
 */
public class LinkPropertyColumn extends PropertyColumn
{
	private static final long serialVersionUID = 1L;

	private final DataTable<?> datatable;
	private final CommandButton button;

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param datatable the holding {@link DataTable}
	 * @param button the {@link CommandButton}
	 */
	public LinkPropertyColumn(String title, DataTable<?> datatable, CommandButton button)
	{
		super(title);

		this.datatable = datatable;
		this.button = button;
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param datatable the holding {@link DataTable}
	 * @param button the {@link CommandButton}
	 */
	public LinkPropertyColumn(String title, String property, DataTable<?> datatable, CommandButton button)
	{
		super(title, property);

		this.datatable = datatable;
		this.button = button;
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 * @param datatable the holding {@link DataTable}
	 * @param button the {@link CommandButton}
	 */
	public LinkPropertyColumn(String title, int width, DataTable<?> datatable, CommandButton button)
	{
		super(title, width);

		this.datatable = datatable;
		this.button = button;
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param datatable the holding {@link DataTable}
	 * @param button the {@link CommandButton}
	 */
	public LinkPropertyColumn(IModel<String> title, String property, DataTable<?> datatable, CommandButton button)
	{
		super(title, property);

		this.datatable = datatable;
		this.button = button;
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param width the desired width of the column
	 * @param datatable the holding {@link DataTable}
	 * @param button the {@link CommandButton}
	 */
	public LinkPropertyColumn(String title, String property, int width, DataTable<?> datatable, CommandButton button)
	{
		super(title, property, width);

		this.datatable = datatable;
		this.button = button;
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param width the desired width of the column
	 * @param datatable the holding {@link DataTable}
	 * @param button the {@link CommandButton}
	 */
	public LinkPropertyColumn(IModel<String> title, String property, int width, DataTable<?> datatable, CommandButton button)
	{
		super(title, property, width);

		this.datatable = datatable;
		this.button = button;
	}

	@Override
	public String getTemplate()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("<div class='grid-cell' data-container-for='").append(this.getField()).append("'>");
		builder.append("<a href='").append(this.getCallbackUrl()).append("&value=#: data.").append(this.button.getProperty()).append(" #'>");
		builder.append("#: ").append(this.getField()).append(" #");
		builder.append("</a>");
		builder.append("</div>");

		return builder.toString();
	}

	/**
	 * Gets the {@link CommandButton}'s callback url
	 * 
	 * @return the callback url
	 */
	private CharSequence getCallbackUrl()
	{
		List<CommandAjaxBehavior> behaviors = this.datatable.getBehaviors(CommandAjaxBehavior.class);
		JQueryAjaxBehavior behavior = DataTableBehavior.getCommandAjaxBehavior(this.button, behaviors);

		return behavior.getCallbackUrl();
	}
}
