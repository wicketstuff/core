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

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;

/**
 * @author Pedro Henrique Oliveira dos Santos
 *
 */
public class Table extends Panel implements IHeaderContributor {

    private static final long serialVersionUID = 1L;
    public static final ResourceReference TABLE_CSS = new ResourceReference(Table.class,
	    "res/table.css");
    private PageableListView rowsListView;
    private ListSelectionModel selectionModel;
    private TableModel tableModel;

    protected void onComponentTag(ComponentTag tag) {
	super.onComponentTag(tag);
	tag.setName("table");
    }

    public Table(String id, TableModel tableModel) {
	super(id);
	this.tableModel = tableModel;
	selectionModel = new DefaultListSelectionModel();
	setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	setOutputMarkupId(true);
	setOutputMarkupId(true);
	add(new ListView("headers") {
	    @Override
	    protected void populateItem(ListItem item) {
		String header = Table.this.tableModel.getColumnName(item.getIndex());
		item.add(new Label("header", new ResourceModel(header, header)));
	    }

	    @Override
	    public int getViewSize() {
		return Table.this.tableModel.getColumnCount();
	    }
	});
	add(rowsListView = new TableListView("rows", tableModel, selectionModel, Integer.MAX_VALUE) {
	    @Override
	    protected void onSelection(SelectableListItem listItem, AjaxRequestTarget target) {
		Table.this.onSelection(listItem.getIndex(), target);
	    }
	});
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

    @Override
    public void renderHead(IHeaderResponse response) {
	response.renderCSSReference(getCss());
    }

    protected ResourceReference getCss() {
	return TABLE_CSS;
    }

    protected void onSelection(int newSelectionIndex, AjaxRequestTarget target) {
    }

}// class Table
