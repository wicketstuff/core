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
import java.util.Arrays;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

/**
 * @author Pedro Henrique Oliveira dos Santos
 *
 */
public class TableListView extends AjaxSelectableListView {
    private static final long serialVersionUID = 1L;
    private TableModel tableModel;

    public TableListView(String id, TableModel tableModel, ListSelectionModel selectionModel,
	    int rowsPerPage) {
	super(id, new ListModelAdapter(tableModel), rowsPerPage, selectionModel);
	this.tableModel = tableModel;
    }

    @Override
    protected void onSelection(SelectableListItem listItem, AjaxRequestTarget target) {
    }

    @Override
    protected void populateItem(final ListItem rowItem) {
	rowItem.add(new ListView("collums") {
	    @Override
	    protected void populateItem(ListItem dataItem) {
		Object data = tableModel.getValueAt(rowItem.getIndex(), dataItem.getIndex());
		if (tableModel.isCellEditable(rowItem.getIndex(), dataItem.getIndex())) {
		    dataItem.add(new EditablePanel("data", new TableCellModel(tableModel, rowItem
			    .getIndex(), dataItem.getIndex())));
		} else {
		    dataItem.add(new Label("data", data == null ? null : data.toString()));
		}
	    }

	    @Override
	    public int getViewSize() {
		return tableModel.getColumnCount();
	    }
	});
    }

    /**
     * 
     * @author Pedro Henrique Oliveira dos Santos
     * 
     */
    private static class ListModelAdapter extends Model {
	private TableModel tableModel;

	public ListModelAdapter(TableModel tableModel) {
	    this.tableModel = tableModel;
	}

	@Override
	public Serializable getObject() {
	    return (Serializable) Arrays.asList(new Object[tableModel.getRowCount()]);
	}
    }
}