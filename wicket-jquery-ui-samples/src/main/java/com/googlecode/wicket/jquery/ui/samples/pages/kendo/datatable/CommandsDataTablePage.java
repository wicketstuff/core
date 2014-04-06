package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datatable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Product;
import com.googlecode.wicket.jquery.ui.samples.data.provider.ProductDataProvider;
import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.datatable.ColumnButton;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.ToolbarAjaxBehavior;
import com.googlecode.wicket.kendo.ui.datatable.column.CommandsColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.CurrencyPropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.PropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.export.CSVDataExporter;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class CommandsDataTablePage extends AbstractDataTablePage
{
	private static final long serialVersionUID = 1L;

	public CommandsDataTablePage()
	{
		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback);

		// DataTable //
		IDataProvider<Product> provider = newDataProvider();
		List<IColumn> columns = newColumnList();

		Options options = new Options();
		options.set("height", 430);
		options.set("pageable", "{ pageSizes: [ 25, 50, 100 ] }");
		options.set("columnMenu", true);
		options.set("selectable", Options.asString("multiple"));
		options.set("toolbar", "[ { name: 'view', text: 'View' }, { name: 'save', text: 'Save' } ]");

		final DataTable<Product> table = new DataTable<Product>("datatable", columns, provider, 20, options) {

			private static final long serialVersionUID = 1L;

			/**
			 * Triggered when a toolbar button is clicked.
			 */
			@Override
			public void onClick(AjaxRequestTarget target, String button, List<String> values)
			{
				this.info(button + " " + values);
				target.add(feedback);
			}

			/**
			 * Triggered when a column button is clicked.
			 */
			@Override
			public void onClick(AjaxRequestTarget target, ColumnButton button, String value)
			{
				this.info(button.getName() + " #" + value);
				target.add(feedback);
			}

			@Override
			protected JQueryAjaxBehavior newToolbarAjaxBehavior(IJQueryAjaxAware source)
			{
				return new ToolbarAjaxBehavior(source, "id");
			}
		};

		this.add(table);

		// form & button//
		final Form<?> form = new Form<Void>("form");
		this.add(form);

		form.add(new Button("export") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getIcon()
			{
				return KendoIcon.TICK;
			}

			@Override
			public void onSubmit()
			{
				CSVDataExporter.export(this.getRequestCycle(), table, "export.csv");
			}
		});
	}

	private static IDataProvider<Product> newDataProvider()
	{
		return new ProductDataProvider();
	}

	private static List<IColumn> newColumnList()
	{
		List<IColumn> columns = new ArrayList<IColumn>();

		columns.add(new PropertyColumn("ID", "id", 50));
		columns.add(new PropertyColumn("Name", "name"));
		columns.add(new PropertyColumn("Description", "description"));
		columns.add(new CurrencyPropertyColumn("Price", "price", 70));

		columns.add(new CommandsColumn("", 100) {

			private static final long serialVersionUID = 1L;

			@Override
			public List<ColumnButton> newButtons()
			{
				return Arrays.asList(new ColumnButton("edit", Model.of("Edit"), "id"));
			}
		});

		return columns;
	}
}
