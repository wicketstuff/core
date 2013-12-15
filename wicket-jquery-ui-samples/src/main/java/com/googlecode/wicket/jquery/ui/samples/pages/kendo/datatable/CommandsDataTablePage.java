package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datatable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.data.ProductsDAO;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Product;
import com.googlecode.wicket.kendo.ui.datatable.ColumnButton;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.column.CommandsColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.PropertyColumn;

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
		List<IColumn> columns = newColumnList();

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

	private static List<IColumn> newColumnList()
	{
		List<IColumn> columns = new ArrayList<IColumn>();

		columns.add(new PropertyColumn("ID", "id", 30));
		columns.add(new PropertyColumn("Name", "name"));
		columns.add(new PropertyColumn("Description", "description"));

		columns.add(new PropertyColumn("Price", "price", 70) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getFormat()
			{
				return "{0:c2}";
			}
		});

		columns.add(new CommandsColumn("", 160) {

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
