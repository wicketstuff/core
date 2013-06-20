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
package com.googlecode.wicket.jquery.ui.kendo.datatable;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Event listener shared by the {@link DataTable} widget and the {@link DataTableBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 */
interface IDataTableListener
{
	/**
	 * Triggered when a button is clicked.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the button being clicked
	 * @param value value retrieved from the row, according to the property supplied to the {@link ColumnButton} that fired the event
	 */
	void onClick(AjaxRequestTarget target, ColumnButton button, String value);
}
