package org.wicketstuff.pageserializer.kryo2.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Interfaces
{

	public static <T> T proxyList(final Class<T> type, final T... instances)
	{
		InvocationHandler handler = new InvocationHandler()
		{

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
			{

				Method realMethod = type.getMethod(method.getName(), method.getParameterTypes());

				for (T p : instances)
				{
					realMethod.invoke(p, args);
				}
				return null;
			}
		};
		return (T)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] { type },
			handler);
	}
}
