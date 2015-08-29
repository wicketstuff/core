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
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.util.io.IClusterable;

/**
 * Event listener shared by the {@link DataTable} widget and the {@link DataTableBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 */
public interface IDataTableListener extends IClusterable
{
	/**
	 * Triggered when a toolbar button is clicked.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the button text (warning, it is not the 'name' property)
	 * @param values the list of retrieved values
	 */
	void onClick(AjaxRequestTarget target, String button, List<String> values);

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
	 * @param target
	 */
	void onCancel(AjaxRequestTarget target);

	/**
	 * Triggered when datasource 'create' function is raised
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param object the {@link JSONObject} holding the row data
	 */
	void onCreate(AjaxRequestTarget target, JSONObject object);

	/**
	 * Triggered when datasource 'update' function is raised
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
