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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.form.action.IFormOnSubmitAction;


/**
 * @author mocleiri
 *
 */
public class DefaultSelectableTableViewPanelButtonProviderImpl implements
		ISelectableTableViewPanelButtonProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3162806247349691580L;
	private static final Logger	log	= LoggerFactory
											.getLogger(DefaultSelectableTableViewPanelButtonProviderImpl.class);
	private String	buttonLabelText;
	private IFormOnSubmitAction<?>	action = null;
	private final boolean	clearSelectedRowOnAction;
	private final boolean	requireSelectedRow;
	private String cssClassName;

	/**
	 * 
	 */
	public DefaultSelectableTableViewPanelButtonProviderImpl(String buttonLabelText, IFormOnSubmitAction<?> action, boolean clearSelectedRowOnAction, boolean requireSelectedRow) {
		this.buttonLabelText = buttonLabelText;
		this.action = action;
		this.clearSelectedRowOnAction = clearSelectedRowOnAction;
		this.requireSelectedRow = requireSelectedRow;

	}
	
	public DefaultSelectableTableViewPanelButtonProviderImpl(String buttonLabelText, boolean clearSelectedRowOnAction, boolean requireSelectedRow) {
		this.buttonLabelText = buttonLabelText;
		this.clearSelectedRowOnAction = clearSelectedRowOnAction;
		this.requireSelectedRow = requireSelectedRow;

	}

	
	
	
	/**
	 * @param buttonLabelText the buttonLabelText to set
	 */
	public void setButtonLabelText(String buttonLabelText) {
	
		this.buttonLabelText = buttonLabelText;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(IFormOnSubmitAction<?> action) {
	
		this.action = action;
	}

	public IFormOnSubmitAction<?> getButtonAction() {

		return action;
	}

	public String getButtonLabelText(String displayEntityName) {

		return buttonLabelText + " " + displayEntityName;
	}

	public boolean isClearSelectedRowOnAction() {

		return clearSelectedRowOnAction;
	}

	public boolean isSelectedRowRequired() {

		return requireSelectedRow;
	}

	public String getCSSClassName() {
		return cssClassName;
	}
	
	
}
