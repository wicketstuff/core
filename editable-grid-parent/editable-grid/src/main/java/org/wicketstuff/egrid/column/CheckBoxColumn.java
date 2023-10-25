package org.wicketstuff.egrid.column;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.wicketstuff.egrid.column.panel.CheckBoxPanel;
import org.wicketstuff.egrid.column.panel.EditablePanel;

import java.io.Serial;

/**
 * AbstractEditablePropertyColumn with a CheckBoxPanel
 *
 * @param <T> the model object type
 * @param <S> the type of the sorting parameter
 * @author Silas Porth
 */
public class CheckBoxColumn<T, S> extends AbstractEditablePropertyColumn<T, S> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     */
    public CheckBoxColumn(final IModel<String> displayModel, final String propertyExpression) {
        super(displayModel, propertyExpression);
    }

    /**
     * Constructor
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     * @param sortProperty       the sort property
     */
    public CheckBoxColumn(final IModel<String> displayModel, final String propertyExpression, final S sortProperty) {
        super(displayModel, propertyExpression, sortProperty);
    }

    @Override
    public EditablePanel createEditablePanel(final String componentId) {
        return new CheckBoxPanel(componentId, this) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected IConverter<Boolean> newObjectConverter(final Class<?> type) {
                return CheckBoxColumn.this.newObjectConverter(type);
            }
        };
    }

    /**
     * Override to create a new IConverter for the CheckBox of CheckBoxPanel.
     *
     * @param type the type to convert to
     * @return a new IConverter
     * @see org.apache.wicket.util.convert.IConverter
     */
    protected IConverter<Boolean> newObjectConverter(final Class<?> type) {
        return null;
    }
}
