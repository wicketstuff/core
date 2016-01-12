package com.inmethod.grid.examples.browser.model;

import java.io.File;
import java.io.Serializable;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class FileTreeModel implements TreeModel, Serializable
{

	private static final long serialVersionUID = 1L;

	public Object getChild(Object parent, int index)
	{
		return ((TreeNode)parent).getChildAt(index);
	}

	public int getChildCount(Object parent)
	{
		return ((TreeNode)parent).getChildCount();
	}

	public int getIndexOfChild(Object parent, Object child)
	{
		if (parent == null || child == null)
			return -1;
		return ((TreeNode)parent).getIndex((TreeNode)child);
	}

	private FileTreeNode root;

	public FileTreeModel(String rootFolder)
	{
		root = new FileTreeNode(new FileEntry(new File(rootFolder)), null);
	}

	public Object getRoot()
	{
		return root;
	}

	public boolean isLeaf(Object node)
	{
		return ((TreeNode)node).isLeaf();
	}

	public void valueForPathChanged(TreePath path, Object newValue)
	{
	}

	public void addTreeModelListener(TreeModelListener l)
	{
	}

	public void removeTreeModelListener(TreeModelListener l)
	{

	}

}
