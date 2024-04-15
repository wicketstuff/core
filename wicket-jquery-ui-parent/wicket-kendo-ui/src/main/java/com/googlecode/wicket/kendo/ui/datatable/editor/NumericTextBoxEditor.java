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

import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a {@link IKendoEditor} editor, based on a {@code kendoNumericTextBox}
 * 
 * @author Sebastien Briquet - sebfz1
 * @see KendoEditorHeaderItem
 */
public class NumericTextBoxEditor implements IKendoEditor
{
	protected final String name;
	protected final Options options;

	/**
	 * Constructor, for inline inclusion
	 */
	public NumericTextBoxEditor()
	{
		this("", new Options());
	}

	/**
	 * Constructor, for inline inclusion
	 * 
	 * @param options the {@link Options}
	 */
	public NumericTextBoxEditor(Options options)
	{
		this("", options);
	}

	/**
	 * Constructor
	 * 
	 * @param name the name of the function
	 */
	public NumericTextBoxEditor(String name)
	{
		this(name, new Options());
	}

	/**
	 * Constructor
	 * 
	 * @param name the name of the function
	 * @param options the {@link Options}
	 */
	public NumericTextBoxEditor(String name, Options options)
	{
		this.name = name;
		this.options = options;
	}

	@Override
	public String toString()
	{
		return "function " + this.name + "(container, options) { " // lf
				+ "$('<input data-bind=\"value:' + options.field + '\"/>')" // lf
				+ ".appendTo(container).kendoNumericTextBox(" + this.options + ");" // lf
				+ "}";
	}
}
