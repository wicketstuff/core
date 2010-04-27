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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.wicket.IClusterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mocleiri
 * 
 *         A Trie is a specialized search tree that is optimized for
 *         <i>retrieval</i> of data.
 * 
 *         This implementation is read-only and expects to load the data then
 *         minimize itself and be part of a singleton that returns the indexed
 *         data.
 * 
 *         It works well for prefix matching. It supports substring matching by
 *         traversing all nodes recursively looking for a match.
 * 
 *         An ITrieFilter<C> can be used to filter additional fields within an
 *         indexed object when the list of matching words (objects) is being
 *         computed.
 * 
 * @see http://en.wikipedia.org/wiki/Radix_tree
 * 
 *      It is suited for quick retrieval of prefix matches over large static
 *      datasets (100,000 elements)
 * 
 *      This implementation will index an object C based on the word (String)
 *      that is extracted using the ITrieNodeConfiguration.getWord (C c) method.
 * 
 */
public class Trie<C> implements IClusterable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6075870905379098868L;

	private static final Logger log = LoggerFactory.getLogger(Trie.class);

	private TrieNode<C> root = null;

	private ITrieConfiguration<C> configuration = null;
	
	/**
	 * 
	 */
	public Trie() {

		super();
	}

	
	/**
	 * 
	 */
	public Trie(ITrieConfiguration<C> configuration) {

		this.configuration = configuration;
		this.configuration.setTrie(this);
		
		this.root = configuration.createTrieNode(null, "", "");
		

	}

	/**
	 * @param streetName
	 * @return
	 */
	public void index(C value) {

		// traverse to the point where no match is found and then insert at that
		// point.

		this.root.index(value);

	}

	/**
	 * Get the list of strings that are reachable from the prefix given.
	 * 
	 * i.e. the ordered traversal of the subtree for the prefix given.
	 * 
	 * @param prefix
	 * @return
	 */
	public List<C> getWordList(String prefix) {

		return getWordList(prefix, configuration.getDefaultFilter(), -1);

	}

	// private List<C> getWordList(TrieNode<C> prefixNode) {
	//
	// return getWordList(prefixNode, configuration.getDefaultFilter(), -1);
	// }

	public PrefixTrieMatch<C> find(String prefix, ITrieFilter<C>filter) {

		return this.root.find(prefix, filter);
	}

	public List<C> getWordList(String prefix, ITrieFilter<C> filter, int limit) {

		PrefixTrieMatch<C> prefixNodeMatch = this.root.find(prefix, filter);

		if (prefixNodeMatch == null)
			return new LinkedList<C>();
		else
			return prefixNodeMatch.getWordList(limit);
	}

	

	/**
	 * Visit each TrieNode<C>
	 * 
	 * @param v
	 */

	public void visit(ITrieNodeVisitor<C> v) {

		this.root.visit(v);
	}

	/**
	 * Compresses the sparse nodes with only 1 branch; makes the Trie into a
	 * Patricia Trie which uses less space.
	 */
	public void simplifyIndex() {

		// the first simplification is to remove nodes that have only 1 branch.
		// we will basically have nodes that represent more than a single
		// character
		this.root.simplify();

		/*
		 * We visit each leaf then iterate over upward to mark the max length of
		 * each nodes sub tree.
		 */

		final List<TrieNode<C>> leafNodeList = new LinkedList<TrieNode<C>>();

		this.root.visit(new ITrieNodeVisitor<C>() {

			public void visit(TrieNode<C> element) {

				if (element.getOrderedNodeList().size() == 0)
					leafNodeList.add(element);

				for (TrieNode<C> trieNode : element.getOrderedNodeList()) {

					trieNode.visit(this);
				}

			}
		});

		for (TrieNode<C> trieNode : leafNodeList) {

			TrieNode<C> parentNode = trieNode.getParentNode();
			TrieNode<C> currentNode = trieNode;

			while (parentNode != null) {

				// start at the bottom and work upwards

				int currentLength = currentNode.getCharacter().length();

				int currentMax = currentNode.getMaxChildStringLength()
						+ currentLength;

				int maxParentLength = parentNode.getMaxChildStringLength();

				if (currentMax > maxParentLength) {
					parentNode.setMaxChildStringLength(currentMax);

				}

				currentNode = parentNode;
				parentNode = parentNode.getParentNode();

			}

		}

	}

	/**
	 * Builds a list where the filter string occurs anywhere within a particular
	 * tree branch.
	 * 
	 * @param substring
	 * 
	 * @return
	 */
	public List<C> getAnyMatchingWordList(final String substring,
			final ITrieFilter<C> nodeFilter, final int limit) {

		AnyWhereTrieMatch<C> matchingNode = this.root.findAnyMatch(substring, nodeFilter);

		return matchingNode.getWordList(limit);
	}

	
	public AnyWhereTrieMatch<C> getAnyMatchingTrieMatch(String value,
			ITrieFilter<C> nodeFilter) {

		AnyWhereTrieMatch<C> matchingNode = this.root.findAnyMatch(value, nodeFilter);

		return matchingNode;
	}

	/**
	 * Get the list of words that have the substring given contained anywhere
	 * within them.
	 * 
	 * Note that we take the first match found to avoid double counting.
	 * 
	 * @param substring
	 * @return the list of words that the substring matches.
	 */

	public List<C> getAnyMatchingWordList(String substring) {
		return this.getAnyMatchingWordList(substring, configuration
				.getDefaultFilter(), -1);
	}

	/**
	 * @return
	 */
	public int getChildren() {

		return root.getOrderedNodeList().size();
	}

	/**
	 * Return the size of the subtree for the prefix given. This avoids the need
	 * to get the list especially when the count is large.
	 * 
	 * @param prefix
	 * @return the number of elements in the subtree corresponding to the prefix
	 *         given.
	 * 
	 */
	public int getPrefixMatchedElementCount(String prefix, final ITrieFilter<C>nodeFilter) {

		PrefixTrieMatch<C> match = root.find(prefix, nodeFilter);

		if (match == null)
			return 0;

		final AtomicInteger counter = new AtomicInteger(0);

		match.getNode().visit(new ITrieNodeVisitor<C>() {

			public void visit(TrieNode<C> node) {
				
				for (C value : node.getOrderedMatchList()) {
					if (nodeFilter.isVisible(value)) {
						counter.addAndGet(node.getTotalMatches());		
					}
				} 
				
			}
		});

		return counter.intValue();

	}

	/**
	 * 
	 * @return the total number of elements indexed by this trie.
	 * 
	 *         Note this can be an expensive call as each node in the trie is
	 *         visited.
	 * 
	 */

	public int size() {

		final AtomicInteger counter = new AtomicInteger(0);

		// visit each node an aggregate the number of matches:
		root.visit(new ITrieNodeVisitor<C>() {

			public void visit(TrieNode<C> node) {

				counter.addAndGet(node.getTotalMatches());
			}
		});

		return counter.intValue();
	}

	/**
	 * @return
	 * @see org.wicketstuff.datatable_autocomplete.trie.TrieNode#getNextNodeCharacterSet()
	 */
	public Set<String> getNextNodeCharacterSet() {
		/*
		 * This is really just to support the datatable-autocomplete-examples
		 * where we give a count of the matches for each first character
		 * contained in this set.
		 */
		return root.getNextNodeCharacterSet();
	}

}
