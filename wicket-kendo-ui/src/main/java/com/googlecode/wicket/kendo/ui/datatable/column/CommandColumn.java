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

import java.util.List;

import org.apache.wicket.model.IModel;

import com.googlecode.wicket.kendo.ui.datatable.CommandButton;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;

/**
 * Provides a commands column for a {@link DataTable}
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class CommandColumn extends AbstractColumn
{
	private static final long serialVersionUID = 1L;

	private List<CommandButton> buttons = null;

	/**
	 * Constructor
	 * 
	 * @param title the text of the column header
	 */
	public CommandColumn(String title)
	{
		super(title);
	}

	/**
	 * Constructor
	 * 
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public CommandColumn(String title, int width)
	{
		super(title, width);
	}

	/**
	 * Constructor
	 * 
	 * @param title the text of the column header
	 */
	public CommandColumn(IModel<String> title)
	{
		super(title);
	}

	/**
	 * Constructor
	 * 
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public CommandColumn(IModel<String> title, int width)
	{
		super(title, width);
	}

	/**
	 * Gets a new {@link List} a {@link CommandButton}
	 *
	 * @return a new {@link List} a {@link CommandButton}
	 */
	protected abstract List<CommandButton> newButtons();

	/**
	 * Gets the list of {@link CommandButton}
	 *
	 * @return the list of {@link CommandButton}
	 */
	public final synchronized List<CommandButton> getButtons()
	{
		if (this.buttons == null)
		{
			this.buttons = this.newButtons();
		}

		return this.buttons;
	}
}
