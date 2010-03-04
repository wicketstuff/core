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

import org.apache.wicket.IClusterable;



/**
 * @author mocleiri
 *
 */
public interface ITrieFilter<C> extends IClusterable {
		/**
		 * 
		 * @param n
		 * @return true if the indexed word should be visible.
		 * 
		 */
		public boolean isVisible (C word);
}
