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
import org.wicketstuff.datatable_autocomplete.trie.PatriciaTrie;
import org.wicketstuff.datatable_autocomplete.trie.TrieNode;

import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * @author mocleiri
 * 
 *         To visualize the Patricia-Trie using JUNG.
 * 
 * 
 */
public class TrieVisualizer<T> {

	private TrieGraph<T> graph;


	/**
	 * @param graph
	 * 
	 */
	public TrieVisualizer(String title, TrieGraph<T> graph,
			TrieVisualizerLayout layoutType) {

		this.graph = graph;

		// The Layout<V, E> is parameterized by the vertex and edge types

		Layout<TrieNode<T>, String> layout = null;
		
		switch (layoutType) {
		case TREE:
			layout = new TreeLayout<TrieNode<T>, String>(
					new DelegateForest<TrieNode<T>, String>(graph));
			break;
			
		case DAG:
			layout = new DAGLayout<TrieNode<T>, String>(graph);
			break;
		}
		
		
		// layout.setSize(new Dimension(300,300)); // sets the initial size of
		// the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		VisualizationViewer<TrieNode<T>, String> vv = new VisualizationViewer<TrieNode<T>, String>(
				layout);
		vv.setPreferredSize(new Dimension(350, 350)); // Sets the viewing area
														// size

		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		vv.getRenderContext().setVertexLabelTransformer(
				new Transformer<TrieNode<T>, String>() {

					public String transform(TrieNode<T> node) {
						return node.getCharacter();
					}
				});

		// Create a graph mouse and add it to the visualization component
		DefaultModalGraphMouse<TrieNode<T>, String> gm = new DefaultModalGraphMouse<TrieNode<T>, String>();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		vv.setGraphMouse(gm);

		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
	}

	public TrieVisualizer(String title, PatriciaTrie<T> trie, TrieVisualizerLayout layoutType) {
		this(title, new TrieGraph<T>(trie), layoutType);
	}

}
