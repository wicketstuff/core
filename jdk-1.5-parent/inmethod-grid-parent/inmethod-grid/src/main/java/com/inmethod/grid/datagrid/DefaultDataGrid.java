package com.inmethod.grid.datagrid;

import java.util.List;

import org.apache.wicket.model.IModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.toolbar.NoRecordsToolbar;
import com.inmethod.grid.toolbar.paging.PagingToolbar;

/**
 * Convenience implementation that adds {@link PagingToolbar} and {@link NoRecordsToolbar} to the grid.
 * @author Matej Knopp
 */
public class DefaultDataGrid extends DataGrid {

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
	public DefaultDataGrid(String id, IModel model, List<IGridColumn> columns) {
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
	public DefaultDataGrid(String id, IDataSource dataSource, List<IGridColumn> columns) {
		super(id, dataSource, columns);
		init();
	}
	
	private void init() {
		addBottomToolbar(new NoRecordsToolbar(this));
		addBottomToolbar(new PagingToolbar(this));
	}
}
