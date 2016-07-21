package org.wicketstuff.event.annotation;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.MetaDataKey;

/**
 * Automatically configures the application to use AnnotationEventDispatcher
 */
public class Initializer implements IInitializer
{

    @SuppressWarnings("serial")
    public static final MetaDataKey<AnnotationEventDispatcherConfig> ANNOTATION_EVENT_DISPATCHER_CONFIG_CONTEXT_KEY = new MetaDataKey<AnnotationEventDispatcherConfig>() {
    };

    @Override
    public void init(final Application application)
    {
        AnnotationEventDispatcherConfig config = new AnnotationEventDispatcherConfig();
        application.setMetaData(ANNOTATION_EVENT_DISPATCHER_CONFIG_CONTEXT_KEY, config);
        AnnotationEventDispatcher dispatcher = new AnnotationEventDispatcher();
        application.getComponentInstantiationListeners().add(dispatcher);
        application.getFrameworkSettings().add(dispatcher);
    }

    @Override
    public void destroy(final Application application)
    {
    }
    
}
