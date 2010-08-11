package org.wicketstuff.datatable_autocomplete.tree;

import org.wicketstuff.datatable_autocomplete.trie.AbstractTrieConfiguration;
import org.wicketstuff.datatable_autocomplete.tst.TernarySearchTrie;

/**
 * @author mocleiri
 * 
 */
public class TSTVisualizerMain {

	public static void main(String[] args) {

		TernarySearchTrie<String> trie = new TernarySearchTrie<String>(
				new AbstractTrieConfiguration<String>() {

					/*
					 * (non-Javadoc)
					 * 
					 * @seeorg.wicketstuff.datatable_autocomplete.trie.
					 * ITrieConfiguration#getWord(java.lang.Object)
					 */
					public String getWord(String ctx) {
						return ctx;
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @seeorg.wicketstuff.datatable_autocomplete.trie.
					 * ITrieConfiguration#isIndexCaseSensitive()
					 */
					public boolean isIndexCaseSensitive() {
						// TODO Auto-generated method stub
						return false;
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @seeorg.wicketstuff.datatable_autocomplete.trie.
					 * ITrieConfiguration#isSuffixTree()
					 */
					public boolean isSuffixTree() {
						// TODO Auto-generated method stub
						return false;
					}

				});

		trie.index("transit");
		trie.index("trip");

		TSTGraph<String> g = new TSTGraph<String>(trie);

		new TSTVisualizer("Ternary Search Trie", g);

	}

}
