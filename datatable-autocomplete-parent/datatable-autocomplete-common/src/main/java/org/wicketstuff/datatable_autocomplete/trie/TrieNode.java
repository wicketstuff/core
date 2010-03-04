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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mocleiri
 * 
 *         A Node in the Trie<C>
 * 
 *         Contains the character(s) that are contained in this node.
 * 
 *         A map of next character(s) to TrieNode<C>'s and the count of the
 *         number of words in the subtree beneath this node.
 * 
 *         The C context object that is indexed by this node.
 * 
 *         The configuration object that provides the string version of an C
 *         object.
 * 
 * 
 * 
 */
public class TrieNode<C> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3544907989469418291L;

	private static final Logger log = LoggerFactory.getLogger(TrieNode.class);

	private static final Comparator<TrieNode<?>> trieNodeComparator = new Comparator<TrieNode<?>>() {

		public int compare(TrieNode<?> o1, TrieNode<?> o2) {

			return o1.getCharacter().compareTo(o2.getCharacter());

		}
	};

	// the string that corresponds to what is matched between our parent node
	// and the root node of the enire trie.
	private final String rootMatchedString;

	// the character in the word (final character) that this node represents
	// after minimization it may be a couple of characters
	private String character;

	private Map<String, TrieNode<C>> nodeMap = new HashMap<String, TrieNode<C>>();

	// child nodes (ordered a-z) so that we can traverse properly
	private List<TrieNode<C>> orderedNodeList = new ArrayList<TrieNode<C>>();

	private TrieNode<C> parentNode;

	// if >1 then this is the total number of strings that terminate on this
	// node
	private int totalMatches = 0;

	// contains the matches for the indexed element in the 'character'
	// i.e. index 0 matches the character.get(0) and index 1 matches the element
	// that matches to character.get(0) + character.get(1)
	private Map<Integer, List<C>> matchMap = new LinkedHashMap<Integer, List<C>>();
	
	// restrictor type to Map of restrictor keys to map of 
	private Map<String, Map<String, Map<Integer, List<C>>>>restrictorMatchMap = new LinkedHashMap<String, Map<String,Map<Integer,List<C>>>>();

	// if the entire tree below this node was traversed this would be the length
	// of the longest string formed.
	// this is used when doing a 'superselect' match to know when a branch is
	// not viable.
	private int maxChildStringLength = 0;

	private final ITrieConfiguration<C> configuration;

	/**
	 * @param context
	 * @param configuration
	 * @param word
	 */
	public TrieNode(TrieNode<C> parentNode, String rootMatchedString,
			String character, ITrieConfiguration<C> configuration) {

		super();

		this.parentNode = parentNode;
		this.rootMatchedString = rootMatchedString;
		this.character = character;

		this.configuration = configuration;

	}

	/**
	 * @return the maxChildStringLength
	 */
	public int getMaxChildStringLength() {

		return this.maxChildStringLength;
	}

	/**
	 * @param maxChildStringLength
	 *            the maxChildStringLength to set
	 */
	public void setMaxChildStringLength(int maxChildStringLength) {

		this.maxChildStringLength = maxChildStringLength;
	}

	public String toString() {

		StringBuffer children = new StringBuffer();

		List<String> nodeList = new ArrayList<String>(this.nodeMap.keySet());

		for (int i = 0; i < nodeList.size() - 1; i++) {

			String node = nodeList.get(i);
			children.append(node);
			children.append(", ");

		}

		if (nodeList.size() > 0)
			children.append(nodeList.get(nodeList.size() - 1));

		return "NODE [ matchedPrefix = '" + rootMatchedString
				+ "', character ='" + getCharacter() + "', word = '"
				+ getWord() + "', children = (" + children.toString() + ") ]";
	}

	/**
	 * @return the character
	 */
	public String getCharacter() {

		return this.character;
	}

	/**
	 * Add the word into the Trie index.
	 * 
	 * Recurses down the tree until all of the characters in word have been
	 * placed.
	 * 
	 * @param word
	 * @return
	 */
	public TrieNode<C> index(C word) {

		return index(word, 0);
	}

	public List<TrieNode<C>> getOrderedNodeList() {

		return this.orderedNodeList;

	}

	/**
	 * @param word2
	 * @param i
	 * @return
	 */
	private TrieNode<C> index(C context, int startingIndex) {

		String word = configuration.getWord(context);

		if (!configuration.isIndexCaseSensitive())
			word = word.toLowerCase();
		
		if (word.length() == startingIndex) {

			/*
			 * This is the node that matches the word.
			 * 
			 * First: make sure that this is the first match on this node.
			 * 
			 * Second: insert the object into the first slot in the matchMap.
			 * Note: the index is always zero because we build an uncompressed
			 * Trie first.
			 */

			List<C> matchList = this.matchMap.get(0);

			if (matchList == null) {
				matchList = new ArrayList<C>();
				this.matchMap.put(0, matchList);
			}
			// note the increase in matches terminating with this node.
			this.totalMatches++;
			matchList.add(context);

			return this;
		} else {

			// use the character at the starting index to get the next node

			String nextCharacter = word.substring(startingIndex,
					startingIndex + 1);

			TrieNode<C> nextNode = nodeMap.get(nextCharacter);

			if (nextNode == null) {

				String matchedSubString = word.substring(0, startingIndex);

				nextNode = newNode(this, matchedSubString, nextCharacter);
				nodeMap.put(nextCharacter, nextNode);
				orderedNodeList.add(nextNode);

			}

			return nextNode.index(context, startingIndex + 1);

		}

	}

	/**
	 * @param nextCharacter
	 * @return
	 */
	protected TrieNode<C> newNode(TrieNode<C> parent, String rootMatchedString,
			String nextCharacter) {

		return this.configuration.createTrieNode(parent, rootMatchedString, nextCharacter);
	}

	/**
	 * Find the list of nodes that match the
	 * 
	 * @param input
	 * @return
	 */
	public Set<TrieNode<C>> findAnyMatch(String substring) {

		if (!configuration.isIndexCaseSensitive())
			substring = substring.toLowerCase();
		
		/*
		 * We want to do a single pass over the nodes and find all the strings
		 * that contain the substring.
		 */

		Set<TrieNode<C>> matchingNodeList = new LinkedHashSet<TrieNode<C>>();

		for (TrieNode<C> trieNode : this.orderedNodeList) {

			trieNode.findMatchingNodes(matchingNodeList, substring);

		}

		return matchingNodeList;
	}

	/**
	 * 
	 * In the normal case there is a single path through the tree and we
	 * identify the node that matches the prefix.
	 * 
	 * In the any case there will be many nodes since there are multiple paths
	 * to a match.
	 * 
	 * @param matchingNodeList
	 * @param substring
	 */
	private void findMatchingNodes(Set<TrieNode<C>> matchingNodeList,
			String substring) {

		TrieNodeMatch<C> match = find(substring);

		if (match != null) {
			TrieNode<C> node = match.getNode();
			
			matchingNodeList.add(node);
			// guaranteed to match the subtree so just exit at this point.
			// this will prevent matching the same words multiple times where the substring is small.
			return;
		}

		if (getMaxChildStringLength() < substring.length())
			return; // not enough length in the subtree to match the string so
		// no need to look.

		for (TrieNode<C> trieNode : this.orderedNodeList) {

			trieNode.findMatchingNodes(matchingNodeList, substring);
			// match = trieNode.find(substring);
			//			
			// if (match != null)
			// matchingNodeList.add (match.getNode());

		}

	}

	/*
	 * Recursively finds the Node that corresponds to the prefix specificed.
	 */
	public TrieNodeMatch<C> find(String key) {


		if (!configuration.isIndexCaseSensitive())
			key = key.toLowerCase();
		
		/*
		 * Check the current character against the
		 */

		int keyLength = key.length();

		int characterLength = getCharacter().length();

		if (keyLength == characterLength) {

			if (getCharacter().equals(key)) {
				// match
				return new TrieNodeMatch<C>(this);
			} else {
				// no match
				return null;
			}
		} else if (keyLength > characterLength) {

			// compare the 'characterLength' substring of key
			String subKey = key.substring(0, characterLength);

			if (subKey.equals(getCharacter())) {
				// matches this node but we still need to compare against the
				// child nodes.

				int difference = keyLength - characterLength;

				String newKey = null;

				newKey = key.substring(keyLength - difference);

				TrieNode<C> nextNode = this.nodeMap.get(newKey.substring(0, 1));

				if (nextNode == null)
					return null;

				return nextNode.find(newKey);

			} else {
				return null;
			}

		} else {
			// keyLength < characterLength
			if (keyLength > 0 && characterLength > 1
					&& getCharacter().contains(key)) {
				return new TrieNodeMatch<C>(this);
			}
			return null;

		}

		

	}

	/**
	 * @return the parentNode
	 */
	public TrieNode<C> getParentNode() {

		return this.parentNode;
	}

	/**
	 * @return the totalMatches
	 */
	public int getTotalMatches() {

		return this.totalMatches;
	}

	/**
	 * Traverses up the tree to the root to generate the word that this node
	 * represents.
	 * 
	 * @return
	 */
	public String getWord() {

		return this.rootMatchedString + this.character;
		
//		StringBuffer buf = new StringBuffer();
//
//		TrieNode<C> currentNode = this;
//
//		while (currentNode != null) {
//			buf.insert(0, currentNode.getCharacter());
//
//			currentNode = currentNode.getParentNode();
//		}
//		
//		if (buf.toString().equals(this.rootMatchedString + this.character)) {
//			log.info("can remove the looping back to parent.");
//		}
//		return buf.toString();
	}

	/**
	 * @param limit -1 if no limit or the value at which point the list should stop being filled in.
	 * @param prefix
	 * @return
	 */
	public void buildWordList(List<C> wordList, ITrieFilter<C> filter, int limit) {

//		if (this.orderedNodeList.size() == 0
//				&& (filter == null || filter.isVisible(this))) {
//			// can be null in certain cases where the match is to an empty Trie.
//			addExistingContextToList(wordList, filter);
//
//		} else {

			log.debug(orderedNodeList.toString());

			addExistingContextToList(wordList, filter, limit);
			
			if (wordList.size() == limit)
				return;

			for (TrieNode<C> node : orderedNodeList) {
				node.buildWordList(wordList, filter, limit);
				
				if (wordList.size() == limit)
					return;
				
			}
//		}

	}

	private void addExistingContextToList(List<C> wordList, ITrieFilter<C> filter, int limit) {

		List<Integer> keyList = new LinkedList<Integer>();

		keyList.addAll(matchMap.keySet());

		/*
		 * Sort numerically so that order is based on shortest match first.
		 */
		Collections.sort(keyList);

		for (Integer i : keyList) {

			List<C> contextList = this.matchMap.get(i);
			
			// check with the filter to only include those with the proper
			
			for (C c : contextList) {
				
				if (filter.isVisible(c)) {
					wordList.add(c);
					
					if (wordList.size() == limit)
						return;
				}
				
				
			}

		
		}
	}

	/**
	 * Visit each of the TrieNodes in the Trie according to the order?
	 * 
	 * @param v
	 */
	public void visit(ITrieNodeVisitor<C> v) {

		v.visit(this);

		for (TrieNode<C> child : this.orderedNodeList) {
			child.visit(v);
		}
	}



	/**
	 * Called once all the keys have been added into the Trie.
	 * 
	 * This will compact the nodes so that there will be no single child nodes
	 * which will help to reduce the memory usage.
	 * 
	 * 
	 */
	public void simplify() {

		Collections.sort(orderedNodeList, trieNodeComparator);

		/*
		 * simplify our children first.
		 */
		while (this.orderedNodeList.size() == 1) {
			// consolidate with the subnode
			// remove the sub node and set us as the parent to their
			// children.
			TrieNode<C> onlyChild = this.orderedNodeList.remove(0);

			this.nodeMap.clear();

			String childCharacter = onlyChild.getCharacter();

			for (int i = 0; i < childCharacter.length(); i++) {

				String c = childCharacter.substring(i, i + 1);

				// we grow our character to represent both
				this.character = this.character + c;

				List<C> childContext = onlyChild.matchMap.get(i);

				if (childContext != null && childContext.size() > 0) {
					int matchIndex = this.character.length() + i;

					List<C>ourContext = this.matchMap.get(i);
					
					if (ourContext == null) {
						// insert the child context as our own.
						this.matchMap
						.put(Integer.valueOf(matchIndex), childContext);
					}
					else {
						// append the child context to our own
						ourContext.addAll(childContext);
					}
				}

			}

			this.nodeMap = onlyChild.nodeMap;
			this.orderedNodeList = onlyChild.orderedNodeList;

			this.totalMatches += onlyChild.totalMatches;

			for (TrieNode<C> n : orderedNodeList) {

				// adjust the parent reference
				n.parentNode = this;
			}

		}

		/*
		 * then simplify our children
		 */

		for (TrieNode<C> n : orderedNodeList) {

			n.simplify();

		}

	}

	/**
	 * 
	 * @return the set of strings that map to the next nodes.
	 * 
	 */
	public Set<String> getNextNodeCharacterSet() {
		return this.nodeMap.keySet();
	}

}
