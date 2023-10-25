package org.wicketstuff.egrid.column;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import java.io.Serial;

/**
 * CheckBoxColumn with a form required CheckBoxPanel
 *
 * @param <T> the model object type
 * @param <S> the type of the sorting parameter
 * @author Silas Porth
 */
public class RequiredCheckBoxColumn<T, S> extends CheckBoxColumn<T, S> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     */
    public RequiredCheckBoxColumn(final IModel<String> displayModel, final String propertyExpression) {
        super(displayModel, propertyExpression);
    }

    /**
     * Constructor
     *
     * @param displayModel       the display model
     * @param propertyExpression the wicket property expression
     * @param sortProperty       the sort property
     */
    public RequiredCheckBoxColumn(final IModel<String> displayModel, final String propertyExpression, final S sortProperty) {
        super(displayModel, propertyExpression, sortProperty);
    }

    @Override
    public void addBehaviors(final FormComponent<?> editorComponent) {
        super.addBehaviors(editorComponent);
        editorComponent.setRequired(true);
    }
}
