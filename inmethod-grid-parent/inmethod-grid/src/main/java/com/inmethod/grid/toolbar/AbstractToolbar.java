package com.inmethod.grid.toolbar;

import java.util.Collection;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.common.AbstractGrid;

/**
 * Base for toolbar classes.
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public abstract class AbstractToolbar<M, I, S> extends Panel
{

	private static final long serialVersionUID = 1L;
	private final AbstractGrid<M, I, S> grid;

	/**
	 * Constructor
	 * 
	 * @param grid
	 * @param model
	 */
	public AbstractToolbar(AbstractGrid<M, I, S> grid, IModel<String> model)
	{
		super(AbstractGrid.INTERNAL_TOOLBAR_ITEM_ID, model);
		setRenderBodyOnly(true);
		this.grid = grid;
	}

	/**
	 * Returns the collection of currently displayed columns in grid.
	 * 
	 * @return collection of active columns.
	 */
	public Collection<IGridColumn<M, I, S>> getActiveColumns()
	{
		return grid.getActiveColumns();
	}

	/**
	 * Returns the grid this toolbar belongs to.
	 * 
	 * @return grid this toolbar belongs to
	 */
	public AbstractGrid<M, I, S> getGrid()
	{
		return grid;
	}
}
