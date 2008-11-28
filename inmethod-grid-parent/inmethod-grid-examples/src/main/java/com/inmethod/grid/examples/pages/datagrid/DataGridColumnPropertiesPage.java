package com.inmethod.grid.examples.pages.datagrid;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import com.inmethod.grid.examples.pages.BaseExamplePage;

/**
 * Page with {@link DataGrid} showing various columns properties.
 * 
 * @author Matej Knopp
 */
public class DataGridColumnPropertiesPage extends BaseExamplePage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public DataGridColumnPropertiesPage() {
		List<IGridColumn> columns = new ArrayList<IGridColumn>();
		
		columns.add(
			new PropertyColumn(new ResourceModel("id"), "id")
				.setInitialSize(3)
				.setSizeUnit(SizeUnit.EM)
				.setResizable(false)
				.setReorderable(false)
				.setHeaderTooltipModel(new Model("Person Id"))
		);
		
		columns.add(
			new PropertyColumn(new ResourceModel("firstName"), "firstName", "firstName")
				.setReorderable(false)
				.setInitialSize(200)
				.setMinSize(100)
				.setMaxSize(250)
				.setHeaderTooltipModel(new Model("Person First Name"))
		);
		
		columns.add(new PropertyColumn(new ResourceModel("lastName"), "lastName", "lastName") {
			private static final long serialVersionUID = 1L;
			@Override
			public String getCellCssClass(IModel rowModel, int rowNum) {
				return "lastName";
			}
		});
		
		columns.add(
			new PropertyColumn(new ResourceModel("homePhone"), "homePhone") {
				private static final long serialVersionUID = 1L;
				@Override
				public String getCellCssClass(IModel rowModel, int rowNum) {
					return "homePhone";
				}
			}
			.setInitialSize(10)
			.setSizeUnit(SizeUnit.EM)
			.setResizable(false)
		);

		columns.add(
			new PropertyColumn(new ResourceModel("cellPhone"), "cellPhone")
				.setInitialSize(10)
				.setSizeUnit(SizeUnit.EM)
				.setResizable(false)
		);
		
		DataGrid grid = new DefaultDataGrid("grid", new ContactDataSource(), columns);
		add(grid);
		
		grid.setRowsPerPage(15);
	}

}
