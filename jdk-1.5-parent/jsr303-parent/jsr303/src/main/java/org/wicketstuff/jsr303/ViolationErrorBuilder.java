package org.wicketstuff.jsr303;

import javax.validation.ConstraintViolation;

import org.apache.wicket.validation.ValidationError;
import org.wicketstuff.jsr303.util.Assert;

abstract class ViolationErrorBuilder
{
    static class Property extends ViolationErrorBuilder
    {
        public Property(final ConstraintViolation violation)
        {
            super(violation);
        }

        @Override
        protected String render()
        {
            return JSR303Validation.getViolationMessageRenderer().renderPropertyViolation(this.violation);
        }
    }
    static class Bean extends ViolationErrorBuilder
    {

        public Bean(final ConstraintViolation violation)
        {
            super(violation);
        }

        @Override
        protected String render()
        {
            return JSR303Validation.getViolationMessageRenderer().renderBeanViolation(this.violation);
        }
    }

    protected final ConstraintViolation violation;

    ViolationErrorBuilder(final ConstraintViolation violation)
    {
        Assert.parameterNotNull(violation, "violation");
        this.violation = violation;
    }

    ValidationError createError()
    {
        final ValidationError ve = new ValidationError();
        ve.setMessage(render());
        final String messageTemplate = this.violation.getMessageTemplate();
        final String key = extractKey(messageTemplate);
        if (key != null)
        {
            ve.addMessageKey(key);
        }
        return ve;
    }

    protected abstract String render();

    private static String extractKey(final String messageTemplate)
    {
        Assert.parameterNotNull(messageTemplate, "messageTemplate");
        final String key = messageTemplate.trim();
        if (key.startsWith("{") && key.endsWith("}"))
        {
            return key.substring(1, key.length() - 1);
        }
        else
        {
            return null;
        }
    }
}
