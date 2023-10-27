package org.wicketstuff.egrid.column.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.wicketstuff.egrid.component.EditableTableSubmitLink;

import java.io.Serial;

import static org.wicketstuff.egrid.component.EditableDataTable.EDITING;

/**
 * A panel containing different buttons to be used for performing different actions on the row.
 *
 * @param <T> the type of the row data
 * @author Nadeem Mohammad
 */
public abstract class ActionsPanel<T> extends Panel {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Item<T> rowItem;

    /**
     * Constructor
     *
     * @param id      the component id
     * @param rowItem the row this panel is attached to
     */
    public ActionsPanel(final String id, final Item<T> rowItem) {
        super(id, rowItem.getModel());
        this.rowItem = rowItem;

        add(newEditLink("edit"), newCancelLink("cancel"), newSaveLink("save"), newDeleteLink("delete"));
    }

    /**
     * Creates an AjaxLink that is bound to the html button with the id "{@code edit}"
     * and calls {@code onEdit} when clicked.
     * It is not visible if editing is disallowed by {@code allowEdit} or the row is in edit mode.
     *
     * @param id the component id used for the AjaxLink
     * @return the AjaxLink
     * @see org.apache.wicket.ajax.markup.html.AjaxLink
     */
    protected AjaxLink<Void> newEditLink(final String id) {
        return new AjaxLink<>(id) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(allowEdit(rowItem) && !isRowInEditMode(rowItem));
            }

            @Override
            public void onClick(final AjaxRequestTarget target) {
                onEdit(target);
            }
        };
    }

    /**
     * Creates an AjaxLink that is bound to the html button with the id "{@code cancel}"
     * and calls {@code onCancel} when clicked.
     * It is not visible if editing is disallowed by {@code allowEdit} or the row is not in edit mode.
     *
     * @param id the component id used for the AjaxLink
     * @return the AjaxLink
     * @see org.apache.wicket.ajax.markup.html.AjaxLink
     */
    protected AjaxLink<Void> newCancelLink(final String id) {
        return new AjaxLink<>(id) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(allowEdit(rowItem) && isRowInEditMode(rowItem));
            }

            @Override
            public void onClick(final AjaxRequestTarget target) {
                onCancel(target);
            }
        };
    }

    /**
     * Creates an AjaxLink that is bound to the html button with the id "{@code save}"
     * and calls {@code onSave} when clicked.
     * It is not visible if editing is disallowed by {@code allowEdit} or the row is not in edit mode.
     *
     * @param id the component id used for the EditableTableSubmitLink
     * @return the EditableTableSubmitLink
     * @see org.wicketstuff.egrid.component.EditableTableSubmitLink
     */
    protected EditableTableSubmitLink newSaveLink(final String id) {
        return new EditableTableSubmitLink(id, rowItem) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(allowEdit(rowItem) && isRowInEditMode(rowItem));
            }

            @Override
            protected void onSuccess(final AjaxRequestTarget target) {
                onSave(target);
            }

            @Override
            protected void onError(final AjaxRequestTarget target) {
                ActionsPanel.this.onError(target);
            }
        };
    }

    /**
     * Creates an AjaxLink that is bound to the html button with the id "{@code delete}"
     * and calls {@code onDelete} when clicked.
     * It is not visible if deleting is disallowed by {@code allowDelete} or the row is in edit mode.
     *
     * @param id the component id used for the AjaxLink
     * @return the AjaxLink
     * @see org.apache.wicket.ajax.markup.html.AjaxLink
     */
    protected AjaxLink<Void> newDeleteLink(final String id) {
        return new AjaxLink<>(id) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(allowDelete(rowItem) && !isRowInEditMode(rowItem));
            }

            @Override
            public void onClick(final AjaxRequestTarget target) {
                onDelete(target);
            }
        };
    }

    /**
     * @param rowItem current row
     * @return {@code true} if the row is currently in edit mode
     */
    protected boolean isRowInEditMode(final Item<T> rowItem) {
        Boolean editing = rowItem.getMetaData(EDITING);
        return editing != null && editing;
    }

    /**
     * Listener method invoked when the edit button gets clicked.
     *
     * @param target
     */
    protected void onEdit(final AjaxRequestTarget target) {
    }

    /**
     * Listener method invoked when the cancel button gets clicked.
     *
     * @param target
     */
    protected void onCancel(final AjaxRequestTarget target) {
    }

    /**
     * Listener method invoked when the save button gets clicked and the user input is valid.
     *
     * @param target
     */
    protected void onSave(final AjaxRequestTarget target) {
    }

    /**
     * Listener method invoked when the user input is invalid during a save attempt.
     *
     * @param target
     */
    protected abstract void onError(AjaxRequestTarget target);

    /**
     * Listener method invoked when the delete button gets clicked.
     *
     * @param target
     */
    protected void onDelete(final AjaxRequestTarget target) {
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
