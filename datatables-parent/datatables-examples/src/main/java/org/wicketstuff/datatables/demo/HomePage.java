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
package org.wicketstuff.datatables.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.wicketstuff.datatables.DemoDatatable;

public class HomePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public HomePage()
	{
		WebMarkupContainer table = new DemoDatatable("table");
		add(table);

		List<String[]> rows = new ArrayList<String[]>();
		for (int i = 0; i < 50; i++)
		{
			rows.add(new String[] { "Foo" + i, "Bar" + i, "fizzbuzz" + i });
		}

		ListView<String[]> lv = new ListView<String[]>("rows", rows)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String[]> item)
			{
				String[] row = item.getModelObject();

				item.add(new Label("col1", row[0]));
				item.add(new Label("col2", row[1]));
				item.add(new Label("col3", row[2]));
			}
		};

		table.add(lv);
	}

}
