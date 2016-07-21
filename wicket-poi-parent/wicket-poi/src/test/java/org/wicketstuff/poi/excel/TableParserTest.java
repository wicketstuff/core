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

import junit.framework.TestCase;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.tester.WicketTester;

/**
 * @author Pedro Santos
 */
public class TableParserTest extends TestCase
{
	private WicketTester tester;

	@Override
	protected void setUp() throws Exception
	{
		tester = new WicketTester();
	}

	@Override
	protected void tearDown() throws Exception
	{
		tester.destroy();
	}

	public void testTable1() throws IOException, ResourceStreamNotFoundException, ParseException
	{
		Sheet sheet = new HSSFWorkbook().createSheet();
		TableParser tableParser = new TableParser(sheet, new GeneralPurposeExporter());
		tableParser.parse(new Table1());
		assertEquals(9, sheet.getLastRowNum());
		assertEquals(3, sheet.getNumMergedRegions());
		assertEquals(0, sheet.getMergedRegion(0).getFirstColumn());
		assertEquals(1, sheet.getMergedRegion(0).getLastColumn());
		assertEquals(3, sheet.getMergedRegion(0).getFirstRow());
		assertEquals(3, sheet.getMergedRegion(0).getLastRow());
		assertEquals(0, sheet.getMergedRegion(1).getFirstColumn());
		assertEquals(0, sheet.getMergedRegion(1).getLastColumn());
		assertEquals(4, sheet.getMergedRegion(1).getFirstRow());
		assertEquals(5, sheet.getMergedRegion(1).getLastRow());
		assertEquals(0, sheet.getMergedRegion(2).getFirstColumn());
		assertEquals(2, sheet.getMergedRegion(2).getLastColumn());
		assertEquals(6, sheet.getMergedRegion(2).getFirstRow());
		assertEquals(6, sheet.getMergedRegion(2).getLastRow());
		assertEquals("04/01/2000", sheet.getRow(3).getCell(2).getStringCellValue());
		assertEquals("05/01/2000", sheet.getRow(4).getCell(2).getStringCellValue());
		assertEquals("06/01/2000", sheet.getRow(5).getCell(2).getStringCellValue());
		assertNull(sheet.getRow(6).getCell(2));
		assertEquals("08/01/2000", sheet.getRow(7).getCell(2).getStringCellValue());
		// tester.startResource(new ResourceStreamResource(new
		// XlsStream(sheet.getWorkbook())));
		// PoiTestUtil.openFileInResponse(tester);

	}
}
