package com.inmethod.grid.examples.pages.datagrid;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.poi.excel.TableComponentAsXlsHandler;

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
		List<IGridColumn<IDataSource<Contact>, Contact>> columns = new ArrayList<IGridColumn<IDataSource<Contact>, Contact>>();

		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, Long>(
			new ResourceModel("id"), "id"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String>(new ResourceModel(
			"firstName"), "firstName", "firstName"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String>(new ResourceModel(
			"lastName"), "lastName", "lastName"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String>(new ResourceModel(
			"homePhone"), "homePhone"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String>(new ResourceModel(
			"cellPhone"), "cellPhone"));

		final DataGrid<Contact> grid = new DefaultDataGrid<Contact>("grid",
			new ContactDataSource(), columns);

		add(grid);

		Link<Void> exportToExcel = new Link<Void>("exportToExcel")
		{
			/** */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				try
				{
					IRequestHandler handler = new TableComponentAsXlsHandler(
						grid.get("form:bodyContainer:body"), "example.xls");
					RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		add(exportToExcel);
	}

}
