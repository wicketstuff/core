package org.wicketstuff.egrid.column;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.wicketstuff.egrid.column.panel.EditablePanel;
import org.wicketstuff.egrid.column.panel.TextFieldPanel;

import java.io.Serial;

/**
 * AbstractEditablePropertyColumn with a TextFieldPanel
 *
 * @param <T> the model object type
 * @param <S> the type of the sorting parameter
 * @author Nadeem Mohammad
 */
public class TextFieldColumn<T, S> extends AbstractEditablePropertyColumn<T, S> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     */
    public TextFieldColumn(final IModel<String> displayModel, final String propertyExpression) {
        super(displayModel, propertyExpression);
    }

    /**
     * Constructor
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     * @param sortProperty       the sort property
     */
    public TextFieldColumn(final IModel<String> displayModel, final String propertyExpression, final S sortProperty) {
        super(displayModel, propertyExpression, sortProperty);
    }

    @Override
    public EditablePanel createEditablePanel(final String componentId) {
        return new TextFieldPanel<>(componentId, this) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected boolean isInputNullable() {
                return TextFieldColumn.this.isInputNullable();
            }

            @Override
            protected IConverter<? extends T> newObjectConverter(final Class<?> type) {
                return TextFieldColumn.this.newObjectConverter(type);
            }
        };
    }

    /**
     * @return True if the TextField's input can be null. Returns false by default.
     * @see org.apache.wicket.markup.html.form.TextField#isInputNullable()
     */
    protected boolean isInputNullable() {
        return false;
    }

    /**
     * Override to create a new IConverter for the TextField of TextFieldPanel.
     *
     * @param type the type to convert to
     * @return a new IConverter
     * @see org.apache.wicket.util.convert.IConverter
     */
    protected IConverter<? extends T> newObjectConverter(final Class<?> type) {
        return null;
    }
}
