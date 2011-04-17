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
package org.wicketstuff.poi;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.wicket.markup.html.link.ILinkListener;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.tester.WicketTester;
import org.wicketstuff.poi.excel.TableComponentAsXlsHandler;

import com.inmethod.grid.examples.pages.datagrid.SimpleDataGridPage;

/**
 * @author Pedro Santos
 */
public class SimpleDataGridPageTest extends TestCase {
	public void testExportToExcel() throws IOException {
		WicketTester tester = new WicketTester(
				new com.inmethod.grid.examples.WicketApplication());
		tester.startPage(TestPage.class);
		tester.executeListener(tester.getLastRenderedPage(),
				ILinkListener.INTERFACE);
		assertTrue(tester.getLastResponse().getHeader("Content-Disposition")
				.contains(".xls"));
		openFileInResponse(tester);
	}

	public static class TestPage extends SimpleDataGridPage implements
			ILinkListener {
		/** */
		private static final long serialVersionUID = 1L;

		public void onLinkClicked() {
			IRequestHandler handler = new TableComponentAsXlsHandler(
					get("grid:form:bodyContainer:body"), "example.xls");
			RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);
		}

	}

	/**
	 * Utility method for test
	 * 
	 * @param tester
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void openFileInResponse(WicketTester tester)
			throws IOException, FileNotFoundException {
		File f = new File(System.getProperty("user.home") + "\\test.xls");
		f.delete();
		f.createNewFile();
		FileOutputStream out = new FileOutputStream(f);
		out.write(tester.getLastResponse().getBinaryContent());
		out.close();
		Desktop d = Desktop.getDesktop();
		d.open(f);
	}
}
