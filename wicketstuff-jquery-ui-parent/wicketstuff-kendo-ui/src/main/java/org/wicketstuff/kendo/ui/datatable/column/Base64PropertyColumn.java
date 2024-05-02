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
package org.wicketstuff.kendo.ui.datatable.column;

import org.apache.wicket.model.IModel;
import org.wicketstuff.kendo.ui.datatable.DataTable;

/**
 * Provides a link property column for a {@link DataTable} for base64 image (png)
 *
 * @author Sebastien Briquet - sebfz1
 */
public class Base64PropertyColumn extends PropertyColumn
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 */
	public Base64PropertyColumn(String title)
	{
		super(title);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 */
	public Base64PropertyColumn(String title, String property)
	{
		super(title, property);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public Base64PropertyColumn(String title, int width)
	{
		super(title, width);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 */
	public Base64PropertyColumn(IModel<String> title, String property)
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
	public Base64PropertyColumn(String title, String property, int width)
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
	public Base64PropertyColumn(IModel<String> title, String property, int width)
	{
		super(title, property, width);
	}

	@Override
	public String getTemplate()
	{
		final String field = this.getField();

		final StringBuilder builder = new StringBuilder();
		builder.append("<div class='grid-cell' data-container-for='").append(field).append("'>");
		builder.append("# if (data.").append(field).append(") { #");
		builder.append("<img src='data:image/png;base64,${data.").append(field).append("}' />");
		builder.append("# } #");
		builder.append("</div>");

		return builder.toString();
	}
}
