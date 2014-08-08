package org.wicketstuff.egrid;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.egrid.column.EditableGridActionsColumn;
import org.wicketstuff.egrid.component.EditableDataTable;
import org.wicketstuff.egrid.component.EditableDataTable.RowItem;
import org.wicketstuff.egrid.provider.IEditableDataProvider;
import org.wicketstuff.egrid.toolbar.EditableGridBottomToolbar;
import org.wicketstuff.egrid.toolbar.EditableGridHeadersToolbar;
import org.wicketstuff.egrid.toolbar.EditableGridNavigationToolbar;

/**
 * 
 * @author Nadeem Mohammad
 * 
 */
public class EditableGrid<T, S> extends Panel
{

	private static final long serialVersionUID = 1L;
	
	private EditableDataTable<T, S> dataTable;

	public EditableGrid(final String id, final List<? extends IColumn<T, S>> columns,
		final IEditableDataProvider<T, S> dataProvider, final long rowsPerPage, Class<T> clazz)
	{
		super(id);
		List<IColumn<T, S>> newCols = new ArrayList<IColumn<T, S>>();
		newCols.addAll(columns);
		newCols.add(newActionsColumn());

		add(buildForm(newCols, dataProvider, rowsPerPage, clazz));
	}

	private Component buildForm(final List<? extends IColumn<T, S>> columns,
		final IEditableDataProvider<T, S> dataProvider, long rowsPerPage, Class<T> clazz)
	{
		Form<T> form = new NonValidatingForm<T>("form");
		form.setOutputMarkupId(true);
		this.dataTable = newDataTable(columns, dataProvider, rowsPerPage, clazz);
		form.add(this.dataTable);
		return form;
	}

	public final EditableGrid<T, S> setTableBodyCss(final String cssStyle)
	{
		this.dataTable.setTableBodyCss(cssStyle);
		return this;
	}
	
	public final EditableGrid<T, S> setTableCss(final String cssStyle)
	{
		this.dataTable.add(AttributeModifier.replace("class", cssStyle));
		return this;
	}

	private static class NonValidatingForm<T> extends Form<T>
	{
		private static final long serialVersionUID = 1L;
		public NonValidatingForm(String id)
		{
			super(id);
		}
		@Override
		public void process(IFormSubmitter submittingComponent)
		{
			delegateSubmit(submittingComponent);
		}
		
	}

	private EditableDataTable<T, S> newDataTable(final List<? extends IColumn<T, S>> columns,
		final IEditableDataProvider<T, S> dataProvider, long rowsPerPage, Class<T> clazz)
	{
		final EditableDataTable<T, S> dataTable = new EditableDataTable<T, S>("dataTable", columns,
			dataProvider, rowsPerPage, clazz)
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				EditableGrid.this.onError(target);
			}
			@Override
			protected Item<T> newRowItem(String id, int index, IModel<T> model) {
				return super.newRowItem(id, index, model);
			}
		};

		dataTable.setOutputMarkupId(true);

		dataTable.addTopToolbar(new EditableGridNavigationToolbar(dataTable));
		dataTable.addTopToolbar(new EditableGridHeadersToolbar<T, S>(dataTable, dataProvider));		
		if (displayAddFeature())
		{			
			dataTable.addBottomToolbar(newAddBottomToolbar(dataProvider, clazz, dataTable));
		}

		return dataTable;
	}

	protected RowItem<T> newRowItem(String id, int index, IModel<T> model) {
		return new RowItem<T>(id, index, model);
	}

	private EditableGridBottomToolbar<T, S> newAddBottomToolbar(
			final IEditableDataProvider<T, S> dataProvider, Class<T> clazz,
			final EditableDataTable<T, S> dataTable)
			{
		return new EditableGridBottomToolbar<T, S>(dataTable, clazz)
				{

					private static final long serialVersionUID = 1L;

					@Override
					protected void onAdd(AjaxRequestTarget target, T newRow)
					{
						dataProvider.add(newRow);
						target.add(dataTable);
						EditableGrid.this.onAdd(target, newRow);
					}

					@Override
					protected void onError(AjaxRequestTarget target)
					{
						super.onError(target);
						EditableGrid.this.onError(target);
					}

				};
	}

	private EditableGridActionsColumn<T, S> newActionsColumn()
	{
		return new EditableGridActionsColumn<T, S>(new Model<String>("Actions"))
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, IModel<T> rowModel)
			{
				EditableGrid.this.onError(target);
			}

			@Override
			protected void onSave(AjaxRequestTarget target, IModel<T> rowModel)
			{
				EditableGrid.this.onSave(target, rowModel);
			}

			@Override
			protected void onDelete(AjaxRequestTarget target, IModel<T> rowModel)
			{
				EditableGrid.this.onDelete(target, rowModel);
			}

			@Override
			protected void onCancel(AjaxRequestTarget target)
			{
				EditableGrid.this.onCancel(target);
			}

			@Override
			protected boolean allowDelete(Item<T> rowItem) {
				return EditableGrid.this.allowDelete(rowItem);
			}
		};
	}

	protected boolean allowDelete(Item<T> rowItem) {
		return true;
	}

	protected void onCancel(AjaxRequestTarget target)
	{

	}


	protected void onDelete(AjaxRequestTarget target, IModel<T> rowModel)
	{

	}

	protected void onSave(AjaxRequestTarget target, IModel<T> rowModel)
	{

	}

	protected void onError(AjaxRequestTarget target)
	{

	}

	protected void onAdd(AjaxRequestTarget target, T newRow)
	{

	}
	
	protected boolean displayAddFeature() {
		return true;
	}
}
