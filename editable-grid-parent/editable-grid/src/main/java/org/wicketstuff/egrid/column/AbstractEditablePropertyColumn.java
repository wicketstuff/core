package org.wicketstuff.egrid.column;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import java.io.Serial;

import static org.wicketstuff.egrid.component.EditableDataTable.EDITING;

/**
 * An extension of the PropertyColumn that often shows an input field if the row is switched into edit mode.
 *
 * @param <T> the model object type
 * @param <S> the type of the sorting parameter
 * @author Nadeem Mohammad
 */
public abstract class AbstractEditablePropertyColumn<T, S> extends PropertyColumn<T, S> implements IEditableColumn {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Creates a non-sortable editable property column.
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     */
    public AbstractEditablePropertyColumn(final IModel<String> displayModel, final String propertyExpression) {
        super(displayModel, propertyExpression);
    }

    /**
     * Creates an editable property column that is also sortable
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     * @param sortProperty       the sort property
     */
    public AbstractEditablePropertyColumn(final IModel<String> displayModel, final String propertyExpression, final S sortProperty) {
        super(displayModel, sortProperty, propertyExpression);
    }

    /**
     * Implementation of populateItem
     * If the row is not currently in edit mode or editing is disallowed in this column,
     * a label is added to the cell whose model is the provided property expression evaluated against rowModel,
     * otherwise an input field is placed in the cell.
     *
     * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(Item, String, IModel)
     */
    @SuppressWarnings("unchecked")
    @Override
    public final void populateItem(final Item<ICellPopulator<T>> item, final String componentId, final IModel<T> rowModel) {

        Item<T> rowItem = ((Item<T>) item.findParent(Item.class));

        Boolean currentlyEditing = rowItem.getMetaData(EDITING);

        if (currentlyEditing == null || !currentlyEditing || !allowEdit()) {
            item.add(newCellContent(componentId, rowModel));
            return;
        }

        var editablePanel = createEditablePanel(componentId);
        var editableComponent = editablePanel.getEditableComponent();

        editableComponent.setDefaultModel(getDataModel(rowModel));
        addBehaviors(editableComponent);

        item.add(editablePanel);
    }

    /**
     * This method can be overridden to customize the content that is rendered by default as a label.
     *
     * @param componentId the id of the component used to render the cell
     * @param rowModel    the model of the row being rendered
     * @return a new component rendering the cell
     */
    protected Component newCellContent(final String componentId, final IModel<T> rowModel) {
        return new Label(componentId, getDataModel(rowModel));
    }


    @Override
    public void addBehaviors(final FormComponent<?> editorComponent) {
    }

    /**
     * Override this method in order to disable or enable the editing of this column in some other way.
     *
     * @return boolean value
     */
    protected boolean allowEdit() {
        return true;
    }
}
