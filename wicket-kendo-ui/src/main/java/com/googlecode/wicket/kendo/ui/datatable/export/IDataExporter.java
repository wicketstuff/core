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
package com.googlecode.wicket.kendo.ui.datatable.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IExportableColumn;

/**
 * Specifies export of {@link IExportableColumn}<tt>s</tt> against an {@link IDataProvider}
 *
 * @author Sebastien Briquet - sebfz1
 */
public interface IDataExporter extends IClusterable
{
	/**
	 * Gets the content type
	 *
	 * @return the content type
	 */
	String getContentType();

	/**
	 * Indicates whether headers will be written to the output
	 *
	 * @return true or false
	 */
	boolean isExportHeadersEnabled();

	/**
	 * Exports all data provided by the {@link IDataProvider} to the {@link OutputStream}.
	 *
	 * @param provider the {@link IDataProvider}
	 * @param columns the list of {@link IColumn}
	 * @param output the {@link OutputStream}
	 * @throws IOException
	 */
	<T> void exportData(IDataProvider<T> provider, List<IExportableColumn> columns, OutputStream output) throws IOException;
}
