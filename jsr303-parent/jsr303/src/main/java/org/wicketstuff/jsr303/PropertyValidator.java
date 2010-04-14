package org.wicketstuff.jsr303;

import java.io.Serializable;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.wicketstuff.jsr303.util.Assert;

class PropertyValidator<T> implements INullAcceptingValidator<T>, Serializable
{
    private static final long serialVersionUID = 1L;

    private final Class<?> beanClass;
    private final String propertyExpression;

    PropertyValidator(final AbstractPropertyModel<?> apm)
    {
        Assert.parameterNotNull(apm, "apm");
        this.beanClass = apm.getTarget().getClass();
        this.propertyExpression = apm.getPropertyExpression();
    }

    public void validate(final IValidatable<T> validatable)
    {
        final T value = validatable.getValue();

        final Set<?> violations = JSR303Validation.getValidator(true).validateValue(this.beanClass,
                this.propertyExpression, value);
        for (final Object v : violations)
        {
            validatable.error(new ViolationErrorBuilder.Property((ConstraintViolation) v).createError());
        }
    }
}