package org.wicketstuff.osgi.inject.impl;

import java.lang.reflect.Field;

import javax.inject.Inject;

import org.apache.wicket.injection.IFieldValueFactory;
import org.apache.wicket.proxy.LazyInitProxyFactory;

/**
 * 
 * @author Harald Wellmann
 * 
 */
public class OsgiFieldValueFactory implements IFieldValueFactory
{

	private final boolean wrapInProxies;

	/**
	 */
	public OsgiFieldValueFactory()
	{
		this(true);
	}

	/**
	 * @param wrapInProxies
	 *            whether or not wicket should wrap dependencies with specialized proxies that can
	 *            be safely serialized. in most cases this should be set to true.
	 */
	public OsgiFieldValueFactory(boolean wrapInProxies)
	{
		this.wrapInProxies = wrapInProxies;
	}

	/**
	 * @see org.apache.wicket.injection.IFieldValueFactory#getFieldValue(java.lang.reflect.Field,
	 *      java.lang.Object)
	 */
	public Object getFieldValue(Field field, Object fieldOwner)
	{
		if (field.isAnnotationPresent(Inject.class))
		{
			OsgiServiceProxyTargetLocator locator = new OsgiServiceProxyTargetLocator(
				field.getType().getName());


			final Object target;
			if (wrapInProxies)
			{
				target = LazyInitProxyFactory.createProxy(field.getType(), locator);
			}
			else
			{
				target = locator.locateProxyTarget();
			}

			return target;
		}
		return null;
	}

	/**
	 * @see org.apache.wicket.injection.IFieldValueFactory#supportsField(java.lang.reflect.Field)
	 */
	public boolean supportsField(Field field)
	{
		return field.isAnnotationPresent(Inject.class);
	}
}
