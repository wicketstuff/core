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

/**
 * @author mocleiri
 * 
 * Provides a base configuration for a Trie<C>.  
 *
 */
public abstract class AbstractTrieConfiguration<C> implements ITrieConfiguration<C> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5376389092400454702L;

	/**
	 * 
	 */
	public AbstractTrieConfiguration() {
		
		defaultFilter = newTrieFilter();
	}

	/**
	 * 
	 * @return the trie filter that will be used by default by the Trie.
	 */
	protected ITrieFilter<C> newTrieFilter() {
		return new ITrieFilter<C>() {

			/* (non-Javadoc)
			 * @see org.wicketstuff.datatable_autocomplete.trie.TrieFilter#isVisible(java.lang.Object)
			 */
			public boolean isVisible(C word) {
				return true;
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.trie.ITrieConfiguration#createTrieNode()
	 */
	public TrieNode<C> createTrieNode(TrieNode<C> parent, String rootMatchedString, String nextCharacter) {
		return new TrieNode<C>(parent, rootMatchedString, nextCharacter, 
				this);
	}

	private final ITrieFilter<C>defaultFilter;
	
	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.trie.ITrieConfiguration#getDefaultFilter()
	 */
	public ITrieFilter<C> getDefaultFilter() {
		
		return defaultFilter;
	}
	
	
	
	

}
