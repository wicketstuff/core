package com.inmethod.grid.treegrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.tree.DefaultTreeState;
import org.apache.wicket.extensions.markup.html.tree.ITreeState;
import org.apache.wicket.extensions.markup.html.tree.ITreeStateListener;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.common.AbstractGrid;

/**
 * Advanced grid with a tree. Supports resizable and reorderable columns.
 * 
 * @param <T>
 *            tree model object type
 * @param <I>
 *            node model object type
 * 
 * @author Matej Knopp
 */
public class TreeGrid<T extends TreeModel & Serializable, I extends TreeNode & Serializable, S>
	extends AbstractGrid<T, I, S>
{

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@link TreeGrid} instance.
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            model used to access the {@link TreeModel}
	 * @param columns
	 *            list of {@link IGridColumn}s.
	 */
	public TreeGrid(String id, IModel<T> model, List<IGridColumn<T, I, S>> columns)
	{
		super(id, model, columns);

		WebMarkupContainer bodyContainer = (WebMarkupContainer)get("form:bodyContainer");
		bodyContainer.add(body = new TreeGridBody<T, I, S>("body", model)
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected Collection<IGridColumn<T, I, S>> getActiveColumns()
			{
				return TreeGrid.this.getActiveColumns();
			}

			@Override
			protected void rowPopulated(WebMarkupContainer item)
			{
				TreeGrid.this.onRowPopulated(item);
			}

			@Override
			protected ITreeState newTreeState()
			{
				return TreeGrid.this.newTreeState();
			}
		});

		getTreeState().addTreeStateListener(new TreeStateListener());
	}

	private ITreeState newTreeState()
	{
		return new DefaultTreeState()
		{

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isNodeSelected(Object node)
			{

				if (!isAutoSelectChildren())
				{
					return super.isNodeSelected(node);
				}
				else
				{
					// check if any parent of the node is selected
					Object parent = node;
					while (parent != null)
					{
						if (super.isNodeSelected(parent))
						{
							return true;
						}
						parent = getTree().getParentNode(parent);
					}
				}
				return false;
			}

			private void deselectChildNodes(Object node)
			{
				List<Object> toDeselect = new ArrayList<Object>();
				for (Object o : getSelectedNodes())
				{
					Object p = getTree().getParentNode(o);
					while (p != null && !p.equals(node))
					{
						p = getTree().getParentNode(p);
					}
					if (p != null)
					{
						toDeselect.add(o);
					}
				}
				for (Object o : toDeselect)
				{
					removeSelectedNodeSilent(o);
				}
			}

			@Override
			public void selectNode(Object node, boolean selected)
			{
				if (!isAutoSelectChildren())
				{
					super.selectNode(node, selected);
				}
				else
				{
					Object parent = getTree().getParentNode(node);
					while (parent != null)
					{
						if (super.isNodeSelected(parent))
						{
							return;
						}
						parent = getTree().getParentNode(parent);
					}
					deselectChildNodes(node);

					if (super.isNodeSelected(node) != selected)
					{
						super.selectNode(node, selected);
						getTree().markNodeChildrenDirty(node);
					}
				}
			}
		};
	};

	private class TreeStateListener implements ITreeStateListener
	{
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		public void allNodesCollapsed()
		{
		}

		/**
		 * {@inheritDoc}
		 */
		public void allNodesExpanded()
		{
		}

		/**
		 * {@inheritDoc}
		 */
		public void nodeCollapsed(Object node)
		{
		}

		/**
		 * {@inheritDoc}
		 */
		public void nodeExpanded(Object node)
		{
		}

		/**
		 * {@inheritDoc}
		 */
		public void nodeSelected(Object node)
		{
			onItemSelectionChanged(new Model<I>((I)node), true);
		}

		/**
		 * {@inheritDoc}
		 */
		public void nodeUnselected(Object node)
		{
			onItemSelectionChanged(new Model<I>((I)node), false);
		}
	};

	/**
	 * Creates a new {@link TreeGrid} instance.
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            tree model
	 * @param columns
	 *            list of {@link IGridColumn}s.
	 */
	public TreeGrid(String id, T model, List<IGridColumn<T, I, S>> columns)
	{
		this(id, Model.of(model), columns);
	}

	private TreeGridBody<T, I, S> body;

	/**
	 * Returns the inner tree of the {@link TreeGrid}.
	 * 
	 * @return inner tree
	 */
	public AbstractTree getTree()
	{
		return body;
	}

	/**
	 * Returns the tree state
	 * 
	 * @return tree state
	 */
	public ITreeState getTreeState()
	{
		return getTree().getTreeState();
	}

	/**
	 * During Ajax request updates the changed parts of tree.
	 */
	@Override
	public final void update()
	{
		getTree().updateTree(getRequestCycle().find(AjaxRequestTarget.class).get());
	};

	/**
	 * Callback function called after user clicked on an junction link. The node has already been
	 * expanded/collapsed (depending on previous status).
	 * 
	 * @param target
	 *            Request target - may be null on non-ajax call
	 * 
	 * @param node
	 *            Node for which this callback is relevant
	 */
	protected void onJunctionLinkClicked(AjaxRequestTarget target, Object node)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<IModel<I>> getSelectedItems()
	{
		Collection<Object> nodes = getTreeState().getSelectedNodes();
		Collection<IModel<I>> result = new ArrayList<IModel<I>>(nodes.size());
		for (Object node : nodes)
		{
			result.add(new Model<I>((I)node));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAllowSelectMultiple()
	{
		return getTreeState().isAllowSelectMultiple();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAllowSelectMultiple(boolean value)
	{
		getTreeState().setAllowSelectMultiple(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isItemSelected(IModel<I> itemModel)
	{
		return getTreeState().isNodeSelected(itemModel.getObject());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSelectedItems()
	{
		Collection<Object> nodes = getTreeState().getSelectedNodes();
		for (Object node : nodes)
		{
			getTreeState().selectNode(node, false);
		}
		getTree().invalidateAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectAllVisibleItems()
	{
		WebMarkupContainer body = (WebMarkupContainer)get("form:bodyContainer:body:i");
		if (body != null)
		{
			boolean first = true;
			for (Component component : body)
			{
				if (getTree().isRootLess() == false || first == false)
				{
					selectItem((IModel<I>)component.getDefaultModel(), true);
				}
				first = false;
			}
		}
		getTree().invalidateAll();
	}

	@Override
	protected WebMarkupContainer findRowComponent(IModel<I> rowModel)
	{
		if (rowModel == null)
		{
			throw new IllegalArgumentException("rowModel may not be null");
		}
		WebMarkupContainer body = (WebMarkupContainer)get("form:bodyContainer:body:i");
		if (body != null)
		{
			for (Component component : body)
			{
				IModel<?> model = component.getDefaultModel();
				if (rowModel.equals(model))
				{
					return (WebMarkupContainer)component;
				}
			}
		}
		return null;
	}

	@Override
	public void markItemDirty(IModel<I> model)
	{
		Object node = model.getObject();
		getTree().markNodeDirty(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectItem(IModel<I> itemModel, boolean selected)
	{
		getTreeState().selectNode(itemModel.getObject(), selected);
	}

	@Override
	public WebMarkupContainer findParentRow(Component child)
	{
		if (child instanceof AbstractTreeGridRow == false)
		{
			child = child.findParent(AbstractTreeGridRow.class);
		}
		return (WebMarkupContainer)(child != null ? child.getParent() : null);
	}

	/**
	 * Sets whether children of selected node should automatically be treated as selected nodes
	 * (default <code>true</code>). Such children can not be deselected individually. Also
	 * {@link ITreeState#isNodeSelected(Object)} returns <code>true</code> if any of node parent is
	 * selected. On the contrary, {@link ITreeState#getSelectedNodes()} only returns "top level"
	 * selected nodes.
	 * 
	 * @param autoSelectChildren
	 */
	public void setAutoSelectChildren(boolean autoSelectChildren)
	{
		this.autoSelectChildren = autoSelectChildren;
	}

	/**
	 * Returns whether children of selected nodes should be automatically treated as selected node.
	 * 
	 * @return
	 */
	public boolean isAutoSelectChildren()
	{
		return isAllowSelectMultiple() && !isSelectToEdit() && autoSelectChildren;
	}

	private boolean autoSelectChildren = true;
}
