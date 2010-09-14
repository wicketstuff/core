/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.datatable_autocomplete.table;

import java.util.Arrays;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.resource.ResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.form.action.IFormOnSubmitAction;
import org.wicketstuff.datatable_autocomplete.selection.ITableRowSelectionHandler;

/**
 * @author mocleiri
 * 
 *         Allows a table to be rendered where the rows are selectable
 * 
 * @param T the type of the object in the table row.  The type of the object returned from the dataprovidcer
 */
public class SelectableTableViewPanel<T> extends AbstractSelectableTableViewPanel<T> {

	private  DefaultSelectableTableViewPanelButtonProviderImpl createButtonProvider = new DefaultSelectableTableViewPanelButtonProviderImpl ("Create New", false, false);
	private  DefaultSelectableTableViewPanelButtonProviderImpl editButtonProvider = new DefaultSelectableTableViewPanelButtonProviderImpl ("Edit Selected", true, true);	
	private  DefaultSelectableTableViewPanelButtonProviderImpl deleteButtonProvider = new DefaultSelectableTableViewPanelButtonProviderImpl ("Delete Selected", true, true);
	
	/**
	 * @param id
	 * @param css_reference
	 * @param cssClassName
	 * @param displayEntityName
	 * @param column
	 * @param dataProvider
	 * @param buttonProviderList
	 * @param selectedRowContextExtractor
	 * @param rowsPerPage
	 * @param showTableFeedbackPanel
	 * @param selectionHandler 
	 */
	public SelectableTableViewPanel(String id,
			ResourceReference css_reference, String cssClassName,
			String displayEntityName, IColumn<?> column,
			ISortableDataProvider<T> dataProvider,
			boolean showTableFeedbackPanel, ITableRowSelectionHandler<T> selectionHandler, IDTATableRenderingHints hints) {

		super(id, css_reference, cssClassName, displayEntityName, column, dataProvider,
				 
				showTableFeedbackPanel, selectionHandler, hints);
		
		setButtonProviderList (Arrays.asList(new ISelectableTableViewPanelButtonProvider[] {editButtonProvider, deleteButtonProvider, createButtonProvider}));
	}

	

	/**
	 * @param id
	 * @param css_reference
	 * @param cssClassNamne
	 * @param displayEntityName
	 * @param column
	 * @param dataProvider
	 * @param buttonProviderList
	 * @param selectedRowContextExtractor
	 * @param rowsPerPage
	 * @param selectionHandler 
	 */
	public SelectableTableViewPanel(String id,
			ResourceReference css_reference, String cssClassNamne,
			String displayEntityName, IColumn<?> column,
			ISortableDataProvider<T> dataProvider,
			ITableRowSelectionHandler<T> selectionHandler, IDTATableRenderingHints hints) {

		super(id, css_reference, cssClassNamne, displayEntityName, column,
				dataProvider,
				selectionHandler, hints);

		setButtonProviderList (Arrays.asList(new ISelectableTableViewPanelButtonProvider[] {editButtonProvider, deleteButtonProvider, createButtonProvider}));
	}

	/**
	 * @param id
	 * @param css_reference
	 * @param cssClassName
	 * @param displayEntityName
	 * @param columns
	 * @param dataProvider
	 * @param buttonProviderList
	 * @param selectedRowContextExtractor
	 * @param rowsPerPage
	 * @param showTableFeedbackPanel
	 * @param selectionHandler 
	 */
	public SelectableTableViewPanel(String id,
			ResourceReference css_reference, String cssClassName,
			String displayEntityName, IColumn<?>[] columns,
			ISortableDataProvider<T> dataProvider,
			boolean showTableFeedbackPanel, ITableRowSelectionHandler<T> selectionHandler, IDTATableRenderingHints hints) {

		super(id, css_reference, cssClassName, displayEntityName, columns,
				dataProvider,
				showTableFeedbackPanel, selectionHandler, hints);
		
		setButtonProviderList (Arrays.asList(new ISelectableTableViewPanelButtonProvider[] {editButtonProvider, deleteButtonProvider, createButtonProvider}));
	}

	/**
	 * @param id
	 * @param css_reference
	 * @param cssClassName
	 * @param displayEntityName
	 * @param columns
	 * @param dataProvider
	 * @param buttonProviderList
	 * @param selectedRowContextExtractor
	 * @param rowsPerPage
	 * @param selectionHandler 
	 */
	public SelectableTableViewPanel(String id,
			ResourceReference css_reference, String cssClassName,
			String displayEntityName, IColumn<?>[] columns,
			ISortableDataProvider<T> dataProvider,
			ITableRowSelectionHandler<T> selectionHandler, IDTATableRenderingHints hints) {

		super(id, css_reference, cssClassName, displayEntityName, columns,
				dataProvider,
				selectionHandler, hints);
		setButtonProviderList (Arrays.asList(new ISelectableTableViewPanelButtonProvider[] {editButtonProvider, deleteButtonProvider, createButtonProvider}));
	}

	/**
	 * @param id
	 * @param title
	 * @param columns
	 * @param sortableListDataProvider
	 * @param buttonProviderList
	 * @param rowContextConverter
	 * @param i
	 * @param showTableFeedbackPanel
	 * @param selectionHandler 
	 */
	public SelectableTableViewPanel(String id, String title,
			IColumn<?>[] columns,
			ISortableDataProvider<T> sortableListDataProvider,
		 boolean showTableFeedbackPanel, ITableRowSelectionHandler<T> selectionHandler, IDTATableRenderingHints hints) {

		super(id, title, columns, sortableListDataProvider, 
			 showTableFeedbackPanel, selectionHandler, hints);
		setButtonProviderList (Arrays.asList(new ISelectableTableViewPanelButtonProvider[] {editButtonProvider, deleteButtonProvider, createButtonProvider}));
	}

	/**
	 * @param id
	 * @param displayEntityName
	 * @param tableColumns
	 * @param attributeFilterDataProvider
	 * @param buttonProviderList
	 * @param selectedRowContextConverter
	 * @param i
	 * @param selectionHandler 
	 */
	public SelectableTableViewPanel(String id, String displayEntityName,
			IColumn<?>[] tableColumns,
			ISortableDataProvider<T> attributeFilterDataProvider,
	 ITableRowSelectionHandler<T> selectionHandler, IDTATableRenderingHints hints) {

		super(id, displayEntityName, tableColumns, attributeFilterDataProvider,
			 selectionHandler, hints);
		
		setButtonProviderList (Arrays.asList(new ISelectableTableViewPanelButtonProvider[] {editButtonProvider, deleteButtonProvider, createButtonProvider}));
	}

	public SelectableTableViewPanel(String string, String string2,
			IColumn<?>[] tableColumns,
			ISortableDataProvider<T> provider, IDTATableRenderingHints hints) {
		
		this (string, string2, tableColumns, provider, null, hints);
	}

	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1000109813724689754L;

	private static final Logger			log					= LoggerFactory
																		.getLogger(SelectableTableViewPanel.class);
	


	

	/**
	 * @param formOnSubmitAction
	 */
	public void setEditOnSubmitAction(
			final IFormOnSubmitAction<T> formOnSubmitAction) {

		editButtonProvider.setAction(formOnSubmitAction);

	}

	
	public void setButtonCSSClass (String cssClass) {
		this.createButtonProvider.setCssClassName(cssClass);
		this.editButtonProvider.setCssClassName(cssClass);
		this.deleteButtonProvider.setCssClassName(cssClass);
	}
	
	/**
	 * @param formOnSubmitAction
	 */
	public void setDeleteOnSubmitAction(
			final IFormOnSubmitAction<T> formOnSubmitAction) {

		deleteButtonProvider.setAction(formOnSubmitAction);

	}
	
	

	/**
	 * @param formOnSubmitAction
	 */
	public void setCreateOnSubmitAction(IFormOnSubmitAction<T> formOnSubmitAction) {

		createButtonProvider.setAction(formOnSubmitAction);
	}

	/**
	 * @param actionLabel
	 */
	public void setEditButtonLabel(String actionLabel) {

		editButtonProvider.setButtonLabelText(actionLabel);

	}

	
	
}
