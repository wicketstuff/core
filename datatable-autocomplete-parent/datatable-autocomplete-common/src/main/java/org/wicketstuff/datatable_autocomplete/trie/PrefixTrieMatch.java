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

import java.util.LinkedList;
import java.util.List;


/**
 * @author mocleiri
 * 
 * A trie match using the prefix search.
 *
 */
public class PrefixTrieMatch<C> extends TrieMatch<C> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// the node that the prefix was matched to
	private TrieNode<C> node;
	
	/**
	 * @param node
	 */
	public PrefixTrieMatch(String matched, ITrieFilter<C>nodeFilter, TrieNode<C> node) {
		super(matched, Type.PREFIX_MATCH, nodeFilter);
		this.node = node;
	}

	
	/**
	 * @return the node
	 */
	public TrieNode<C> getNode() {
	
		return this.node;
	}


	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.trie.TrieMatch#getWordList(int)
	 */
	@Override
	public List<C> getWordList(int limit) {
		
		List<C> wordList = new LinkedList<C>();;
		
		this.node.buildWordList(wordList, nodeFilter, limit);
		
		return wordList;
	}
	
	
}
