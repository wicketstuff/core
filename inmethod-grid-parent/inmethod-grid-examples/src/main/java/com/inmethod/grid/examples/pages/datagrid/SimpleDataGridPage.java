package com.inmethod.grid.examples.pages.datagrid;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import com.inmethod.grid.examples.pages.BaseExamplePage;

/**
 * Page with a basic {@link DefaultDataGrid}.
 * 
 * @author Matej Knopp
 */
public class SimpleDataGridPage extends BaseExamplePage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public SimpleDataGridPage() {
		List<IGridColumn> columns = new ArrayList<IGridColumn>();
		
		columns.add(new PropertyColumn(new ResourceModel("id"), "id"));
		columns.add(new PropertyColumn(new ResourceModel("firstName"), "firstName", "firstName"));
		columns.add(new PropertyColumn(new ResourceModel("lastName"), "lastName", "lastName"));
		columns.add(new PropertyColumn(new ResourceModel("homePhone"), "homePhone"));
		columns.add(new PropertyColumn(new ResourceModel("cellPhone"), "cellPhone"));
		
		DataGrid grid = new DefaultDataGrid("grid", new ContactDataSource(), columns);
		
		add(grid);
	}

}
