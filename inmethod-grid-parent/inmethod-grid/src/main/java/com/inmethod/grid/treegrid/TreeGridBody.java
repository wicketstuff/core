package com.inmethod.grid.treegrid;

import java.util.Collection;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.common.AbstractGridRow;
import com.inmethod.grid.common.AttachPrelightBehavior;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.tree.AbstractTree;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * Body of {@link TreeGrid}. Contains the rows for tree nodes.
 * 
 * @param <T>
 *            tree model object type
 * @param <I>
 *            node model object type
 * 
 * @author Matej Knopp
 */
public abstract class TreeGridBody<T extends TreeModel, I extends TreeNode, S> extends AbstractTree
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 * @param model
	 */
	public TreeGridBody(String id, IModel<T> model)
	{
		super(id, model);
		setRenderBodyOnly(true);
	}

	@Override
	protected void populateTreeItem(final WebMarkupContainer item, int level)
	{
		AbstractGridRow<T, I, S> row = new AbstractTreeGridRow<T, I, S>("item",
			(IModel<I>)item.getDefaultModel(), level)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Collection<IGridColumn<T, I, S>> getActiveColumns()
			{
				return TreeGridBody.this.getActiveColumns();
			}

			@Override
			protected int getRowNumber()
			{
				return -1;
			}
		};
		item.add(row);
		item.add(new Behavior()
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTag(Component component, ComponentTag tag)
			{
				CharSequence klass = "imxt-want-prelight imxt-grid-row";
				if (getTreeState().isNodeSelected(item.getDefaultModelObject()))
				{
					klass = klass + " imxt-selected";
				}
				tag.put("class", klass);
			}

		});
		item.add(new AttachPrelightBehavior());
		rowPopulated(item);
	}

	@Override
	protected void addComponent(AjaxRequestTarget target, Component component)
	{
		if (component == this)
		{
			// can't refresh this component directly because of setRenderBodyOnly(true) that's set
			// in
			// constructor
			target.add(findParent(TreeGrid.class));
		}
		else
		{
			super.addComponent(target, component);
		}
	}

	/**
	 * @see AbstractTree#isForceRebuildOnSelectionChange()
	 */
	@Override
	protected boolean isForceRebuildOnSelectionChange()
	{
		return false;
	}

	boolean isNodeExpanded2(Object object)
	{
		return super.isNodeExpanded(object);
	}

	protected abstract Collection<IGridColumn<T, I, S>> getActiveColumns();

	protected abstract void rowPopulated(WebMarkupContainer item);
}
