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

import java.util.LinkedHashMap;
import java.util.Map;

import org.wicketstuff.datatable_autocomplete.tst.TernaryNode;
import org.wicketstuff.datatable_autocomplete.tst.TernaryNodeVisitor;
import org.wicketstuff.datatable_autocomplete.tst.TernarySearchTrie;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.OrderedKAryTree;

/**
 * @author mocleiri
 * 
 * A JUNG graph to represent the TernarySearchTrie
 *
 */
public class TSTGraph<C> extends DirectedSparseMultigraph<TernaryNode<C>, String> {

	/**
	 * @param order
	 */
	public TSTGraph(TernarySearchTrie<C>trie) {
		super();
		
		TernaryNode<C> rootNode = trie.getRoot();
		
		addVertex(rootNode);
		
		
		buildNode (rootNode);
		
		
		
	}

	private void buildNode(TernaryNode<C> node) {
		
		node.visit(new TernaryNodeVisitor<C>() {

			/* (non-Javadoc)
			 * @see org.wicketstuff.datatable_autocomplete.tst.TernaryNodeVisitor#postVisit()
			 */
			public void postVisit() {
				// TODO Auto-generated method stub
				
			}

			/* (non-Javadoc)
			 * @see org.wicketstuff.datatable_autocomplete.tst.TernaryNodeVisitor#preVisit()
			 */
			public void preVisit() {
				// TODO Auto-generated method stub
				
			}

			/* (non-Javadoc)
			 * @see org.wicketstuff.datatable_autocomplete.tst.TernaryNodeVisitor#visit(org.wicketstuff.datatable_autocomplete.tst.TernaryNode)
			 */
			public void visit(TernaryNode<C> node) {
				
				TernaryNode<C> parent = node.getParentNode();
				
				if (parent != null) {
					addEdge("", node.getParentNode(), node);
				
				
				}
				
				node.visit(this);
			}
			
		});
	}
	
	

}
