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
package org.wicketstuff.datatable_autocomplete.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.behaviour.AutoCompletingBehavior;
import org.wicketstuff.datatable_autocomplete.selection.ITableRowSelectionHandler;

/**
 * @author mocleiri
 * 
 *         R is the type used in the look up.
 */
public class AutoCompletingTextField<R> extends Panel {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5234331727415699325L;
	private static final Logger log = LoggerFactory
			.getLogger(AutoCompletingTextField.class);
	private AutoCompletingPanel<R> autoCompletingPanel;

	private final IAutocompleteRenderingHints renderingHints;
	private final IModel<String> searchFieldModel;
	private AutoCompletingBehavior autoCompletingBehaviour;
	private TextField<String> searchField;

	/**
	 * 
	 * @param id
	 * @param searchFieldModel
	 *            model that contains the prefix used in filtering the results.
	 * @param rowsPerPage
	 *            number of rows to show per page.
	 * @param columns
	 *            the columns to show in the @see {@link AutoCompletingPanel}
	 * @param provider
	 *            provides the data for the autocompleting panel.
	 * @param rowSelectionHandler
	 *            This is what handles the assignment of the selected row
	 *            somewhere.
	 * @param usePagination
	 *            allows turning on or off the pagination controls.
	 */

	public AutoCompletingTextField(String id, IModel<String> searchFieldModel,
			IColumn<?>[] columns,
			SortableDataProvider<R> provider,
			ITableRowSelectionHandler<R> rowSelectionHandler, IAutocompleteControlPanelProvider controlPanelProvider,
			IAutocompleteRenderingHints renderingHints) {

		this(id, searchFieldModel, columns, provider,
				rowSelectionHandler, controlPanelProvider, 300, renderingHints);

	}

	/**
	 * 
	 * @param id
	 * @param searchFieldModel
	 *            model that contains the prefix used in filtering the results.
	 * @param rowsPerPage
	 *            number of rows to show per page.
	 * @param columns
	 *            the columns to show in the @see {@link AutoCompletingPanel}
	 * @param provider
	 *            provides the data for the autocompleting panel.
	 * @param rowSelectionHandler
	 *            This is what handles the assignment of the selected row
	 *            somewhere.
	 * @param usePagination
	 *            allows turning on or off the pagination controls.
	 * @param throttlingDelay
	 *            Controls the number of miliseconds to wait after a keystroke
	 *            before issuing the get request to the server.
	 * @param renderingHints2 
	 */
	public AutoCompletingTextField(String id, IModel<String> searchFieldModel,
			IColumn<?>[] columns,
			SortableDataProvider<R> provider,
			ITableRowSelectionHandler<R> rowSelectionHandler, IAutocompleteControlPanelProvider controlPanelProvider,
			int throttlingDelay, IAutocompleteRenderingHints renderingHints) {

		// dummy model.
		// we are a formcomponentpanel so that we are traversed during form
		// submissions.
		super(id, new Model());
		this.searchFieldModel = searchFieldModel;
		this.renderingHints = renderingHints;

		searchField = new TextField<String>("searchField",
				searchFieldModel);

		searchField.add(new AttributeModifier("autocomplete", true,
				new Model<String>("off")));

		autoCompletingPanel = new AutoCompletingPanel<R>("autoCompletingPanel",
				searchField.getModel(), columns, provider, 
				rowSelectionHandler, controlPanelProvider, renderingHints);

		add(searchField);

		add(autoCompletingPanel);

		searchField.add(autoCompletingBehaviour = new AutoCompletingBehavior(searchField,
				autoCompletingPanel, throttlingDelay));
		
		add(new AjaxLink<Void>("showLink") {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5603196048089462726L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			public void onClick(AjaxRequestTarget target) {
				
				String callbackScript = autoCompletingBehaviour.getCallbackScript(true).toString();
				
				target.prependJavaScript(callbackScript);
				
				
				
			}
			
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.Component#onBeforeRender()
	 */
	@Override
	protected void onBeforeRender() {

		if (renderingHints != null) {

			if (renderingHints.isVisibleOnRender()) {
				this.autoCompletingPanel.setInitialRenderDisabledMode(false);
			}
			else {
				this.autoCompletingPanel.setInitialRenderDisabledMode(true);
			}
			
			
		} else {
			// default to hide the autocomplete panel on no input.
			this.autoCompletingPanel.setInitialRenderDisabledMode(true);
		}

		super.onBeforeRender();
	}

	/**
	 * @return the AJAX listener url for the autocompleting behaviour
	 * @see org.apache.wicket.behavior.AbstractAjaxBehavior#getCallbackUrl()
	 */
	public String getAutoCompleteCallbackUrl() {
		return autoCompletingBehaviour.getCallbackUrl().toString();
	}


	/**
	 * Allows behaviours to be placed on the inner text field.
	 * 
	 * This can be used to add an onblur action to push results to the server side. 
	 * 
	 * @param beh
	 */
	public void addBehaviorToAutoCompletingTextField (Behavior beh) {
		this.searchField.add(beh);
	}
	

}
