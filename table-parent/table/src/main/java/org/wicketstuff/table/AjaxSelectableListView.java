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
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author Pedro Henrique Oliveira dos Santos
 *
 */
public abstract class AjaxSelectableListView extends PageableListView {
    private static final long serialVersionUID = 1L;
    private ListSelectionModel listSelectionModel;

    public AjaxSelectableListView(String id, IModel model) {
	this(id, model, Integer.MAX_VALUE);
    }

    public AjaxSelectableListView(String id, List list) {
	this(id, list, Integer.MAX_VALUE);
    }

    public AjaxSelectableListView(String id, List list, int rowsPerPage) {
	this(id, new Model((Serializable) list), rowsPerPage);
    }

    public AjaxSelectableListView(String id, IModel model, int rowsPerPage) {
	this(id, model, rowsPerPage, createDefaultSelectionModel());
    }

    public AjaxSelectableListView(String id, IModel model, int rowsPerPage,
	    ListSelectionModel selectionModel) {
	super(id, model, rowsPerPage);
	this.listSelectionModel = selectionModel;
    }

    private static ListSelectionModel createDefaultSelectionModel() {
	ListSelectionModel selectionModel = new DefaultListSelectionModel();
	selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	return selectionModel;
    }

    public IModel getSelection() {
	return (IModel) visitChildren(SelectableListItem.class, new IVisitor() {
	    @Override
	    public Object component(Component component) {
		SelectableListItem item = (SelectableListItem) component;
		if (item.getIndex() == listSelectionModel.getLeadSelectionIndex()) {
		    return item.getModel();
		}
		return null;
	    }
	});
    }

    @Override
    protected ListItem newItem(final int index) {
	final SelectableListItem listItem = new SelectableListItem(index, getListItemModel(
		getModel(), index), listSelectionModel) {
	    @Override
	    protected void onSelection(AjaxRequestTarget target) {
		AjaxSelectableListView.this.setSelection(this, target);
	    }
	};

	return listItem;
    }
    public void clearSelection(){
	listSelectionModel.clearSelection();
    }
    private void setSelection(final SelectableListItem selectedItem, final AjaxRequestTarget target) {
	listSelectionModel.setSelectionInterval(selectedItem.getIndex(), selectedItem.getIndex());
	visitChildren(SelectableListItem.class, new IVisitor() {
	    @Override
	    public Object component(Component component) {
		SelectableListItem listItem = (SelectableListItem) component;
		if (listItem.getIndex() == selectedItem.getIndex()) {
		    listItem.setSelected(target);
		} else {
		    if (listSelectionModel.getSelectionMode() == ListSelectionModel.SINGLE_SELECTION) {
			listItem.removeSelection(target);
		    }
		}
		return null;
	    }
	});
	onSelection(selectedItem, target);
    }

    protected void onSelection(SelectableListItem listItem, AjaxRequestTarget target) {

    }

    public ListSelectionModel getListSelectionModel() {
        return listSelectionModel;
    };
}