package org.wicketstuff.osgi;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.application.DefaultClassResolver;
import org.apache.wicket.application.IClassResolver;

/**
 * A class resolver to be used for Wicket page deserialization in an OSGi context. This is a
 * modified copy of Wicket's {@link DefaultClassResolver} which uses the classloader of the current
 * Application class instead of the thread context classloader.
 * <p>
 * This resolver should be registered in the Application.init() method:
 * 
 * <pre>
 * getApplicationSettings().setClassResolver(new OsgiClassResolver());
 * </pre>
 * 
 * The OsgiClassResolver is able to access all classes visible to the application bundle, which may
 * not cover all use cases, but is a reasonable default.
 * 
 * @author Harald Wellmann
 * 
 */
public class OsgiClassResolver implements IClassResolver
{

	/**
	 * Usually class loaders implement more efficient caching strategies than we could possibly do,
	 * but we experienced synchronization issue resulting in stack traces like:
	 * java.lang.LinkageError: duplicate class definition:
	 * 
	 * <pre>
	 *    wicket/examples/repeater/RepeatingPage at java.lang.ClassLoader.defineClass1(Native Method)
	 * </pre>
	 * 
	 * This problem has gone since we synchronize the access.
	 */
	private final ConcurrentHashMap<String, WeakReference<Class<?>>> classes = new ConcurrentHashMap<String, WeakReference<Class<?>>>();

	/**
	 * @see org.apache.wicket.application.IClassResolver#resolveClass(java.lang.String)
	 */
	public final Class<?> resolveClass(final String classname) throws ClassNotFoundException
	{
		Class<?> clazz = null;
		WeakReference<Class<?>> ref = classes.get(classname);

		// Might be garbage-collected between getting the WeakRef and retrieving
		// the Class from it.
		if (ref != null)
		{
			clazz = ref.get();
		}
		if (clazz == null)
		{
			if (classname.equals("byte"))
			{
				clazz = byte.class;
			}
			else if (classname.equals("short"))
			{
				clazz = short.class;
			}
			else if (classname.equals("int"))
			{
				clazz = int.class;
			}
			else if (classname.equals("long"))
			{
				clazz = long.class;
			}
			else if (classname.equals("float"))
			{
				clazz = float.class;
			}
			else if (classname.equals("double"))
			{
				clazz = double.class;
			}
			else if (classname.equals("boolean"))
			{
				clazz = boolean.class;
			}
			else if (classname.equals("char"))
			{
				clazz = char.class;
			}
			else
			{
				synchronized (classes)
				{
					ClassLoader loader = Application.get().getClass().getClassLoader();

					// see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6500212
					// clazz = loader.loadClass(classname);
					clazz = Class.forName(classname, false, loader);
				}
				classes.put(classname, new WeakReference<Class<?>>(clazz));
			}
		}
		return clazz;
	}

	/**
	 * 
	 * @see org.apache.wicket.application.IClassResolver#getResources(java.lang.String)
	 */
	public Iterator<URL> getResources(String name)
	{
		HashSet<URL> loadedFiles = new HashSet<URL>();
		try
		{
			// Try the classloader for the wicket jar/bundle
			Enumeration<URL> resources = Application.class.getClassLoader().getResources(name);
			loadResources(resources, loadedFiles);

			// Try the classloader for the user's application jar/bundle
			resources = Application.get().getClass().getClassLoader().getResources(name);
			loadResources(resources, loadedFiles);

			// Try the context class loader
			resources = Thread.currentThread().getContextClassLoader().getResources(name);
			loadResources(resources, loadedFiles);
		}
		catch (IOException e)
		{
			throw new WicketRuntimeException(e);
		}

		return loadedFiles.iterator();
	}

	/**
	 * 
	 * @param resources
	 * @param loadedFiles
	 */
	private void loadResources(Enumeration<URL> resources, Set<URL> loadedFiles)
	{
		if (resources != null)
		{
			while (resources.hasMoreElements())
			{
				final URL url = resources.nextElement();
				if (!loadedFiles.contains(url))
				{
					loadedFiles.add(url);
				}
			}
		}
	}
	
	public ClassLoader getClassLoader()
	{
		return Application.get().getClass().getClassLoader();
	}        
}
