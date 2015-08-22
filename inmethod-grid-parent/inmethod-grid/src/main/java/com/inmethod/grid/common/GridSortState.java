package com.inmethod.grid.common;

import java.util.ArrayList;
import java.util.List;

import com.inmethod.grid.IGridSortState;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

/**
 * An {@link IGridSortState} implementation.
 * 
 * @see IGridSortState
 * 
 * @author Matej Knopp
 */
public class GridSortState<S> implements IGridSortState<S>, IClusterable
{

	private static final long serialVersionUID = 1L;

	/**
	 * Sort state for one column (property).
	 * 
	 * @author Matej Knopp
	 */
	private static class SortStateColumn<S> implements ISortStateColumn<S>, IClusterable
	{

		private static final long serialVersionUID = 1L;

		private final S propertyName;

		private final IGridSortState.Direction direction;

		private SortStateColumn(S propertyName, IGridSortState.Direction direction)
		{
			this.propertyName = propertyName;
			this.direction = direction;
		}

		/**
		 * {@inheritDoc}
		 */
		public S getPropertyName()
		{
			return propertyName;
		}

		/**
		 * {@inheritDoc}
		 */
		public IGridSortState.Direction getDirection()
		{
			return direction;
		}

	};

	private final List<ISortStateColumn<S>> columns = new ArrayList<ISortStateColumn<S>>();

	private final AbstractGrid<?, ?, S> grid;

	/**
	 * Constructor.
	 * 
	 * @param grid
	 *            the related grid, not null
	 */
	public GridSortState(AbstractGrid<?, ?, S> grid)
	{
		this.grid = grid;
	}

	/**
	 * Returns the index of {@link SortStateColumn} with given propertyName.
	 * 
	 * @param propertyName
	 * @return
	 */
	private int getSortStateColumnIndex(S propertyName)
	{
		int i = 0;
		for (ISortStateColumn<S> column : columns)
		{
			if (column.getPropertyName().equals(propertyName))
			{
				return i;
			}
			++i;
		}
		return -1;
	}

	/**
	 * Resets the entire sort state.
	 */
	public void clearSortState()
	{
		columns.clear();
	}

	/**
	 * Clears the sort state for given property.
	 * 
	 * @param propertyName
	 */
	public void clearSortState(S propertyName)
	{
		setSortState(propertyName, null);
	}

	/**
	 * Returns the {@link IGridSortState.ISortStateColumn} for given property or null if there is no
	 * such entry.
	 * 
	 * @param propertyName
	 * @return {@link IGridSortState.ISortStateColumn} for given property or null if there is no
	 *         such entry
	 */
	public IGridSortState.ISortStateColumn<S> getSortStateForProperty(S propertyName)
	{
		int i = getSortStateColumnIndex(propertyName);
		if (i != -1)
		{
			return columns.get(i);
		}
		else
		{
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
	public void setSortState(S propertyName, IGridSortState.Direction direction)
	{
		Args.notNull(propertyName, "propertyName");

		if (propertyName instanceof String && Strings.isEmpty((String) propertyName))
		{
			throw new IllegalArgumentException("'propertyName' must be a non-empty string.");
		}

		int index = getSortStateColumnIndex(propertyName);
		if (index != -1)
		{
			columns.remove(index);
		}

		if (direction != null)
		{
			SortStateColumn<S> column = new SortStateColumn<S>(propertyName, direction);
			columns.add(0, column);
		}
	};

	/**
	 * Returns all {@link IGridSortState.ISortStateColumn} instances in this state.
	 * 
	 * @return all {@link IGridSortState.ISortStateColumn} instances in this state
	 */
	public List<ISortStateColumn<S>> getColumns()
	{
		return columns;
	}

	public AbstractGrid<?, ?, S> getGrid()
	{
		return grid;
	}
}
