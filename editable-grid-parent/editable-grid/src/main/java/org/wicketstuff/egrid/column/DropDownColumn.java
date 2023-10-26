package org.wicketstuff.egrid.column;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.wicketstuff.egrid.column.panel.DropDownPanel;
import org.wicketstuff.egrid.column.panel.EditablePanel;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * AbstractEditablePropertyColumn with a DropDownPanel
 *
 * @param <T> the model object type
 * @param <S> the type of the sorting parameter
 * @author Silas Porth
 */
public class DropDownColumn<T, P extends Serializable, S> extends AbstractEditablePropertyColumn<T, S> {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<? extends P> choices;

    /**
     * Constructor
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     * @param choices            the list of choices
     */
    public DropDownColumn(final IModel<String> displayModel, final String propertyExpression, final List<? extends P> choices) {
        super(displayModel, propertyExpression);
        this.choices = choices;
    }

    /**
     * Constructor
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     * @param choices            the list of choices
     * @param sortProperty       the sort property
     */
    public DropDownColumn(final IModel<String> displayModel, final String propertyExpression, final List<? extends P> choices, final S sortProperty) {
        super(displayModel, propertyExpression, sortProperty);
        this.choices = choices;
    }

    @Override
    public EditablePanel createEditablePanel(final String componentId) {
        return new DropDownPanel<T, P>(componentId, this, getChoices()) {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected IChoiceRenderer<P> newChoiceRenderer() {
                return DropDownColumn.this.newChoiceRenderer();
            }

            @Override
            protected IConverter<? extends P> newObjectConverter(final Class<?> type) {
                return DropDownColumn.this.newObjectConverter(type);
            }
        };
    }

    /**
     * This getter should only be used in an override otherwise use {@link org.wicketstuff.egrid.column.panel.DropDownPanel#getChoices()}.
     *
     * @return the list of choices
     */
    protected List<? extends P> getChoices() {
        return choices;
    }

    /**
     * Override to create a new IChoiceRenderer for the DropDownChoice.
     *
     * @return a new IChoiceRenderer
     * @see org.apache.wicket.markup.html.form.IChoiceRenderer
     */
    protected IChoiceRenderer<P> newChoiceRenderer() {
        return null;
    }

    /**
     * Override to create a new IConverter for the DropDownChoice.
     *
     * @param type the type to convert to
     * @return a new IConverter
     * @see org.apache.wicket.util.convert.IConverter
     */
    protected IConverter<? extends P> newObjectConverter(final Class<?> type) {
        return null;
    }
}
