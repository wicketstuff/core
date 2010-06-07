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

package org.wicketstuff.datatable_autocomplete.tst;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.wicketstuff.datatable_autocomplete.trie.ITrieConfiguration;
import org.wicketstuff.datatable_autocomplete.trie.ITrieFilter;
import org.wicketstuff.datatable_autocomplete.trie.Trie;

/**
 * @author mocleiri
 * 
 *         A ternary search trie is designed to save space by having less nodes.
 * 
 *         It works like a standard trie but instead of having sizeof(alphabet)
 * 
 * 
 */
public class TernarySearchTrie<C> implements Trie<C> {

	private TernaryNode<C> root = null;
	private final ITrieConfiguration<C> configuration;

	/**
	 * 
	 */
	public TernarySearchTrie(ITrieConfiguration<C> configuration) {

		this.configuration = configuration;

	}

	public void index(C value) {

		String word = this.configuration.getWord(value);

		if (!this.configuration.isIndexCaseSensitive())
			word = word.toLowerCase();

		String firstLetter = word.substring(0, 1);

		if (root == null) {

			root = new TernaryNode<C>(null, 0, firstLetter);

		}

	
			root.index(word, value);
	

	}

	public List<C> getWordList(String prefix, int limit) {

		List<C> wordList = new LinkedList<C>();

		TernaryNode<C> prefixNode = root.matchPrefix(prefix);

		if (prefixNode != null)
			prefixNode.collect(wordList);

		return wordList;
	}

	public List<C> getWordList(String prefix) {

		return this.getWordList(prefix, -1);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.wicketstuff.datatable_autocomplete.trie.Trie#getWordList(java.lang
	 * .String, org.wicketstuff.datatable_autocomplete.trie.ITrieFilter, int)
	 */
	public List<C> getWordList(String prefix, ITrieFilter<C> filter, int limit) {
		/*
		 * TODO: add support for TrieFiltering
		 */
		return getWordList(prefix, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.wicketstuff.datatable_autocomplete.trie.Trie#getWordList(java.lang
	 * .String, org.wicketstuff.datatable_autocomplete.trie.ITrieFilter)
	 */
	public List<C> getWordList(String prefix, ITrieFilter<C> filter) {
		/*
		 * TODO: add support for TrieFiltering
		 */
		return getWordList(prefix);
	}

	public void visit(TernaryNodeVisitor<C> visitor) {

		visitor.preVisit();

		root.visit(visitor);

		visitor.postVisit();
	}

	/**
	 * 
	 * @return the Set<String> for the starting (level 0) characters in the
	 *         index.
	 * 
	 */
	public Set<String> getNextNodeCharacterSet() {

		Set<String> levelZeroSet = new LinkedHashSet<String>();

		root.getStartingCharacterSet(levelZeroSet);

		return levelZeroSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.datatable_autocomplete.trie.Trie#postIndexing()
	 */
	public void postIndexing() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.datatable_autocomplete.trie.Trie#preIndexing()
	 */
	public void preIndexing() {
		// TODO Auto-generated method stub

	}
	
	

	/**
	 * @return the root
	 */
	public TernaryNode<C> getRoot() {
		return root;
	}

	public void printTree() {
		
		/*
		 * _N_
		 *  | \
		 *  
		 *  build all down less than then level ordered from there. back up until an unrendered left is encountered.
		 */
		
		
		
	}

}
