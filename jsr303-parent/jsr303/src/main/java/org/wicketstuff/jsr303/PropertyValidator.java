package org.wicketstuff.jsr303;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.jsr303.util.Assert;

@SuppressWarnings("rawtypes")
public class PropertyValidator<T> extends AbstractBehavior
{
    public class Exclude extends AbstractBehavior
    {
        private static final long serialVersionUID = 1L;
    }

    class Validator implements INullAcceptingValidator<T>, Serializable
    {
        private static final long serialVersionUID = 1L;

        public void validate(final IValidatable<T> validatable)
        {
            // skip, if propertyExpression is empty
            if (propertyExpression == null || propertyExpression.trim().length() == 0) return;

            // skip, if marked as excluded
            if (hasExclusionBehaviour()) return;

            final T value = validatable.getValue();

            final Set<?> violations = JSR303Validation.getValidator().validateValue(beanClass, propertyExpression,
                    value);
            for (final Object v : violations)
            {
                validatable.error(new ViolationErrorBuilder.Property((ConstraintViolation) v).createError());
            }
        }
    }

    private static final long serialVersionUID = 1L;

    private final Class<?> beanClass;
    private final String propertyExpression;

    private FormComponent fc;

    public PropertyValidator(final AbstractPropertyModel<?> apm)
    {
        Assert.parameterNotNull(apm, "apm");
        this.beanClass = apm.getTarget().getClass();
        this.propertyExpression = apm.getPropertyExpression();
    }

    private boolean hasExclusionBehaviour()
    {
        List<IBehavior> behaviors = fc.getBehaviors();
        for (IBehavior iBehavior : behaviors)
        {
            if (iBehavior instanceof PropertyValidator.Exclude)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void bind(Component component)
    {
        super.bind(component);
        if (component instanceof FormComponent)
        {

            FormComponent fc = (FormComponent) component;
            this.fc = fc;

            fc.add(new Validator());

        }
    }
    private static transient volatile Logger _transient_logger = LoggerFactory.getLogger(PropertyValidator.class);

    public static final Logger log()
    {
        if (_transient_logger == null)
        {
            _transient_logger = LoggerFactory.getLogger(PropertyValidator.class);
        }
        return _transient_logger;
    }
}
