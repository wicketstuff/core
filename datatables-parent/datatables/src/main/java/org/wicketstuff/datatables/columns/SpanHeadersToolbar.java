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

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.datatables.IDataTablesColumn;


/**
 * Toolbars that displays spanning column headers.
 * If the column has a rowspan or a colspan they will be rendered as
 * attributes of the &lt;th&gt; element.
 *
 * @param <S>
 *            the type of the sorting parameter
 *
 */
public class SpanHeadersToolbar<S> extends AbstractToolbar
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
     * @param table
     *            data table this toolbar will be attached to
     * @param columns
     */
	public <T> SpanHeadersToolbar(final DataTable<T, S> table, final IDataTablesColumn<T, S>... columns)
	{
		super(table);

		RefreshingView<IColumn<T, S>> headers = new RefreshingView<IColumn<T, S>>("headers")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<IModel<IColumn<T, S>>> getItemModels()
			{
				List<IModel<IColumn<T, S>>> columnsModels = new LinkedList<>();

				List<? extends IColumn<T, S>> tableColumns = (columns != null && columns.length > 0)
                    ? Arrays.asList(columns)
                    : table.getColumns();
				for (IColumn<T, S> column : tableColumns)
				{
					columnsModels.add(Model.of(column));
				}

				return columnsModels.iterator();
			}

			@Override
			protected void populateItem(Item<IColumn<T, S>> item)
			{
				final IColumn<T, S> column = item.getModelObject();

				WebMarkupContainer header = new WebMarkupContainer("header");
				if (column instanceof IDataTablesColumn) {
					IDataTablesColumn<T, S> dtColumn = (IDataTablesColumn<T, S>) column;
					if (dtColumn.getColspan() > 0) {
						header.add(AttributeModifier.replace("colspan", dtColumn.getColspan()));
					}

					if (dtColumn.getRowspan() > 0) {
						header.add(AttributeModifier.replace("rowspan", dtColumn.getRowspan() - 1));
					}
				}

				if (column instanceof IStyledColumn)
				{
					Behavior cssAttributeBehavior = new Behavior()
					{
						private static final long serialVersionUID = 1L;

						@Override
						public void onComponentTag(final Component component, final ComponentTag tag) {
							super.onComponentTag(component, tag);

							String cssClass = ((IStyledColumn<?, S>) column).getCssClass();
							if (!Strings.isEmpty(cssClass)) {
								tag.append("class", cssClass, " ");
							}
						}
					};

					header.add(cssAttributeBehavior);
				}

				item.add(header);
				item.setRenderBodyOnly(true);
				header.add(column.getHeader("label"));
			}
		};
		add(headers);
	}
}
