package com.inmethod.grid.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.util.string.Strings;

import com.inmethod.grid.IGridSortState;

/**
 * An {@link IGridSortState} implementation.
 * 
 * @see IGridSortState
 * 
 * @author Matej Knopp
 */
public class GridSortState implements IGridSortState, IClusterable {

	private static final long serialVersionUID = 1L;

	/**
	 * Sort state for one column (property).
	 * 
	 * @author Matej Knopp
	 */
	private class SortStateColumn implements ISortStateColumn, IClusterable {

		private static final long serialVersionUID = 1L;

		private final String propertyName;

		private final IGridSortState.Direction direction;

		private SortStateColumn(String propertyName, IGridSortState.Direction direction) {
			this.propertyName = propertyName;
			this.direction = direction;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getPropertyName() {
			return propertyName;
		}

		/**
		 * {@inheritDoc}
		 */
		public IGridSortState.Direction getDirection() {
			return direction;
		}

	};

	private final List<ISortStateColumn> columns = new ArrayList<ISortStateColumn>();

	/**
	 * Returns the index of {@link SortStateColumn} with given propertyName.
	 * 
	 * @param propertyName
	 * @return
	 */
	private int getSortStateColumnIndex(String propertyName) {
		int i = 0;
		for (ISortStateColumn column : columns) {
			if (column.getPropertyName().equals(propertyName)) {
				return i;
			}
			++i;
		}
		return -1;
	}

	/**
	 * Resets the entire sort state.
	 */
	public void clearSortState() {
		columns.clear();
	}

	/**
	 * Clears the sort state for given property.
	 * 
	 * @param propertyName
	 */
	public void clearSortState(String propertyName) {
		setSortState(propertyName, null);
	}

	/**
	 * Returns the {@link IGridSortState.ISortStateColumn} for given property or null if there is no
	 * such entry.
	 * 
	 * @param propertyName
	 * @return {@link IGridSortState.ISortStateColumn} for given property or null if there is no such
	 *         entry
	 */
	public IGridSortState.ISortStateColumn getSortStateForProperty(String propertyName) {
		int i = getSortStateColumnIndex(propertyName);
		if (i != -1) {
			return columns.get(i);
		} else {
			return null;
		}
	}

	/**
	 * Sets the sort direction for given property name.
	 * 
	 * @param propertyName
	 * @param direction
	 * 
	 */
	public void setSortState(String propertyName, IGridSortState.Direction direction) {

		if (Strings.isEmpty(propertyName)) {
			throw new IllegalArgumentException("'propertyName' must be a non-empty string.");
		}

		int index = getSortStateColumnIndex(propertyName);
		if (index != -1) {
			columns.remove(index);
		}

		if (direction != null) {
			SortStateColumn column = new SortStateColumn(propertyName, direction);
			columns.add(0, column);
		}
	};
	
	/**
	 * Returns all {@link IGridSortState.ISortStateColumn} instances in this state.
	 * @return all {@link IGridSortState.ISortStateColumn} instances in this state
	 */
	public List<ISortStateColumn> getColumns() {
		return columns;
	}
}
