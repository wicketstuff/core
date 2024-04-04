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


/**
 * Provides a {@link IKendoEditor} editor, based on a textarea
 * 
 * @author Sebastien Briquet - sebfz1
 * @see KendoEditorHeaderItem
 */
public class TextAreaEditor implements IKendoEditor
{
	protected final String name;

	/**
	 * Constructor, for inline inclusion
	 */
	public TextAreaEditor()
	{
		this("");
	}

	/**
	 * Constructor
	 * 
	 * @param name the name of the function
	 */
	public TextAreaEditor(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "function " + this.name + "(container, options) { " // lf
				+ "jQuery('<textarea class=\"k-textbox\" data-bind=\"value:' + options.field + '\"></textarea>')" // lf
				+ ".appendTo(container); " // lf
				+ "}";
	}
}
