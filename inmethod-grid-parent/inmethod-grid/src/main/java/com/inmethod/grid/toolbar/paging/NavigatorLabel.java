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
package com.inmethod.grid.toolbar.paging;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.datagrid.DataGrid;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.io.IClusterable;

/**
 * Label that provides Showing x to y of z message given for a DataGrid. The message can be
 * overridden using the <code>NavigatorLabel</code> property key, the default message is used is of
 * the format <code>Showing ${from} to ${to} of ${of}</code>. The message can also be configured
 * pragmatically by setting it as the model object of the label.
 *
 * @author Igor Vaynberg (ivaynberg)
 * @author Matej Knopp
 *
 */
public class NavigatorLabel extends Label
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            component id
	 * @param table
	 *            pageable view
	 */
	public <D extends IDataSource<T>, T> NavigatorLabel(final String id, final DataGrid<D, T, ?> table)
	{
		super(id);
		setDefaultModel(new StringResourceModel("NavigatorLabel", this,
			new Model<LabelModelObject<D, T>>(new LabelModelObject<D, T>(table))));
	}

	private class LabelModelObject<D extends IDataSource<T>, T> implements IClusterable
	{
		private static final long serialVersionUID = 1L;
		private final DataGrid<D, T, ?> table;

		/**
		 * Construct.
		 *
		 * @param table
		 */
		public LabelModelObject(DataGrid<D, T, ?> table)
		{
			this.table = table;
		}

		/**
		 * @return "z" in "Showing x to y of z"
		 */
		public String getOf()
		{
			long total = table.getTotalRowCount();
			return total != -1 ? "" + total : getString("unknown", null, "unknown");
		}

		/**
		 * @return "x" in "Showing x to y of z"
		 */
		public long getFrom()
		{
			if (table.getTotalRowCount() == 0)
			{
				return 0;
			}
			return table.getCurrentPage() * table.getRowsPerPage() + 1;
		}

		/**
		 * @return "y" in "Showing x to y of z"
		 */
		public long getTo()
		{
			if (table.getTotalRowCount() == 0)
			{
				return 0;
			}
			else
			{
				long count = getFrom() + table.getCurrentPageItemCount() - 1;
				return count;
			}
		}

	}
}
