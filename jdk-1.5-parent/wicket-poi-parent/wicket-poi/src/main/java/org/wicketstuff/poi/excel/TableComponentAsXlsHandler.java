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

import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.application.IComponentOnBeforeRenderListener;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.parser.XmlPullParser;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.protocol.http.BufferedWebResponse;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

/**
 * <p>
 * Request handler that respond the specified table component as a XLS file.
 * </p>
 * <p>
 * An possible {@link Link} onClick method implementation seting this handler could be:
 * </p>
 * <code>
 * public void onClick() {<br />
 * IRequestHandler handler = new TableComponentAsXlsHandler( table, "fileName.xls");<br />
 * RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);<br />
 * } 
 * </code>
 * 
 * @author Pedro Santos
 */
public class TableComponentAsXlsHandler implements IRequestHandler
{
	private Row row;
	private Cell cell;
	private CellExporter cellExporter = new GeneralPurposeExporter();
	private Component tableComponent;
	private String filename;
	private Response originalResponse;
	private BufferedWebResponse mockResponse;

	/**
	 * @param tableComponent
	 * @param filename
	 */
	public TableComponentAsXlsHandler(Component tableComponent, String filename)
	{
		this.tableComponent = tableComponent;
		this.filename = filename;
	}

	public void respond(IRequestCycle requestCycle)
	{
		try
		{
			Sheet sheet = createSheet();
			if (tableComponent instanceof IPageable)
			{
				IPageable pageable = (IPageable)tableComponent;
				for (int i = 0; i < pageable.getPageCount(); i++)
				{
					pageable.setCurrentPage(i);
					parse(sheet);
				}
			}
			else
			{
				parse(sheet);
			}
			XlsStream xlsStream = new XlsStream(sheet.getWorkbook());
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
		tableComponent = null;
	}

	protected Sheet createSheet()
	{
		return new HSSFWorkbook().createSheet("data");
	}

	public void setCellExporter(CellExporter cellExporter)
	{
		this.cellExporter = cellExporter;
	}

	private final void parse(Sheet sheet) throws IOException, ResourceStreamNotFoundException,
		ParseException
	{
		try
		{
			beforeParse();
			parseImplementation(sheet, mockResponse.getText());
		}
		finally
		{
			afterParse();
		}
	}

	/**
	 * Parse the grid component to a {@link Workbook} object
	 * 
	 * @param workbook
	 * @throws IOException
	 * @throws ResourceStreamNotFoundException
	 * @throws ParseException
	 */
	protected void parseImplementation(Sheet sheet, CharSequence gridComponentMarkup)
		throws IOException, ResourceStreamNotFoundException, ParseException
	{
		XmlPullParser parser = new XmlPullParser();
		parser.parse(gridComponentMarkup);
		XmlTag tag = null;
		int tableDeep = 0;
		while ((tag = parser.nextTag()) != null)
		{
			if ("table".equals(tag.getName().toLowerCase()))
			{
				if (tag.isOpen())
				{
					tableDeep++;
				}
				else
				{
					tableDeep--;
				}
			}
			if (tableDeep > 1)
			{
				// we don't want to read inner tables
				continue;
			}
			if (tag.isOpen())
			{
				if ("tr".equals(tag.getName().toLowerCase()))
				{
					if (tableDeep == 0)
					{
						// means that root table is outside the component markup
						tableDeep = 1;
					}
					int index = row == null ? 0 : row.getRowNum() + 1;
					row = sheet.createRow(index);
					cell = null;
				}
				else if ("td".equals(tag.getName().toLowerCase()))
				{
					int index = cell == null ? 0 : cell.getColumnIndex() + 1;
					cell = row.createCell(index);
					cellExporter.exportCell(tag, parser, cell, tableComponent);
				}
			}
		}
	}

	private void beforeParse()
	{
		originalResponse = RequestCycle.get().getResponse();
		mockResponse = new BufferedWebResponse((WebResponse)originalResponse);
		RequestCycle.get().setResponse(mockResponse);
		Application.get().getComponentPreOnBeforeRenderListeners().add(PathSetupListener.INSTANCE);
		Page page = tableComponent.getPage();
		page.startComponentRender(tableComponent);
		tableComponent.prepareForRender();
		tableComponent.render();
	}

	private void afterParse()
	{
		tableComponent.getPage().endComponentRender(tableComponent);
		Application.get()
			.getComponentPreOnBeforeRenderListeners()
			.remove(PathSetupListener.INSTANCE);
		RequestCycle.get().setResponse(originalResponse);
		originalResponse = null;
		mockResponse = null;
	}

	/**
	 * We try to maintain a relation between the HTML tag and its Wicket component.
	 */
	private static class PathSetupListener implements IComponentOnBeforeRenderListener
	{
		public static final PathSetupListener INSTANCE = new PathSetupListener();

		public void onBeforeRender(Component component)
		{
			component.add(OutputPathBehavior.INSTANCE);
		}
	}

	/**
	 * Set the page relative path in tag.
	 */
	public static class OutputPathBehavior extends Behavior
	{
		/** */
		private static final long serialVersionUID = 1L;
		public static final String PATH_ATTRIBUTE = "component_path";
		public static final OutputPathBehavior INSTANCE = new OutputPathBehavior();
		private boolean removing;

		@Override
		public void onComponentTag(Component component, ComponentTag tag)
		{
			tag.put(PATH_ATTRIBUTE, component.getPageRelativePath());
		}

		@Override
		public void detach(Component component)
		{
			// preventing endless recursion.
			if (removing)
			{
				return;
			}
			removing = true;
			component.remove(this);
			removing = false;
		}
	}
}
