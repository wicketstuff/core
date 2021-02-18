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
import org.apache.wicket.util.io.IClusterable;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;

import com.googlecode.wicket.kendo.ui.datatable.button.CommandButton;
import com.googlecode.wicket.kendo.ui.datatable.button.ToolbarButton;
import com.googlecode.wicket.kendo.ui.datatable.column.CheckboxColumn;

/**
 * Event listener shared by the {@link DataTable} widget and the {@link DataTableBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 */
public interface IDataTableListener extends IClusterable
{
	/**
	 * Indicates whether item(s) can be selected.<br>
	 * If true, the {@link #onChange(AjaxRequestTarget, JSONArray)} event will be triggered.<br>
	 * If a {@link CheckboxColumn} has been provided, {@link #onChecked(AjaxRequestTarget, Object[])} will be triggered instead.
	 *
	 * @return false by default
	 */
	boolean isSelectable();

	/**
	 * Triggered when a toolbar button is clicked.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the button name
	 * @param values the list of retrieved values
	 */
	void onClick(AjaxRequestTarget target, ToolbarButton button, List<String> values);

	/**
	 * Triggered when a column button is clicked.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the button being clicked
	 * @param value value retrieved from the row, according to the property supplied to the {@link CommandButton} that fired the event
	 */
	void onClick(AjaxRequestTarget target, CommandButton button, String value);

	/**
	 * Triggered when an editing is cancelled
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	void onCancel(AjaxRequestTarget target);

	/**
	 * Triggered when item(s) is/are selected
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param items the {@link JSONArray} of retrieved {@link JSONObject}{@code s}
	 */
	void onChange(AjaxRequestTarget target, JSONArray items);

    /**
     * Triggered when a checkbox is 'checked'.
     * 
     * @param target the {@link AjaxRequestTarget}
     * @param selectedKeys the selected keys
     * @see CheckboxColumn
     */
    void onChecked(AjaxRequestTarget target, List<String> selectedKeys);
 
	/**
	 * Triggered when the user changes the order of a column.
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param oldIndex the old column index
	 * @param newIndex the new column index
	 * @param column the column's {@link JSONObject}
	 */
	void onColumnReorder(AjaxRequestTarget target, int oldIndex, int newIndex, JSONObject column);

	/**
	 * Triggered when datasource 'create' function is raised
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param object the {@link JSONObject} holding the row data
	 */
	void onCreate(AjaxRequestTarget target, JSONObject object);

	/**
	 * Triggered when datasource 'update' function is raised<br>
	 * If {@code batch} mode is used, the {@code object} is a {@link JSONArray} that might be retrieved this way: {@code object.optJSONArray("models")}
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param object the {@link JSONObject} holding the row data
	 */
	void onUpdate(AjaxRequestTarget target, JSONObject object);

	/**
	 * Triggered when datasource 'destroy' function is raised
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param object the {@link JSONObject} holding the row data
	 */
	void onDelete(AjaxRequestTarget target, JSONObject object);
}
