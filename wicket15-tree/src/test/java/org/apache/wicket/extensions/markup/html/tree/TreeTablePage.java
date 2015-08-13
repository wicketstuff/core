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

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Alignment;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation.Unit;
import org.apache.wicket.extensions.markup.html.tree.table.IColumn;
import org.apache.wicket.extensions.markup.html.tree.table.PropertyRenderableColumn;
import org.apache.wicket.extensions.markup.html.tree.table.TreeTable;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@SuppressWarnings("deprecation")
public class TreeTablePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public TreeTablePage(final PageParameters parameters) {
		super(parameters);

		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		Person pietje = new Person();
		pietje.setName("Pietje Puk");
		root.setUserObject(pietje);
		TreeModel treeModel = new DefaultTreeModel(root);

		IColumn[] columns = new IColumn[] {
				new PropertyRenderableColumn<>(new ColumnLocation(Alignment.LEFT, 100, Unit.PERCENT), "Titel", "userObject.name") };
		add(new TreeTable("tree", treeModel, columns));

	}

	public static class Person implements Serializable {
		private static final long serialVersionUID = 1L;

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String naam) {
			this.name = naam;
		}
	}
}
