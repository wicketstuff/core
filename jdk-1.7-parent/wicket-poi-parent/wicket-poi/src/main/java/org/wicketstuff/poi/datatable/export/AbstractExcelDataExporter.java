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
package org.wicketstuff.poi.datatable.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.AbstractDataExporter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IDataExporter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

/**
 * An abstract {@link IDataExporter} that exports to a POI {@link Workbook}. This has an abstract factory method to create a new
 * {@link Workbook}. This can be overridden to provide either a {@link HSSFWorkbook} or a {@link XSSFWorkbook}.
 *
 * @author Jesse Long
 * @see ExcelDataExporter
 * @see OOXMLDataExporter
 */
public abstract class AbstractExcelDataExporter
	extends AbstractDataExporter
{

	/**
	 * Creates a new instance.
	 *
	 * @param dataFormatNameModel An {@link IModel} of the data format name.
	 * @param contentType The MIME content type to use when serving up the exported data.
	 * @param fileNameExtension The file name extensions to use for the exported file. This should exclude the ".".
	 */
	public AbstractExcelDataExporter(IModel<String> dataFormatNameModel, String contentType, String fileNameExtension)
	{
		super(dataFormatNameModel, contentType, fileNameExtension);
	}

	/**
	 * Creates a new {@link Workbook} to which the exported data will be added. This should be either a {@link HSSFWorkbook} or
	 * a {@link XSSFWorkbook}.
	 *
	 * @return a new {@link Workbook} to which the exported data will be added.
	 */
	protected abstract Workbook createWorkbook();

	/**
	 * Exports data to a Excel {@link Workbook}.
	 *
	 * @param <T> The type of each row in the exported data.
	 * @param dataProvider The {@link IDataProvider} from which the row data is retrieved.
	 * @param columns A {@link List} of {@link IExportableColumn}s which can be exported.
	 * @param outputStream The {@link OutputStream} to which the exported spreadsheet will be written.
	 * @throws IOException If an error occurs while exporting the data.
	 */
	@Override
	public <T> void exportData(IDataProvider<T> dataProvider, List<IExportableColumn<T, ?>> columns, OutputStream outputStream)
		throws IOException
	{
		Workbook workbook = createWorkbook();

		Sheet sheet = workbook.createSheet();

		int rowNumber = 0;

		Row row = sheet.createRow(rowNumber);

		rowNumber++;

		int columnNumber = 0;
		for (IExportableColumn<T, ?> column : columns)
		{
			Cell cell = row.createCell(columnNumber);
			IModel<String> headerModel = column.getDisplayModel();
			if (headerModel != null)
			{
				String header = headerModel.getObject();
				populateHeaderCell(cell, header, columnNumber, column, workbook);
			}
			else
			{
				populateHeaderCell(cell, null, columnNumber, column, workbook);
			}
			columnNumber++;
		}

		Iterator<? extends T> it = dataProvider.iterator(0, dataProvider.size());
		while (it.hasNext())
		{
			T rowObject = it.next();
			IModel<T> rowModel = dataProvider.model(rowObject);

			row = sheet.createRow(rowNumber);
			rowNumber++;

			columnNumber = 0;
			for (IExportableColumn<T, ?> column : columns)
			{
				Cell cell = row.createCell(columnNumber);
				// rowNumber - 1 because 
				populateCell(cell, rowModel, column, rowNumber - 1, columnNumber, workbook);
				columnNumber++;
			}
		}

		workbook.write(outputStream);
	}

	/**
	 * Populates a header cell. This can be overridden to decorate header cells, or to add some custom header cell populating
	 * code.
	 *
	 * @param cell The {@link Cell} to be populated.
	 * @param value The header description, as returned from {@link IExportableColumn#getDisplayModel()}. This can be
	 * {@code null}.
	 * @param columnIndex The index of the column being populated. This is zero based.
	 * @param column The {@link IExportableColumn} for which the cell is being exported.
	 * @param workbook The {@link Workbook} in which the cell is created. This is included to than it may be used to create
	 * formatting objects etc.
	 */
	protected void populateHeaderCell(Cell cell, String value, int columnIndex, IExportableColumn<?, ?> column, Workbook workbook)
	{
		if (value != null)
		{
			cell.setCellValue(value);
		}
	}

	/**
	 * Populates a cell of exported data. This can be overridden to provide custom cell population behavior, or to decorate the
	 * cell.
	 *
	 * @param <T> The type of each exported row.
	 * @param cell The {@link Cell} to be populated.
	 * @param rowModel The {@link IModel} of the row.
	 * @param column The {@link IExportableColumn} for which this cell if being populated.
	 * @param rowIndex The zero based index of this row in the data set. If headers are exported. then the row index in the
	 * spreadsheet will be one more than this, because the first row of the spreadsheet contains headers.
	 * @param columnIndex The zero based index of the column.
	 * @param workbook The {@link Workbook} in which the cell is created. This is included to than it may be used to create
	 * formatting objects etc.
	 */
	@SuppressWarnings("unchecked")
	protected <T> void populateCell(Cell cell, IModel<T> rowModel, IExportableColumn<T, ?> column, int rowIndex, int columnIndex, Workbook workbook)
	{
		IModel<?> cellModel = column.getDataModel(rowModel);
		if (cellModel != null)
		{
			Object cellValue = cellModel.getObject();

			if (cellValue != null)
			{
				if (cellValue instanceof Boolean)
				{
					cell.setCellValue(((Boolean) cellValue).booleanValue());
				}
				else if (cellValue instanceof Number)
				{
					cell.setCellValue(((Number) cellValue).doubleValue());
				}
				else if (cellValue instanceof Date)
				{
					cell.setCellValue((Date) cellValue);
				}
				else
				{
					Class<?> c = cellValue.getClass();

					String s;

					IConverter converter = Application.get().getConverterLocator().getConverter(c);

					if (converter == null)
					{
						s = cellValue.toString();
					}
					else
					{
						s = converter.convertToString(cellValue, Session.get().getLocale());
					}

					cell.setCellValue(s);
				}
			}
		}
	}
}
