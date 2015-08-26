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
package com.googlecode.wicket.kendo.ui.datatable.behavior;

import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.DataTableBehavior;

/**
 * Provides a {@link DataTable} {@code Behavior} that will hide the datatable if datasource returns no row.<br/>
 * <b>Warning:</b> In order to work correctly, you should use {@code DataTable#refresh} in place of {@code AjaxRequestTarget#add(datatable)}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class HideIfNoRecordBehavior extends DataBoundBehavior
{
	private static final long serialVersionUID = 1L;

	public HideIfNoRecordBehavior()
	{
		super(DataTableBehavior.METHOD + "-hide");
	}

	@Override
	protected String getDataBoundCallback()
	{
		return "function(e) { if (this.dataSource.total() > 0) { this.wrapper.show(); } else { this.wrapper.hide(); } }";
	}
}
