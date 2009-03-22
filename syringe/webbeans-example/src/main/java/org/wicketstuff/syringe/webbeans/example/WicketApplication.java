package org.wicketstuff.syringe.webbeans.example;

import org.wicketstuff.plugin.WicketPluginApplication;
import org.wicketstuff.syringe.SyringePlugin;
import org.wicketstuff.syringe.webbeans.WebBeansDependencyProvider;
import org.apache.webbeans.container.ManagerImpl;

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
        getPluginRegistry().registerPlugin(new SyringePlugin(this, new WebBeansDependencyProvider(ManagerImpl.getManager())));
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class<HomePage> getHomePage()
    {
        return HomePage.class;
    }

}
