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
package org.wicketstuff.datatable_autocomplete.tree;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.wicketstuff.datatable_autocomplete.trie.ITrieNodeVisitor;
import org.wicketstuff.datatable_autocomplete.trie.PatriciaTrie;
import org.wicketstuff.datatable_autocomplete.trie.TrieNode;

import edu.uci.ics.jung.algorithms.filters.KNeighborhoodFilter.EdgeType;
import edu.uci.ics.jung.graph.AbstractTypedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * @author mocleiri
 *
 */
public class TrieGraph<C> extends DirectedSparseMultigraph<TrieNode<C>, String> {

	private final AtomicLong counter = new AtomicLong();
	
	/**
	 * @param order
	 */
	public TrieGraph(PatriciaTrie<C>trie) {
		super();
		
		addVertex(trie.getRoot());
		
		buildGraph (trie.getRoot());
		
		
		
		
		
		
		
		
	}

	private void buildGraph(TrieNode<C> root) {

		List<TrieNode<C>> nodeList = root.getOrderedNodeList();
		
		for (TrieNode<C> trieNode : nodeList) {
		
			addEdge(String.valueOf(counter.addAndGet(1)), root, trieNode);
			
			buildGraph(trieNode);
		}
		
		
	}
	
	

	
	

}
