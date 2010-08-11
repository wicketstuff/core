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

/**
 * @author mocleiri
 * 
 * Default implementation of the IDTATableRenderingHints interface.
 * 
 *
 */
public class DefaultDTATableRenderingHints implements IDTATableRenderingHints {

	private final int pageSize;
	private final boolean paginationEnabled;

	/**
	 * 
	 */
	public DefaultDTATableRenderingHints(int pageSize, boolean paginationEnabled) {
		this.pageSize = pageSize;
		this.paginationEnabled = paginationEnabled;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.table.IDTATableRenderingHints#getPageSize()
	 */
	public int getPageSize() {
		return this.pageSize;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.table.IDTATableRenderingHints#isPaginationEnabled()
	 */
	public boolean isPaginationEnabled() {
		return this.paginationEnabled;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.table.IDTATableRenderingHints#showNoMatchToolbar()
	 */
	public boolean showNoRecordsToolbar() {
		
		return true;
	}

}
