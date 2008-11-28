package com.inmethod.grid.treegrid;

import org.apache.wicket.model.IModel;

import com.inmethod.grid.common.AbstractGridRow;

/**
 * A row in tree grid.
 * 
 * @author Matej Knopp
 */
public abstract class AbstractTreeGridRow extends AbstractGridRow {

	private final int level;
	
	/**
	 * Constructor
	 * @param id
	 * @param model
	 * @param level
	 */
	public AbstractTreeGridRow(String id, IModel model, int level) {
		super(id, model);
		this.level = level;
	}

	/**
	 * Returns a depth level of the node belonging to this row.
	 * @return tree node level
	 */
	public int getLevel() {
		return level;
	}
}
