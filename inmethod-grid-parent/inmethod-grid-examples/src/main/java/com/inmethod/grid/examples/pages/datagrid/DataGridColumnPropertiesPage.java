package com.inmethod.grid.examples.pages.datagrid;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import com.inmethod.grid.examples.contact.Contact;
import com.inmethod.grid.examples.pages.BaseExamplePage;

/**
 * Page with {@link DataGrid} showing various columns properties.
 * 
 * @author Matej Knopp
 */
public class DataGridColumnPropertiesPage extends BaseExamplePage
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public DataGridColumnPropertiesPage()
	{
		List<IGridColumn<IDataSource<Contact>, Contact, String>> columns =
				new ArrayList<IGridColumn<IDataSource<Contact>, Contact, String>>();

		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, Long, String>(
			new ResourceModel("id"), "id").setInitialSize(3)
			.setSizeUnit(SizeUnit.EM)
			.setResizable(false)
			.setReorderable(false)
			.setHeaderTooltipModel(Model.of("Person Id")));

		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"firstName"), "firstName", "firstName").setReorderable(false)
			.setInitialSize(200)
			.setMinSize(100)
			.setMaxSize(250)
			.setHeaderTooltipModel(Model.of("Person First Name")));

		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"lastName"), "lastName", "lastName")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getCellCssClass(IModel<Contact> rowModel, int rowNum)
			{
				return "lastName";
			}
		});

		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"homePhone"), "homePhone")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getCellCssClass(IModel<Contact> rowModel, int rowNum)
			{
				return "homePhone";
			}
		}.setInitialSize(10).setSizeUnit(SizeUnit.EM).setResizable(false));

		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"cellPhone"), "cellPhone").setInitialSize(10)
			.setSizeUnit(SizeUnit.EM)
			.setResizable(false));

		DataGrid<IDataSource<Contact>, Contact, String> grid =
				new DefaultDataGrid<IDataSource<Contact>, Contact, String>("grid", new ContactDataSource(), columns);
		add(grid);

		grid.setRowsPerPage(15);
	}

}
