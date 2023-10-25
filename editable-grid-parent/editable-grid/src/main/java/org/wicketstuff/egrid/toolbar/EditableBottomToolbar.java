package org.wicketstuff.egrid.toolbar;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.form.IFormVisitorParticipant;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.egrid.column.AbstractEditablePropertyColumn;
import org.wicketstuff.egrid.column.panel.EditablePanel;
import org.wicketstuff.egrid.column.IEditableColumn;
import org.wicketstuff.egrid.component.EditableDataTable;
import org.wicketstuff.egrid.component.EditableTableSubmitLink;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Toolbar that can be used to add new rows.
 *
 * @param <T> the type of the data in the table.
 * @param <S> the type of the sorting parameter
 * @author Nadeem Mohammad
 */
public abstract class EditableBottomToolbar<T extends Serializable, S> extends AbstractEditableToolbar {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(EditableBottomToolbar.class.getName());

    private Model<T> rowModel;


    /**
     * Constructor
     *
     * @param table data table this toolbar will be attached to
     * @param clazz class of type T
     */
    public EditableBottomToolbar(final EditableDataTable<?, ?> table, final Class<T> clazz) {
        super(table);

        createNewInstance(clazz, null);

        var addToolBarForm = new AddToolBarForm("addToolbarForm");
        add(newAddButton("add", addToolBarForm), addToolBarForm);
    }

    /**
     * Creates a new instance for the rowModel object.
     *
     * @param clazz  class of type T
     * @param target optional AjaxRequestTarget used for error messages
     */
    private void createNewInstance(final Class<T> clazz, final AjaxRequestTarget target) {
        try {
            rowModel = Model.of(clazz.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | NoSuchMethodException | SecurityException e) {
            LOGGER.warning(e.toString());
            error(getInstantiationErrorMessage(clazz));
            if (target != null) {
                onError(target);
            }
        }
    }

    /**
     * @param id        the markup id for the button
     * @param container the container where the button will be placed into
     * @return new 'Add' button as EditableGridSubmitLink
     */
    protected EditableTableSubmitLink newAddButton(final String id, final WebMarkupContainer container) {
        return new EditableTableSubmitLink(id, container) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSuccess(final AjaxRequestTarget target) {
                onAdd(target, rowModel);
                createNewInstance(rowModel.getObjectClass(), target);
                target.add(getTable());
            }

            @Override
            protected void onError(final AjaxRequestTarget target) {
                EditableBottomToolbar.this.onError(target);
            }
        };
    }

    protected abstract void onAdd(AjaxRequestTarget target, Model<T> rowModel);

    protected void onError(final AjaxRequestTarget target) {
    }

    /**
     * Creates a container with different inputs that will be displayed in the toolbar
     * and used for the creation of a new row.
     *
     * @param id the markup id for the container
     * @return container with different input components
     */
    protected WebMarkupContainer newEditorComponents(final String id) {
        List<? extends IColumn<?, ?>> columns = getTable().getColumns();
        return new Loop(id, columns.size() - 1) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final LoopItem item) {
                var column = columns.get(item.getIndex());
                item.add((column instanceof IEditableColumn) ? getColumnCellPanel("cell", (IEditableColumn) column) : new WebMarkupContainer("cell"));
            }
        };
    }

    /**
     * Gets the cell panel of an {@code editableColumn} that mostly contains an input field.
     *
     * @param id             the markup id for the cell panel
     * @param editableColumn an editable column of the data table
     * @return the cell panel
     */
    protected Component getColumnCellPanel(final String id, final IEditableColumn editableColumn) {
        EditablePanel panel = editableColumn.createEditablePanel(id);

        if (editableColumn instanceof AbstractEditablePropertyColumn<?, ?> propertyColumn) {
            FormComponent<?> editorComponent = panel.getEditableComponent();
            editorComponent.setDefaultModel(PropertyModel.<T>of(rowModel, propertyColumn.getPropertyExpression()));
        }

        return panel;
    }

    /**
     * Can be overridden to customize the error message of an instantiation failure in {@link #createNewInstance}.
     *
     * @param clazz class of type T
     * @return error message as String
     */
    protected String getInstantiationErrorMessage(final Class<T> clazz) {
        return "Could not instantiate Class " + clazz.getName();
    }

    private class AddToolBarForm extends Form<T> implements IFormVisitorParticipant {

        public AddToolBarForm(final String id) {
            super(id);
            add(newEditorComponents("td"));
        }

        @Override
        public boolean processChildren() {
            IFormSubmitter submitter = getRootForm().findSubmitter();
            return submitter != null && submitter.getForm().equals(this);
        }
    }
}
