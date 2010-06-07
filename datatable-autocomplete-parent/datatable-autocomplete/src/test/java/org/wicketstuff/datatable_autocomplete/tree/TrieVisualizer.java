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

import java.awt.Dimension;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;
import org.wicketstuff.datatable_autocomplete.trie.AbstractTrieConfiguration;
import org.wicketstuff.datatable_autocomplete.trie.PatriciaTrie;
import org.wicketstuff.datatable_autocomplete.trie.TrieNode;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * @author mocleiri
 * 
 * To visualize the Patricia-Trie using JUNG.
 * 
 *
 */
public class TrieVisualizer {

	private TrieGraph<String> graph;

	/**
	 * @param graph 
	 * 
	 */
	public TrieVisualizer(String title, TrieGraph<String> graph) {
	
		
		this.graph = graph;
		
		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<TrieNode<String>, String> layout = new TreeLayout<TrieNode<String>, String>(new DelegateForest<TrieNode<String>, String>(graph));
//		layout.setSize(new Dimension(300,300)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<TrieNode<String>, String>  vv =
		new BasicVisualizationServer<TrieNode<String>, String> (layout);
		vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
		
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		
		vv.getRenderContext().setVertexLabelTransformer(new Transformer<TrieNode<String>, String>() {
			
			public String transform(TrieNode<String> node) {
				return node.getCharacter();
			}
		});	
		
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
	}
	
public static void main(String[] args) {
		
		PatriciaTrie<String>trie = new PatriciaTrie<String>(new AbstractTrieConfiguration<String>() {

			/* (non-Javadoc)
			 * @see org.wicketstuff.datatable_autocomplete.trie.ITrieConfiguration#getWord(java.lang.Object)
			 */
			public String getWord(String ctx) {
				return ctx;
			}

			/* (non-Javadoc)
			 * @see org.wicketstuff.datatable_autocomplete.trie.ITrieConfiguration#isIndexCaseSensitive()
			 */
			public boolean isIndexCaseSensitive() {
				// TODO Auto-generated method stub
				return false;
			}

			/* (non-Javadoc)
			 * @see org.wicketstuff.datatable_autocomplete.trie.ITrieConfiguration#isSuffixTree()
			 */
			public boolean isSuffixTree() {
				// TODO Auto-generated method stub
				return false;
			}
			
		});
		
		
		trie.index("transit");
		trie.index("trip");
		
		
		TrieGraph<String> g = new TrieGraph<String>(trie);
		
		new TrieVisualizer("Patrcia Trie", g);
		
		
		
		}

}
