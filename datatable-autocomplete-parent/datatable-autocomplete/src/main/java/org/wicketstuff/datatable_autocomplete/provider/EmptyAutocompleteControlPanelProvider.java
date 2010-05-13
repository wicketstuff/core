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
package org.wicketstuff.datatable_autocomplete.provider;

import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.wicketstuff.datatable_autocomplete.panel.IAutocompleteControlPanelProvider;

/**
 * @author mocleiri
 *
 */
public class EmptyAutocompleteControlPanelProvider implements
		IAutocompleteControlPanelProvider, IClusterable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public EmptyAutocompleteControlPanelProvider() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.panel.IAutocompleteControlPanelProvider#getPanel(java.lang.String)
	 */
	public Component getPanel(Component updateOnChangeComponent, String controlPanelId) {
		return new EmptyPanel(controlPanelId);
	}

}
