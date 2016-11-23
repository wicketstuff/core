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

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.utils.PropertyUtils;

/**
 * Provides a property column for a {@link DataTable}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class PropertyColumn extends AbstractColumn implements IExportableColumn
{
	private static final long serialVersionUID = 1L;

	private final String property;

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 */
	public PropertyColumn(String title)
	{
		this(Model.of(title), title, WIDTH);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public PropertyColumn(String title, int width)
	{
		this(Model.of(title), title, width);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 */
	public PropertyColumn(String title, String property)
	{
		this(Model.of(title), property, WIDTH);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param width the desired width of the column
	 */
	public PropertyColumn(String title, String property, int width)
	{
		this(Model.of(title), property, width);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 */
	public PropertyColumn(IModel<String> title, String property)
	{
		this(title, property, WIDTH);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param width the desired width of the column
	 */
	public PropertyColumn(IModel<String> title, String property, int width)
	{
		super(title, property, width);

		this.property = property;
	}

	@Override
	public String getField()
	{
		return PropertyUtils.escape(super.getField()); // fixes #56
	}

	/**
	 * Gets the value of the supplied object.<br>
	 * Implementation may call {@link #getField()}
	 *
	 * @param object the model object
	 * @return the value of the object
	 */
	public Object getValue(Object object)
	{
		return PropertyResolver.getValue(this.property, object); // if the object is null, null is returned
	}

	// Export //

	@Override
	public IModel<Object> newDataModel(IModel<?> rowModel)
	{
		return new PropertyModel<Object>(rowModel, this.property);
	}
}
