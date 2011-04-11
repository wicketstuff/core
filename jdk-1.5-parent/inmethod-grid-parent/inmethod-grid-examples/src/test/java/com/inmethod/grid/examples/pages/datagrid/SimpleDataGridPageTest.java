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
package com.inmethod.grid.examples.pages.datagrid;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.WicketTester;

import com.inmethod.grid.examples.WicketApplication;

/**
 * @author Pedro Santos
 */
public class SimpleDataGridPageTest extends TestCase
{
	public void testExportToExcel() throws IOException
	{
		WicketTester tester = new WicketTester(new WicketApplication());
		tester.startPage(SimpleDataGridPage.class);
		tester.clickLink("exportToExcel");
		assertTrue(tester.getLastResponse().getHeader("Content-Disposition").contains(".xls"));
		// File f = new File(System.getProperty("user.home") + "\\test.xls");
		// f.delete();
		// f.createNewFile();
		// FileOutputStream out = new FileOutputStream(f);
		// out.write(tester.getLastResponse().getBinaryContent());
		// out.close();
		// Desktop d = Desktop.getDesktop();
		// d.open(f);
	}
}
