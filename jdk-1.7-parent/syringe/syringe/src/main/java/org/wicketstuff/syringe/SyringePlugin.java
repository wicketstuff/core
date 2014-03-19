package org.wicketstuff.syringe;

import org.apache.commons.proxy.ProxyFactory;
import org.apache.wicket.Application;

/**
 * @since 1.4
 */
public class SyringePlugin
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private final DependencyProvider dependencyProvider;

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public SyringePlugin(Application application, DependencyProvider dependencyProvider)
    {
        this(application, dependencyProvider, new ProxyFactory());
    }

    public SyringePlugin(Application application, DependencyProvider dependencyProvider, ProxyFactory proxyFactory)
    {
        this.dependencyProvider = dependencyProvider;
        application.addComponentInstantiationListener(new DependencyInjectionListener(proxyFactory));
    }

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    public final <T> T lookupDependency(Class<T> dependencyType, Inject injectAnnotation)
    {
        if("".equals(injectAnnotation.name()))
        {
            return dependencyProvider.getDependencyByType(dependencyType);
        }
        else
        {
            return dependencyProvider.getDependencyByName(dependencyType, injectAnnotation.name());   
        }
    }
}
