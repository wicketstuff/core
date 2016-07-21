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
package org.wicketstuff.poi.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceStreamResource;

/**
 * <p>
 * 		Request handler that respond the specified table components as a XLS file.
 * </p>
 * <p>
 * 		A possible implementation of {@link Link} on click be:
 * </p>
 * <code>
 * public void onClick() {<br />
 * IRequestHandler handler = new TablesComponentAsXlsHandler("fileName.xls", table1, table2);<br />
 * RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);<br />
 * }
 * </code>
 *
 * @author reiern70
 */
public class TablesComponentAsXlsHandler implements IRequestHandler
{
	private Component[] tableComponents;
	private String filename;
	private CellExporter cellExporter = new GeneralPurposeExporter();
	private Workbook workbook;

	/**
	 * @param tableComponent
	 * @param filename
	 */
	public TablesComponentAsXlsHandler(String filename, Component... tableComponents)
	{
		if( tableComponents == null )
		{
			throw new IllegalArgumentException( "At least one table compont should be passed as argument!" );
		}
		this.tableComponents = tableComponents;
		this.filename = filename;
	}

	public void respond(IRequestCycle requestCycle)
	{
		try
		{
			workbook = new HSSFWorkbook();
			int index = 0;
			for( Component tableComponent:  tableComponents )
			{
				TableParser parser = new TableParser( newSheet( workbook, tableComponent, index++), cellExporter);
				if (tableComponent instanceof IPageable)
				{
					IPageable pageable = (IPageable)tableComponent;
					for (int i = 0; i < pageable.getPageCount(); i++)
					{
						pageable.setCurrentPage(i);
						parser.parse(tableComponent);
					}
				}
				else
				{
					parser.parse(tableComponent);
				}
			}
			XlsStream xlsStream = new XlsStream( workbook );
			ResourceStreamResource resource = new ResourceStreamResource(xlsStream);
			resource.setFileName(filename);
			resource.setContentDisposition(ContentDisposition.ATTACHMENT);
			IResource.Attributes a = new IResource.Attributes(requestCycle.getRequest(),
				requestCycle.getResponse());
			resource.respond(a);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error while generating a xls file to table component", e);
		}
	}

	public void detach(IRequestCycle requestCycle)
	{
		tableComponents = null;
	}

	/**
	 * Create the sheet in where component table data will be set
	 *
	 * @return a new {@link Sheet} to receive the table component data
	 */
	protected Sheet newSheet(Workbook workbook, Component component, int index)
	{
		return workbook.createSheet( "Worksheet " + component.getId() );
	}

	/**
	 * Set the exporter strategy to be used by this handler. The default is
	 * {@link GeneralPurposeExporter}
	 *
	 * @see CellExporter
	 * @param cellExporter
	 */
	public void setCellExporter(CellExporter cellExporter)
	{
		this.cellExporter = cellExporter;
	}

}
