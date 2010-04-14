/**
 * 
 */
package org.wicketstuff.jsr303;

import java.util.Locale;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;
import org.hibernate.validator.engine.ResourceBundleMessageInterpolator;
import org.wicketstuff.jsr303.util.Assert;

public class JSR303Validation
{
    public class WicketSessionLocaleMessageInterpolator extends ResourceBundleMessageInterpolator
    {
        @Override
        public String interpolate(final String message, final Context context)
        {
            return super.interpolate(message, context, Session.get().getLocale());
        }

        @Override
        public String interpolate(final String message, final Context context, final Locale locale)
        {
            return super.interpolate(message, context, Session.get().getLocale());
        }
    }

    private static final JSR303Validation INSTANCE = new JSR303Validation();
    private static final MetaDataKey<ViolationMessageRenderer> violationMessageRendererKey = new MetaDataKey<ViolationMessageRenderer>()
    {
        private static final long serialVersionUID = 1L;
    };

    public static final JSR303Validation getInstance()
    {
        return INSTANCE;
    }

    public static Validator getValidator(final boolean forPropertyValidation)
    {
        return getInstance().createFactory(forPropertyValidation).getValidator();
    }

    private JSR303Validation()
    {
    }

    private synchronized ValidatorFactory createFactory(final boolean forPropertyValidation)
    {

        final Configuration<?> configuration = Validation.byDefaultProvider().configure();
        // FIXME seems like needed for hib-val 4.0.2.? strange enough it does
        // not respect the locale passed on interpolate call. Working on it.

        // geez. they screwed it up.
        // http://opensource.atlassian.com/projects/hibernate/browse/HV-306
        Locale.setDefault(Session.get().getLocale());
        final ValidatorFactory validationFactory = configuration.messageInterpolator(
                new WicketSessionLocaleMessageInterpolator()).buildValidatorFactory();

        return validationFactory;
    }

    synchronized static ViolationMessageRenderer getViolationMessageRenderer()
    {
        final Application app = Application.get();
        ViolationMessageRenderer renderer = app.getMetaData(violationMessageRendererKey);
        if (renderer == null)
        {
            renderer = new ViolationMessageRenderer.Default();
            setViolationMessageRenderer(renderer);
        }
        return renderer;
    }

    public synchronized static void setViolationMessageRenderer(final ViolationMessageRenderer renderer)
    {
        Assert.parameterNotNull(renderer, "renderer");
        final Application app = Application.get();
        app.setMetaData(violationMessageRendererKey, renderer);
    }

}
