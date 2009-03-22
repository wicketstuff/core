package org.wicketstuff.syringe.webbeans;

import org.wicketstuff.syringe.DependencyProvider;

import javax.inject.manager.Manager;

/**
 * @since 1.4
 */
public class WebBeansDependencyProvider implements DependencyProvider
{
    private final Manager manager;

    public WebBeansDependencyProvider(Manager manager)
    {
        this.manager = manager;
    }

    public <T> T getDependencyByName(Class<T> dependencyType, String name)
    {
        return dependencyType.cast(manager.getInstanceByName(name));
    }

    public <T> T getDependencyByType(Class<T> dependencyType)
    {
        return manager.getInstanceByType(dependencyType);
    }
}
