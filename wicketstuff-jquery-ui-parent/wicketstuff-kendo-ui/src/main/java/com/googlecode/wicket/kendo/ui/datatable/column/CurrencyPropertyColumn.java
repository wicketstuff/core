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

/**
 * Provides a currency property column for a {@link DataTable}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class CurrencyPropertyColumn extends PropertyColumn
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 */
	public CurrencyPropertyColumn(String title)
	{
		super(title);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public CurrencyPropertyColumn(String title, int width)
	{
		super(title, width);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 */
	public CurrencyPropertyColumn(String title, String property)
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
	public CurrencyPropertyColumn(String title, String property, int width)
	{
		super(title, property, width);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param property the object property name
	 */
	public CurrencyPropertyColumn(IModel<String> title, String property)
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
	public CurrencyPropertyColumn(IModel<String> title, String property, int width)
	{
		super(title, property, width);
	}

	@Override
	public String getType()
	{
		return "number";
	}

	@Override
	public String getFormat()
	{
		return "{0:c2}";
	}
}
