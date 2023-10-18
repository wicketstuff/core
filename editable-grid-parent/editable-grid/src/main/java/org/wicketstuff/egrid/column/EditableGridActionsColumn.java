package org.wicketstuff.egrid.column;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import java.io.Serial;

/**
 * @author Nadeem Mohammad
 */
public class EditableGridActionsColumn<T, S> extends PropertyColumn<T, S> {

    @Serial
    private static final long serialVersionUID = 1L;

    public EditableGridActionsColumn(final IModel<String> displayModel) {
        super(displayModel, "");
    }

    @Override
    public void populateItem(final Item<ICellPopulator<T>> item, final String componentId, final IModel<T> rowModel) {

        item.add(new EditableGridActionsPanel<T>(componentId, item) {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSave(final AjaxRequestTarget target) {
                EditableGridActionsColumn.this.onSave(target, rowModel);
            }

            @Override
            protected void onError(final AjaxRequestTarget target) {
                EditableGridActionsColumn.this.onError(target, rowModel);
            }

            @Override
            protected void onCancel(final AjaxRequestTarget target) {
                EditableGridActionsColumn.this.onCancel(target);
            }

            @Override
            protected void onDelete(final AjaxRequestTarget target) {
                EditableGridActionsColumn.this.onDelete(target, rowModel);
            }

            @Override
            protected boolean allowDelete(final Item<T> rowItem) {
                return EditableGridActionsColumn.this.allowDelete(rowItem);
            }

        });
    }

    protected boolean allowDelete(final Item<T> rowItem) {
        return true;
    }

    protected void onDelete(final AjaxRequestTarget target, final IModel<T> rowModel) {

    }

    protected void onSave(final AjaxRequestTarget target, final IModel<T> rowModel) {

    }

    protected void onError(final AjaxRequestTarget target, final IModel<T> rowModel) {

    }

    protected void onCancel(final AjaxRequestTarget target) {

    }
}
