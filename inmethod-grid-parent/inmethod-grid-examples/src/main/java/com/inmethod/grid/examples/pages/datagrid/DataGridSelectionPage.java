package com.inmethod.grid.examples.pages.datagrid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.column.CheckBoxColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import com.inmethod.grid.examples.contact.Contact;
import com.inmethod.grid.examples.pages.BaseExamplePage;

/**
 * Page with {@link DataGrid} that allows user to select items.
 * 
 * @author Matej Knopp
 */
public class DataGridSelectionPage extends BaseExamplePage
{

	private static final long serialVersionUID = 1L;

	private Label selectionLabel;

	/**
	 * Constructor.
	 */
	public DataGridSelectionPage()
	{
		List<IGridColumn<IDataSource<Contact>, Contact, String>> columns =
				new ArrayList<IGridColumn<IDataSource<Contact>, Contact, String>>();

		columns.add(new CheckBoxColumn<IDataSource<Contact>, Contact, String>("checkBox"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, Long, String>(
			new ResourceModel("id"), "id"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"firstName"), "firstName", "firstName"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"lastName"), "lastName", "lastName"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"homePhone"), "homePhone"));
		columns.add(new PropertyColumn<IDataSource<Contact>, Contact, String, String>(new ResourceModel(
			"cellPhone"), "cellPhone"));

		final DataGrid<IDataSource<Contact>, Contact, String> grid =
				new DefaultDataGrid<IDataSource<Contact>, Contact, String>(
			"grid", new ContactDataSource(), columns)
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onItemSelectionChanged(IModel<Contact> item, boolean newValue)
			{
				super.onItemSelectionChanged(item, newValue);

				// when item selection changes the label showing selected items needs to be
				// refreshed
				getRequestCycle().find(AjaxRequestTarget.class).ifPresent(t -> t.add(selectionLabel));
			}
		};
		add(grid);

		grid.setCleanSelectionOnPageChange(false);
		grid.setClickRowToSelect(true);
		grid.setRowsPerPage(15);

		// model for label that shows selected items
		IModel<String> selectedItemsModel = new Model<String>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				return selectedItemsAsString(grid);
			}
		};
		add(selectionLabel = new Label("currentSelection", selectedItemsModel));
		selectionLabel.setOutputMarkupId(true);

		addOptionLinks(grid);
	}

	private String selectedItemsAsString(DataGrid<IDataSource<Contact>, Contact, String> grid)
	{
		StringBuilder res = new StringBuilder();
		Collection<IModel<Contact>> selected = grid.getSelectedItems();
		for (IModel<Contact> model : selected)
		{
			Contact contact = model.getObject();
			if (res.length() > 0)
			{
				res.append(", ");
			}
			res.append(contact.getFirstName());
			res.append(" ");
			res.append(contact.getLastName());
		}
		return res.toString();
	}

	private void addOptionLinks(final DataGrid<IDataSource<Contact>, Contact, String> grid)
	{

		add(new Link<Void>("cleanSelectionOnPageChangeOn")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				grid.setCleanSelectionOnPageChange(true);
			}

			@Override
			public boolean isEnabled()
			{
				return !grid.isCleanSelectionOnPageChange();
			}
		});

		add(new Link<Void>("cleanSelectionOnPageChangeOff")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				grid.setCleanSelectionOnPageChange(false);
			}

			@Override
			public boolean isEnabled()
			{
				return grid.isCleanSelectionOnPageChange();
			}
		});

		add(new Link<Void>("selectMultipleOn")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				grid.setAllowSelectMultiple(true);
			}

			@Override
			public boolean isEnabled()
			{
				return !grid.isAllowSelectMultiple();
			}
		});

		add(new Link<Void>("selectMultipleOff")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				grid.setAllowSelectMultiple(false);
			}

			@Override
			public boolean isEnabled()
			{
				return grid.isAllowSelectMultiple();
			}
		});

	}

}
