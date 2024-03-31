package org.wicketstuff.egrid;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.wicketstuff.egrid.column.EditableActionsColumn;
import org.wicketstuff.egrid.component.EditableDataTable;
import org.wicketstuff.egrid.component.EditableDataTable.RowItem;
import org.wicketstuff.egrid.provider.IEditableDataProvider;
import org.wicketstuff.egrid.toolbar.AddRowToolbar;
import org.wicketstuff.egrid.toolbar.HeadersToolbar;
import org.wicketstuff.egrid.toolbar.NavigationToolbar;
import org.wicketstuff.egrid.toolbar.NoRecordsToolbar;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Example usage of the EditableDataTable
 *
 * @param <T> the model object type
 * @param <S> the type of the sorting parameter
 * @author Nadeem Mohammad
 * @see org.wicketstuff.egrid.component.EditableDataTable
 */
public class EditableGrid<T extends Serializable, S> extends Panel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final EditableDataTable<T, S> dataTable;
    private final IEditableDataProvider<T, S> dataProvider;

    /**
     * Constructor
     *
     * @param id           the component id
     * @param columns      the list of IColumn objects
     * @param dataProvider an {@link org.wicketstuff.egrid.provider.IEditableDataProvider}
     * @param rowsPerPage  the number of rows per page
     * @param clazz        the class of type T
     */
    public EditableGrid(final String id, final List<? extends IColumn<T, S>> columns, final IEditableDataProvider<T, S> dataProvider, final long rowsPerPage, final Class<T> clazz) {
        super(id);
        this.dataProvider = dataProvider;

        List<IColumn<T, S>> newCols = new ArrayList<>(columns);
        newCols.add(newActionsColumn(new StringResourceModel("editableGrid.actionsColumn.headerText")));

        Form<T> form = createForm("form");
        dataTable = newDataTable("dataTable", newCols, dataProvider, rowsPerPage, clazz);

        form.add(dataTable);
        add(form);
    }

    /**
     * Factory method for {@link org.wicketstuff.egrid.column.EditableActionsColumn}.
     *
     * @param stringModel the text for the header
     * @return the new EditableActionsColumn
     */
    protected EditableActionsColumn<T, S> newActionsColumn(final IModel<String> stringModel) {
        return new EditableActionsColumn<>(stringModel) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void onEdit(final AjaxRequestTarget target, final IModel<T> rowModel) {
                super.onEdit(target, rowModel);
                EditableGrid.this.onEdit(target, rowModel);
            }

            @Override
            protected void onCancel(final AjaxRequestTarget target, final IModel<T> rowModel) {
                super.onCancel(target, rowModel);
                EditableGrid.this.onCancel(target, rowModel);
            }

            @Override
            protected void onSave(final AjaxRequestTarget target, final IModel<T> rowModel) {
                super.onSave(target, rowModel);
                EditableGrid.this.onSave(target, rowModel);
            }

            @Override
            protected void onError(final AjaxRequestTarget target, final IModel<T> rowModel) {
                super.onError(target, rowModel);
                EditableGrid.this.onError(target, rowModel);
            }

            @Override
            protected void onDelete(final AjaxRequestTarget target, final IModel<T> rowModel) {
                super.onDelete(target, rowModel);
                EditableGrid.this.onDelete(target, rowModel);
            }

            @Override
            protected boolean allowEdit(final Item<T> rowItem) {
                return EditableGrid.this.allowEdit(rowItem);
            }

            @Override
            protected boolean allowDelete(final Item<T> rowItem) {
                return EditableGrid.this.allowDelete(rowItem);
            }
        };
    }

    /**
     * Creates a new form which is used for the data table
     *
     * @param id the component id
     * @return the form
     * @see org.wicketstuff.egrid.component.EditableDataTable
     */
    protected Form<T> createForm(final String id) {
        return new NonValidatingForm(id);
    }

    /**
     * Creates a new {@link org.wicketstuff.egrid.component.EditableDataTable}
     * with the four toolbars
     * {@link org.wicketstuff.egrid.toolbar.NavigationToolbar},
     * {@link org.wicketstuff.egrid.toolbar.HeadersToolbar},
     * {@link org.wicketstuff.egrid.toolbar.NoRecordsToolbar}
     * and {@link org.wicketstuff.egrid.toolbar.AddRowToolbar}.
     *
     * @param id           the component id
     * @param columns      the list of IColumn objects
     * @param dataProvider an {@link org.wicketstuff.egrid.provider.IEditableDataProvider}
     * @param rowsPerPage  the number of rows per page
     * @param clazz        the class of type T
     * @return the editable data table
     */
    protected EditableDataTable<T, S> newDataTable(final String id, final List<? extends IColumn<T, S>> columns, final IEditableDataProvider<T, S> dataProvider, final long rowsPerPage, final Class<T> clazz) {
        EditableDataTable<T, S> dataTable = new EditableDataTable<>(id, columns, dataProvider, rowsPerPage) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected Item<T> newRowItem(final String id, final int index, final IModel<T> model) {
                return EditableGrid.this.newRowItem(id, index, model);
            }
        };

        dataTable.addTopToolbar(new NavigationToolbar(dataTable));
        dataTable.addTopToolbar(new HeadersToolbar<>(dataTable, dataProvider));
        dataTable.addBottomToolbar(new NoRecordsToolbar(dataTable));
        if (allowAdd()) {
            dataTable.addBottomToolbar(newAddRowToolbar(dataTable, clazz));
        }

        return dataTable;
    }

    /**
     * Factory method for Item container that represents a row in the underlying DataGridView.
     *
     * @param id    the component id for the new data item
     * @param index the index of the new data item
     * @param model the model for the new data item.
     * @return the created DataItem
     * @see org.apache.wicket.markup.repeater.Item
     * @see org.wicketstuff.egrid.component.EditableDataTable.RowItem
     */
    protected Item<T> newRowItem(final String id, final int index, final IModel<T> model) {
        return new RowItem<>(id, index, model, dataTable.getMarkupId());
    }

    /**
     * Factory method for {@link org.wicketstuff.egrid.toolbar.AddRowToolbar}.
     *
     * @param dataTable the data table this toolbar will be attached to
     * @param clazz     the class of type T
     * @return the new AddRowToolbar
     */
    protected AddRowToolbar<T> newAddRowToolbar(final EditableDataTable<T, S> dataTable, final Class<T> clazz) {
        return new AddRowToolbar<>(dataTable, clazz) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onAdd(final AjaxRequestTarget target, final IModel<T> rowModel) {
                EditableGrid.this.onAdd(target, rowModel);
            }

            @Override
            protected void onError(final AjaxRequestTarget target) {
                super.onError(target);
                EditableGrid.this.onError(target, null);
            }
        };
    }

    /**
     * Listener method invoked when a new row gets added.
     *
     * @param target
     * @param rowModel the data model of the row
     */
    protected void onAdd(final AjaxRequestTarget target, final IModel<T> rowModel) {
        dataProvider.add(rowModel.getObject());
        target.add(dataTable);
    }

    /**
     * Listener method invoked when the edit button in a row gets clicked.
     *
     * @param target
     * @param rowModel the data model of the row
     */
    protected void onEdit(final AjaxRequestTarget target, final IModel<T> rowModel) {
        target.add(dataTable.getTopToolbars());
    }

    /**
     * Listener method invoked when the cancel button in a row gets clicked.
     * The edited row items always get refreshed in {@link org.wicketstuff.egrid.component.EditableDataTable#onEvent}.
     *
     * @param target
     * @param rowModel the data model of the row
     */
    protected void onCancel(final AjaxRequestTarget target, final IModel<T> rowModel) {
        if (!dataTable.isCurrentlyAnyEdit()) {
            target.add(dataTable);
        }
    }

    /**
     * Listener method invoked when the save button in a row gets clicked and the user input is valid.
     * The edited row items always get refreshed in {@link org.wicketstuff.egrid.component.EditableDataTable#onEvent}.
     *
     * @param target
     * @param rowModel the data model of the row
     */
    protected void onSave(final AjaxRequestTarget target, final IModel<T> rowModel) {
        dataProvider.update(rowModel.getObject());
        if (!dataTable.isCurrentlyAnyEdit()) {
            target.add(dataTable);
        }
    }

    /**
     * Listener method invoked when the user input is invalid during a save attempt.
     *
     * @param target
     * @param rowModel the data model of the row
     */
    protected void onError(final AjaxRequestTarget target, final IModel<T> rowModel) {
    }

    /**
     * Listener method invoked when the delete button in a row gets clicked.
     *
     * @param target
     * @param rowModel the data model of the row
     */
    protected void onDelete(final AjaxRequestTarget target, final IModel<T> rowModel) {
        dataProvider.remove(rowModel.getObject());
        target.add(dataTable);
    }

    /**
     * Override this method in order to disable or enable adding in some other way.
     *
     * @return boolean value
     */
    protected boolean allowAdd() {
        return true;
    }

    /**
     * Override this method in order to disable or enable editing in some other way.
     *
     * @param rowItem current row
     * @return boolean value
     */
    protected boolean allowEdit(final Item<T> rowItem) {
        return true;
    }

    /**
     * Override this method in order to disable or enable deleting in some other way.
     *
     * @param rowItem current row
     * @return boolean value
     */
    protected boolean allowDelete(final Item<T> rowItem) {
        return true;
    }

    /**
     * @return the editable data table
     */
    public EditableDataTable<T, S> getDataTable() {
        return dataTable;
    }

    /**
     * @return the data provider
     */
    public IEditableDataProvider<T, S> getDataProvider() {
        return dataProvider;
    }

    /**
     * This form encapsulates all rows to allow the submission of edits.
     * Validation is controlled by each row's EditableTableSubmitLink, not by the form,
     * so multiple rows can be in edit simultaneously.
     */
    private class NonValidatingForm extends Form<T> {
        @Serial
        private static final long serialVersionUID = 1L;

        public NonValidatingForm(final String id) {
            super(id);
            setOutputMarkupId(true);
        }

        @Override
        public void process(final IFormSubmitter submittingComponent) {
            delegateSubmit(submittingComponent);
        }
    }
}
