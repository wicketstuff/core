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
	 * Gets the desired width of the column.
	 *
	 * @return the column's width
	 */
	int getWidth();

	/**
	 * Get the field's type
	 *
	 * @return the the field's type
	 */
	String getType();

	/**
	 * The format that is applied to the value before it is displayed. Takes the form "{0:format}" where "format" is a standard number format, custom number format, standard date format or a custom date format.
	 *
	 * @return the format or <tt>null</tt> if it does not apply
	 * @see <a href="http://docs.kendoui.com/api/framework/kendo#standard-number-formats">standard-number-formats</a>
	 * @see <a href="http://docs.kendoui.com/api/framework/kendo#custom-number-formats">custom-number-formats</a>
	 * @see <a href="http://docs.kendoui.com/api/framework/kendo#standard-date-formats">standard-date-formats</a>
	 * @see <a href="http://docs.kendoui.com/api/framework/kendo#custom-date-formats">custom-date-formats</a>
	 */
	String getFormat();

	/**
	 * The template or javascript function which renders the column content.<br/>
	 * If the template is a string (not a function), it should itself be enclosed into double quotes, ie:<br/>
	 * <code>return Options.asString("&lt;a href='?id=#:data.id#'&gt;#:data.id#&lt;/a&gt;");</code>
	 *
	 * @return the template or <tt>null</tt> if it does not apply
	 */
	String getTemplate();

	/**
	 * If set to true a filter menu will be displayed for this column when filtering is enabled.<br/>
	 * If set to false the filter menu will not be displayed.<br/>
	 * By default a filter menu is displayed for all columns when filtering is enabled via the filterable option.
	 *
	 * @return the <tt>filterable</tt> object; ie: <tt>true</tt> or <tt>{ extra: false }</tt>
	 * @see <a href="http://docs.kendoui.com/api/web/grid#configuration-columns.filterable">configuration-columns.filterable</a>
	 */
	String getFilterable();

	/**
	 * If set to true the column will be visible in the grid column menu. By default the column menu includes all data-bound columns (ones that have their field set).
	 *
	 * @return the <tt>menu</tt> object; ie: <tt>false</tt> or <tt>{ extra: false }</tt>
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
}
