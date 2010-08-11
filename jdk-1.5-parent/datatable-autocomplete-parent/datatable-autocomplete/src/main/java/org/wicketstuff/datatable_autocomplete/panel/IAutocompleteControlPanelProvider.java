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

import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;

/**
 * @author mocleiri
 * 
 * The autocomplete panel provides a space just above the results table where extra controls can be placed to finetune 
 * the operation of the autocompletion process.
 * 
 * This interface provides a way to create the component to use as the control panel.
 *
 */
public interface IAutocompleteControlPanelProvider extends IClusterable {

	Component getPanel(Component container, String controlPanelId);

}
