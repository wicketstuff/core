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
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.application.IComponentOnBeforeRenderListener;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.parser.XmlPullParser;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.protocol.http.BufferedWebResponse;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

/**
 * Parse a Wicket component and generates a {@link Sheet} based on its markup and component models.
 *
 * @author Pedro Santos
 */
public class TableParser
{
	private Row row;
	private Cell cell;
	private final Map<Integer, Integer> rowsToSpanByColumn = new HashMap<Integer, Integer>();
	private final Map<Integer, Integer> columnSpan = new HashMap<Integer, Integer>();
	private int colsToSpan;
	private final Sheet targetSheet;
	private CellExporter cellExporter;
	private Response originalResponse;

	public TableParser(Sheet sheet, CellExporter cellExporter)
	{
		this.cellExporter = cellExporter;
		targetSheet = sheet;
	}

	/**
	 * Parse the grid component to a {@link Sheet} object
	 *
	 * @param tableComponent
	 * @throws IOException
	 * @throws ResourceStreamNotFoundException
	 * @throws ParseException
	 */
	public void parse(Component tableComponent) throws IOException,
		ResourceStreamNotFoundException, ParseException
	{
		try
		{
			BufferedWebResponse mockResponse = doRequest(tableComponent);
			doParse(mockResponse.getText(), tableComponent);
		}
		finally
		{
			afterParse(tableComponent);
		}
	}

	private void doParse(CharSequence gridComponentMarkup, Component tableComponent)
		throws IOException, ResourceStreamNotFoundException, ParseException
	{
		XmlPullParser parser = new XmlPullParser();
		parser.parse(gridComponentMarkup);
		XmlTag tag;
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
				String tagName = tag.getName().toLowerCase();

				if ("tr".equals(tagName))
				{
					if (tableDeep == 0)
					{
						// means that root table is outside the component markup
						tableDeep = 1;
					}
					int index = row == null ? 0 : row.getRowNum() + 1;
					row = targetSheet.createRow(index);
					cell = null;
				}
				else if ("td".equals(tagName) || "th".equals(tagName) )
				{
					int index = cell == null ? 0 : cell.getColumnIndex() + 1 + colsToSpan;
					if (skipColumn(index))
					{
						index += columnSpan.get(index);
					}
					colsToSpan = 0;
					CharSequence rowspan = tag.getAttribute("rowspan");
					CharSequence colspan = tag.getAttribute("colspan");
					cell = row.createCell(index);
					if (rowspan != null || colspan != null)
					{
						int rowsToSpan = rowspan == null ? 0
							: Integer.valueOf(rowspan.toString()) - 1;
						colsToSpan = colspan == null ? 0 : Integer.valueOf(colspan.toString()) - 1;

						if (rowsToSpan > 0)
						{
							rowsToSpanByColumn.put(index, rowsToSpan);
							columnSpan.put(index, colsToSpan + 1);
						}

						int lastRowNum = row.getRowNum() + rowsToSpan;
						int lastColIndex = index + colsToSpan;
						targetSheet.addMergedRegion(new CellRangeAddress(//
							row.getRowNum(), // first row (0-based)
							lastRowNum, // last row (0-based)
							index, // first column (0-based)
							lastColIndex // last column (0-based)
						));
					}
					cellExporter.exportCell(tag, parser, cell, tableComponent);
				}
			}
		}
	}

	private boolean skipColumn(int column)
	{
		Integer rowspan = rowsToSpanByColumn.remove(column);
		if (rowspan != null && rowspan > 0)
		{
			rowsToSpanByColumn.put(column, rowspan - 1);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Mock a request to table component and return its response.
	 *
	 * @param tableComponent
	 * @return
	 */
	private BufferedWebResponse doRequest(Component tableComponent)
	{
		originalResponse = RequestCycle.get().getResponse();
		BufferedWebResponse mockResponse = new BufferedWebResponse(null);
		RequestCycle.get().setResponse(mockResponse);
		Application.get().getComponentPreOnBeforeRenderListeners().add(PathSetupListener.INSTANCE);
		Page page = tableComponent.getPage();
		page.startComponentRender(tableComponent);
		tableComponent.prepareForRender();
		tableComponent.render();
		return mockResponse;
	}

	private void afterParse(Component tableComponent)
	{
		tableComponent.getPage().endComponentRender(tableComponent);
		Application.get()
			.getComponentPreOnBeforeRenderListeners()
			.remove(PathSetupListener.INSTANCE);
		RequestCycle.get().setResponse(originalResponse);
		originalResponse = null;
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

	public Sheet getSheet()
	{
		return targetSheet;
	}
}
