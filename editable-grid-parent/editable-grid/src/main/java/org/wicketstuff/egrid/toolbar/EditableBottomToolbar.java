package org.wicketstuff.egrid.toolbar;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.form.IFormVisitorParticipant;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.egrid.column.AbstractEditablePropertyColumn;
import org.wicketstuff.egrid.column.EditableCellPanel;
import org.wicketstuff.egrid.component.EditableDataTable;
import org.wicketstuff.egrid.component.EditableGridSubmitLink;

import java.io.Serial;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadeem Mohammad
 */
public abstract class EditableBottomToolbar<T, S> extends AbstractEditableToolbar {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String CELL_ID = "cell";
    private static final String CELLS_ID = "cells";

    private T newRow = null;

    public EditableBottomToolbar(final EditableDataTable<?, ?> table, final Class<T> clazz) {
        super(table);
        createNewInstance(clazz);
        MarkupContainer td = new WebMarkupContainer("td");
        td.add(new AttributeModifier("colspan", table.getColumns().size() - 1));
        AddToolBarForm addToolBarForm = new AddToolBarForm("addToolbarForm");
        td.add(addToolBarForm);
        add(td);
        add(newAddButton(addToolBarForm));
    }

    protected abstract void onAdd(AjaxRequestTarget target, T newRow);

    protected void onError(final AjaxRequestTarget target) {
    }

    //TODO: use Objenesis instead of the following

    private void createNewInstance(final Class<T> clazz) {
        try {
            newRow = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private Component newAddButton(final WebMarkupContainer encapsulatingContainer) {
        return new EditableGridSubmitLink("add", encapsulatingContainer) {

            @SuppressWarnings("unchecked")
            @Override
            protected void onSuccess(final AjaxRequestTarget target) {
                onAdd(target, newRow);
                createNewInstance((Class<T>) newRow.getClass());
                target.add(getTable());

            }

            @Override
            protected void onError(final AjaxRequestTarget target) {
                EditableBottomToolbar.this.onError(target);
            }
        };
    }

    private Loop newEditorComponents() {
        final List<AbstractEditablePropertyColumn<T, S>> columns = getEditableColumns();
        return new Loop(CELLS_ID, columns.size()) {

            @Override
            protected void populateItem(final LoopItem item) {
                addEditorComponent(item, getEditorColumn(columns, item.getIndex()));
            }
        };
    }

    private void addEditorComponent(final LoopItem item, final AbstractEditablePropertyColumn<T, S> toolBarCell) {
        item.add(newCell(toolBarCell));
    }

    @SuppressWarnings("unchecked")
    private List<AbstractEditablePropertyColumn<T, S>> getEditableColumns() {
        List<AbstractEditablePropertyColumn<T, S>> columns = new ArrayList<>();
        for (IColumn<?, ?> column : getTable().getColumns()) {
            if (column instanceof AbstractEditablePropertyColumn) {
                columns.add((AbstractEditablePropertyColumn<T, S>) column);
            }

        }

        return columns;
    }

    private Component newCell(final AbstractEditablePropertyColumn<T, S> editableGridColumn) {
        EditableCellPanel panel = editableGridColumn.getEditableCellPanel(CELL_ID);
        FormComponent<?> editorComponent = panel.getEditableComponent();
        editorComponent.setDefaultModel(new PropertyModel<T>(newRow, editableGridColumn.getPropertyExpression()));
        return panel;
    }

    private AbstractEditablePropertyColumn<T, S> getEditorColumn(final List<AbstractEditablePropertyColumn<T, S>> editorColumn, int index) {
        return editorColumn.get(index);
    }

    private class AddToolBarForm extends Form<T> implements IFormVisitorParticipant {

        public AddToolBarForm(final String id) {
            super(id);
            add(newEditorComponents());
        }

        @Override
        public boolean processChildren() {
            IFormSubmitter submitter = getRootForm().findSubmitter();
            return submitter != null && submitter.getForm() == this;
        }
    }
}
