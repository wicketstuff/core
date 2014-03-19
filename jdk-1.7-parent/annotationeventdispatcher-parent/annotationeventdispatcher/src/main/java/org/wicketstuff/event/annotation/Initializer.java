package org.wicketstuff.event.annotation;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;

/**
 * Automatically configures the application to use AnnotationEventDispatcher
 */
public class Initializer implements IInitializer {

    @Override
    public void init(Application application) {
        AnnotationEventDispatcher dispatcher = new AnnotationEventDispatcher();
        application.getComponentInstantiationListeners().add(dispatcher);
        application.getFrameworkSettings().add(dispatcher);
    }

    @Override
    public void destroy(Application application) {
    }
}
