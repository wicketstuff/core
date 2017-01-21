package com.googlecode.wicket.jquery.ui.samples.kendoui.datatable;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Product;
import com.googlecode.wicket.jquery.ui.samples.data.provider.ProductDataProvider;
import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandButton;
import com.googlecode.wicket.kendo.ui.datatable.button.ToolbarButton;
import com.googlecode.wicket.kendo.ui.datatable.column.CommandColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.CurrencyPropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IdPropertyColumn;
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
		Options options = new Options();
		options.set("height", 430);
		options.set("pageable", "{ pageSizes: [ 25, 50, 100 ] }");
		options.set("columnMenu", true);
		options.set("selectable", Options.asString("multiple"));
		options.set("toolbar", "[ { name: 'view', text: 'View' }, { name: 'save', text: 'Save' } ]");

		final DataTable<Product> table = new DataTable<Product>("datatable", newColumnList(), newDataProvider(), 25, options) {

			private static final long serialVersionUID = 1L;

			private ToolbarButton viewButton = new ToolbarButton("view", Model.of("View"), "id");
			private ToolbarButton saveButton = new ToolbarButton("_save", Model.of("Save"), "id");
			// 'save' is a built-in command, that's why it is prefixed by '_'

			// properties //

			@Override
			protected List<ToolbarButton> getToolbarButtons()
			{
				return Arrays.asList(this.viewButton, this.saveButton);
			}

			// events //

			/**
			 * Triggered when a toolbar button is clicked.
			 */
			@Override
			public void onClick(AjaxRequestTarget target, ToolbarButton button, List<String> values)
			{
				this.info(button.getText().getObject() + " " + values);
				target.add(feedback);
			}

			/**
			 * Triggered when a column button is clicked.
			 */
			@Override
			public void onClick(AjaxRequestTarget target, CommandButton button, String value)
			{
				this.info(button.getText().getObject() + " #" + value);
				target.add(feedback);
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
		List<IColumn> columns = Generics.newArrayList();

		columns.add(new IdPropertyColumn("ID", "id", 50)); /* Important, for being sent back to server */
		columns.add(new PropertyColumn("Name", "name"));
		columns.add(new PropertyColumn("Description", "description"));
		columns.add(new CurrencyPropertyColumn("Price", "price", 70));

		columns.add(new CommandColumn(100) {

			private static final long serialVersionUID = 1L;

			@Override
			public List<CommandButton> newButtons()
			{
				return Arrays.asList(new CommandButton("_edit", Model.of("Edit")));
				// 'edit' is a built-in command, that's why it is prefixed by '_'
			}
		});

		return columns;
	}
}
