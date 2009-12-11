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

import java.util.List;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.markup.repeater.IItemReuseStrategy;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.radio.DTARadioGroup;
import org.wicketstuff.datatable_autocomplete.selection.ITableRowSelectionHandler;
import org.wicketstuff.datatable_autocomplete.table.DTADataTable.DTADataTableItemModifier;
import org.wicketstuff.datatable_autocomplete.table.button.ButtonListView;
import org.wicketstuff.datatable_autocomplete.table.column.DTARadioColumn;

/**
 * @author mocleiri
 * 
 */
public abstract class AbstractSelectableTableViewPanel<T> extends
		FormComponentPanel<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4369026666390989926L;

	private static final Logger log = LoggerFactory
			.getLogger(AbstractSelectableTableViewPanel.class);

	private DTADataTable<T> dataTable;

	// for the auto complete case we will set this to true and then the
	// style.display of the panel will be adjusted.
	private boolean hideIfNoResults = false;

	protected final ISortableDataProvider<T> dataProvider;

	private static final ResourceReference DEFAULT_CSS = new CompressedResourceReference(
			DTADataTable.class, "dta_table.css");

	protected ButtonListView buttonView;

	private DTARadioGroup<T> radioGroup;

	private final ITableRowSelectionHandler<T> rowSelectionHandler;

	/**
	 * @param asList
	 * 
	 *            Sets the list of button providers to use with the selectable
	 *            table view @see
	 *            {@link DefaultSelectableTableViewPanelButtonProviderImpl}
	 * 
	 *            and {@link SelectableTableViewPanel} for examples
	 */
	public final void setButtonProviderList(
			List<ISelectableTableViewPanelButtonProvider> asList) {

		this.buttonView.setList(asList);
	}

	public AbstractSelectableTableViewPanel(String id,
			ResourceReference css_reference, String cssClassNamne,
			String displayEntityName, IColumn<?> column,
			ISortableDataProvider<T> dataProvider, 
			ITableRowSelectionHandler<T> selectionHandler, IDTATableRenderingHints hints) {

		this(id, css_reference, cssClassNamne, displayEntityName, column,
				dataProvider, true, selectionHandler, hints);
	}

	public AbstractSelectableTableViewPanel(String id,
			ResourceReference css_reference, String cssClassName,
			String displayEntityName, IColumn<?> column,
			ISortableDataProvider<T> dataProvider, 
			boolean showTableFeedbackPanel,
			ITableRowSelectionHandler<T> selectionHandler, IDTATableRenderingHints hints) {

		this(id, css_reference, cssClassName, displayEntityName,
				new IColumn[] { column }, dataProvider, 
				showTableFeedbackPanel, selectionHandler, hints);
	}

	/**
	 * @param id
	 * @param displayEntityName
	 *            the name of the entity used for button customization
	 * @param viewAction
	 * @param columns
	 * @param dataProvider
	 * @param rowsPerPage
	 * @param selectionHandler
	 * @param selectedRowContextExtractor
	 * @param buttonProviderList
	 * @param showTableFeedbackPanel
	 *            true if the feedback panel should be visible; used in the
	 *            nesteded components with many feedback panels situation.
	 */
	public AbstractSelectableTableViewPanel(String id,
			ResourceReference css_reference, String cssClassName,
			String displayEntityName, IColumn<?>[] columns,
			ISortableDataProvider<T> dataProvider, 
			ITableRowSelectionHandler<T> selectionHandler, IDTATableRenderingHints hints) {

		this(id, css_reference, cssClassName, displayEntityName, columns,
				dataProvider, true, selectionHandler, hints);
	}

	public AbstractSelectableTableViewPanel(String id,
			ResourceReference css_reference, String cssClassName,
			String displayEntityName, IColumn<?>[] columns,
			ISortableDataProvider<T> dataProvider, 
			boolean showTableFeedbackPanel,
			ITableRowSelectionHandler<T> rowSelectionHandler, IDTATableRenderingHints hints) {

		super(id, new Model());
		this.dataProvider = dataProvider;
		this.rowSelectionHandler = rowSelectionHandler;

		add(CSSPackageResource.getHeaderContribution(css_reference));

		Form form = new Form("viewForm");

		radioGroup = new DTARadioGroup<T>("radioGroup", this.dataProvider
				.model(null));

		IColumn<?>[] includingRadioColumns = new IColumn[columns.length + 1];

		// TODO: allow customization of the radio column label
		final DTARadioColumn<T> radioColumn = new DTARadioColumn<T>("");

		includingRadioColumns[0] = radioColumn;

		for (int i = 0; i < columns.length; i++) {

			includingRadioColumns[i + 1] = columns[i];
		}

		dataTable = new DTADataTable<T>("dataTable", cssClassName,
				includingRadioColumns, dataProvider, hints);

		
		radioGroup.add(dataTable);

		form.add(radioGroup);

		dataTable.setItemModifier(new DTADataTableItemModifier<T>() {

			private static final long serialVersionUID = 5144108737965241352L;

		
			public void modifyRowItem(final Item<T> item) {

				if (AbstractSelectableTableViewPanel.this.rowSelectionHandler != null) {

					item.add(new AjaxEventBehavior("onclick") {

						/**
						 * 
						 */
						private static final long serialVersionUID = 2282163166444338308L;

						@Override
						protected void onEvent(AjaxRequestTarget target) {

							int index = item.getIndex();
							T selectedObject = item.getModelObject();
							
							/*
							 * Delegate to the row selection handler
							 */
							AbstractSelectableTableViewPanel.this.rowSelectionHandler
									.handleSelection(index, selectedObject, target);

						}
					});

				} else {

					// Intentionally does nothing.
				}
			}

		});

		

		buttonView = new ButtonListView("buttonView", form,
				displayEntityName, radioGroup);

		form.add(buttonView);


		FeedbackPanel feedbackPanel;
		add(feedbackPanel = new FeedbackPanel("feedback"));

		// in certain cases where we nest components with feedback panels we
		// will want to hide some of them.
		feedbackPanel.setVisible(showTableFeedbackPanel);

		add(form);

	}

	/**
	 * @param string
	 * @param string2
	 * @param tableColumns
	 * @param attributeFilterDataProvider
	 * @param selectedRowContextConverter
	 * @param i
	 */
	public AbstractSelectableTableViewPanel(String id,
			String displayEntityName, IColumn<?>[] tableColumns,
			ISortableDataProvider<T> attributeFilterDataProvider, 
			ITableRowSelectionHandler<T> selectionHandler, IDTATableRenderingHints hints) {

		this(id, DEFAULT_CSS, "dta_data_table", displayEntityName,
				tableColumns, attributeFilterDataProvider, selectionHandler, hints);
	}

	/**
	 * @param id
	 * @param title
	 * @param columns
	 * @param sortableListDataProvider
	 * @param rowContextConverter
	 * @param i
	 * @param b
	 */
	public AbstractSelectableTableViewPanel(String id, String title,
			IColumn<?>[] columns,
			ISortableDataProvider<T> sortableListDataProvider,
			boolean showTableFeedbackPanel, ITableRowSelectionHandler<T> selectionHandler, IDTATableRenderingHints hints) {

		this(id, DEFAULT_CSS, "dta_data_table", title, columns,
				sortableListDataProvider, showTableFeedbackPanel, selectionHandler, hints);

	}

	public T getSelectedRow() {
		return radioGroup.getModelObject();
	}

	/**
	 * @param hideIfNoResults
	 *            the hideIfNoResults to set
	 */
	public void setHideIfNoResults(boolean hideIfNoResults) {

		this.hideIfNoResults = hideIfNoResults;
	}

	/**
	 * @return
	 */
	public final DTADataTable<T> getDataTable() {

		return dataTable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.Component#onBeforeRender()
	 */
	@Override
	protected void onBeforeRender() {

		updateVisibility();

		super.onBeforeRender();

	}

	/**
	 * Normally a wicket component that is made invisible will never render again.  This will cause our
	 * visibility to be updated and allow rendering to occur.
	 * 
	 * This method is public because our visibility is used by the AutoCompletingPanel to determine its visibility.
	 * 
	 */
	public void updateVisibility() {

		
		if (this.hideIfNoResults && dataProvider.size() == 0) {

			setVisible(false);
		} else
			setVisible(true);

	}

	/**
	 * does not highlight when a row is selected.
	 * 
	 * @param disableRowHighlight
	 */
	public final void setDisableRowHighlight(boolean disableRowHighlight) {

		// FIXME: find out how to disable the row highlight on the table.
		
//		this.dataTable.setDisableRowHighlight(disableRowHighlight);
	}

	/**
	 * @param strategy
	 * @return
	 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable#setItemReuseStrategy(org.apache.wicket.markup.repeater.IItemReuseStrategy)
	 */
	public final DataTable<T> setItemReuseStrategy(IItemReuseStrategy strategy) {
		return dataTable.setItemReuseStrategy(strategy);
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.FormComponent#convertInput()
	 */
	@Override
	protected void convertInput() {
		
		radioGroup.processInput();
		
		super.convertInput();
		
		
	}

	
}
