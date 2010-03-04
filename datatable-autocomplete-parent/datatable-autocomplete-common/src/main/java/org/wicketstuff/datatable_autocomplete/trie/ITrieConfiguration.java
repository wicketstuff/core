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
package org.wicketstuff.datatable_autocomplete.trie;

import org.apache.wicket.IClusterable;


/**
 * @author mocleiri
 * 
 * Provides the logic to create the string that is indexed from the actual object context.
 * 
 * Can hint as to the 
 *
 */
public interface ITrieConfiguration<C> extends IClusterable {
	
	/**
	 * Extract the word from the context given.
	 * 
	 * @param ctx
	 * @return
	 */
	public String getWord (C ctx);
	

	
	/**
	 * Provides the default filter to be used when searching for a match.
	 * 
	 */
	public ITrieFilter<C> getDefaultFilter();
	
	/**
	 * 
	 * If true then capitalized and lower case will be sorted separately.
	 * 
	 * If false the word will be converted to lower case.
	 * 
	 * @return case sensitivity of the index.
	 * 
	 */
	public boolean isIndexCaseSensitive();
	

	public TrieNode<C> createTrieNode(TrieNode<C> parent, String rootMatchedString,
			String nextCharacter);
	
}
