package com.inmethod.grid.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.inmethod.grid.IGridColumn;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.core.util.lang.WicketObjects;

/**
 * Manages the state (visibility, order and size) of grid columns.
 * 
 * @author Matej Knopp
 */
public class ColumnsState implements IClusterable, Cloneable
{

	private static final long serialVersionUID = 1L;

	/**
	 * State entry for single column.
	 * 
	 * @author Matej Knopp
	 */
	public static class Entry implements IClusterable
	{

		private static final long serialVersionUID = 1L;

		private final String columnId;
		private int currentWidth = -1;
		private boolean visible = true;

		/**
		 * Creates new entry instance
		 * 
		 * @param columnId
		 */
		public Entry(String columnId)
		{
			this.columnId = columnId;
		}

		/**
		 * Returns the current width, or -1 if the width is not set. In that case the initial column
		 * width will be used.
		 * 
		 * @return current column width
		 */
		public int getCurrentWidth()
		{
			return currentWidth;
		}

		/**
		 * Sets the current column width. If <code>currentWidth</code> is -1, the initial column
		 * width will be used.
		 * 
		 * @param currentWidth
		 */
		public void setCurrentWidth(int currentWidth)
		{
			this.currentWidth = currentWidth;
		}

		/**
		 * Return whether the column is visible.
		 * 
		 * @return <code>true</code> if the column is visible, <code>false</code> otherwise.
		 */
		public boolean isVisible()
		{
			return visible;
		}

		/**
		 * Sets the visibility of the column.
		 * 
		 * @param visible
		 */
		public void setVisible(boolean visible)
		{
			this.visible = visible;
		}

		/**
		 * Returns column identifier.
		 * 
		 * @return column id
		 */
		public String getColumnId()
		{
			return columnId;
		};
	};

	private final Entry[] stateArray;

	/**
	 * Creates new {@link ColumnsState} instance. The state will be initialized from the specified
	 * collection of {@link IGridColumn}s.
	 * 
	 * @param columns
	 */
	public <M, I, S> ColumnsState(Collection<IGridColumn<M, I, S>> columns)
	{
		stateArray = new Entry[columns.size()];
		int i = 0;
		for (IGridColumn<M, I, S> column : columns)
		{
			stateArray[i] = new Entry(column.getId());
			++i;
		}
	};

	/**
	 * Creates new {@link ColumnsState} instance. The instance will be initialized from the
	 * specified {@link Entry} array.
	 * 
	 * @param columnStates
	 */
	public ColumnsState(Entry[] columnStates)
	{
		stateArray = columnStates;
	}

	/**
	 * Returns index of specified entry.
	 * 
	 * @param id
	 * @return
	 */
	private int getEntryIndex(String id)
	{
		for (int i = 0; i < stateArray.length; ++i)
		{
			if (stateArray[i].getColumnId().equals(id))
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the {@link Entry} instance for column with give id, or null if the entry doesn't
	 * exist.
	 * 
	 * @param id
	 *            column id
	 * @return entry or null
	 */
	public Entry getEntry(String id)
	{
		int i = getEntryIndex(id);
		return i != -1 ? stateArray[i] : null;
	}

	/**
	 * Returns the {@link Entry} instance or throws an exception if the entry doesn't exist.
	 * 
	 * @param id
	 * @return
	 */
	private Entry getColumnStateChecked(String id)
	{
		Entry state = getEntry(id);
		if (state == null)
		{
			throw new IllegalArgumentException("Column state for column with id '" + id +
				"' not found.");
		}
		return state;
	}

	/**
	 * Sets the width of column with specified id.
	 * 
	 * @param id
	 *            column id
	 * @param newWidth
	 *            new width or -1 if initial column width should be used
	 */
	public void setColumnWidth(String id, int newWidth)
	{
		Entry state = getColumnStateChecked(id);
		state.setCurrentWidth(newWidth);
	}

	/**
	 * Returns the width of column with specified id.
	 * 
	 * @param id
	 *            column id
	 * @return column width or -1 if initial width should be used
	 */
	public int getColumnWidth(String id)
	{
		Entry state = getColumnStateChecked(id);
		return state.getCurrentWidth();
	}

	/**
	 * Sets the visibility of column with given id.
	 * 
	 * @param id
	 *            column id
	 * @param newValue
	 */
	public void setColumnVisibility(String id, boolean newValue)
	{
		Entry state = getColumnStateChecked(id);
		state.setVisible(newValue);
	}

	/**
	 * Returns the visibility of given column.
	 * 
	 * @param id
	 *            column id
	 * @return <code>true</code> if the column is visible, <code>false</code> otherwise
	 */
	public boolean getColumnVisibility(String id)
	{
		Entry state = getColumnStateChecked(id);
		return state.isVisible();
	}

	/**
	 * Returns collection of {@link Entry} instances of this {@link ColumnsState}.
	 * 
	 * @return collection of {@link Entry} instances
	 */
	public Collection<Entry> getColumnStates()
	{
		return Arrays.asList(stateArray);
	}

	/**
	 * Moves the column with specified to specified position. If the position is out of range throws
	 * {@link IndexOutOfBoundsException}. If the column with given id is not found, throws an
	 * {@link IllegalStateException}. The other columns are shifted accordingly.
	 * 
	 * @param id
	 *            column id
	 * @param newIndex
	 *            new column index
	 */
	public void setColumnIndex(String id, int newIndex)
	{
		int index = getEntryIndex(id);
		if (index == -1)
		{
			throw new IllegalArgumentException("Column state for column with id '" + id +
				"' not found.");
		}
		if (newIndex < 0 || newIndex > stateArray.length - 1)
		{
			throw new IndexOutOfBoundsException("'newIndex' parameter is out of range.");
		}
		if (newIndex > index)
		{
			Entry source = stateArray[index];
			for (int i = index; i < newIndex; ++i)
			{
				stateArray[i] = stateArray[i + 1];
			}
			stateArray[newIndex] = source;
		}
		else if (newIndex < index)
		{
			Entry source = stateArray[index];
			for (int i = index; i > newIndex; --i)
			{
				stateArray[i] = stateArray[i - 1];
			}
			stateArray[newIndex] = source;
		}
	}

	/**
	 * Returns deep copy of the object.
	 * 
	 * @return {@link ColumnsState} instance that is a deep copy of this instance
	 */
	@Override
	public ColumnsState clone()
	{
		return (ColumnsState)WicketObjects.cloneObject(this);
	}

	/**
	 * Returns whether there is an {@link Entry} instance for each of the given columns.
	 * 
	 * @param columns
	 * @return
	 */
	<M, I, S> boolean matches(Collection<IGridColumn<M, I, S>> columns)
	{
		if (stateArray.length != columns.size())
		{
			return false;
		}
		for (IGridColumn<M, I, S> column : columns)
		{
			if (getEntryIndex(column.getId()) == -1)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Called from javascript callback - updates column to current client state.
	 * 
	 * @param state
	 */
	void updateColumnsState(String state)
	{
		// state is in format "columnId,width;columnId,width..."
		// columns not specified in state will not be touched

		List<Entry> entries = new ArrayList<Entry>();
		Set<Integer> indices = new TreeSet<Integer>();
		String segments[] = state.split(";");
		for (String segment : segments)
		{
			String parts[] = segment.split(",");
			if (parts.length == 2)
			{
				int index = getEntryIndex(parts[0]);
				if (index != -1)
				{
					Entry entry = stateArray[index];
					entry.setCurrentWidth(Integer.parseInt(parts[1]));
					entries.add(entry);
					indices.add(index);
				}
			}
		}

		int i = 0;
		for (Integer index : indices)
		{
			stateArray[index] = entries.get(i);
			++i;
		}
	}

	/**
	 * Returns column with specified id from the collection.
	 * 
	 * @param id
	 * @param columns
	 * @return
	 */
	private <M, I, S> IGridColumn<M, I, S> getColumn(String id, Collection<IGridColumn<M, I, S>> columns)
	{
		for (IGridColumn<M, I, S> column : columns)
		{
			if (column.getId().equals(id))
			{
				return column;
			}
		}
		return null;
	}

	/**
	 * Filters the given {@link IGridColumn} collection leaving only the columns with visibility set
	 * to <code>true</code>.
	 * 
	 * @param allColumns
	 *            Collection to be filtered.
	 * @return Collection of columns with visibility set to <code>true</code>.
	 */
	public <M, I, S> Collection<IGridColumn<M, I, S>> getVisibleColumns(
		Collection<IGridColumn<M, I, S>> allColumns)
	{
		List<IGridColumn<M, I, S>> result = new ArrayList<IGridColumn<M, I, S>>();
		for (Entry state : stateArray)
		{
			if (state.isVisible())
			{
				IGridColumn<M, I, S> column = getColumn(state.getColumnId(), allColumns);
				if (column != null)
				{
					result.add(column);
				}
			}
		}
		return result;
	}
}
