package com.inmethod.grid.examples.pages.datagrid;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import com.inmethod.grid.examples.contact.Contact;
import com.inmethod.grid.examples.pages.BaseExamplePage;

/**
 * Page with {@link DataGrid} that doesn't know the exact items count from the beginning. The paging
 * navigation pages enabled incrementally as user moves through items.
 * 
 * @author Matej Knopp
 */
public class DataGridWithUnknownItemCount extends BaseExamplePage
{

	private static final long serialVersionUID = 1L;

	/**
	 * Page constructor
	 */
	public DataGridWithUnknownItemCount()
	{
		List<IGridColumn<ContactDataSourceWithUnknownItemCount, Contact, String>> columns =
				new ArrayList<IGridColumn<ContactDataSourceWithUnknownItemCount, Contact, String>>();

		columns.add(new PropertyColumn<ContactDataSourceWithUnknownItemCount, Contact, Long, String>(
			new ResourceModel("id"), "id"));
		columns.add(new PropertyColumn<ContactDataSourceWithUnknownItemCount, Contact, String, String>(
			new ResourceModel("firstName"), "firstName", "firstName"));
		columns.add(new PropertyColumn<ContactDataSourceWithUnknownItemCount, Contact, String, String>(
			new ResourceModel("lastName"), "lastName", "lastName"));
		columns.add(new PropertyColumn<ContactDataSourceWithUnknownItemCount, Contact, String, String>(
			new ResourceModel("homePhone"), "homePhone"));
		columns.add(new PropertyColumn<ContactDataSourceWithUnknownItemCount, Contact, String, String>(
			new ResourceModel("cellPhone"), "cellPhone"));

		DataGrid<ContactDataSourceWithUnknownItemCount, Contact, String> grid =
				new DefaultDataGrid<ContactDataSourceWithUnknownItemCount, Contact, String>("grid", new ContactDataSourceWithUnknownItemCount(), columns);
		add(grid);

		grid.setRowsPerPage(50);
		grid.setContentHeight(25, SizeUnit.EM);
	}

}
