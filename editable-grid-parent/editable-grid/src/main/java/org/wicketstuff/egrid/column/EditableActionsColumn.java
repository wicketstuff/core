package org.wicketstuff.egrid.column;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.wicketstuff.egrid.column.panel.ActionsPanel;

import java.io.Serial;

import static org.wicketstuff.egrid.component.EditableDataTable.EDITING;

/**
 * Wrapper column for ActionsPanel
 *
 * @param <T> the model object type
 * @param <S> the type of the sorting parameter
 * @author Nadeem Mohammad
 * @see org.wicketstuff.egrid.column.panel.ActionsPanel
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn
 */
public class EditableActionsColumn<T, S> extends AbstractColumn<T, S> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @param displayModel model used to generate header text
     */
    public EditableActionsColumn(final IModel<String> displayModel) {
        super(displayModel);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void populateItem(final Item<ICellPopulator<T>> item, final String componentId, final IModel<T> rowModel) {
        Item<T> rowItem = item.findParent(Item.class);
        item.add(newActionsPanel(componentId, rowItem));
    }

    /**
     * Factory method used to create a new ActionsPanel for a row.
     *
     * @param rowItem     the row the ActionsPanel will be attached to
     * @param componentId the component id for the panel
     * @return a new ActionsPanel
     */
    protected ActionsPanel<T> newActionsPanel(final String componentId, final Item<T> rowItem) {
        return new ActionsPanel<>(componentId, rowItem) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onEdit(final AjaxRequestTarget target) {
                super.onEdit(target);
                rowItem.setMetaData(EDITING, true);
                send(getPage(), Broadcast.BREADTH, rowItem);
                target.add(rowItem);
                EditableActionsColumn.this.onEdit(target, rowItem.getModel());
            }

            @Override
            protected void onCancel(final AjaxRequestTarget target) {
                rowItem.setMetaData(EDITING, false);
                send(getPage(), Broadcast.BREADTH, rowItem);
                target.add(rowItem);
                EditableActionsColumn.this.onCancel(target, rowItem.getModel());
            }

            @Override
            protected void onError(final AjaxRequestTarget target) {
                EditableActionsColumn.this.onError(target, rowItem.getModel());
            }

            @Override
            protected void onSave(final AjaxRequestTarget target) {
                rowItem.setMetaData(EDITING, false);
                send(getPage(), Broadcast.BREADTH, rowItem);
                target.add(rowItem);
                EditableActionsColumn.this.onSave(target, rowItem.getModel());
            }

            @Override
            protected void onDelete(final AjaxRequestTarget target) {
                EditableActionsColumn.this.onDelete(target, rowItem.getModel());
            }

            @Override
            protected boolean allowEdit(final Item<T> rowItem) {
                return EditableActionsColumn.this.allowEdit(rowItem);
            }

            @Override
            protected boolean allowDelete(final Item<T> rowItem) {
                return EditableActionsColumn.this.allowDelete(rowItem);
            }
        };
    }

    /**
     * Listener method invoked when the edit button in a row gets clicked.
     *
     * @param target
     * @param rowModel the data model of the row
     */
    public void onEdit(final AjaxRequestTarget target, final IModel<T> rowModel) {
    }

    /**
     * Listener method invoked when the cancel button in a row gets clicked.
     *
     * @param target
     * @param rowModel the data model of the row
     */
    protected void onCancel(final AjaxRequestTarget target, final IModel<T> rowModel) {
    }

    /**
     * Listener method invoked when the save button in a row gets clicked and the user input is valid.
     *
     * @param target
     * @param rowModel the data model of the row
     */
    protected void onSave(final AjaxRequestTarget target, final IModel<T> rowModel) {
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
}
