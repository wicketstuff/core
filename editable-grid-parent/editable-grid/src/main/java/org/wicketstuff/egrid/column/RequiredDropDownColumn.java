package org.wicketstuff.egrid.column;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * DropDownColumn with a form required DropDownChoice
 *
 * @param <T> the model object type
 * @param <S> the type of the sorting parameter
 * @author Silas Porth
 */
public class RequiredDropDownColumn<T, P extends Serializable, S> extends DropDownColumn<T, P, S> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     * @param choices            the list of choices
     */
    public RequiredDropDownColumn(final IModel<String> displayModel, final String propertyExpression, final List<? extends P> choices) {
        super(displayModel, propertyExpression, choices);
    }

    /**
     * Constructor
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     * @param choices            the list of choices
     * @param sortProperty       the sort property
     */
    public RequiredDropDownColumn(final IModel<String> displayModel, final String propertyExpression, final List<? extends P> choices, final S sortProperty) {
        super(displayModel, propertyExpression, choices, sortProperty);
    }

    @Override
    public void addBehaviors(final FormComponent<?> editorComponent) {
        super.addBehaviors(editorComponent);
        editorComponent.setRequired(true);
    }
}
