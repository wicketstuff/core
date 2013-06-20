package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datatable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.kendo.datatable.ColumnButton;
import com.googlecode.wicket.jquery.ui.kendo.datatable.DataTable;
import com.googlecode.wicket.jquery.ui.kendo.datatable.column.CommandsColumn;
import com.googlecode.wicket.jquery.ui.kendo.datatable.column.IColumn;
import com.googlecode.wicket.jquery.ui.kendo.datatable.column.PropertyColumn;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.data.ProductsDAO;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Product;

public class CommandsDataTablePage extends AbstractDataTablePage
{
	private static final long serialVersionUID = 1L;

	public CommandsDataTablePage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// DataTable //
		IDataProvider<Product> provider = newDataProvider();
		List<IColumn<Product>> columns = newColumnList();

		Options options = new Options();
		options.set("height", 430);
		options.set("pageable", true);

		final DataTable<Product> table = new DataTable<Product>("datatable", columns, provider, 20, options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target, ColumnButton button, String value) {

				this.info(button + " #" + value);
				target.add(feedback);
			}
		};
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
		columns.add(new PropertyColumn<Product>("Price", "price", 60));
		columns.add(new CommandsColumn<Product>("", 160) {

			private static final long serialVersionUID = 1L;

			@Override
			public List<ColumnButton> newButtons()
			{
				return Arrays.asList(new ColumnButton("view", "id"), new ColumnButton("edit", "id"));
			}
		});

		return columns;
	}
}
