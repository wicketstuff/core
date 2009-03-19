package org.wicketstuff.syringe;

import org.apache.commons.proxy.ProxyFactory;
import org.apache.wicket.Application;

/**
 * @since 1.4
 */
public abstract class SyringePlugin
{
//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public SyringePlugin(Application application)
    {
        this(application, new ProxyFactory());
    }

    public SyringePlugin(Application application, ProxyFactory proxyFactory)
    {
        application.addComponentInstantiationListener(new DependencyInjectionListener(proxyFactory));
    }

//**********************************************************************************************************************
// Abstract Methods
//**********************************************************************************************************************

    public abstract <T> T lookupDependency(Class<T> dependencyType, Inject injectAnnotation);
}
