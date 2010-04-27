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

/**
 * @author mocleiri
 *
 */
public class DefaultTrieDataProviderHints implements ITrieDataProviderHints {

	/**
	 * 
	 */
	public DefaultTrieDataProviderHints() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.provider.ITrieDataProviderHints#getMaxResultsLimit(java.lang.String)
	 */
	public int getMaxResultsLimit(String value) {
		
		if (value.length() == 0) {
			return 0;
		}
		else if (value.length() == 1) {
			return 500;
		}
		else if (value.length() == 2) {
			return 500;
		}
		else if (value.length() == 3) {
			return 500;
		}
		else // > 3
			return -1;
	}
	
	

}
