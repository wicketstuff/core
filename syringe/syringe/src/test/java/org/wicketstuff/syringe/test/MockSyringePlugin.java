package org.wicketstuff.syringe.test;

import org.wicketstuff.syringe.SyringePlugin;
import org.wicketstuff.syringe.Inject;
import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.commons.proxy.ProxyFactory;

import java.util.Map;
import java.util.HashMap;

/**
 * @since 1.4
 */
public class MockSyringePlugin extends SyringePlugin
{
    private Map<String,Object> beans = new HashMap<String,Object>();

    public MockSyringePlugin(Application application)
    {
        super(application);
    }

    public MockSyringePlugin(Application application, ProxyFactory proxyFactory)
    {
        super(application, proxyFactory);
    }

    public <T> T lookupDependency(Class<T> dependencyType, Inject injectAnnotation)
    {
        if(injectAnnotation.name() == null)
        {
            for (Object bean : beans.values())
            {
                if(dependencyType.isInstance(bean))
                {
                    return dependencyType.cast(bean);
                }
            }
        }
        else
        {
            Object bean = beans.get(injectAnnotation.name());
            if(dependencyType.isInstance(bean))
            {
                return dependencyType.cast(bean);
            }
        }
        throw new WicketRuntimeException("Unable to find bean of type " + dependencyType.getName() + ".");
    }

    public void registerBean(String name, Object bean)
    {
        beans.put(name, bean);
    }
}
