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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.wicket.IRequestListener;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.Test;
import org.wicketstuff.poi.excel.TableComponentAsXlsHandler;

/**
 * @author Pedro Santos
 */
public class ListViewFormComponentReuseManagerPageTest
{
	@Test
	public void testExportToExcel() throws IOException
	{
		WicketTester tester = new WicketTester(new WicketApplication());
		tester.startPage(TestPage.class);
		tester.executeListener(tester.getLastRenderedPage());
		assertTrue(tester.getLastResponse().getHeader("Content-Disposition").contains(".xls"));
		// openFileInResponse(tester);
	}

	public static class TestPage extends ListViewFormComponentReuseManagerPage implements
			IRequestListener
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void onRequest()
		{
			IRequestHandler handler = new TableComponentAsXlsHandler(get("rowsForm:rowsList"),
				"example.xls");
			RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);
		}

	}
}
