package org.wicketstuff.jsr303;

import javax.validation.ConstraintViolation;

import org.apache.wicket.Component;

/**
 * BeanValidator is not actually a Wicket IValidator. The reason for this is the
 * fact, that values must have been written back into the Bean for it to be able
 * to validate. This is supposed to be used manually within the onSubmit call of
 * a form, for example. It may report the error to the component being passed on
 * construction.
 */
public class BeanValidator<T>
{
    private final Component context;;

    public BeanValidator(final Component contextOrNull)
    {
        this.context = contextOrNull;
    }

    public <U> boolean isValid(final U e)
    {
        if (e == null)
        {
            return true;
        }

        JSR303Validation.getInstance();
        final java.util.Set<?> s = JSR303Validation.getValidator().validate(e);
        if (s.isEmpty())
        {
            return true;
        }

        for (final Object v : s)
        {
            final ConstraintViolation<?> violation = (ConstraintViolation<?>) v;
            if (this.context != null)
            {
                this.context.error(JSR303Validation.createValidationError(violation));
            }
        }
        return false;
    }
}