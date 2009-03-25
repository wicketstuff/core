package org.wicketstuff.syringe.guice.example;

import com.google.inject.Module;
import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * @since 1.4
 */
public class GuiceModule implements Module
{
    public void configure(Binder binder)
    {
        binder.bind(IMessageService.class).annotatedWith(Names.named("messageService")).to(MessageServiceImpl.class);
    }
}
