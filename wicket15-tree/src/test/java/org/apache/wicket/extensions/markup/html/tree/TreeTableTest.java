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
package org.apache.wicket.extensions.markup.html.tree;

import javax.swing.tree.TreeModel;

import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Alignment;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Unit;
import org.apache.wicket.extensions.markup.html.tree.table.IColumn;
import org.apache.wicket.extensions.markup.html.tree.table.PropertyTreeColumn;
import org.apache.wicket.extensions.markup.html.tree.table.TreeTable;

/**
 * Tests {@link TreeTable} on the current Wicket version.
 */
public class TreeTableTest extends AbstractTreeComponentTest
{
	@Override
	protected AbstractTree newTree(String id, TreeModel model)
	{
		return new TreeTable(id, model, new IColumn[] {
			new PropertyTreeColumn<>(new ColumnLocation(Alignment.LEFT, 100, Unit.PERCENT), "Name",
				"self.name") });
	}

	@Override
	protected String junctionLinkPath()
	{
		return "sideBodyColumns:0:comp:link";
	}

	@Override
	protected String selectLinkPath()
	{
		return "sideBodyColumns:0:comp:nodeLink";
	}

	@Override
	protected String selectedCssClass()
	{
		return "row-selected";
	}
}
