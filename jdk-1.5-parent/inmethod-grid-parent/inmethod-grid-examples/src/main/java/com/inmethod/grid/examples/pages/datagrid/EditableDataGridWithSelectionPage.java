package com.inmethod.grid.examples.pages.datagrid;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.column.CheckBoxColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.column.editable.EditablePropertyColumn;
import com.inmethod.grid.column.editable.SubmitCancelColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import com.inmethod.grid.examples.contact.Contact;
import com.inmethod.grid.examples.pages.BaseExamplePage;


/**
 * Page with {@link DataGrid} that enables to edit selected items.
 * 
 * @author Matej Knopp
 */
public class EditableDataGridWithSelectionPage extends BaseExamplePage
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public EditableDataGridWithSelectionPage()
	{
		List<IGridColumn<IDataSource<Contact>, Contact>> columns = new ArrayList<IGridColumn<IDataSource<Contact>, Contact>>();

		Form<Void> form = new Form<Void>("form");
		add(form);

		columns.add(new CheckBoxColumn<IDataSource<Contact>, Contact>("check"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, Long>(
			new ResourceModel("id"), "id"));
		columns.add(new EditablePropertyColumn<IDataSource<Contact>, Contact, String>(
			new ResourceModel("firstName"), "firstName", "firstName")
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void addValidators(FormComponent<String> component)
			{
				component.setRequired(true);
			}
		});
		columns.add(new EditablePropertyColumn<IDataSource<Contact>, Contact, String>(
			new ResourceModel("lastName"), "lastName", "lastName")
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void addValidators(FormComponent<String> component)
			{
				component.setRequired(true);
			}
		});
		columns.add(new EditablePropertyColumn<IDataSource<Contact>, Contact, String>(
			new ResourceModel("homePhone"), "homePhone"));
		columns.add(new EditablePropertyColumn<IDataSource<Contact>, Contact, String>(
			new ResourceModel("cellPhone"), "cellPhone"));
		columns.add(new SubmitCancelColumn<IDataSource<Contact>, Contact>("esd", Model.of("Edit")));

		DataGrid<Contact> grid = new DefaultDataGrid<Contact>("grid", new ContactDataSource(),
			columns);
		form.add(grid);

		grid.setAllowSelectMultiple(true);
		grid.setSelectToEdit(false);

		grid.setClickRowToSelect(true);
	}

}
