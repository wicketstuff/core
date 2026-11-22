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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
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
 * Toolbar that displays spanning column headers.
 * If the column has a rowspan or a colspan they will be rendered as
 * attributes of the &lt;th&gt; element.
 * <p>
 * A column with a {@link IDataTablesColumn#getRowspan() rowspan} larger than 1 covers the
 * header rows below the toolbar it is rendered in. Add it to the topmost toolbar it should
 * appear in; the {@code SpanHeadersToolbar}s that follow in the same table leave it out of
 * the rows it covers. Invisible toolbars do not count as rows. For example, to render
 *
 * <pre>
 * &lt;tr&gt;&lt;th colspan="2"&gt;Names&lt;/th&gt;&lt;th rowspan="2"&gt;Age&lt;/th&gt;&lt;/tr&gt;
 * &lt;tr&gt;&lt;th&gt;First&lt;/th&gt;&lt;th&gt;Last&lt;/th&gt;&lt;/tr&gt;
 * </pre>
 *
 * add the toolbars as
 *
 * <pre>
 * table.addTopToolbar(new SpanHeadersToolbar&lt;&gt;(table, namesColumn, ageColumn));
 * table.addTopToolbar(new SpanHeadersToolbar&lt;&gt;(table));
 * </pre>
 *
 * where {@code ageColumn} is the same instance as in the columns of the table and returns 2
 * from {@code getRowspan()}.
 *
 * @param <S>
 *            the type of the sorting parameter
 *
 */
public class SpanHeadersToolbar<S> extends AbstractToolbar
{
	private static final long serialVersionUID = 1L;

	/** The columns given to the constructor, or {@code null} to render the columns of the table. */
	private final List<? extends IColumn<?, ?>> headerColumns;

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

		headerColumns = (columns != null && columns.length > 0) ? List.of(columns) : null;

		RefreshingView<IColumn<T, S>> headers = new RefreshingView<IColumn<T, S>>("headers")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<IModel<IColumn<T, S>>> getItemModels()
			{
				List<IModel<IColumn<T, S>>> columnsModels = new LinkedList<>();

				Set<IColumn<?, ?>> coveredColumns = getColumnsCoveredByPrecedingToolbars();
				for (IColumn<?, ?> column : getColumns())
				{
					if (!coveredColumns.contains(column))
					{
						@SuppressWarnings("unchecked")
						IColumn<T, S> tableColumn = (IColumn<T, S>)column;
						columnsModels.add(Model.of(tableColumn));
					}
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
					if (dtColumn.getColspan() > 1) {
						header.add(AttributeModifier.replace("colspan", dtColumn.getColspan()));
					}

					if (dtColumn.getRowspan() > 1) {
						header.add(AttributeModifier.replace("rowspan", dtColumn.getRowspan()));
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

	/**
	 * @return the columns this toolbar renders when no preceding toolbar covers them
	 */
	private List<? extends IColumn<?, ?>> getColumns()
	{
		return headerColumns != null ? headerColumns : getTable().getColumns();
	}

	/**
	 * Collects the columns that a preceding {@code SpanHeadersToolbar} of the same toolbar
	 * section renders with a rowspan that reaches into the row of this toolbar. Only visible
	 * toolbars are counted as rows.
	 */
	private Set<IColumn<?, ?>> getColumnsCoveredByPrecedingToolbars()
	{
		Set<IColumn<?, ?>> covered = new HashSet<>();
		MarkupContainer toolbars = getParent();
		if (toolbars == null)
		{
			return covered;
		}

		// the row (exclusive) up to which a column rendered with a rowspan covers the header
		Map<IColumn<?, ?>, Long> coveredUntil = new HashMap<>();
		long row = 0;
		for (Component sibling : toolbars)
		{
			if (sibling == this)
			{
				break;
			}
			sibling.configure();
			if (!sibling.determineVisibility())
			{
				continue;
			}
			if (sibling instanceof SpanHeadersToolbar<?> toolbar)
			{
				for (IColumn<?, ?> column : toolbar.getColumns())
				{
					if (column instanceof IDataTablesColumn<?, ?> dtColumn
						&& dtColumn.getRowspan() > 1
						&& coveredUntil.getOrDefault(column, 0L) <= row)
					{
						coveredUntil.put(column, row + dtColumn.getRowspan());
					}
				}
			}
			row++;
		}

		for (Map.Entry<IColumn<?, ?>, Long> entry : coveredUntil.entrySet())
		{
			if (entry.getValue() > row)
			{
				covered.add(entry.getKey());
			}
		}
		return covered;
	}
}
