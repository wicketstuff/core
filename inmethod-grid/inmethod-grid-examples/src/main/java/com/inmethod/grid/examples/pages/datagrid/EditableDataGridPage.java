package com.inmethod.grid.examples.pages.datagrid;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.column.editable.EditablePropertyColumn;
import com.inmethod.grid.column.editable.SubmitCancelColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import com.inmethod.grid.examples.pages.BaseExamplePage;


/**
 * Page with {@link DataGrid} that enables to edit selected items.
 * 
 * @author Matej Knopp
 */
public class EditableDataGridPage extends BaseExamplePage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public EditableDataGridPage() {
		List<IGridColumn> columns = new ArrayList<IGridColumn>();
		
		Form form = new Form("form");
		add(form);
		
		columns.add(new PropertyColumn(new ResourceModel("id"), "id"));
		columns.add(new EditablePropertyColumn(new ResourceModel("firstName"), "firstName", "firstName") {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void addValidators(FormComponent component) {
				component.setRequired(true);
			}
		});
		columns.add(new EditablePropertyColumn(new ResourceModel("lastName"), "lastName", "lastName") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void addValidators(FormComponent component) {
				component.setRequired(true);
			}
		});
		columns.add(new EditablePropertyColumn(new ResourceModel("homePhone"), "homePhone"));
		columns.add(new EditablePropertyColumn(new ResourceModel("cellPhone"), "cellPhone"));
		columns.add(new SubmitCancelColumn("esd", new Model("Edit")));
		
		DataGrid grid = new DefaultDataGrid("grid", new ContactDataSource(), columns);
		form.add(grid);
		
		grid.setAllowSelectMultiple(true);
		
		grid.setSelectToEdit(true);
		grid.setClickRowToSelect(true);
		grid.setClickRowToDeselect(false);
	}

}
