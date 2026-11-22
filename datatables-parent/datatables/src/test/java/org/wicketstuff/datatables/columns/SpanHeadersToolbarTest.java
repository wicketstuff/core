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
package org.wicketstuff.datatables.columns;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Checks the header rows rendered by {@link SpanHeadersToolbar}.
 */
class SpanHeadersToolbarTest
{
	private WicketTester tester;

	@BeforeEach
	void setUp()
	{
		tester = new WicketTester(new MockApplication());
		tester.getApplication().getMarkupSettings().setStripWicketTags(true);
	}

	@AfterEach
	void tearDown()
	{
		tester.destroy();
	}

	@Test
	void columnWithRowspanIsRenderedOnceInTopmostRow()
	{
		TablePage page = new TablePage();
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table, page.names, page.age));
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table));

		assertEquals(List.of(
			"<th colspan=\"2\">Names</th><th rowspan=\"2\">Age</th>",
			"<th>First</th><th>Last</th>"), renderHeaderRows(page));
	}

	@Test
	void columnWithRowspanCoversAllRowsItSpans()
	{
		TablePage page = new TablePage(3);
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table, page.names, page.age));
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table, page.names));
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table));

		assertEquals(List.of(
			"<th colspan=\"2\">Names</th><th rowspan=\"3\">Age</th>",
			"<th colspan=\"2\">Names</th>",
			"<th>First</th><th>Last</th>"), renderHeaderRows(page));
	}

	@Test
	void columnIsRenderedAgainBelowTheRowsItsRowspanCovers()
	{
		TablePage page = new TablePage();
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table, page.names, page.age));
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table));
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table));

		assertEquals(List.of(
			"<th colspan=\"2\">Names</th><th rowspan=\"2\">Age</th>",
			"<th>First</th><th>Last</th>",
			"<th>First</th><th>Last</th><th rowspan=\"2\">Age</th>"), renderHeaderRows(page));
	}

	@Test
	void invisiblePrecedingToolbarDoesNotCoverColumns()
	{
		TablePage page = new TablePage();
		SpanHeadersToolbar<String> hidden = new SpanHeadersToolbar<>(page.table, page.names, page.age);
		hidden.setVisible(false);
		page.table.addTopToolbar(hidden);
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table));

		assertEquals(List.of("<th>First</th><th>Last</th><th rowspan=\"2\">Age</th>"),
			renderHeaderRows(page));
	}

	@Test
	void invisibleToolbarInBetweenIsNotCountedAsRow()
	{
		TablePage page = new TablePage();
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table, page.names, page.age));
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table, page.names)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure()
			{
				super.onConfigure();
				setVisible(false);
			}
		});
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table));

		assertEquals(List.of(
			"<th colspan=\"2\">Names</th><th rowspan=\"2\">Age</th>",
			"<th>First</th><th>Last</th>"), renderHeaderRows(page));
	}

	@Test
	void headerIsTheSameWhenRenderedAgain()
	{
		TablePage page = new TablePage();
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table, page.names, page.age));
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table));

		List<String> first = renderHeaderRows(page);
		assertEquals(first, renderHeaderRows(page));
	}

	@Test
	void headerIsTheSameAfterPageIsSerialized()
	{
		TablePage page = new TablePage();
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table, page.names, page.age));
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table));
		List<String> before = renderHeaderRows(page);

		// columns are matched by identity, which serialization must keep between the
		// toolbars and the table
		TablePage copy = WicketObjects.cloneObject(page);

		assertEquals(before, renderHeaderRows(copy));
	}

	@Test
	void colspanOfOneRendersNoAttribute()
	{
		TablePage page = new TablePage();
		SpanColumn<String, String> single = new SpanColumn<>(Model.of("Single"), null)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public int getColspan()
			{
				return 1;
			}
		};
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table, single, page.age));
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table));

		assertEquals(List.of(
			"<th>Single</th><th rowspan=\"2\">Age</th>",
			"<th>First</th><th>Last</th>"), renderHeaderRows(page));
	}

	@Test
	void columnWithRowspanInLastRowIsRendered()
	{
		TablePage page = new TablePage();
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table));

		assertEquals(List.of("<th>First</th><th>Last</th><th rowspan=\"2\">Age</th>"),
			renderHeaderRows(page));
	}

	@Test
	void columnsWithoutSpansRenderNoSpanAttributes()
	{
		TablePage page = new TablePage(1);
		page.table.addTopToolbar(new SpanHeadersToolbar<>(page.table));

		assertEquals(List.of("<th>First</th><th>Last</th><th>Age</th>"), renderHeaderRows(page));
	}

	private List<String> renderHeaderRows(TablePage page)
	{
		tester.startPage(page);
		String html = tester.getLastResponseAsString();
		String thead = html.substring(html.indexOf("<thead"), html.indexOf("</thead>"));

		List<String> rows = new ArrayList<>();
		Matcher row = Pattern.compile("<tr class=\"headers\">(.*?)</tr>", Pattern.DOTALL).matcher(thead);
		while (row.find())
		{
			rows.add(row.group(1)
				.replaceAll("<span[^>]*>|</span>", "")
				.replaceAll("\\s+", " ")
				.replaceAll("> <", "><")
				.trim());
		}
		return rows;
	}

	public static class TablePage extends WebPage implements IMarkupResourceStreamProvider
	{
		private static final long serialVersionUID = 1L;

		final DataTable<String, String> table;

		final SpanColumn<String, String> names = new SpanColumn<>(Model.of("Names"), null)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public int getColspan()
			{
				return 2;
			}
		};

		final SpanPropertyColumn<String, String> age;

		TablePage()
		{
			this(2);
		}

		TablePage(int ageRowspan)
		{
			age = new SpanPropertyColumn<>(Model.of("Age"), null, "length")
			{
				private static final long serialVersionUID = 1L;

				@Override
				public int getRowspan()
				{
					return ageRowspan;
				}
			};

			List<IColumn<String, String>> columns = new ArrayList<>();
			columns.add(new PropertyColumn<>(Model.of("First"), "bytes"));
			columns.add(new PropertyColumn<>(Model.of("Last"), "bytes"));
			columns.add(age);

			table = new DataTable<>("table", columns, new EmptyDataProvider(), 10);
			add(table);
		}

		@Override
		public IResourceStream getMarkupResourceStream(MarkupContainer container,
			Class<?> containerClass)
		{
			return new StringResourceStream(
				"<html><body><table wicket:id=\"table\"></table></body></html>");
		}
	}

	private static class EmptyDataProvider implements IDataProvider<String>
	{
		private static final long serialVersionUID = 1L;

		@Override
		public Iterator<? extends String> iterator(long first, long count)
		{
			return List.<String> of().iterator();
		}

		@Override
		public long size()
		{
			return 0;
		}

		@Override
		public IModel<String> model(String object)
		{
			return Model.of(object);
		}
	}
}
