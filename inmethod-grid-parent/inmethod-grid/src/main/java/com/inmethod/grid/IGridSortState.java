/**
 * 
 */
package com.inmethod.grid;

import java.util.List;

import com.inmethod.grid.common.AbstractGrid;

/**
 * Allows to query the sort state of a grid. Sort state determines by which properties and in what
 * direction the data should be sorted. This interface allows data to be sorted by multiple
 * properties at the same time.
 * <p>
 * Example of use:
 * 
 * <pre>
 *  IGridSortState state = ... // acquire sort state
 *  
 *  if (state.getColumns().isEmpty() == false) { // at least one column is being sorted
 *     ISortStateColumn column = state.getColumns().get(0); // get the one with highest priority
 *     
 *     // property name is what IGridColumn.getSortProperty() returns
 *     String propertyName property = column.getPropertyName();
 *           
 *     // direction is either ASC or DESC
 *     IGridSortState.Direction direction = column.getDirection();
 *           
 *  }
 * </pre>
 * 
 * @author Matej Knopp
 */
public interface IGridSortState<S>
{

	/**
	 * The direction.
	 * 
	 * @author Matej Knopp
	 */
	enum Direction
	{
		/**
		 * Ascending direction
		 */
		ASC,

		/**
		 * Descending direction
		 */
		DESC;
	};

	/**
	 * Pair of property name and {@link Direction}
	 * 
	 * @author Matej Knopp
	 */
	public interface ISortStateColumn<S>
	{
		/**
		 * Returns the property name
		 * 
		 * @see IGridColumn#getSortProperty()
		 * @return property name
		 */
		public S getPropertyName();

		/**
		 * Returns the direction in which this column should be sorted
		 * 
		 * @return direction
		 */
		public IGridSortState.Direction getDirection();
	};

	/**
	 * @return the related grid, not null
	 */
	public AbstractGrid<?, ?, S> getGrid();

	/**
	 * Returns the sort state values for various columns sorted by column priority. Columns with
	 * higher priority are placed before (have lower index than) columns with lower priority.
	 * <p>
	 * If your business logic supports sorting on one property only, you should sort by the first
	 * entry in the result list.
	 * 
	 * @return list of {@link ISortStateColumn} instances that determines the sort state of grid
	 */
	public List<IGridSortState.ISortStateColumn<S>> getColumns();
}