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
package com.googlecode.wicket.kendo.ui.repeater.listview;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.googlecode.wicket.kendo.ui.repeater.dataview.DataView;

/**
 * Event listener shared by the {@link DataView} widget and the {@link ListViewBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 */
public interface IListViewListener extends IClusterable
{
	/**
	 * Indicates whether item(s) can be selected.<br>
	 * If true, the {@link #onChange(AjaxRequestTarget, JSONArray)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isSelectable();

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

	/**
	 * Triggered when item(s) is/are selected
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param items the {@link JSONArray} of retrieved {@link JSONObject}{@code s}
	 */
	void onChange(AjaxRequestTarget target, JSONArray items);
}
