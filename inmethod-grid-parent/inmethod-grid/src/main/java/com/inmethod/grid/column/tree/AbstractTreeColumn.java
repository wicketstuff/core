package com.inmethod.grid.column.tree;

import java.io.Serializable;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IRenderable;
import com.inmethod.grid.common.Icons;
import com.inmethod.grid.treegrid.BaseTreeColumn;
import com.inmethod.grid.treegrid.TreeGrid;
import com.inmethod.icon.Icon;

/**
 * Base class for {@link TreeGrid} column that contains the actual tree.
 * 
 * @param <T>
 *            tree model object type
 * @param <I>
 *            node model object type
 * 
 * @author Matej Knopp
 */
public abstract class AbstractTreeColumn<T extends TreeModel & Serializable, I extends TreeNode & Serializable, S>
	extends BaseTreeColumn<T, I, S>
{

	private static final long serialVersionUID = 1L;

	/**
	 * Creates instance with specified column id, header model and sort property.
	 * 
	 * @param columnId
	 *            column identifier - must be unique within the grid
	 * @param headerModel
	 *            model for column title
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState} to indicate that the
	 *            column is being sorted
	 */
	public AbstractTreeColumn(String columnId, IModel<String> headerModel, S sortProperty)
	{
		super(columnId, headerModel, sortProperty);
	}

	/**
	 * Creates instance with specified column id and header model
	 * 
	 * @param columnId
	 *            column identifier - must be unique within the grid
	 * @param headerModel
	 *            model for column title
	 */
	public AbstractTreeColumn(String columnId, IModel<String> headerModel)
	{
		super(columnId, headerModel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Icon getIcon(IModel<I> model)
	{
		TreeModel treeModel = (TreeModel)getTreeGrid().getTree().getDefaultModelObject();
		Object node = model.getObject();
		if (treeModel.isLeaf(node))
		{
			return Icons.ITEM;
		}
		else if (getTreeGrid().getTreeState().isNodeExpanded(node))
		{
			return Icons.FOLDER_OPEN;
		}
		else
		{
			return Icons.FOLDER_CLOSED;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected abstract Component newNodeComponent(String id, IModel<I> model);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Component newCell(WebMarkupContainer parent, String componentId, IModel<I> rowModel)
	{
		return super.newCell(parent, componentId, rowModel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final IRenderable<I> newCell(IModel<I> rowModel)
	{
		return super.newCell(rowModel);
	}
}
