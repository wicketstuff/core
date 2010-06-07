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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.wicketstuff.datatable_autocomplete.data.TernarySearchTrieBuilder;

/**
 * @author mocleiri
 * 
 *         This done in the examples because of the availability of the
 *         TrieBuilder which can build a large dataset.
 * 
 * 
 */
public class LargeTrieTestCase extends TestCase {

	// note you will need to increase the heap size to run this example
	private static final int TRIE_SIZE = 125000;
	private TernarySearchTrie<Method> trie;
	private TernarySearchTrieBuilder builder;

	/**
	 * 
	 */
	public LargeTrieTestCase() {
		super("LargeTrieTestCase");
		init(true);
	}

	/*
	 */
	protected void init(boolean isSuffixTrie) {

		builder = new TernarySearchTrieBuilder(isSuffixTrie);

		builder.buildTrie(TRIE_SIZE);

		trie = builder.getTrie();
		
	}

	private Comparator<Method> methodComparator = new Comparator<Method>() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Method o1, Method o2) {
			return o1.getName().compareTo(o2.getName());
		}

	};

	public void testTrieStructure() {

		int total = 0;

		List<String> nextCharacterList = new LinkedList<String>();
		
		nextCharacterList.addAll(trie.getNextNodeCharacterSet());

		Collections.sort(nextCharacterList);

		for (String c : nextCharacterList) {

			List<Method> wordList = trie.getWordList(c, -1);

			List<Method> indexedList = builder.getListForFirstCharacter(c);

			Collections.sort(wordList, methodComparator);
			Collections.sort(indexedList, methodComparator);

			for (int i = 0; i < wordList.size(); i++) {

				Method found = wordList.get(i);
				Method indexed = wordList.get(i);

				String foundString = found.toString();
				String indexedString = indexed.toString();

				if (!foundString.equals(indexedString)) {
					System.out.println("found = " + found.toString());
					System.out.println("as indexed = " + indexed.toString());
				}

				assertEquals(found.toString(), indexed.toString());

			}

			for (int i = wordList.size(); i < indexedList.size(); i++) {

				System.out.println("found = NONE");
				System.out.println("as indexed = "
						+ indexedList.get(i).toString());

			}

			// get the list of elements that are not in the union of the two lists
			Collection<Method> disjunctionList = CollectionUtils.disjunction(wordList, indexedList);
			
			for (Method method : disjunctionList) {
				
				System.out.println("missing = " + method.toString());
			}
			
			assertEquals(wordList.size(), indexedList.size());

		}
		

	}

	public void testSpecificWords() {

		List<Method> wordList = trie.getWordList("assign", -1);

		for (Method method : wordList) {

			System.out.println("match = " + method.toString());
		}
		

		System.out.println("assign matches " + wordList.size() + " methods.");
		
		wordList = trie.getWordList("access", -1);

		for (Method method : wordList) {

			System.out.println("match = " + method.toString());
		}
		
		System.out.println("access matches " + wordList.size() + " methods.");
		

	}

	
}
