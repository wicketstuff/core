package org.wicketstuff.syringe;

import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.commons.proxy.ProxyFactory;
import org.apache.commons.proxy.ObjectProvider;
import org.wicketstuff.plugin.WicketPluginApplication;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @since 1.4
 */
public class DependencyInjectionListener implements IComponentInstantiationListener
{

//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private final ProxyFactory proxyFactory;

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public DependencyInjectionListener(ProxyFactory proxyFactory)
    {
        this.proxyFactory = proxyFactory;
    }

//**********************************************************************************************************************
// IComponentInstantiationListener Implementation
//**********************************************************************************************************************

    private static class IocDependencyProvider implements ObjectProvider, Serializable
    {
        private Class dependencyType;
        private Inject injectAnnotation;

        private IocDependencyProvider(Class dependencyType, Inject injectAnnotation)
        {
            this.dependencyType = dependencyType;
            this.injectAnnotation = injectAnnotation;
        }

        @SuppressWarnings("unchecked")
        public Object getObject()
        {
            return getSyringePlugin().lookupDependency(dependencyType, injectAnnotation);
        }
    }

    @SuppressWarnings("unchecked")
    public void onInstantiation(Component component)
    {
        final Field[] fields = component.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            field.setAccessible(true);
            Inject injectAnnotation = field.getAnnotation(Inject.class);
            try
            {
                if (injectAnnotation != null && field.get(component) == null)
                {
                    Class fieldType = field.getType();
                    Object dependency = getSyringePlugin().lookupDependency(fieldType, injectAnnotation);
                    if (dependency != null)
                    {
                        final Class[] proxyTypes = {fieldType, Serializable.class};
                        if (proxyFactory.canProxy(proxyTypes))
                        {
                            Object proxy = proxyFactory.createDelegatorProxy(new IocDependencyProvider(fieldType, injectAnnotation), proxyTypes);
                            field.set(component, proxy);
                        }
                        else
                        {
                            throw new WicketRuntimeException("Proxy factory implementation cannot create proxy for type " + fieldType.getName() + ".");
                        }
                    }
                    else
                    {
                        throw new WicketRuntimeException("Dependency of type " + fieldType.getName() + " for field " + field.getName() + " of class " + component.getClass().getName() + " not found.");
                    }
                }
            }
            catch (IllegalAccessException e)
            {
                throw new WicketRuntimeException("Unable to inject field " + field.getName() + " in component class " + component.getClass().getName() + ".", e);
            }
        }
    }

    private static SyringePlugin getSyringePlugin()
    {
        return WicketPluginApplication.lookupPlugin(SyringePlugin.class);
    }
}
