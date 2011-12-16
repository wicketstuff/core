package org.wicketstuff.syringe;

/**
 * @since 1.4
 */
public interface DependencyProvider
{
    public <T> T getDependencyByType(Class<T> dependencyType);
    public <T> T getDependencyByName(Class<T> dependencyType, String name);
}
