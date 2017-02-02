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
package com.googlecode.wicket.kendo.ui.datatable.editor;

import org.json.JSONArray;

import com.googlecode.wicket.jquery.core.utils.JsonUtils;

/**
 * Provides a simple {@link IKendoEditor} editor, based on a list of string (not on json objects)
 * 
 * @author Sebastien Briquet - sebfz1
 * @see KendoEditorHeaderItem
 */
public class DropDownListEditor implements IKendoEditor
{
	protected final String name;
	protected final JSONArray array;

	/**
	 * Constructor, for inline inclusion
	 * 
	 * @param values the values
	 */
	public DropDownListEditor(String[] values)
	{
		this("", values);
	}

	/**
	 * Constructor, for inline inclusion
	 * 
	 * @param values the values
	 */
	public DropDownListEditor(Enum<?>[] values)
	{
		this("", values);
	}

	/**
	 * Constructor
	 * 
	 * @param name the name of the function
	 * @param values the values
	 */
	public DropDownListEditor(String name, String[] values)
	{
		this.name = name;
		this.array = JsonUtils.toArray(values);
	}

	/**
	 * Constructor
	 * 
	 * @param name the name of the function
	 * @param values the enum values
	 */
	public DropDownListEditor(String name, Enum<?>[] values)
	{
		this.name = name;
		this.array = JsonUtils.toArray(values);
	}

	@Override
	public String toString()
	{
		return "function " + this.name + "(container, options) { " // lf
				+ "jQuery('<input required data-bind=\"value:' + options.field + '\"/>')" // lf
				+ ".appendTo(container)" // lf
				+ ".kendoDropDownList({ " // lf
				+ "  autoBind: false," // lf
				+ "  dataSource: " + this.array // lf
				+ " }); " // lf
				+ "}";
	}
}
