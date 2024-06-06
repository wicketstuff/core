/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.datatable;

import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.util.lang.Generics;
import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.jquery.ui.samples.data.bean.Product;
import org.wicketstuff.jquery.ui.samples.data.provider.ProductDataProvider;
import org.wicketstuff.kendo.ui.datatable.DataTable;
import org.wicketstuff.kendo.ui.datatable.column.CurrencyPropertyColumn;
import org.wicketstuff.kendo.ui.datatable.column.IColumn;
import org.wicketstuff.kendo.ui.datatable.column.PropertyColumn;

public class InfiniteDataTablePage extends AbstractDataTablePage
{
	private static final long serialVersionUID = 1L;

	public InfiniteDataTablePage()
	{
		// DataTable //
		Options options = new Options();
		options.set("height", 430);
		options.set("scrollable", "{ virtual: true }"); // infinite scroll

		final DataTable<Product> table = new DataTable<Product>("datatable", newColumnList(), newDataProvider(), 15, options);
		this.add(table);
	}

	private static IDataProvider<Product> newDataProvider()
	{
		return new ProductDataProvider();
	}

	private static List<IColumn> newColumnList()
	{
		List<IColumn> columns = Generics.newArrayList();

		columns.add(new PropertyColumn("ID", "id", 50));
		columns.add(new PropertyColumn("Name", "name"));
		columns.add(new PropertyColumn("Description", "description"));
		columns.add(new CurrencyPropertyColumn("Price", "price", 70));

		return columns;
	}
}
