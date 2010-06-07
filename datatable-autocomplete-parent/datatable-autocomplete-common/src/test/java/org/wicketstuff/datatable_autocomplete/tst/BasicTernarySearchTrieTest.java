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

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.trie.AbstractTrieConfiguration;

/**
 * @author mocleiri
 * 
 * Basic tests  for the TernarySearchTrie
 *
 */
public class BasicTernarySearchTrieTest extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(BasicTernarySearchTrieTest.class);
	
	/**
	 * 
	 */
	public BasicTernarySearchTrieTest() {
		// TODO Auto-generated constructor stub
	}
	
	public void testSimpleTST() {
		
		TernarySearchTrie<String>trie = new TernarySearchTrie<String>(new AbstractTrieConfiguration<String>() {

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
		
		
		trie.index("household");
		trie.index("person");
		trie.index("trip");
		trie.index("transit");
		trie.index("survey");
		
		trie.visit(new TernaryNodeCountingVisitor());
		
		log.info("prefix = 'hou'");
		
		for (String word : trie.getWordList("hou", -1)) {
			
			log.info("word = " + word);
		}
		
		log.info("prefix = ''");

		for (String word : trie.getWordList("", -1)) {
			
			log.info("word = " + word);
		}
		
		log.info("prefix = 'tr'");
		
		for (String word : trie.getWordList("tr", -1)) {
			
			log.info("word = " + word);
		}
	}
	
	public void testCompressedTST() {
		
		TernarySearchTrie<String>trie = new TernarySearchTrie<String>(new AbstractTrieConfiguration<String>() {

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
		
		trie.printTree();
		
		trie.visit(new TernaryNodeCountingVisitor());
		
		log.info("prefix = 'hou'");
		
		for (String word : trie.getWordList("hou", -1)) {
			
			log.info("word = " + word);
		}
		
		log.info("prefix = ''");

		for (String word : trie.getWordList("", -1)) {
			
			log.info("word = " + word);
		}
		
		log.info("prefix = 'tr'");
		
		for (String word : trie.getWordList("tr", -1)) {
			
			log.info("word = " + word);
		}
	}

	public void testSimpleSuffixTST() {
		
		TernarySearchTrie<String>trie = new TernarySearchTrie<String>(new AbstractTrieConfiguration<String>() {

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
				return true;
			}
			
		});
		
		
		trie.index("household");
		trie.index("person");
		trie.index("trip");
		trie.index("transit");
		trie.index("survey");
		
		trie.visit(new TernaryNodeCountingVisitor());
		
		reportPrefix (trie, "o");
		
		log.info("prefix = ''");

		for (String word : trie.getWordList("", -1)) {
			
			log.info("word = " + word);
		}
		
		log.info("prefix = 'e'");
		
		for (String word : trie.getWordList("e", -1)) {
			
			log.info("word = " + word);
		}
	}

	private void reportPrefix(TernarySearchTrie<String> trie, String prefix) {
		
		log.info("prefix = '"+prefix+"'");
		
		for (String word : trie.getWordList(prefix, -1)) {
			
			log.info("word = " + word);
		}
		
	}
}
