package org.wicketstuff.syringe.spring;

import org.wicketstuff.syringe.SyringePlugin;
import org.wicketstuff.syringe.Inject;
import org.wicketstuff.syringe.DependencyProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.commons.proxy.ProxyFactory;

import java.util.Map;

/**
 * @since 1.4
 */
public class SpringDependencyProvider implements DependencyProvider
{
    private final ApplicationContext applicationContext;

    public SpringDependencyProvider(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    public <T> T getDependencyByName(Class<T> dependencyType, String name)
    {
        return dependencyType.cast(applicationContext.getBean(name, dependencyType));
    }

    public <T> T getDependencyByType(Class<T> dependencyType)
    {
        Map<String, T> beanMap = applicationContext.getBeansOfType(dependencyType);
        switch (beanMap.size())
        {
            case 1:
                return beanMap.values().iterator().next();
            case 0:
                throw new WicketRuntimeException("No bean of type " + dependencyType.getName() + " found in Spring application context.");
            default:
                throw new WicketRuntimeException("Spring application context contains " + beanMap.size() + " beans of type " + dependencyType.getName() + ".");
        }
    }
}
