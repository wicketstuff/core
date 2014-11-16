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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;
import org.apache.wicket.util.resource.IResourceStreamWriter;

import com.googlecode.wicket.jquery.core.utils.ConverterUtils;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IExportableColumn;

/**
 * Provides export capability for the {@link DataTable}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class CSVDataExporter implements IDataExporter
{
	private static final long serialVersionUID = 1L;

	private static final char QUOTE = '"';
	private static final String CRLF = "\r\n";
	private static final String MIME = "text/csv";

	private static final char delimiter = ',';
	private static final String characterSet = "utf-8";

	public static void export(DataTable<?> table, String filename)
	{
		CSVDataExporter.export(RequestCycle.get(), table, filename);
	}

	/**
	 * Exports {@link DataTable} data to a CSV file
	 *
	 * @param cycle the {@link RequestCycle}
	 * @param table the {@link DataTable}
	 * @param filename the file name of the output
	 */
	public static void export(RequestCycle cycle, final DataTable<?> table, String filename)
	{
		List<IExportableColumn> columns = new ArrayList<IExportableColumn>();

		for (IColumn column : table.getColumns())
		{
			if (column instanceof IExportableColumn)
			{
				columns.add((IExportableColumn) column);
			}
		}

		CSVDataExporter.export(cycle, table.getDataProvider(), columns, filename);
	}

	/**
	 * Exports {@link DataTable} data to a CSV file
	 *
	 * @param cycle the {@link RequestCycle}
	 * @param provider the {@link IDataProvider}
	 * @param columns the list of {@link IExportableColumn}
	 * @param filename the file name of the output
	 */
	public static void export(RequestCycle cycle, final IDataProvider<?> provider, final List<IExportableColumn> columns, String filename)
	{
		DataExporterResourceStreamWriter writer = new DataExporterResourceStreamWriter(new CSVDataExporter(), provider, columns);

		cycle.scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(writer, filename));
	}

	private final String contentType;
	private boolean exportHeadersEnabled = true;

	/**
	 * Constructor
	 */
	public CSVDataExporter()
	{
		this(MIME);
	}

	/**
	 * Constructor
	 *
	 * @param contentType the content-type, ie: text/csv
	 */
	public CSVDataExporter(String contentType)
	{
		this.contentType = contentType;
	}

	// Properties //

	/**
	 * Gets the content type
	 *
	 * @return the content type
	 */
	@Override
	public String getContentType()
	{
		return String.format("%s; charset=%s; header=%s", this.contentType, CSVDataExporter.characterSet, this.exportHeadersEnabled ? "present" : "absent");
	}

	/**
	 * Indicates whether headers will be written to the output
	 *
	 * @return true or false
	 */
	@Override
	public boolean isExportHeadersEnabled()
	{
		return this.exportHeadersEnabled;
	}

	// Methods //

	/**
	 * Quotes a value for export to CSV.<br/>
	 * According to RFC4180, this should just duplicate all occurrences of the quote character and wrap the result in the quote character.
	 *
	 * @param value The value to be quoted.
	 * @return a quoted copy of the value.
	 */
	protected String quoteValue(String value)
	{
		return QUOTE + value.replace("" + QUOTE, "" + QUOTE + QUOTE) + QUOTE;
	}

	/**
	 * Writes the headers to the output
	 *
	 * @param provider the {@link IDataProvider}
	 * @param columns the list of {@link IColumn}
	 * @param writer the {@link PrintWriter}
	 */
	private void exportHeaders(List<IExportableColumn> columns, PrintWriter writer)
	{
		boolean first = true;

		for (IExportableColumn column : columns)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				writer.print(CSVDataExporter.delimiter);
			}

			writer.print(this.quoteValue(column.getTitle()));
		}

		writer.print(CRLF);
	}

	/**
	 * Exports a row to the output
	 *
	 * @param provider the {@link IDataProvider}
	 * @param columns the list of {@link IColumn}
	 * @param row the row of typed objects
	 * @param writer the {@link PrintWriter}
	 */
	private <T> void exportRow(IDataProvider<T> provider, List<IExportableColumn> columns, T row, PrintWriter writer)
	{
		boolean first = true;

		for (IExportableColumn column : columns)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				writer.print(CSVDataExporter.delimiter);
			}

			Object object = column.newDataModel(provider.model(row)).getObject();

			if (object != null)
			{
				String value = ConverterUtils.toString(object);
				writer.print(this.quoteValue(value));
			}
		}

		writer.print(CRLF);
	}

	/**
	 * Exports all data provided by the {@link IDataProvider} to the {@link OutputStream}.
	 *
	 * @param provider the {@link IDataProvider}
	 * @param columns the list of {@link IColumn}
	 * @param output the {@link OutputStream}
	 * @throws IOException
	 */
	@Override
	public <T> void exportData(IDataProvider<T> provider, List<IExportableColumn> columns, OutputStream output) throws IOException
	{
		this.exportData(provider, columns, output, 0, provider.size());
	}

	/**
	 * Exports the data provided by the {@link IDataProvider} to the {@link OutputStream}.
	 *
	 * @param provider the {@link IDataProvider}
	 * @param columns the list of {@link IColumn}
	 * @param output the {@link OutputStream}
	 * @param first the first row of datacount
	 * @param count the number of elements to retrieve
	 * @throws IOException
	 */
	public <T> void exportData(IDataProvider<T> provider, List<IExportableColumn> columns, OutputStream output, long first, long count) throws IOException
	{
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, Charset.forName(CSVDataExporter.characterSet)));

		try
		{
			// headers //
			if (this.isExportHeadersEnabled())
			{
				this.exportHeaders(columns, writer);
			}

			// rows //
			Iterator<? extends T> iterator = provider.iterator(first, count);

			while (iterator.hasNext())
			{
				this.exportRow(provider, columns, iterator.next(), writer);
			}
		}
		finally
		{
			writer.close();
		}
	}

	/**
	 * Provides the {@link IResourceStreamWriter} for the {@link CSVDataExporter}
	 */
	public static class DataExporterResourceStreamWriter extends AbstractResourceStreamWriter
	{
		private static final long serialVersionUID = 1L;

		private final IDataExporter exporter;
		private final IDataProvider<?> provider;
		private final List<IExportableColumn> columns;

		public DataExporterResourceStreamWriter(IDataExporter exporter, final IDataProvider<?> provider, final List<IExportableColumn> columns)
		{
			this.exporter = exporter;
			this.provider = provider;
			this.columns = columns;
		}

		@Override
		public String getContentType()
		{
			return this.exporter.getContentType();
		}

		@Override
		public void write(OutputStream output) throws IOException
		{
			this.exporter.exportData(this.provider, this.columns, output);
		}
	}
}
