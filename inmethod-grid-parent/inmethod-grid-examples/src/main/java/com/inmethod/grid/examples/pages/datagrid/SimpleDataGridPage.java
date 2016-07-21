package com.inmethod.grid.examples.pages.datagrid;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import com.inmethod.grid.examples.contact.Contact;
import com.inmethod.grid.examples.pages.BaseExamplePage;

/**
 * Page with a basic {@link DefaultDataGrid}.
 * 
 * @author Matej Knopp
 */
public class SimpleDataGridPage extends BaseExamplePage
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public SimpleDataGridPage()
	{
		List<IGridColumn<IDataSource<Contact>, Contact, String>> columns = new ArrayList<IGridColumn<IDataSource<Contact>, Contact, String>>();

		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, Long, String>(
			new ResourceModel("id"), "id"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"firstName"), "firstName", "firstName"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"lastName"), "lastName", "lastName"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"homePhone"), "homePhone"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"cellPhone"), "cellPhone"));

		final DataGrid<IDataSource<Contact>, Contact, String> grid = new DefaultDataGrid<IDataSource<Contact>, Contact, String>(
			"grid", new ContactDataSource(), columns);

		add(grid);
	}

}
