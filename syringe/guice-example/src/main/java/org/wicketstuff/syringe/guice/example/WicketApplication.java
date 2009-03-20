package org.wicketstuff.syringe.guice.example;

import org.wicketstuff.plugin.WicketPluginApplication;
import org.wicketstuff.syringe.guice.GuiceSyringePlugin;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @since 1.4
 */
public class WicketApplication extends WicketPluginApplication
{
    /**
     * Constructor
     */
    public WicketApplication()
    {
    }

    @Override
    protected void init()
    {
        Injector injector = Guice.createInjector(new GuiceModule());
        getPluginRegistry().registerPlugin(new GuiceSyringePlugin(this, injector));
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class<HomePage> getHomePage()
    {
        return HomePage.class;
    }

}
