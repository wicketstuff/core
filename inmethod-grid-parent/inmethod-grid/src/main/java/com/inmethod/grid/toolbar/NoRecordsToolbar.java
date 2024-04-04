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
package com.inmethod.grid.toolbar;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.datagrid.DataGrid;

/**
 * A toolbar that displays a "no records found" message when the data table contains no rows.
 * <p>
 * The message can be overridden by providing a resource with key
 * <code>datagrid.no-records-found</code>
 * 
 * @param <D>
 *            datasource model object type = grid type
 * @param <T>
 *            row/item model object type
 * 
 * @author Igor Vaynberg (ivaynberg)
 * @author Matej Knopp
 */
public class NoRecordsToolbar<D extends IDataSource<T>, T, S> extends AbstractToolbar<D, T, S>
{
	private static final long serialVersionUID = 1L;

	private static final IModel<String> DEFAULT_MESSAGE_MODEL = new ResourceModel(
		"datagrid.no-records-found", "No Records Found");

	/**
	 * Constructor
	 * 
	 * @param table
	 *            data table this toolbar will be attached to
	 */
	public NoRecordsToolbar(final DataGrid<D, T, S> table)
	{
		this(table, DEFAULT_MESSAGE_MODEL);
	}

	/**
	 * @param grid
	 *            data grid this toolbar will be attached to
	 * @param messageModel
	 *            model that will be used to display the "no records found" message
	 */
	public NoRecordsToolbar(final DataGrid<D, T, S> grid, IModel<String> messageModel)
	{
		super(grid, null);

		add(new Label("msg", messageModel));
	}

	/**
	 * Returns the {@link DataGrid} instance this toolbar belongs to.
	 * 
	 * @return {@link DataGrid} instance this toolbar belongs to.
	 */
	public DataGrid<D, T, S> getDataGrid()
	{
		return (DataGrid<D, T, S>)super.getGrid();
	}

	/**
	 * Only shows this toolbar when there are no rows
	 * 
	 * @see org.apache.wicket.Component#isVisible()
	 */
	@Override
	public boolean isVisible()
	{
		return getDataGrid().getTotalRowCount() == 0;
	}

}
