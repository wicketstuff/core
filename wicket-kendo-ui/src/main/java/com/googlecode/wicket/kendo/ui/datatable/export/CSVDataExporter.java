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
import org.apache.wicket.request.resource.ContentDisposition;
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
public class CSVDataExporter
{
	private static final String CRLF = "\r\n";
	private static final String MIME = "text/csv";

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
		CSVDataExporterResourceStream writer = new CSVDataExporterResourceStream(provider, columns);
		ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(writer, filename);
		handler.setContentDisposition(ContentDisposition.ATTACHMENT);

		cycle.scheduleRequestHandlerAfterCurrent(handler);
	}

	private String contentType;
	private char delimiter = ',';
	private String characterSet = "utf-8";
	private char quoteCharacter = '"';
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
	public String getContentType()
	{
		return this.contentType;
	}

	/**
	 * Indicates whether headers will be written to the output
	 * 
	 * @return true or false
	 */
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
		return quoteCharacter + value.replace("" + quoteCharacter, "" + quoteCharacter + quoteCharacter) + quoteCharacter;
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
				writer.print(this.delimiter);
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
				writer.print(this.delimiter);
			}

			Object o = column.newDataModel(provider.model(row)).getObject();

			if (o != null)
			{
				String value = ConverterUtils.toString(o);
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
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, Charset.forName(this.characterSet)));

		try
		{
			if (this.isExportHeadersEnabled())
			{
				this.exportHeaders(columns, writer);
			}

			Iterator<? extends T> iterator = provider.iterator(0, count);

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
	public static class CSVDataExporterResourceStream extends AbstractResourceStreamWriter
	{
		private static final long serialVersionUID = 1L;

		private final CSVDataExporter exporter;
		private final IDataProvider<?> provider;
		private final List<IExportableColumn> columns;

		public CSVDataExporterResourceStream(final IDataProvider<?> provider, final List<IExportableColumn> columns)
		{
			this.exporter = new CSVDataExporter();
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
			this.exporter.exportData(provider, columns, output);
		}
	}
}
