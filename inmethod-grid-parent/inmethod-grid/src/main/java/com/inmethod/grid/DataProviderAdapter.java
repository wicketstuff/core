package com.inmethod.grid;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.datagrid.DataGrid;

/**
 * Adapter that allows using a wicket extension {@link IDataProvider} in a {@link DataGrid}. The
 * adapter also supports sortable data providers.
 * 
 * @author Matej Knopp
 */
public class DataProviderAdapter<T> implements IDataSource<T> {

	private static final long serialVersionUID = 1L;

	final IDataProvider<T> dataProvider;

	/**
	 * Creates a new {@link DataProviderAdapter} instance.
	 * 
	 * @param dataProvider
	 *            {@link IDataProvider} instance
	 */
	public DataProviderAdapter(IDataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	public void detach() {
		dataProvider.detach();
	}

	/**
	 * {@inheritDoc}
	 */
	public IModel<T> model(T object) {
		return dataProvider.model(object);
	}
	
	private void setSortState(ISortState dest, DataGrid grid, IGridSortState gridSortState) {
		Set<String> unsortedColumns = new HashSet<String>(grid.getAllColumns().size());
		for (IGridColumn column : grid.getAllColumns()) {
			if (column.getSortProperty() != null) {
				unsortedColumns.add(column.getSortProperty());
			}
		}
		for (IGridSortState.ISortStateColumn column : gridSortState.getColumns()) {
			unsortedColumns.remove(column.getPropertyName());
		}
		for (int i = gridSortState.getColumns().size(); i > 0; --i) {
			IGridSortState.ISortStateColumn column = gridSortState.getColumns().get(i - 1);
			int dir = ISortState.NONE;
			if (column.getDirection() == IGridSortState.Direction.ASC) {
				dir = ISortState.ASCENDING;
			} else if (column.getDirection() == IGridSortState.Direction.DESC) {
				dir = ISortState.DESCENDING;
			}
			dest.setPropertySortOrder(column.getPropertyName(), dir);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void query(IQuery query, IQueryResult result) {
		if (dataProvider instanceof ISortStateLocator) {
			ISortStateLocator locator = (ISortStateLocator) dataProvider;

			IGridSortState gridSortState = query.getSortState();

			ISortState state = locator.getSortState();
			if (state != null) {
				DataGrid grid = ((DataGrid.IGridQuery) query).getDataGrid();
				setSortState(state, grid, gridSortState);
			}
		}

		result.setTotalCount(dataProvider.size());
		result.setItems(dataProvider.iterator(query.getFrom(), query.getCount()));
	}

}
