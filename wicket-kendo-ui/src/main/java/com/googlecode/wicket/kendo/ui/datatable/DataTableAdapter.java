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

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;

import com.googlecode.wicket.kendo.ui.datatable.button.CommandButton;
import com.googlecode.wicket.kendo.ui.datatable.button.ToolbarButton;

/**
 * Adapter class for {@link IDataTableListener}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class DataTableAdapter implements IDataTableListener
{
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isSelectable()
	{
		return false;
	}

	@Override
	public void onClick(AjaxRequestTarget target, ToolbarButton button, List<String> values)
	{
		// noop
	}

	@Override
	public void onClick(AjaxRequestTarget target, CommandButton button, String value)
	{
		// noop
	}

	@Override
	public void onCancel(AjaxRequestTarget target)
	{
		// noop		
	}
	
	@Override
	public void onChange(AjaxRequestTarget target, JSONArray items)
	{
		// noop
	}

	@Override
	public void onChecked(AjaxRequestTarget target, Object[] selectedKeys) {
		// noop		
	}
	
	@Override
	public void onColumnReorder(AjaxRequestTarget target, int oldIndex, int newIndex, JSONObject column)
	{
		// noop		
	}

	@Override
	public void onCreate(AjaxRequestTarget target, JSONObject object)
	{
		// noop		
	}

	@Override
	public void onUpdate(AjaxRequestTarget target, JSONObject object)
	{
		// noop
	}

	@Override
	public void onDelete(AjaxRequestTarget target, JSONObject object)
	{
		// noop		
	}
}
