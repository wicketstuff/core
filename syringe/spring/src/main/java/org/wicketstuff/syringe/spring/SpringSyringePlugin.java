package org.wicketstuff.syringe.spring;

import org.wicketstuff.syringe.SyringePlugin;
import org.wicketstuff.syringe.Inject;
import org.springframework.context.ApplicationContext;
import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.commons.proxy.ProxyFactory;

import java.util.Map;

/**
 * @since 1.4
 */
public class SpringSyringePlugin extends SyringePlugin
{
    private final ApplicationContext applicationContext;

    public SpringSyringePlugin(Application application, ApplicationContext applicationContext)
    {
        super(application);
        this.applicationContext = applicationContext;
    }

    public SpringSyringePlugin(Application application, ProxyFactory proxyFactory, ApplicationContext applicationContext)
    {
        super(application, proxyFactory);
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public <T> T lookupDependency(Class<T> dependencyType, Inject injectAnnotation)
    {
        Map<String, T> beanMap = applicationContext.getBeansOfType(dependencyType);
        if ("".equals(injectAnnotation.name()))
        {
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
        else
        {
            T bean = beanMap.get(injectAnnotation.name());
            if(bean == null)
            {
                throw new WicketRuntimeException("No bean of type " + dependencyType.getName() + " named \"" + injectAnnotation.name() + "\" defined in Spring application context.");
            }
            return bean;
        }
    }
}
