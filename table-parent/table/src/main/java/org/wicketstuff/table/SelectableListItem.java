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

import javax.swing.ListSelectionModel;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.JavascriptUtils;

/**
 * List item that resolve the view complexity for a item that can or not to be
 * selected based on a ListSelectionModel
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public abstract class SelectableListItem extends ColoredListItem {
    private static final long serialVersionUID = 1L;
    public static final String CLASS_SELECTED = "selected";
    public static final String CLASS_MOUSE_OVER = "onMouseOver";
    private ListSelectionModel listSelectionModel;
    private static final HeaderContributor TABLE_JS = JavascriptPackageResource
	    .getHeaderContribution(new ResourceReference(SelectableListItem.class, "res/table.js"));

    /**
     * @param index
     * @param model
     *            list item model, users parameter.
     * @param listSelectionModel
     *            Based on its state, the row selection is resolved. Ex: row css
     *            class will reflect the actual state.
     */
    public SelectableListItem(int index, IModel model, ListSelectionModel listSelectionModel) {
	super(index, model);
	setOutputMarkupId(true);
	add(TABLE_JS);
	this.listSelectionModel = listSelectionModel;
	updateBackgroundColor();
	this.add(new AjaxEventBehavior("onclick") {
	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		onSelection(target);
	    }
	});
    }

    protected abstract void onSelection(AjaxRequestTarget target);

    public void updateOnAjaxRequest(AjaxRequestTarget target) {
	updateBackgroundColor();
	target.appendJavascript("document.getElementById('" + getMarkupId() + "').className = '"
		+ classAttribute + "';");
	target.appendJavascript("document.getElementById('" + getMarkupId()
		+ "').setAttribute('originalClass', '" + classAttribute + "'); ");
    }

    @Override
    protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
	super.onComponentTagBody(markupStream, openTag);
	updateBackgroundColor();
	JavascriptUtils js = new JavascriptUtils(getResponse());
	js.write("changeStyleOnOnMouseOver( '" + getMarkupId() + "', '" + CLASS_MOUSE_OVER + "');");
	js.close();
    }

    @Override
    protected void updateBackgroundColor() {
	if (listSelectionModel != null && listSelectionModel.isSelectedIndex(getIndexOnModel())) {
	    classAttribute = CLASS_SELECTED;
	} else {
	    super.updateBackgroundColor();
	}
    }

    protected int getIndexOnModel() {
	return this.getIndex();
    }
}