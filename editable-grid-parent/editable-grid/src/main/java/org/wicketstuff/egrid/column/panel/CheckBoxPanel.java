package org.wicketstuff.egrid.column.panel;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.convert.IConverter;

import java.io.Serial;

/**
 * An EditablePanel with a CheckBox
 *
 * @author Silas Porth
 * @see org.apache.wicket.markup.html.form.CheckBox
 */
public class CheckBoxPanel extends EditablePanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final CheckBox checkBox;

    /**
     * Constructor
     *
     * @param id     the component id
     * @param column the column in which this panel will be placed in
     */
    public CheckBoxPanel(final String id, final AbstractColumn<?, ?> column) {
        super(id);

        checkBox = new CheckBox("checkBox") {
            @Override
            protected IConverter<?> createConverter(final Class<?> type) {
                var converter = newObjectConverter(type);
                return (converter != null) ? converter : super.createConverter(type);
            }
        };
        checkBox.setOutputMarkupId(true);
        checkBox.setLabel(column.getDisplayModel());
        add(checkBox);
    }

    /**
     * Override to create a new IConverter for the {@code dropDownChoice}.
     *
     * @param type the type to convert to
     * @return a new IConverter
     * @see org.apache.wicket.util.convert.IConverter
     */
    protected IConverter<Boolean> newObjectConverter(final Class<?> type) {
        return null;
    }

    @Override
    public FormComponent<?> getEditableComponent() {
        return checkBox;
    }
}
