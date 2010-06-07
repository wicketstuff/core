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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michael O'Cleirigh (michael.ocleirigh@rivulet.ca)
 *
 */
public class TernaryNodeCountingVisitor<C> implements TernaryNodeVisitor<C> {

	private static final Logger log = LoggerFactory.getLogger(TernaryNodeCountingVisitor.class);
	
	/**
	 * 
	 */
	public TernaryNodeCountingVisitor() {
		// TODO Auto-generated constructor stub
	}


		private Map<String, AtomicLong>nodeCountMap = new LinkedHashMap<String, AtomicLong>();
		
		
		/* (non-Javadoc)
		 * @see org.wicketstuff.datatable_autocomplete.tst.TernaryNodeVisitor#visit(org.wicketstuff.datatable_autocomplete.tst.TernaryNode)
		 */
		public void visit(TernaryNode<C> node) {
			
			AtomicLong nodeCounter = nodeCountMap.get(node.getCharacter());
			
			if (nodeCounter == null) {
				nodeCounter = new AtomicLong();
				nodeCountMap.put(node.getCharacter(), nodeCounter);
				
			}
			
			nodeCounter.addAndGet(1L);
			
			
		}

		/* (non-Javadoc)
		 * @see org.wicketstuff.datatable_autocomplete.tst.TernaryNodeVisitor#postVisit()
		 */
		public void postVisit() {
			
			List<String>keyList = new LinkedList<String>();
			
			keyList.addAll(nodeCountMap.keySet());
			
			Collections.sort(keyList);
			
			AtomicLong overallNodeCounter = new AtomicLong();
			
			
			for (String character : keyList) {
			
				AtomicLong counter = nodeCountMap.get(character);
				
				overallNodeCounter.addAndGet(counter.longValue());
				
				log.info(" [ "+character+" ] = " + counter.longValue());
				
			}
			
			log.info("overall node count = " + overallNodeCounter.longValue());
			
			
		}

		/* (non-Javadoc)
		 * @see org.wicketstuff.datatable_autocomplete.tst.TernaryNodeVisitor#preVisit()
		 */
		public void preVisit() {
			
		}
		
		
		
	
}
