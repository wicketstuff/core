package org.wicketstuff.syringe.guice;

import org.wicketstuff.syringe.SyringePlugin;
import org.wicketstuff.syringe.Inject;
import org.wicketstuff.syringe.DependencyProvider;
import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.commons.proxy.ProxyFactory;
import com.google.inject.Key;
import com.google.inject.Injector;
import com.google.inject.name.Names;

/**
 * @since 1.4
 */
public class GuiceDependencyProvider implements DependencyProvider
{
    private final Injector injector;

    public GuiceDependencyProvider(Injector injector)
    {
        this.injector = injector;
    }

    public <T> T getDependencyByName(Class<T> dependencyType, String name)
    {
        return injector.getInstance(Key.get(dependencyType, Names.named(name)));
    }

    public <T> T getDependencyByType(Class<T> dependencyType)
    {
        return injector.getInstance(Key.get(dependencyType));
    }
}
