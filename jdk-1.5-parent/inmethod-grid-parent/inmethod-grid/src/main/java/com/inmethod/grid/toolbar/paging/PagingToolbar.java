package com.inmethod.grid.toolbar.paging;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;

import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.toolbar.AbstractToolbar;

/**
 * Toolbar that displays a paging navigator and a label with message about which rows are being
 * displayed and their total number in the data table.
 * 
 * This toolbar can only be added to {@link DataTable}
 * 
 * @author Matej Knopp
 */
public class PagingToolbar extends AbstractToolbar {

	private static final long serialVersionUID = 1L;

	/**
	 * Returns the {@link DataGrid} to which this toolbar belongs.
	 * @return data grid
	 */
	public DataGrid getDataGrid() {
		return (DataGrid) super.getGrid();
	}

	/**
	 * Constructor
	 * 
	 * @param grid
	 * 		data grid
	 */
	public PagingToolbar(DataGrid grid) {
		super(grid, null);

		add(newPagingNavigator("navigator"));
		add(newNavigationLabel("navigationLabel"));
	}

	protected Component newNavigationLabel(String id) {
		return new NavigatorLabel(id, getDataGrid());
	}
	
	protected Component newPagingNavigator(String id) {
		return new PagingNavigator(id, getDataGrid());
	}

	/**
	 * Important to prevent early initialization of QueryResult at
	 * AbstractPageableView. The isVisible method can be called during an early
	 * step in the form process and the QuertyResult initialization can fail if
	 * it depend upon form components
	 */
	@Override
	protected void onConfigure()
	{
		super.onConfigure();
		setVisible(getDataGrid().getTotalRowCount() != 0);
	}
}
