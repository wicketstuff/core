package org.wicketstuff.osgi.inject;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.injection.Injector;
import org.wicketstuff.osgi.inject.impl.OsgiFieldValueFactory;

/**
 * A hook for injecting OSGi services into Wicket components. This class performs field injection
 * for all component fields annotated by {@code @javax.inject.Inject} by looking up a service of the
 * required type from the OSGi service registry.
 * <p>
 * To enable this mechanism, register the injection in your {@link Application#init()} method like
 * this:
 * 
 * <pre>
 * getComponentInstantiationListeners().add(new OsgiComponentInjector());
 * </pre>
 * 
 * By default, injected services are wrapped in a proxy to take care of class loading issues when
 * deserializing a component from the page store. On serialization, the proxy replaces the wrapped
 * service (which may not be serializable itself) by a serializable reference (its interface name,
 * essentially). On deserialization, the proxy retrieves the matching service from the OSGi service
 * registry.
 * <p>
 * TODO Injection is not deterministic when there is more than one service of the required type.
 * This class should be extended to disambiguate candidate services either by a {@code @Named}
 * qualifier or by a dedicated qualifier taking service properties as parameters.
 * 
 * @author Harald Wellmann
 * 
 */
public class OsgiComponentInjector extends Injector implements IComponentInstantiationListener
{
	private OsgiFieldValueFactory fieldFactory;

	public OsgiComponentInjector()
	{
		this(true);
	}

	public OsgiComponentInjector(boolean wrapInProxies)
	{
		fieldFactory = new OsgiFieldValueFactory(wrapInProxies);
	}

	@Override
	public void inject(Object object)
	{
		inject(object, fieldFactory);
	}

	public void onInstantiation(Component component)
	{
		inject(component);
	}
}
