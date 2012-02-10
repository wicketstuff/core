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
import org.wicketstuff.datatable_autocomplete.tst.TernaryNode;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * @author mocleiri
 * 
 */
public class TSTVisualizer
{


	/**
	 * @param title
	 * @param graph
	 * 
	 */
	public TSTVisualizer(String title, TSTGraph<String> graph)
	{
		super();

		this.graph = graph;

		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<TernaryNode<String>, String> layout = new TreeLayout<TernaryNode<String>, String>(
			new DelegateForest<TernaryNode<String>, String>(graph));
// layout.setSize(new Dimension(300,300)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<TernaryNode<String>, String> vv = new BasicVisualizationServer<TernaryNode<String>, String>(
			layout);
		vv.setPreferredSize(new Dimension(350, 350)); // Sets the viewing area size

		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		vv.getRenderContext().setVertexLabelTransformer(
			new Transformer<TernaryNode<String>, String>()
			{

				public String transform(TernaryNode<String> node)
				{
					return node.getCharacter();
				}
			});

		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
	}

	private TSTGraph<String> graph;


}
