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

import java.io.Serializable;
import java.util.List;



/**
 * @author mocleiri
 * 
 * Represents a match of a string into a Trie
 *
 */
public abstract class TrieMatch<Context> implements Serializable {

	private static final long	serialVersionUID	= -6521672186761294453L;

	public static enum Type { PREFIX_MATCH, ANY_MATCH };
	
	private Type type;
	private final String matched;
	
	protected ITrieFilter<Context> nodeFilter;
	
	
	public abstract List<Context>getWordList(int limit);
	
	

	/**
	 * @param node
	 * @param matchedPrefix
	 */
	public TrieMatch(String matched, Type type, ITrieFilter<Context>filter) {

		super();
		this.matched = matched;
		this.type = type;
		nodeFilter = filter;
	}













	

	



	
	
	
	
}
