package org.wicketstuff.yui.examples.pages;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.tree.LinkTree;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAjaxAction;
import org.wicketstuff.yui.markup.html.menu2.action.AbstractYuiMenuAction;
import org.wicketstuff.yui.markup.html.menu2.contextMenu.MenuItem;
import org.wicketstuff.yui.markup.html.menu2.contextMenu.YuiContextMenu;
import org.wicketstuff.yui.markup.html.menu2.contextMenu.YuiContextMenuBehavior;

/**
 * Examples that shows how you can display a tree like structure (in this case
 * nested lists with string elements) using nested panels and using a tree
 * component.
 * 
 * @author Doug Leeper (ContextMenu)
 */
public class ContextMenu2TreePage extends WicketExamplePage {
	private int newNodeCounter = 0;
	private DefaultTreeModel treeModel = null;
	private LinkTree tree;
	private YuiContextMenuBehavior cmBehavior;

	/**
	 * Constructor.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public ContextMenu2TreePage(final PageParameters parameters) {

		final YuiContextMenu nodeMenu = new YuiContextMenu("floorMenu");

		nodeMenu.add(new MenuItem("addNode", new AddNodeAction()));
		nodeMenu.add(new MenuItem("moveNodeUp", new MoveNodeUpAction()));
		nodeMenu.add(new MenuItem("moveNodeDown", new MoveNodeDownAction()));
		
		
		WebMarkupContainer treeContainer = new WebMarkupContainer( "treeContextMenu");
		
		add( treeContainer );


		// create a tree
		treeModel = getTreeModel();
		tree = new LinkTree("tree", treeModel) {

			public Component newNodeComponent(String id, IModel model) {
				Component comp = super.newNodeComponent(id, model);

				if (model == null) {
					; // ignore
				} else if (model.getObject() instanceof DefaultMutableTreeNode) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) model
							.getObject();
					if (node.getAllowsChildren() && node != treeModel.getRoot()) {
						cmBehavior.applyAttributes(comp, nodeMenu, new Model(
								node.getUserObject().toString()));
					}
				} else {
					System.out.println("Model: " + model);
					System.out.println("Object: " + model.getObject());
				}
				return comp;
			}
		};

		treeContainer.add(tree);
		add(new Link("expandAll") {
			public void onClick() {
				tree.getTreeState().expandAll();
			}
		});

		add(new Link("collapseAll") {
			public void onClick() {
				tree.getTreeState().collapseAll();
			}
		});
		
		cmBehavior = new YuiContextMenuBehavior(nodeMenu );
		treeContainer.add(cmBehavior);

	}

	/**
	 * Convert the nested lists to a tree model
	 * 
	 * @param list
	 *            the list
	 * @return tree model
	 */
	private DefaultTreeModel getTreeModel() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("<root>");

		DefaultMutableTreeNode parentNode = null;

		parentNode = createNode("Folder 1.0", true);
		rootNode.add(parentNode);
		parentNode.add(createNode("test 1.1"));
		parentNode.add(createNode("test 1.2"));
		parentNode.add(createNode("test 1.3"));

		parentNode = createNode("Folder 2.0", true);
		rootNode.add(parentNode);
		parentNode.add(createNode("test 2.1"));
		parentNode.add(createNode("test 2.1"));
		parentNode.add(createNode("test 2.2"));
		parentNode.add(createNode("test 2.3"));

		parentNode = createNode("Folder 3.0", true);
		rootNode.add(parentNode);
		parentNode.add(createNode("test 3.1"));

		return new DefaultTreeModel(rootNode);
	}

	private DefaultMutableTreeNode createNode(String id) {
		return createNode(id, false);
	}

	private DefaultMutableTreeNode createNode(String id, boolean allowChildren) {
		return new DefaultMutableTreeNode(id, allowChildren);
	}

	private DefaultMutableTreeNode getTreeNode(DefaultTreeModel treeModel,
			String id) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel
				.getRoot();
		Enumeration iter = root.depthFirstEnumeration();
		while (iter.hasMoreElements()) {
			DefaultMutableTreeNode tNode = (DefaultMutableTreeNode) iter
					.nextElement();
			if (tNode.getUserObject().equals(id)) {
				return tNode;
			}
		}
		return null;
	}

	private class AddNodeAction extends AbstractYuiMenuAction implements
			IYuiMenuAjaxAction {

		public AddNodeAction() {
			super(new Model("Add Node"));

		}

		public void onClick(AjaxRequestTarget target, String targetId) {

			DefaultMutableTreeNode parent = getTreeNode(treeModel, targetId);

			DefaultMutableTreeNode newNode = createNode("Node: " + newNodeCounter++);
			treeModel.insertNodeInto(newNode, parent, parent.getChildCount());
			target.addComponent(tree);
		}

		@Override
		public void onClick() {
		}

	}

	private class MoveNodeUpAction extends AbstractYuiMenuAction implements
			IYuiMenuAjaxAction {

		public MoveNodeUpAction() {
			super(new Model("Move Node Up"));

		}

		public void onClick(AjaxRequestTarget target, String targetId) {
			DefaultMutableTreeNode folderNode = getTreeNode(treeModel, targetId);

			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) folderNode
					.getParent();
			int idx = parent.getIndex(folderNode);
			treeModel.removeNodeFromParent(folderNode);
			idx--;
			int newIdx = Math.max(0, idx);
			
			System.out.println( "Moving: " + folderNode.getUserObject() + " from " + idx );

			treeModel.insertNodeInto(folderNode, parent, newIdx);
			target.addComponent(tree);
		}

		@Override
		public void onClick() {
		}

	}

	private class MoveNodeDownAction extends AbstractYuiMenuAction implements
			IYuiMenuAjaxAction {

		public MoveNodeDownAction() {
			super(new Model("Move Node Down"));

		}

		public void onClick(AjaxRequestTarget target, String targetId) {
			DefaultMutableTreeNode folderNode = getTreeNode(treeModel, targetId);

			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) folderNode
					.getParent();

			int idx = parent.getIndex(folderNode);
			treeModel.removeNodeFromParent(folderNode);
			
		
			idx++;
			int newIdx = Math.min(parent.getChildCount(), idx);
			
			System.out.println( "Moving: " + folderNode.getUserObject() + " from " + idx );


			treeModel.insertNodeInto(folderNode, parent, newIdx);
			target.addComponent(tree);
		}

		@Override
		public void onClick() {
		}

	}
}