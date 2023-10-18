package org.wicketstuff.egrid.column;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import java.io.Serial;

/**
 * @author Nadeem Mohammad
 */
public abstract class AbstractEditablePropertyColumn<T, S> extends PropertyColumn<T, S> implements IEditableGridColumn {

    @Serial
    private static final long serialVersionUID = 1L;
    private boolean isEditable = true;

    public AbstractEditablePropertyColumn(final IModel<String> displayModel, final String propertyExpression) {
        super(displayModel, propertyExpression);
    }

    public AbstractEditablePropertyColumn(final IModel<String> displayModel, final String propertyExpression, final boolean isEditable) {
        super(displayModel, propertyExpression);
        this.isEditable = isEditable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void populateItem(final Item<ICellPopulator<T>> item, final String componentId, final IModel<T> rowModel) {

        final Item<T> rowItem = ((Item<T>) item.findParent(Item.class));

        if (inEditingMode(rowItem) && isEditable) {
            EditableCellPanel provider = getEditableCellPanel(componentId);
            FormComponent<?> editorComponent = provider.getEditableComponent();
            editorComponent.setDefaultModel((IModel<Object>) getDataModel(rowModel));
            item.add(provider);
        } else {
            super.populateItem(item, componentId, rowModel);
        }
    }

    private boolean inEditingMode(final Item<T> rowItem) {
        return rowItem.getMetaData(EditableGridActionsPanel.EDITING);
    }

    protected void addBehaviors(final FormComponent<T> editorComponent) {

    }
}
