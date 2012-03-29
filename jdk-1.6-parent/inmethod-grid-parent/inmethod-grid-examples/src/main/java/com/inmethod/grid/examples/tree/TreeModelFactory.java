package com.inmethod.grid.examples.tree;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;


/**
 * Creates the demo {@link TreeModel}
 * 
 * @author Matej Knopp
 */
public class TreeModelFactory
{
	/**
	 * Creates the model that feeds the tree.
	 * 
	 * @return New instance of tree model.
	 */
	protected DefaultTreeModel createTreeModelInternal()
	{
		List<Object> l1 = new ArrayList<Object>();
		l1.add("test 1.1");
		l1.add("test 1.2");
		l1.add("test 1.3");
		List<Object> l2 = new ArrayList<Object>();
		l2.add("test 2.1");
		l2.add("test 2.2");
		l2.add("test 2.3");
		List<Object> l3 = new ArrayList<Object>();
		l3.add("test 3.1");
		l3.add("test 3.2");
		l3.add("test 3.3");

		l2.add(l3);

		l2.add("test 2.4");
		l2.add("test 2.5");
		l2.add("test 2.6");

		l3 = new ArrayList<Object>();
		l3.add("test 3.1");
		l3.add("test 3.2");
		l3.add("test 3.3");
		l2.add(l3);

		l1.add(l2);

		l2 = new ArrayList<Object>();
		l2.add("test 2.1");
		l2.add("test 2.2");
		l2.add("test 2.3");

		l1.add(l2);

		l1.add("test 1.3");
		l1.add("test 1.4");
		l1.add("test 1.5");

		return convertToTreeModel(l1);
	}

	private DefaultTreeModel convertToTreeModel(List<?> list)
	{
		DefaultTreeModel model = null;
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new TreeBean("Root"));
		add(rootNode, list);
		model = new DefaultTreeModel(rootNode);
		return model;
	}

	private void add(DefaultMutableTreeNode parent, List<?> sub)
	{
		for (Object o : sub)
		{
			if (o instanceof List)
			{
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(
					new TreeBean("subtree..."));
				parent.add(child);
				add(child, (List<?>)o);
			}
			else
			{
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(
					new TreeBean(o.toString()));
				parent.add(child);
			}
		}
	}

	/**
	 * Creates a {@link TreeModel} with demo data.
	 * 
	 * @return {@link TreeModel} instance.
	 */
	public static DefaultTreeModel createTreeModel()
	{
		return new TreeModelFactory().createTreeModelInternal();
	}
}
