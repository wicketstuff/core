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

import org.wicketstuff.datatable_autocomplete.table.DefaultDTATableRenderingHints;


/**
 * @author mocleiri
 * 
 * Default implementation of the hints.
 * 
 *
 */
public class DefaultAutocompleteRenderingHints extends DefaultDTATableRenderingHints implements
		IAutocompleteRenderingHints {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6948932478194624159L;

	/**
	 * @param pageSize
	 * @param paginationEnabled
	 */
	public DefaultAutocompleteRenderingHints(int pageSize,
			boolean paginationEnabled) {
		super(pageSize, paginationEnabled);
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.panel.IAutocompleteRenderingHints#isVisibleOnRender(java.lang.String)
	 */
	public boolean isVisibleOnRender() {
		// default is to be invisible on initial render. becoming visible only after the ajax requests are sent.
		return false;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.panel.IAutocompleteRenderingHints#isVisibleOnZeroMatches()
	 */
	public boolean isVisibleOnZeroMatches() {
		// default is to be visible when there are zero matches.
		return true;
	}

	

	

}
