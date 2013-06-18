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
package com.googlecode.wicket.jquery.ui.kendo.datatable.column;

import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.ui.kendo.datatable.DataTable;

/**
 * Specifies the column definition of a {@link DataTable}
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 */
public interface IColumn<T> extends IClusterable
{
	/**
	 * Gets the text of the column header
	 * @return the column title
	 */
	String getTitle();

	/**
	 * Get the field in the data set that this column should be bound to.
	 * @return the field name
	 */
	String getField();

	/**
	 * Gets the value of the supplied object.<br/>
	 * Implementation may call {@link #getField()}
	 *
	 * @param object the model object
	 * @return the value of the object
	 */
	String getValue(T object);

	/**
	 * Gets the desired width of the column.
	 * @return the column's width
	 */
	int getWidth();
}
