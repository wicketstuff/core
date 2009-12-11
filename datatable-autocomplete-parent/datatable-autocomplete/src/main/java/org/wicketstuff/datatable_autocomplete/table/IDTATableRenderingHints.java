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

/**
 * @author mocleiri
 *
 */
public interface IDTATableRenderingHints extends IClusterable {
	/**
	 * 
	 * @return true if the results table should use AJAX pagination.
	 */
	public boolean isPaginationEnabled();
	
	/**
	 * 
	 * @return the number of rows per page.  If isPaginationEnabled() returns false then only 1 page is shown.
	 */  
	public int getPageSize(); 
	
	/**
	 * 
	 * @return true if the no match tool bar should be shown in the table when there are no results.
	 */
	public boolean showNoRecordsToolbar();
}
