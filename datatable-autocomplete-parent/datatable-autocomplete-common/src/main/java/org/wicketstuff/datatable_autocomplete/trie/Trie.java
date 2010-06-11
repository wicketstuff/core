package org.wicketstuff.datatable_autocomplete.trie;

import java.util.List;

import org.apache.wicket.IClusterable;

/**
 * 
 * @author mocleiri
 *
 * With the introduction of the TernarySearchTrie this interface was extracted to provide a common base between them.
 */
public interface Trie<C> extends IClusterable {

	/**
	 * @param streetName
	 * @return
	 */
	public abstract void index(C value);

	/**
	 * Get the list of strings that are reachable from the prefix given.
	 * 
	 * i.e. the ordered traversal of the subtree for the prefix given.
	 * 
	 * @param prefix
	 * @return
	 */
	public abstract List<C> getWordList(String prefix);
	
	public abstract List<C>getWordList (String prefix, ITrieFilter<C>filter);
	
	public abstract List<C>getWordList (String prefix, ITrieFilter<C>filter, int limit);

	public abstract List<C> getWordList(String prefix, int limit);

	/**
	 * Invoked before the indexing process is started.
	 */
	public abstract void preIndexing();

	/**
	 * Invoked after the index process has completed.
	 */
	public abstract void postIndexing();
	

}