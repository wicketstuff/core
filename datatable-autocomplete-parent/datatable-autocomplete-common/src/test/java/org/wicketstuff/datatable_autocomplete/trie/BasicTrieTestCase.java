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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author mocleiri
 */

public class BasicTrieTestCase extends TestCase {

	private Trie<String> trie;
	private List<String> dataList;

	private ArrayList<String> doubleZeroWordList;
	private ArrayList<String> oneWordList;

	/**
	 * 
	 */
	public BasicTrieTestCase() {
		init();
	}

	/**
	 * @param name
	 */
	public BasicTrieTestCase(String name) {
		super(name);
		init();
	}

	protected void init() {

		this.trie = new Trie<String>(new AbstractTrieConfiguration<String>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.wicketstuff.datatable_autocomplete.trie.ITrieConfiguration
			 * #getWord(java.lang.Object)
			 */
			public String getWord(String ctx) {
				return ctx;
			}

			/* (non-Javadoc)
			 * @see org.wicketstuff.datatable_autocomplete.trie.ITrieConfiguration#isIndexCaseSensitive()
			 */
			public boolean isIndexCaseSensitive() {
				// default to no insensitivity
				return false;
			}
			
			
			

		});

		dataList = new LinkedList<String>();

		doubleZeroWordList = new ArrayList<String>();
		oneWordList = new ArrayList<String>();

		for (int i = 1000; i < 9999; i++) {

			String stringValue = String.valueOf(i);

			if (stringValue.contains("00"))
				doubleZeroWordList.add(stringValue);
			
			if (stringValue.contains("1"))
				oneWordList.add(stringValue);

			trie.index(stringValue);
			dataList.add(stringValue);
		}

		// for (char c = 'a'; c <= 'z'; c++) {
		//			
		// for (char b = 'a'; b <= 'z'; b++) {
		//			
		// String value = String.valueOf(c) + String.valueOf(b);
		//				
		// trie.index(value);
		// dataList.add(value);
		// }

		// }

		trie.simplifyIndex();

	}

	public void testMissingValues() {

		List<String> wordList = trie.getWordList("");

		assertTrue(wordList.contains("1000"));

	}

	public void testAnyDoubleZeroMatch() {

		List<String> wordList = trie.getAnyMatchingWordList("00");

		Collection<String> disjunction = CollectionUtils.disjunction(wordList, doubleZeroWordList);
		
		for (String string : disjunction) {
			System.out.println("extra = " + string);
		}
		
	
		
		assertEquals(doubleZeroWordList.size(), wordList.size());
		
	
		
		
		
	}
	
	public void testAnyOneMatch () {
		
		List<String> wordList = trie.getAnyMatchingWordList("1");

		Collection<String> disjunction = CollectionUtils.disjunction(wordList, oneWordList);
		
		for (String string : disjunction) {
			System.out.println("extra = " + string);
		}
		
	
		
		assertEquals(oneWordList.size(), wordList.size());
		
		
	}

	// public void testContainsAll() {
	//		
	// for (char c = 'a'; c <= 'z'; c++) {
	//			
	// for (char b = 'a'; b <= 'z'; b++) {
	//				
	// List<String> wordList = trie.getWordList(String.valueOf(c) +
	// String.valueOf(b));
	// t
	// assertTrue(wordList.size() == 1);
	//				
	// }
	// }
	// }

	/**
	 * Verify that all the words that were indexed into the Trie can be
	 * retrieved.
	 */
	public void testValueTrie() {

		List<String> wordList = trie.getWordList("");

		Collections.sort(wordList);
		Collections.sort(dataList);

		for (String source : dataList) {

			if (!wordList.contains(source)) {
				System.out
						.println(source + " is not contained in the wordlist");
			}

		}

		for (int i = 0; i < dataList.size(); i++) {

			// if (wordList.size() > i)
			// log.info("["+i+"] index = " + wordList.get(i));
			//			
			// log.info("["+i+"] data = " + dataList.get(i));

			String word = wordList.get(i);
			String data = dataList.get(i);

			assertEquals(word, data);

		}

		assertEquals(dataList.size(), wordList.size());

	}

}
