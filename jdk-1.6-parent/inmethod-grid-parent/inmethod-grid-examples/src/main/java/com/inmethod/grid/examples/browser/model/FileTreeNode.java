package com.inmethod.grid.examples.browser.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.TreeNode;

public class FileTreeNode implements TreeNode, Serializable
{

	private static final long serialVersionUID = 1L;

	private FileEntry fileEntry;
	private List<FileTreeNode> children = null;
	private FileTreeNode parent;

	FileTreeNode(FileEntry fileEntry, FileTreeNode parent)
	{
		this.fileEntry = fileEntry;
		this.parent = parent;
	}

	public FileEntry getFileEntry()
	{
		return fileEntry;
	}

	private void ensureChildrenLoaded()
	{
		if (children == null)
		{
			children = new ArrayList<FileTreeNode>();

			List<FileEntry> entries = fileEntry.getChildren();
			for (FileEntry entry : entries)
			{
				FileTreeNode node = new FileTreeNode(entry, this);
				children.add(node);
			}
		}
	}

	public Enumeration<FileTreeNode> children()
	{
		ensureChildrenLoaded();
		return new Enumeration<FileTreeNode>()
		{
			Iterator<FileTreeNode> i = children.iterator();

			public boolean hasMoreElements()
			{
				return i.hasNext();
			}

			public FileTreeNode nextElement()
			{
				return i.next();
			}
		};
	}

	public boolean getAllowsChildren()
	{
		return fileEntry.isFolder();
	}

	public TreeNode getChildAt(int childIndex)
	{
		ensureChildrenLoaded();
		return children.get(childIndex);
	}

	public int getChildCount()
	{
		ensureChildrenLoaded();
		return children.size();
	}

	public int getIndex(TreeNode node)
	{
		ensureChildrenLoaded();
		return children.indexOf(node);
	}

	public TreeNode getParent()
	{
		return parent;
	}

	public boolean isLeaf()
	{
		return fileEntry.isFolder() == false;
	}

}
