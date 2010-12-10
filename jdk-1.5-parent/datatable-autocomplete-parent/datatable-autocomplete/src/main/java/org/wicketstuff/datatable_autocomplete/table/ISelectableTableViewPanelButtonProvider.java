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

import org.apache.wicket.IClusterable;
import org.wicketstuff.datatable_autocomplete.form.action.IFormSubmitAction;


/**
 * 
 * @author mocleiri
 * 
 *         Provides a button details.
 * 
 *         We default to ajaxfallback buttons.
 * 
 */
public interface ISelectableTableViewPanelButtonProvider extends
		IClusterable {

	/**
	 * 
	 * @return the action to perform when this button is clicked.
	 */
	public IFormSubmitAction<?> getButtonAction();

	/**
	 * 
	 * @param displayEntityName
	 * @return the button label contextualized to the display entity.
	 * 
	 */
	public String getButtonLabelText(String displayEntityName);

	/**
	 * 
	 * @return non null if there is a class atribute that should be written on the button.
	 */
	public String getCSSClassName();
	
	/**
	 * 
	 * @return if a selected row is a precondition to this action.
	 */
	public boolean isSelectedRowRequired();

	/**
	 * @return true if the selected row should be cleared as a result of
	 *         clicking on the button.
	 */
	public boolean isClearSelectedRowOnAction();
	
	

}
