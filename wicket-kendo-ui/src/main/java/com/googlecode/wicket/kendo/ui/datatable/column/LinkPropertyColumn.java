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

import org.apache.wicket.model.IModel;

import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandButton;
import com.googlecode.wicket.kendo.ui.utils.KendoUrlUtils;

/**
 * Provides a link property column for a {@link DataTable} bound to a {@link CommandButton} action/url
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class LinkPropertyColumn extends PropertyColumn
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 */
	protected LinkPropertyColumn(String title)
	{
		super(title);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 */
	protected LinkPropertyColumn(String title, String property)
	{
		super(title, property);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	protected LinkPropertyColumn(String title, int width)
	{
		super(title, width);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 */
	protected LinkPropertyColumn(IModel<String> title, String property)
	{
		super(title, property);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param width the desired width of the column
	 */
	protected LinkPropertyColumn(String title, String property, int width)
	{
		super(title, property, width);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param width the desired width of the column
	 */
	protected LinkPropertyColumn(IModel<String> title, String property, int width)
	{
		super(title, property, width);
	}

	@Override
	public String getTemplate()
	{
		final String url = KendoUrlUtils.unescape(this.getCallbackUrl());
		final StringBuilder builder = new StringBuilder();

		builder.append("<div class='grid-cell' data-container-for='").append(this.getField()).append("'>");
		builder.append("<a href='").append(url).append("'>");
		builder.append("#: ").append(this.getField()).append(" #");
		builder.append("</a>");
		builder.append("</div>");

		return builder.toString();
	}

	/**
	 * Gets the {@link CommandButton}'s callback url<br>
	 * {@code #:data.myproperty#} is allowed (should not contain spaces)
	 * 
	 * @return the callback url
	 */
	protected abstract String getCallbackUrl();
}
