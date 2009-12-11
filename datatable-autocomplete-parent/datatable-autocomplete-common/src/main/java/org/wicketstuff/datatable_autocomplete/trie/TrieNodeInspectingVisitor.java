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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author mocleiri
 * 
 * Used as part of the minimization procedure when compacting the Trie.
 *
 */
public class TrieNodeInspectingVisitor<Context> implements ITrieNodeVisitor<Context> {

	private static final Logger	log	= LoggerFactory
											.getLogger(TrieNodeInspectingVisitor.class);
	


		int totalConsolidateable = 0;
		
		int totalNodes = 0;
		
		public void visit(TrieNode<Context> element) {

			this.totalNodes++;
			
			if (element.getOrderedNodeList().size() == 1)
				this.totalConsolidateable++;
			
		}
		
		/**
		 * @return the totalConsolidateable
		 */
		public int getTotalConsolidateable() {
		
			return this.totalConsolidateable;
		}

		
		/**
		 * @return the totalNodes
		 */
		public int getTotalNodes() {
		
			return this.totalNodes;
		}
		
		
		
		
}
