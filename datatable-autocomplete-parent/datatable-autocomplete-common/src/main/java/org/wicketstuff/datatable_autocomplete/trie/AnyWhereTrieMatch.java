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
import java.util.Set;


/**
 * @author mocleiri
 * 
 * A match corresponding to an anywhere in the string search.
 *
 */
public class AnyWhereTrieMatch<C> extends TrieMatch<C> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Set<TrieNode<C>> nodeSet;

	/**
	 * @param matchingNodeList
	 */
	public AnyWhereTrieMatch(String matched, ITrieFilter<C>nodeFilter, Set<TrieNode<C>> matchingNodeList) {
		super(matched, Type.ANY_MATCH, nodeFilter);
		this.nodeSet = matchingNodeList;
	}

	/**
	 * @return the nodeSet
	 */
	public Set<TrieNode<C>> getNodeSet() {
		return nodeSet;
	}

	public void visit(ITrieNodeVisitor<C>visitor) {
		
		for (TrieNode<C> node : nodeSet) {
			
			visitor.visit(node);
			
		}
		
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.trie.TrieMatch#getWordList()
	 */
	@Override
	public List<C> getWordList(final int limit) {
		
		final List<C> dataList = new LinkedList<C>();
		
		for (TrieNode<C> node : nodeSet) {
			
			node.buildWordList(dataList, super.nodeFilter, limit); 
			
			if (dataList.size() == limit)
				break;
		}
		
		return dataList;
		
	}
	
	
	
	

}
