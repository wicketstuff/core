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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


public class XlsStreamTest
{
	private WicketTester tester;

	@BeforeEach
	protected void setUp() throws Exception
	{
		tester = new WicketTester();
	}

	@AfterEach
	protected void tearDown() throws Exception
	{
		tester.destroy();
	}

	public void testXlsStream()
	{
		Sheet sheet = new HSSFWorkbook().createSheet();
		tester.startResource(new ResourceStreamResource(new XlsStream(sheet.getWorkbook())));
		assertTrue(tester.getLastResponse().getContentType().contains("excel"));
	}
}
