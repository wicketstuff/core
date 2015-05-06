package org.wicketstuff.foundation.blockgrid;

import java.io.Serializable;

/**
 * Options for FoundationBlockGrid.
 * @author ilkka
 *
 */
public class BlockGridOptions implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BlockGridType type;
	private int columnCount;

	public BlockGridOptions(BlockGridType type, int columnCount) {
		this.type = type;
		this.columnCount = columnCount;
	}

	public BlockGridOptions(BlockGridOptions other) {
		this.type = other.type;
		this.columnCount = other.columnCount;
	}
	
	public BlockGridType getType() {
		return type;
	}

	public void setType(BlockGridType type) {
		this.type = type;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}
}
