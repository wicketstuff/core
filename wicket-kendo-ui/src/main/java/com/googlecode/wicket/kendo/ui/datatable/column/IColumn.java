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
	 * Gets the desired width of the column.
	 *
	 * @return the column's width
	 */
	int getWidth();
}
