package com.inmethod.grid.datagrid;

import java.util.List;

import org.apache.wicket.model.IModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.toolbar.NoRecordsToolbar;
import com.inmethod.grid.toolbar.paging.PagingToolbar;

/**
 * Convenience implementation that adds {@link PagingToolbar} and {@link NoRecordsToolbar} to the
 * grid.
 * 
 * @param <D>
 *            datasource model object type = grid type
 * @param <T>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public class DefaultDataGrid<D extends IDataSource<T>, T, S> extends DataGrid<D, T, S>
{

	private static final long serialVersionUID = 1L;

	/**
	 * Crates a new {@link DefaultDataGrid} instance.
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            model to access the {@link IDataSource} instance used to fetch the data
	 * @param columns
	 *            list of grid columns
	 */
	public DefaultDataGrid(String id, IModel<D> model, List<IGridColumn<D, T, S>> columns)
	{
		super(id, model, columns);
		init();
	}

	/**
	 * Crates a new {@link DefaultDataGrid} instance.
	 * 
	 * @param id
	 *            component id
	 * @param dataSource
	 *            data source used to fetch the data
	 * @param columns
	 *            list of grid columns
	 */
	public DefaultDataGrid(String id, D dataSource, List<IGridColumn<D, T, S>> columns)
	{
		super(id, dataSource, columns);
		init();
	}

	private void init()
	{
		addBottomToolbar(new NoRecordsToolbar<D, T, S>(this));
		addBottomToolbar(new PagingToolbar<D, T, S>(this));
	}
}
