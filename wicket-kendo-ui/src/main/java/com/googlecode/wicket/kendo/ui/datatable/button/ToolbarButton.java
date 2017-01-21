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
package com.googlecode.wicket.kendo.ui.datatable.button;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.kendo.ui.datatable.DataTable;

/**
 * Provides a toolbar button object that can be used in {@link DataTable} toolbar
 *
 * @author Sebastien Briquet - sebfz1
 */
public class ToolbarButton extends AbstractButton
{
	private static final long serialVersionUID = 1L;

	// TODO built-in
	// The "cancel" built-in command reverts any data changes done by the end user.
	// The "create" command adds an empty data item to the grid.
	// The "save" command persists any data changes done by the end user.
	// The "excel" command exports the grid data in MS Excel format.
	// The "pdf" command exports the grid data in PDF format.

	/**
	 * Constructor for built-in commands
	 *
	 * @param name the button's name
	 */
	public ToolbarButton(String name)
	{
		super(name);
	}

	/**
	 * Constructor for built-in commands
	 *
	 * @param name the button's name
	 * @param text the button's text
	 */
	public ToolbarButton(String name, IModel<String> text)
	{
		super(name, text);
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param property the property used to retrieve the row's object value
	 */
	public ToolbarButton(String name, String property)
	{
		super(name, property);
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param text the button's text
	 * @param property the property used to retrieve the row's object value
	 */
	public ToolbarButton(String name, IModel<String> text, String property)
	{
		super(name, text, property);
	}

	// Properties //

	@Override
	public boolean isBuiltIn()
	{
		switch (this.getName())
		{
			case EDIT:
			case SAVE:
			case CREATE:
			case CANCEL:
			case DESTROY:
				return true;
			default:
				break;
		}

		return false;
	}

	// Methods //

	@Override
	public String toString()
	{
		return String.format("{ name: '%s', text: '%s' } ", this.getName(), this.getText().getObject());
	}

	// Events //

	/**
	 * Triggered when the toolbar-button is clicked
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param values the rows object value
	 */
	public void onClick(AjaxRequestTarget target, List<String> values)
	{
		// noop
	}
}
