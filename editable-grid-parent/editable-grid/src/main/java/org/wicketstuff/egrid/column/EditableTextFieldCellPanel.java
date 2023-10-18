package org.wicketstuff.egrid.column;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;

import java.io.Serial;

/**
 * @author Nadeem Mohammad
 */
public class EditableTextFieldCellPanel<T, S> extends EditableCellPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    public EditableTextFieldCellPanel(final String id, final PropertyColumn<T, S> column) {
        super(id);

        TextField<T> field = new TextField<T>("textfield");
        field.setOutputMarkupId(true);
        field.setLabel(column.getDisplayModel());
        addBehaviors(field);
        add(field);
    }

    @SuppressWarnings("unchecked")
    public FormComponent<T> getEditableComponent() {
        return (FormComponent<T>) get("textfield");
    }

    protected void addBehaviors(final FormComponent<T> formComponent) {

    }
}
