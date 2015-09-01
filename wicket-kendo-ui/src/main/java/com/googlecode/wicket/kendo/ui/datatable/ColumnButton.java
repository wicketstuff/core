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
package com.googlecode.wicket.kendo.ui.datatable;

import org.apache.wicket.model.IModel;

/**
 * Provides the button object that can be used in {@link DataTable}
 *
 * @author Sebastien Briquet - sebfz1
 * @deprecated renamed ColumnButton to CommandButton
 * TODO: 6.23.0/7.2.0 - remove
 */
@Deprecated
public class ColumnButton extends CommandButton
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for built-in commands (no property supplied)
	 *
	 * @param name the button's name
	 */
	public ColumnButton(String name)
	{
		super(name);
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param property the property used to retrieve the row's object value
	 */
	public ColumnButton(String name, String property)
	{
		super(name, property);
	}

	/**
	 * Constructor for built-in commands (no property supplied)
	 *
	 * @param name the button's name
	 * @param text the button's text
	 */
	public ColumnButton(String name, IModel<String> text)
	{
		super(name, text);
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param text the button's text
	 * @param property the property used to retrieve the row's object value
	 */
	public ColumnButton(String name, IModel<String> text, String property)
	{
		super(name, text, property);
	}
}
