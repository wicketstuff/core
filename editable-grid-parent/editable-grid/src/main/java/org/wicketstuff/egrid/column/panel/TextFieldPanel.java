package org.wicketstuff.egrid.column.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.convert.IConverter;

import java.io.Serial;

/**
 * An EditablePanel with a TextField
 *
 * @param <T> the model object type
 * @author Nadeem Mohammad
 * @see org.apache.wicket.markup.html.form.TextField
 */
public class TextFieldPanel<T> extends EditablePanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private final TextField<T> textField;

    /**
     * Constructor
     *
     * @param id     the component id
     * @param column the column in which this panel will be placed in
     */
    public TextFieldPanel(final String id, final AbstractColumn<T, ?> column) {
        super(id);

        textField = new TextField<>("textField") {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isInputNullable() {
                return TextFieldPanel.this.isInputNullable();
            }

            @Override
            protected IConverter<?> createConverter(final Class<?> type) {
                return TextFieldPanel.this.newObjectConverter(type);
            }
        };
        textField.setOutputMarkupId(true);
        textField.setLabel(column.getDisplayModel());
        add(new AttributeModifier("placeholder", column.getDisplayModel()));
        add(textField);
    }

    /**
     * @return True if this TextField's input can be null. Returns false by default.
     * @see org.apache.wicket.markup.html.form.TextField#isInputNullable()
     */
    protected boolean isInputNullable() {
        return false;
    }

    /**
     * Override to create a new IConverter for the {@code textField}.
     *
     * @param type the type to convert to
     * @return a new IConverter
     * @see org.apache.wicket.util.convert.IConverter
     */
    protected IConverter<? extends T> newObjectConverter(final Class<?> type) {
        return null;
    }

    @Override
    public FormComponent<T> getEditableComponent() {
        return textField;
    }
}
