package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datatable;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.kendo.datatable.DataTable;
import com.googlecode.wicket.jquery.ui.kendo.datatable.column.IColumn;
import com.googlecode.wicket.jquery.ui.kendo.datatable.column.PropertyColumn;
import com.googlecode.wicket.jquery.ui.samples.data.ProductsDAO;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Product;

public class DefaultDataTablePage extends AbstractDataTablePage
{
	private static final long serialVersionUID = 1L;

	public DefaultDataTablePage()
	{
		// DataTable //
		IDataProvider<Product> provider = newDataProvider();
		List<IColumn<Product>> columns = newColumnList();

		Options options = new Options();
		options.set("height", 430);
		options.set("pageable", "{ pageSizes: [ 10, 20, 30 ] }");

		final DataTable<Product> table = new DataTable<Product>("datatable", columns, provider, 20, options);
		this.add(table);
	}


	private static IDataProvider<Product> newDataProvider()
	{
		return new ListDataProvider<Product>(ProductsDAO.all());
	}

	private static List<IColumn<Product>> newColumnList()
	{
		List<IColumn<Product>> columns = new ArrayList<IColumn<Product>>();

		columns.add(new PropertyColumn<Product>("ID", "id", 30));
		columns.add(new PropertyColumn<Product>("Name", "name"));
		columns.add(new PropertyColumn<Product>("Description", "description"));
		columns.add(new PropertyColumn<Product>("Unit Price", "price"));

		return columns;
	}
}
