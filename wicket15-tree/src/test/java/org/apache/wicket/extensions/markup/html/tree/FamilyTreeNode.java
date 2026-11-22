package org.apache.wicket.extensions.markup.html.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.wicket.extensions.markup.html.tree.TreeTablePage.Person;

public class FamilyTreeNode implements TreeNode, Serializable {
	private static final long serialVersionUID = 1L;

	private final Person me;

	private final FamilyTreeNode parent;

	private final List<FamilyTreeNode> children = new ArrayList<>();

	public FamilyTreeNode(Person me) {
		this(null, me);
	}

	public FamilyTreeNode(FamilyTreeNode parent, Person me) {
		this.parent = parent;
		this.me = me;
	}

	public FamilyTreeNode addChild(Person child) {
		FamilyTreeNode childNode = new FamilyTreeNode(this, child);
		children.add(childNode);
		return childNode;
	}

	public Person getSelf() {
		return me;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getIndex(TreeNode node) {
		return children.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return children.isEmpty();
	}

	@Override
	public Enumeration<FamilyTreeNode> children() {
		return Collections.enumeration(children);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + me.getName();
	}
}
