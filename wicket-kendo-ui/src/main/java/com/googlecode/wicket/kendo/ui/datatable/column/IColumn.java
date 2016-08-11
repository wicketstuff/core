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

import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.editor.IKendoEditor;

/**
 * Specifies the column definition of a {@link DataTable}
 *
 * @author Sebastien Briquet - sebfz1
 */
public interface IColumn extends IClusterable
{
	/**
	 * Gets the text of the column header
	 *
	 * @return the column title
	 */
	String getTitle();

	/**
	 * Get the field in the data set that this column should be bound to.
	 *
	 * @return the field name
	 */
	String getField();

	/**
	 * TODO javadoc, implement & use
	 */
	// void getDefaultValue();

	/**
	 * Gets the desired width of the column.
	 *
	 * @return the column's width
	 */
	int getWidth();

	/**
	 * The pixel screen width below which the column will be hidden.<br/>
	 * The setting takes precedence over the hidden setting, so the two should not be used at the same time.
	 * 
	 * @return 0 by default
	 */
	int getMinScreenWidth();

	/**
	 * Provides a way to specify a custom editing UI for the column.
	 * 
	 * @return a {@code function} or {@code null} if it does not apply
	 */
	IKendoEditor getEditor();

	/**
	 * The format that is applied to the value before it is displayed. Takes the form "{0:format}" where "format" is a standard number format, custom number format, standard date format or a custom date format.
	 *
	 * @return the format or {@code null} if it does not apply
	 * @see <a href="http://docs.kendoui.com/api/framework/kendo#standard-number-formats">standard-number-formats</a>
	 * @see <a href="http://docs.kendoui.com/api/framework/kendo#custom-number-formats">custom-number-formats</a>
	 * @see <a href="http://docs.kendoui.com/api/framework/kendo#standard-date-formats">standard-date-formats</a>
	 * @see <a href="http://docs.kendoui.com/api/framework/kendo#custom-date-formats">custom-date-formats</a>
	 */
	String getFormat();

	/**
	 * The template which renders the column content.<br/>
	 * ie: {@code return "<a href='?id=#:data.id#'>#:data.id#</a>"}<br/>
	 * TODO: make it work for function(?)
	 *
	 * @return the template or {@code null} if it does not apply
	 */
	String getTemplate();

	/**
	 * HTML attributes of the table cell rendered for the column.
	 * 
	 * @return a JSON string object
	 */
	String getAttributes();

	/**
	 * The template which renders the footer table cell for the column.
	 * 
	 * @return the template or {@code null} if it does not apply
	 * @see <a href="http://docs.telerik.com/kendo-ui/api/javascript/ui/grid#configuration-columns.footerTemplate">columns.footerTemplate</a>
	 */
	String getFooterTemplate();

	/**
	 * If set to true a filter menu will be displayed for this column when filtering is enabled.<br/>
	 * If set to false the filter menu will not be displayed.<br/>
	 * By default a filter menu is displayed for all columns when filtering is enabled via the filterable option.
	 *
	 * @return the {@code filterable} object; ie: {@code true} or <code>{ extra: false }</code>
	 * @see <a href="http://docs.kendoui.com/api/web/grid#configuration-columns.filterable">configuration-columns.filterable</a>
	 */
	String getFilterable();

	/**
	 * If set to true the column will be visible in the grid column menu. By default the column menu includes all data-bound columns (ones that have their field set).
	 *
	 * @return the {@code menu} object; ie: {@code false} or <code>{ extra: false }</code>
	 * @see <a href="http://docs.kendoui.com/api/web/grid#configuration-columns.menu">configuration-columns.menu</a>
	 */
	String getMenu();

	/**
	 * The aggregate(s) which are calculated when the grid is grouped by the columns field. The supported aggregates are "average", "count", "max", "min" and "sum".
	 *
	 * @return the list of aggregates
	 * @see <a href="http://docs.kendoui.com/api/web/grid#configuration-columns.aggregates">configuration-columns.aggregates</a>
	 */
	List<String> getAggregates();

	/**
	 * The template which renders the group header when the grid is grouped by the column field. By default the name of the field and the current group value is displayed.
	 *
	 * @return the text template
	 * @see <a href="http://docs.kendoui.com/api/web/grid#configuration-columns.groupHeaderTemplate">configuration-columns.groupHeaderTemplate</a>
	 */
	String getGroupHeaderTemplate();

	/**
	 * The template which renders the group footer when the grid is grouped by the column field. By default the group footer is not displayed.
	 *
	 * @return the text template
	 * @see <a href="http://docs.kendoui.com/api/web/grid#configuration-columns.groupFooterTemplate">configuration-columns.groupFooterTemplate</a>
	 */
	String getGroupFooterTemplate();

	// schema model //

	/**
	 * Indicates whether the column is editable
	 * 
	 * @return {@code null} by default, meaning not specified
	 */
	Boolean isEditable();

	/**
	 * Indicates whether the column is nullable
	 * 
	 * @return {@code null} by default, meaning not specified
	 */
	Boolean isNullable();

	/**
	 * Get the field's type<br/>
	 * Available options are "string", "number", "boolean", "date".
	 *
	 * @return the the field's type
	 */
	String getType();
}
