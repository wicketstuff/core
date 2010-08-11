package org.wicketstuff.datatable_autocomplete.tst;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.IClusterable;

public class TernaryNode<C> implements IClusterable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// index refers to the ith letter in this.character
	private Map<Integer, List<C>> valueListMap;

	// single character if we are an interior node
	// long string if we are an interior node.
	private String character;
	
	
	// character within the original string?
	private int level = -1;

	private TernaryNode<C> parentNode = null;

	private TernaryNode<C> lessThanNode = null;
	private TernaryNode<C> equalsNode = null;
	private TernaryNode<C> greaterThanNode = null;

	
	public boolean isCompressedLeafNode() {
		
		if (this.character.length() > 1)
			return true;
		else
			return false;
	}

	
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}


	/**
	 * @return the str
	 */
	public String getCharacter() {
		return character;
	}

	/**
		 * 
		 */
	
	public TernaryNode(TernaryNode<C> parent, int level, String str) {
		this(parent, level, str, null);
		
	}
	public TernaryNode(TernaryNode<C> parent, int level, String str, C value) {
		super();
		this.level = level;

		this.valueListMap = new LinkedHashMap<Integer, List<C>>();

		this.character = str;
		
		List<C> list = new LinkedList<C>();
		
		list.add(value);
		
		this.valueListMap.put((str.length()-1), list);

	}

	/**
	 * Write the word into the index adding nodes when required.
	 * 
	 * @param word
	 * @param value
	 */
	public void index(String word, C value) {

		if (isCompressedLeafNode()) {
			
			// is the word contained within our string.
			
			
//			if (this.character.startsWith(word)) {
//				
//				int index = word.length()-1;
//				
//				List<C>matchList = this.valueListMap.get(index);
//				
//				if (matchList == null) {
//					matchList = new LinkedList<C>();
//					this.valueListMap.put(index, matchList);
//				}
//				
//				matchList.add(value);
//			}
//			else {
		}
		
		String testCharacter = word.substring(this.level, this.level+1);
		
		
		int comparison = testCharacter.compareTo(this.character);

		TernaryNode<C> node;

		if (comparison < 0) {

			if (lessThanNode == null)
				lessThanNode = new TernaryNode<C>(this, this.level, testCharacter);
			
			lessThanNode.index(word, value);
			
			
		} else if (comparison > 0) {
			

			if (greaterThanNode == null)
				greaterThanNode = new TernaryNode<C>(this, this.level, testCharacter);
			
			greaterThanNode.index(word, value);

		}
		else if (level < (word.length() -1)) {
			// equals
			
			if (equalsNode == null) {
				// if null we write the rest of the string into this node
				equalsNode = new TernaryNode<C>(this, this.level+1, word.substring(this.level+1));
				
			}
			else {
				equalsNode.index(word, value);
			}
			
		}
		
		else {
			// terminates here
			List<C>valueList = this.valueListMap.get(0);
			
			if (valueList == null) {
				valueList = new LinkedList<C>();
				this.valueListMap.put(0, valueList);
			}
			
			valueList.add(value);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TernaryNode [str= " + character + ", lessThanNode=" + lessThanNode + ", equalsNode=" + equalsNode + ", greaterThanNode="
				+ greaterThanNode  + " ]";
	}

	
//	public void buildWordList(List<C> wordList, String prefix, int limit) {
//
//		// find the node that matches the prefix then add all words in the tree
//		// and subtree.
//
//		if (prefix.length() == 1 && prefix.equals(this.str)) {
//
//			// this and below is the match
//
//			this.accumulate(wordList, limit, true);
//			
//			
//
//		} else {
//			
//			String decendent = prefix.substring(1);
//
//			TernaryNode<C> next = a(decendent, false);
//
//			next.buildWordList(wordList, decendent, limit);
//
//		}
//
//	}

	/**
	 * @return the parentNode
	 */
	public TernaryNode<C> getParentNode() {
		return parentNode;
	}


	/**
	 * Find all of the matches in the sub trie indicated and add them onto the
	 * list.
	 * 
	 * @param root
	 * @param wordList
	 * @param first true if we want to only include the equals first
	 * 
	 */
	public void accumulate(List<C> wordList, int limit, boolean first) {

		for (List<C> col : this.valueListMap.values()) {
			wordList.addAll(col);
		}

		if (!first && lessThanNode != null)
			lessThanNode.accumulate(wordList, limit, false);
		
		if (equalsNode != null)
			equalsNode.accumulate(wordList, limit, false);
		
		if (!first && greaterThanNode != null)
			greaterThanNode.accumulate(wordList, limit, false);

	}
	
	public void collect (List<C>valueList) {
//		  private void collect(Node x, String prefix, Queue<String> queue) {
//		        if (x == null) return;
//		        collect(x.left,  prefix,       queue);
//		        if (x.val != null) queue.enqueue(prefix + x.c);
//		        collect(x.mid,   prefix + x.c, queue);
//		        collect(x.right, prefix,       queue);
		

		for (List<C> col : this.valueListMap.values()) {
			valueList.addAll(col);
		}
		
		if (lessThanNode != null)
			lessThanNode.collect(valueList);
		
		if (equalsNode != null)
			equalsNode.collect(valueList);
		
		if (greaterThanNode != null)
			greaterThanNode.collect(valueList);
		
		
		
	}

//	private TernaryNode<C> getNextNode(String word, boolean initialize) {
//
//		
//		String targetCharacter = word.substring(this.level+1, this.level+2);
//
//		int comparison = targetCharacter.compareTo(this.str);
//
//		TernaryNode<C> node;
//
//		if (comparison < 1) {
//
//			lessThanNode = initializeNode(this, targetCharacter, lessThanNode, level,
//					initialize);
//
//			node = lessThanNode;
//
//		} else if (comparison > 1) {
//			greaterThanNode = initializeNode(this, targetCharacter,
//					greaterThanNode, level, initialize);
//			
//			node = greaterThanNode;
//
//		} else {
//			equalsNode = initializeNode(this, targetCharacter, equalsNode, level+1,
//					initialize);
//			node = equalsNode;
//		}
//
//		return node;
//
//	}

	private TernaryNode<C> initializeNode(TernaryNode<C> parent, String str,
			TernaryNode<C> node, int level, boolean initialize) {

		if (node != null)
			return node;
		else if (initialize)
			return new TernaryNode<C>(parent, level, str);
		else
			return null;

	}

	public void visit(TernaryNodeVisitor<C> visitor) {

		visitor.visit(this);

		if (this.lessThanNode != null)
			lessThanNode.visit(visitor);

		if (this.equalsNode != null)
			equalsNode.visit(visitor);

		if (this.greaterThanNode != null)
			greaterThanNode.visit(visitor);

	}

	/**
	 * 
	 * @param prefix
	 * @return the node theat matches the prefix given.
	 * 
	 */
	public TernaryNode<C> matchPrefix(String prefix) {
		
//		 private Node get(Node x, String key, int d) {
//		        if (key == null || key.length() == 0) throw new RuntimeException("illegal key");
//		        if (x == null) return null;
//		        char c = key.charAt(d);
//		        if      (c < x.c)              return get(x.left,  key, d);
//		        else if (c > x.c)              return get(x.right, key, d);
//		        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
//		        else                           return x;
		

		if (prefix.length() == 0)
			return this;
		
		String testCharacter = prefix.substring(this.level, this.level+1);
		
		String thisCharacter = this.character;
		
		int comparison = testCharacter.compareTo(thisCharacter);

		TernaryNode<C> node;

		if (comparison < 0) {

			if (lessThanNode != null)
				return lessThanNode.matchPrefix(prefix);
			else
				return null;
			
			
		} else if (comparison > 0) {
			

			if (greaterThanNode != null)
				return greaterThanNode.matchPrefix(prefix);
			else
				return null;

		}
		else if (level < (prefix.length() -1)) {
			// equals
			
			if (equalsNode != null)
				return equalsNode.matchPrefix(prefix);
			else
				return null;
			
			
		}
		
		else {
			return this;
		}

		
	}

	/**
	 * 
	 * @param levelZeroSet
	 * 
	 * Visit all of the level zero nodes and add their character value into the set.
	 * 
	 */
	public void getStartingCharacterSet(Set<String> levelZeroSet) {
		
		if (this.level > 0)
			return;
		
		if (this.level == 0) {
			
			levelZeroSet.add(this.character);
			
			if (lessThanNode != null)
				lessThanNode.getStartingCharacterSet(levelZeroSet);
			
			if (greaterThanNode != null)
				greaterThanNode.getStartingCharacterSet(levelZeroSet);
		}
		
	}

}
