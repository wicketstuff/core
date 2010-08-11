package com.inmethod.grid.examples.pages.datagrid;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import com.inmethod.grid.examples.pages.BaseExamplePage;

/**
 * Page with {@link DataGrid} that doesn't know the exact items count from the beginning. The paging
 * navigation pages enabled incrementally as user moves through items.
 * 
 * @author Matej Knopp
 */
public class DataGridWithUnknownItemCount extends BaseExamplePage {

	private static final long serialVersionUID = 1L;

	/**
	 * Page constructor
	 */
	public DataGridWithUnknownItemCount() {
		List<IGridColumn> columns = new ArrayList<IGridColumn>();

		columns.add(new PropertyColumn(new ResourceModel("id"), "id"));
		columns.add(new PropertyColumn(new ResourceModel("firstName"), "firstName", "firstName"));
		columns.add(new PropertyColumn(new ResourceModel("lastName"), "lastName", "lastName"));
		columns.add(new PropertyColumn(new ResourceModel("homePhone"), "homePhone"));
		columns.add(new PropertyColumn(new ResourceModel("cellPhone"), "cellPhone"));

		DataGrid grid = new DefaultDataGrid("grid", new ContactDataSourceWithUnknownItemCount(), columns);
		add(grid);

		grid.setRowsPerPage(50);
		grid.setContentHeight(25, SizeUnit.EM);
	}

}
