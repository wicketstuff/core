package com.inmethod.grid;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.common.AbstractGrid;

/**
 * Adapter that allows using a wicket extension {@link IDataProvider} in an {@link AbstractGrid}.
 * The adapter also supports sortable data providers.
 * 
 * @param <T>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public class DataProviderAdapter<T, S> implements IDataSource<T>
{

	private static final long serialVersionUID = 1L;

	final IDataProvider<T> dataProvider;

	/**
	 * Creates a new {@link DataProviderAdapter} instance.
	 * 
	 * @param dataProvider
	 *            {@link IDataProvider} instance
	 */
	public DataProviderAdapter(IDataProvider<T> dataProvider)
	{
		this.dataProvider = dataProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	public void detach()
	{
		dataProvider.detach();
	}

	/**
	 * {@inheritDoc}
	 */
	public IModel<T> model(T object)
	{
		return dataProvider.model(object);
	}

	private void setSortState(ISortState<S> dest, IGridSortState<S> gridSortState)
	{
		AbstractGrid<?, ?, S> grid = gridSortState.getGrid();
		Set<S> unsortedColumns = new HashSet<S>(grid.getAllColumns().size());
		for (IGridColumn<?, ?, S> column : grid.getAllColumns())
		{
			if (column.getSortProperty() != null)
			{
				unsortedColumns.add(column.getSortProperty());
			}
		}
		for (IGridSortState.ISortStateColumn<S> column : gridSortState.getColumns())
		{
			unsortedColumns.remove(column.getPropertyName());
		}
		for (int i = gridSortState.getColumns().size(); i > 0; --i)
		{
			IGridSortState.ISortStateColumn<S> column = gridSortState.getColumns().get(i - 1);
			SortOrder dir = SortOrder.NONE;
			if (column.getDirection() == IGridSortState.Direction.ASC)
			{
				dir = SortOrder.ASCENDING;
			}
			else if (column.getDirection() == IGridSortState.Direction.DESC)
			{
				dir = SortOrder.DESCENDING;
			}
			dest.setPropertySortOrder(column.getPropertyName(), dir);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void query(IQuery query, IQueryResult<T> result)
	{
		if (dataProvider instanceof ISortStateLocator)
		{
			ISortStateLocator<S> locator = (ISortStateLocator<S>)dataProvider;

			IGridSortState<S> gridSortState = query.getSortState();

			ISortState<S> state = locator.getSortState();
			if (state != null)
			{
				setSortState(state, gridSortState);
			}
		}

		result.setTotalCount(dataProvider.size());
		result.setItems(dataProvider.iterator(query.getFrom(), query.getCount()));
	}

}
