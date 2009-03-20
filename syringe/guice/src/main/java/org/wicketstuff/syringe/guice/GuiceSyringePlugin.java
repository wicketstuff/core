package org.wicketstuff.syringe.guice;

import org.wicketstuff.syringe.SyringePlugin;
import org.wicketstuff.syringe.Inject;
import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.commons.proxy.ProxyFactory;
import com.google.inject.Key;
import com.google.inject.Injector;
import com.google.inject.name.Names;

/**
 * @since 1.0
 */
public class GuiceSyringePlugin extends SyringePlugin
{
    private final Injector injector;

    public GuiceSyringePlugin(Application application, Injector injector)
    {
        super(application);
        this.injector = injector;
    }

    public GuiceSyringePlugin(Application application, ProxyFactory proxyFactory, Injector injector)
    {
        super(application, proxyFactory);
        this.injector = injector;
    }

    public <T> T lookupDependency(Class<T> dependencyType, Inject injectAnnotation)
    {
        T bean = null;
        if ("".equals(injectAnnotation.name()))
        {
            bean = injector.getInstance(Key.get(dependencyType));
        }
        else
        {
            bean = injector.getInstance(Key.get(dependencyType, Names.named(injectAnnotation.name())));
        }
        if (bean == null)
        {
            throw new WicketRuntimeException("Unable to find dependency of type " + dependencyType.getName() + ".");
        }
        return bean;
    }
}
