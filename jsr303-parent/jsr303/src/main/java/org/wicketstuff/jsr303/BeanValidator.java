package org.wicketstuff.jsr303;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ValidationErrorFeedback;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidationError;

/**
 * BeanValidator is not actually a Wicket IValidator. The reason for this is the
 * fact, that values must have been written back into the Bean for it to be able
 * to validate. This is supposed to be used manually within the onSubmit call of
 * a form, for example. It may report the error to the component being passed on
 * construction.
 */
public class BeanValidator
{
    private final Form context;

    public BeanValidator(final Form contextOrNull)
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
        final java.util.Set<?> s = JSR303Validation.getValidator(false).validate(e);
        if (s.isEmpty())
        {
            return true;
        }

        for (final Object v : s)
        {
            final ConstraintViolation<?> violation = (ConstraintViolation<?>) v;
            if (this.context != null)
            {
                final IValidationError ve = new ViolationErrorBuilder.Bean((ConstraintViolation) v).createError();
                this.context.error(new ValidationErrorFeedback(ve, ve.getErrorMessage(new MessageSource())));
            }
        }
        return false;
    }
    class MessageSource implements IErrorMessageSource
    {
        private final Set<String> triedKeys = new LinkedHashSet<String>();

        // basically copied from FormComponent, suggestions welcome

        public String getMessage(final String key)
        {

            // Use the following log4j config for detailed logging on the
            // property resolution
            // process
            // log4j.logger.org.apache.wicket.resource.loader=DEBUG
            // log4j.logger.org.apache.wicket.Localizer=DEBUG

            final Localizer localizer = BeanValidator.this.context.getLocalizer();

            // retrieve prefix that will be used to construct message keys
            final String prefix = BeanValidator.this.context.getValidatorKeyPrefix();
            String message = null;

            // first try the full form of key [form-component-id].[key]
            String resource = BeanValidator.this.context.getId() + "." + prefix(prefix, key);
            message = getString(localizer, resource, BeanValidator.this.context);

            // if not found, try a more general form (without prefix)
            // [form-component-id].[prefix].[key]
            if (Strings.isEmpty(message) && Strings.isEmpty(prefix))
            {
                resource = BeanValidator.this.context.getId() + "." + key;
                message = getString(localizer, resource, BeanValidator.this.context);
            }

            // If not found try a more general form [prefix].[key]
            if (Strings.isEmpty(message))
            {
                resource = prefix(prefix, key);
                message = getString(localizer, resource, BeanValidator.this.context);
            }

            // If not found try the most general form [key]
            if (Strings.isEmpty(message))
            {
                // Try a variation of the resource key
                message = getString(localizer, key, BeanValidator.this.context);
            }

            // convert empty string to null in case our default value of "" was
            // returned from localizer
            if (Strings.isEmpty(message))
            {
                message = null;
            }
            return message;
        }

        private String prefix(final String prefix, final String key)
        {
            if (!Strings.isEmpty(prefix))
            {
                return prefix + "." + key;
            }
            else
            {
                return key;
            }
        }

        /**
         * @param localizer
         * @param key
         * @param component
         * @return string
         */
        private String getString(final Localizer localizer, final String key, final Component component)
        {
            this.triedKeys.add(key);

            // Note: It is important that the default value of "" is
            // provided to getString() not to throw a MissingResourceException
            // or to
            // return a default string like "[Warning: String ..."
            return localizer.getString(key, component, "");
        }

        /**
         * @see org.apache.wicket.validation.IErrorMessageSource#substitute(java.lang.String,
         *      java.util.Map)
         */
        public String substitute(final String string, final Map<String, Object> vars) throws IllegalStateException
        {
            return new MapVariableInterpolator(string, vars, Application.get().getResourceSettings()
                    .getThrowExceptionOnMissingResource()).toString();
        }

    }
}