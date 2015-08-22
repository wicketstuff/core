package com.inmethod.grid.treegrid;

import org.apache.wicket.model.IModel;

import com.inmethod.grid.common.AbstractGridRow;

/**
 * A row in tree grid.
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public abstract class AbstractTreeGridRow<M, I, S> extends AbstractGridRow<M, I, S>
{

	private static final long serialVersionUID = 1L;
	private final int level;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 * @param level
	 */
	public AbstractTreeGridRow(String id, IModel<I> model, int level)
	{
		super(id, model);
		this.level = level;
	}

	/**
	 * Returns a depth level of the node belonging to this row.
	 * 
	 * @return tree node level
	 */
	public int getLevel()
	{
		return level;
	}
}
