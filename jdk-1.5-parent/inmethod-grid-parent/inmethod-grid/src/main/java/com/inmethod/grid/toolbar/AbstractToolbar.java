package com.inmethod.grid.toolbar;

import java.util.Collection;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.common.AbstractGrid;

/**
 * Base for toolbar classes. 
 * 
 * @author Matej Knopp
 */
public abstract class AbstractToolbar extends Panel {

	private final AbstractGrid grid;
	
	/**
	 * Constructor
	 * @param grid
	 * @param model
	 */
	public AbstractToolbar(AbstractGrid grid, IModel model) {
		super(AbstractGrid.INTERNAL_TOOLBAR_ITEM_ID, model);
		setRenderBodyOnly(true);
		this.grid = grid;
	}

	/**
	 * Returns the collection of currently displayed columns in grid.
	 * @return collection of active columns.
	 */
	public Collection<IGridColumn> getActiveColumns() {
		return grid.getActiveColumns();
	}
	
	/**
	 * Returns the grid this toolbar belongs to.
	 * @return grid this toolbar belongs to
	 */
	public AbstractGrid getGrid() {
		return grid;
	}
}
