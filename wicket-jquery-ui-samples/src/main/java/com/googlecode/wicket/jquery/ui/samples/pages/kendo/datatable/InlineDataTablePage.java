package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datatable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Product;
import com.googlecode.wicket.jquery.ui.samples.data.dao.ProductsDAO;
import com.googlecode.wicket.jquery.ui.samples.data.provider.ProductDataProvider;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.button.CommandButton;
import com.googlecode.wicket.kendo.ui.datatable.column.CommandColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.CurrencyPropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.DatePropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IdPropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.PropertyColumn;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class InlineDataTablePage extends AbstractDataTablePage
{
	private static final long serialVersionUID = 1L;

	public InlineDataTablePage()
	{
		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback);

		// DataTable //
		Options options = new Options();
		options.set("height", 430);
		options.set("editable", Options.asString("inline"));
		options.set("pageable", true);
		options.set("toolbar", "[ { name: 'create', text: 'New' } ]"); /* 'toolbar' option can be used as long as #getToolbarButtons returns no button */

		final DataTable<Product> table = new DataTable<Product>("datatable", newColumnList(), newDataProvider(), 25, options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onCancel(AjaxRequestTarget target)
			{
				this.info("Cancelled...");
				target.add(feedback);
			}

			@Override
			public void onCreate(AjaxRequestTarget target, JSONObject object)
			{
				Product product = Product.of(object);
				ProductsDAO.insert(product);

				this.warn("Inserted #" + product.getId());
				target.add(feedback);
			}

			@Override
			public void onUpdate(AjaxRequestTarget target, JSONObject object)
			{
				Product product = Product.of(object);
				ProductsDAO.update(product);

				this.warn("Updated #" + product.getId());
				target.add(feedback);
			}

			@Override
			public void onDelete(AjaxRequestTarget target, JSONObject object)
			{
				Product product = Product.of(object);
				ProductsDAO.delete(product);

				this.warn("Deleted #" + product.getId());
				target.add(feedback);
			}
		};

		this.add(table);
	}

	private static IDataProvider<Product> newDataProvider()
	{
		return new ProductDataProvider();
	}

	private static List<IColumn> newColumnList()
	{
		List<IColumn> columns = new ArrayList<IColumn>();

		columns.add(new IdPropertyColumn("ID", "id", 40)); /* Important, for being sent back to server */
		columns.add(new PropertyColumn("Name", "name"));
		columns.add(new PropertyColumn("Description", "description"));
		columns.add(new DatePropertyColumn("Created", "date"));
		columns.add(new CurrencyPropertyColumn("Price", "price", 100));

		columns.add(new CommandColumn("", 170) {

			private static final long serialVersionUID = 1L;

			@Override
			public List<CommandButton> newButtons()
			{
				/*
				 * 'edit' and 'destroy' are built-in buttons/commands, no property has to be to supply #onUpdate(AjaxRequestTarget target, JSONObject object) will be triggered #onDelete(AjaxRequestTarget target, JSONObject object) will be
				 * triggered #onClick(AjaxRequestTarget, CommandButton, String) will not be triggered
				 */
				return Arrays.asList(new CommandButton("edit", Model.of("Edit")), new CommandButton("destroy", Model.of("Delete")));
			}
		});

		return columns;
	}
}
