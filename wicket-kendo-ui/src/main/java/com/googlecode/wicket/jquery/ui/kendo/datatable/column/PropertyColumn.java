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
package com.googlecode.wicket.jquery.ui.kendo.datatable.column;

import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.googlecode.wicket.jquery.ui.kendo.datatable.DataTable;

/**
 * Provides a property column for a {@link DataTable}
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 */
public class PropertyColumn<T> extends AbstractColumn<T>
{
	private static final long serialVersionUID = 1L;

	private final ITextRenderer<T> renderer;

	/**
	 * Constructor
	 * @param title the text of the column header
	 */
	public PropertyColumn(String title)
	{
		this(title, title, AbstractColumn.WIDTH);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public PropertyColumn(String title, int width)
	{
		this(title, title, width);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param property the object property name
	 */
	public PropertyColumn(String title, String property)
	{
		this(title, property, AbstractColumn.WIDTH);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param width the desired width of the column
	 */
	public PropertyColumn(String title, String property, int width)
	{
		super(title, property, width);

		this.renderer = new TextRenderer<T>(property);
	}

	/**
	 * Gets the value of the supplied object.<br/>
	 * Implementation may call {@link #getField()}
	 *
	 * @param object the model object
	 * @return the value of the object
	 */
	public final String getValue(T object)
	{
		return this.renderer.getText(object);
	}

}
