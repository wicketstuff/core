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
package org.wicketstuff.table;

import java.io.Serializable;
import java.util.Iterator;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.RowSorter.SortKey;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.table.sorter.SerializableTableRowSorter;

/**
 * Table component to present an swing TableModel.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class Table extends Panel implements IHeaderContributor {

    private static final long serialVersionUID = 1L;
    public static final ResourceReference TABLE_CSS = new ResourceReference(Table.class,
	    "res/table.css");
    public static final ResourceReference ARROW_UP = new ResourceReference(Table.class,
	    "res/arrow_up.png");
    public static final ResourceReference ARROW_OFF = new ResourceReference(Table.class,
	    "res/arrow_off.png");
    public static final ResourceReference ARROW_DOWN = new ResourceReference(Table.class,
	    "res/arrow_down.png");
    private PageableListView rowsListView;
    private ListSelectionModel selectionModel;
    private RowSorter sorter;
    private boolean autoCreateRowSorter;

    /**
     * @param id
     * @param swingTableModel
     *            the tableModel need to be serializable, to exist along the
     *            session.
     */
    public Table(String id, TableModel swingTableModel) {
	super(id);
	setDefaultModel(new TableModelAdapter(swingTableModel));
	selectionModel = new DefaultListSelectionModel();
	setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	setOutputMarkupId(true);
	add(new ListView("headers") {
	    @Override
	    protected void populateItem(final ListItem item) {
		String header = getTableModel().getColumnName(item.getIndex());
		item.add(new Label("header", new ResourceModel(header, header)));
		item.add(new Image("arrow") {
		    @Override
		    protected ResourceReference getImageResourceReference() {
			if (sorter != null) {
			    if (sorter.getSortKeys() == null || sorter.getSortKeys().size() == 0) {
				return ARROW_OFF;
			    } else {
				for (Iterator i = sorter.getSortKeys().iterator(); i.hasNext();) {
				    SortKey sortKey = (SortKey) i.next();
				    if (sortKey.getColumn() == item.getIndex()) {
					if (sortKey.getSortOrder() == SortOrder.ASCENDING) {
					    return ARROW_UP;
					} else if (sortKey.getSortOrder() == SortOrder.DESCENDING) {
					    return ARROW_DOWN;
					}
				    }
				}// for
				return ARROW_OFF;
			    }
			} else {
			    return null;
			}
		    }

		    @Override
		    public boolean isVisible() {
			return getImageResourceReference() != null;
		    }
		});
		item.add(new AjaxEventBehavior("onclick") {
		    @Override
		    protected void onEvent(AjaxRequestTarget target) {
			if (sorter != null) {
			    int columnIndex = item.getIndex();
			    sorter.toggleSortOrder(columnIndex);
			    target.addComponent(Table.this);
			}
		    }
		});
	    }

	    @Override
	    public int getViewSize() {
		return getTableModel().getColumnCount();
	    }
	});
	add(rowsListView = new TableListView("rows"));
    }

    /**
     * Repeating component that extends the AjaxSelectableListView. The extended
     * behavior are the table model rendering complexity partially implemented.
     * 
     */
    class TableListView extends AjaxSelectableListView {
	public TableListView(String id) {
	    super(id, new ListModelAdapter(getTableModel()), Integer.MAX_VALUE);
	}

	@Override
	protected ListItem newItem(final int index) {
	    final SelectableListItem listItem = new SelectableListItem(index, getListItemModel(
		    getModel(), index), listSelectionModel) {
		@Override
		protected void onSelection(AjaxRequestTarget target) {
		    TableListView.this.setSelection(this, target);
		}

		@Override
		protected int getIndexOnModel() {
		    if (sorter != null) {
			try {
			    return sorter.convertRowIndexToModel(getIndex());
			} catch (IndexOutOfBoundsException e) {
			    System.out.println(e.getMessage());
			    System.out.println(sorter);
			    System.out.println(sorter.getModel());
			    return super.getIndexOnModel();
			}
		    } else {
			return super.getIndexOnModel();
		    }
		}
	    };
	    return listItem;
	}

	@Override
	protected void onSelection(SelectableListItem listItem, AjaxRequestTarget target) {
	    int rowIndex = listItem.getIndex();
	    System.out.print("selecting: " + rowIndex);
	    if (sorter != null) {
		rowIndex = sorter.convertRowIndexToModel(rowIndex);
		System.out.print(" converted to: " + rowIndex);
	    }
	    System.out.println();
	    Table.this.onSelection(rowIndex, target);
	}

	@Override
	protected void populateItem(final ListItem rowItem) {
	    rowItem.add(new ListView("collums", new ColumnsModelAdapter(getTableModel())) {
		@Override
		protected void populateItem(ListItem dataItem) {
		    int rowIndex = rowItem.getIndex();
		    System.out.print("rendering: " + rowIndex);
		    if (sorter != null) {
			try {
			    rowIndex = sorter.convertRowIndexToView(rowIndex);
			} catch (IndexOutOfBoundsException e) {
			    System.out.println();
			    System.out.println(e.getMessage());
			    System.out.println(sorter);
			    System.out.println(sorter.getModel());
			}
			System.out.print(" converted to: " + rowIndex);
		    }
		    System.out.println();
		    Object data = getTableModel().getValueAt(rowIndex, dataItem.getIndex());
		    /*
		     * TODO from the table model we can get much more
		     * informations. Is possible to add checkboxes for booleans,
		     * image components for images, date components for dates,
		     * etc.
		     */
		    if (getTableModel().isCellEditable(rowIndex, dataItem.getIndex())) {
			dataItem.add(new SelfSubmitTextFieldPanel("data", new TableCellModel(
				getTableModel(), rowIndex, dataItem.getIndex())));
		    } else {
			dataItem.add(new Label("data", data == null ? null : data.toString()));
		    }
		}
	    });
	}
    }

    public AjaxPagingNavigator getAjaxPagingNavigator(String id, int rowsPerPage) {
	rowsListView.setRowsPerPage(rowsPerPage);
	return new AjaxPagingNavigator(id, rowsListView) {
	    protected void onComponentTag(ComponentTag tag) {
		tag.put("class", "navigator");
	    }
	};
    }

    /**
     * Number of rows to be presented per page on table.
     * 
     * @param rowsPerPage
     */
    public void setRowsPerPage(int rowsPerPage) {
	rowsListView.setRowsPerPage(rowsPerPage);
    }

    /**
     * See constraints in @see javax.swing.ListSelectionModel
     * 
     * @param selectionMode
     */
    public void setSelectionMode(int selectionMode) {
	selectionModel.setSelectionMode(selectionMode);
    }

    /**
     * Add a listener to the list that's notified each time a change to the
     * selection occurs.
     */
    public void addListSelectionListener(ListSelectionListener x) {
	selectionModel.addListSelectionListener(x);
    }

    public void setSelectionIndex(Integer newSelectionIndex) {
	selectionModel.setSelectionInterval(newSelectionIndex, newSelectionIndex);
    }

    public ListSelectionModel getListSelectionModel() {
	return selectionModel;
    }

    public TableModel getTableModel() {
	return (TableModel) getDefaultModelObject();
    }

    private class TableModelAdapter extends Model {
	public TableModelAdapter(TableModel tableModel) {
	    super((Serializable) tableModel);
	    if (autoCreateRowSorter) {
		setRowSorter(new SerializableTableRowSorter(tableModel));
	    }
	}

	@Override
	public void setObject(Serializable object) {
	    super.setObject(object);
	    if (autoCreateRowSorter) {
		setRowSorter(new SerializableTableRowSorter((TableModel) object));
	    }
	}
    }

    public void setAutoCreateRowSorter(boolean autoCreateRowSorter) {
	this.autoCreateRowSorter = autoCreateRowSorter;
	if (autoCreateRowSorter) {
	    setRowSorter(new SerializableTableRowSorter(getTableModel()));
	}
    }

    public void setRowSorter(RowSorter sorter) {
	this.sorter = sorter;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
	response.renderCSSReference(getCss());
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
	super.onComponentTag(tag);
	tag.setName("table");
    }

    protected ResourceReference getCss() {
	return TABLE_CSS;
    }

    /**
     * TODO: Consider to work with observer pattern.
     * 
     * @param newSelectionIndex
     * @param target
     */
    protected void onSelection(int newSelectionIndex, AjaxRequestTarget target) {
    }

}
