package com.inmethod.grid.treegrid;


import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.column.AbstractColumn;
import com.inmethod.grid.column.tree.AbstractTreeColumn;
import com.inmethod.grid.common.AbstractGrid;
import com.inmethod.icon.Icon;

/**
 * INTERNAL
 * <p>
 * Base class for column containing the actual tree in {@link TreeGrid}. Users should not subclass this
 * class directly. Rather than that the {@link AbstractTreeColumn} class should be used.
 *
 * @author Matej Knopp
 */
public abstract class BaseTreeColumn extends AbstractColumn {

	/**
	 * Constructor.
	 * @param columnId
	 * @param headerModel
	 * @param sortProperty
	 */
	public BaseTreeColumn(String columnId, IModel headerModel, String sortProperty) {
		super(columnId, headerModel, sortProperty);
	}

	/**
	 * Constructor.
	 * @param columnId
	 * @param headerModel
	 */
	public BaseTreeColumn(String columnId, IModel headerModel) {
		super(columnId, headerModel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component newCell(WebMarkupContainer parent, String componentId, IModel rowModel) {
		AbstractTreeGridRow row = (AbstractTreeGridRow) parent;
		return new TreePanel(componentId, rowModel, row.getLevel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Component newNodeComponent(String id, IModel model) {
				return BaseTreeColumn.this.newNodeComponent(id, model);
			}

			protected Icon getIcon(IModel model) {
				return BaseTreeColumn.this.getIcon(model);
			}

			@Override
			protected void onJunctionLinkClicked(AjaxRequestTarget target, Object node) {
				getTreeGrid().onJunctionLinkClicked(target, node);
			}
		};
	};

	/**
	 * Creates the node component.
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            model used to access the {@link TreeNode}
	 * 
	 * @return newly created component instance
	 */
	protected abstract Component newNodeComponent(String id, IModel model);

	/**
	 * Returns the icon for given {@link TreeNode}.
	 * 
	 * @param model
	 *            model used to access the {@link TreeNode}
	 * @return icon instance or <code>null</code> if no icon should be displayed
	 */
	protected abstract Icon getIcon(IModel model);

	/**
	 * Returns the {@link TreeGrid} this column belongs to.
	 * @return {@link TreeGrid} this column belongs to.
	 */
	public TreeGrid getTreeGrid() {
		return (TreeGrid) getGrid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGrid(AbstractGrid grid) {
		if (getTreeGrid() != null && getTreeGrid() != grid) {
			throw new IllegalStateException("One BaseTreeColumn can not be used with multiple TreeGrid instances");
		}

		if (grid instanceof TreeGrid == false) {
			throw new IllegalStateException("BaseTreeColumn can only be added to a TreeGrid.");
		}
		super.setGrid(grid);
	}
}
