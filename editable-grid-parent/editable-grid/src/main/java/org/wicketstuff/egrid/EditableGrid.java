package org.wicketstuff.egrid;

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
import org.wicketstuff.egrid.toolbar.EditableBottomToolbar;
import org.wicketstuff.egrid.toolbar.EditableHeadersToolbar;
import org.wicketstuff.egrid.toolbar.EditableNavigationToolbar;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadeem Mohammad
 */
public class EditableGrid<T, S> extends Panel {

    @Serial
    private static final long serialVersionUID = 1L;

    private EditableDataTable<T, S> dataTable;

    public EditableGrid(final String id, final List<? extends IColumn<T, S>> columns, final IEditableDataProvider<T, S> dataProvider, final long rowsPerPage, final Class<T> clazz) {
        super(id);
        List<IColumn<T, S>> newCols = new ArrayList<>(columns);
        newCols.add(newActionsColumn());

        add(buildForm(newCols, dataProvider, rowsPerPage, clazz));
    }

    private Component buildForm(final List<? extends IColumn<T, S>> columns, final IEditableDataProvider<T, S> dataProvider, final long rowsPerPage, final Class<T> clazz) {
        Form<T> form = new NonValidatingForm<>("form");
        form.setOutputMarkupId(true);
        this.dataTable = newDataTable(columns, dataProvider, rowsPerPage, clazz);
        form.add(this.dataTable);
        return form;
    }

    public final EditableGrid<T, S> setTableBodyCss(final String cssStyle) {
        this.dataTable.setTableBodyCss(cssStyle);
        return this;
    }

    public final EditableGrid<T, S> setTableCss(final String cssStyle) {
        this.dataTable.add(AttributeModifier.replace("class", cssStyle));
        return this;
    }

    private EditableDataTable<T, S> newDataTable(final List<? extends IColumn<T, S>> columns, final IEditableDataProvider<T, S> dataProvider, final long rowsPerPage, final Class<T> clazz) {
        final EditableDataTable<T, S> dataTable = new EditableDataTable<T, S>("dataTable", columns, dataProvider, rowsPerPage) {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected Item<T> newRowItem(final String id, final int index, final IModel<T> model) {
                return EditableGrid.this.newRowItem(id, index, model);
            }
        };

        dataTable.setOutputMarkupId(true);

        dataTable.addTopToolbar(new EditableNavigationToolbar(dataTable));
        dataTable.addTopToolbar(new EditableHeadersToolbar<T, S>(dataTable, dataProvider));
        if (displayAddFeature()) {
            dataTable.addBottomToolbar(newAddBottomToolbar(dataProvider, clazz, dataTable));
        }

        return dataTable;
    }

    protected RowItem<T> newRowItem(final String id, final int index, final IModel<T> model) {
        return new RowItem<T>(id, index, model, dataTable.getMarkupId());
    }

    private EditableBottomToolbar<T, S> newAddBottomToolbar(final IEditableDataProvider<T, S> dataProvider, final Class<T> clazz, final EditableDataTable<T, S> dataTable) {
        return new EditableBottomToolbar<T, S>(dataTable, clazz) {

            @Override
            protected void onAdd(final AjaxRequestTarget target, final T newRow) {
                dataProvider.add(newRow);
                target.add(dataTable);
                EditableGrid.this.onAdd(target, newRow);
            }

            @Override
            protected void onError(final AjaxRequestTarget target) {
                super.onError(target);
                EditableGrid.this.onError(target);
            }

        };
    }

    private EditableGridActionsColumn<T, S> newActionsColumn() {
        return new EditableGridActionsColumn<T, S>(new Model<String>("Actions")) {

            @Override
            protected void onError(final AjaxRequestTarget target, final IModel<T> rowModel) {
                EditableGrid.this.onError(target);
            }

            @Override
            protected void onSave(final AjaxRequestTarget target, final IModel<T> rowModel) {
                EditableGrid.this.onSave(target, rowModel);
            }

            @Override
            protected void onDelete(final AjaxRequestTarget target, final IModel<T> rowModel) {
                EditableGrid.this.onDelete(target, rowModel);
            }

            @Override
            protected void onCancel(final AjaxRequestTarget target) {
                EditableGrid.this.onCancel(target);
            }

            @Override
            protected boolean allowDelete(final Item<T> rowItem) {
                return EditableGrid.this.allowDelete(rowItem);
            }
        };
    }

    protected boolean allowDelete(final Item<T> rowItem) {
        return true;
    }

    protected void onCancel(final AjaxRequestTarget target) {
    }

    protected void onDelete(final AjaxRequestTarget target, final IModel<T> rowModel) {
    }

    protected void onSave(final AjaxRequestTarget target, final IModel<T> rowModel) {
    }

    protected void onError(final AjaxRequestTarget target) {
    }

    protected void onAdd(final AjaxRequestTarget target, final T newRow) {
    }

    protected boolean displayAddFeature() {
        return true;
    }

    private static class NonValidatingForm<T> extends Form<T> {
        @Serial
        private static final long serialVersionUID = 1L;

        public NonValidatingForm(final String id) {
            super(id);
        }

        @Override
        public void process(final IFormSubmitter submittingComponent) {
            delegateSubmit(submittingComponent);
        }

    }
}
