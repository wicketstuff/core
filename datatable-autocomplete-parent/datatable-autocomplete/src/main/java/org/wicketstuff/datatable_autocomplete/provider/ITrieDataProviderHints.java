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

import org.apache.wicket.IClusterable;

/**
 * @author mocleiri
 * 
 * When performing the first lookups especially where the dataset is large it can be expensive.
 * 
 * Typically we just want to see the 
 *
 */
public interface ITrieDataProviderHints extends IClusterable {
	
	/**
	 * 
	 * @return a hard limit for the number of results to return.
	 * 
	 * Can be based on the size of the input string.
	 * 
	 */
	public int getMaxResultsLimit(String value);

}
