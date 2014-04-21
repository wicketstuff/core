/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.utils.mounting;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.lang.Args;
import org.wicketstuff.rest.annotations.ResourcePath;

public class PackageScanner
{
	public static void scanPackage(String... packageNames)
	{
		scanPackage(WebApplication.get(), packageNames);
	}

	public static void scanPackage(WebApplication application, String... packageNames)
	{
		for (String packageName : packageNames)
		{
			scanPackage(application, packageName);
		}
	}

	public static void scanPackage(WebApplication application, String packageName)
	{
		Args.notNull(application, "application");

		try
		{
			Class<?>[] packageClasses = getClasses(packageName);

			for (Class<?> clazz : packageClasses)
			{
				mountAnnotatedResource(application, clazz);
			}

		} catch (Exception exception)
		{
			throw new WicketRuntimeException(exception);
		}
	}

	private static void mountAnnotatedResource(WebApplication application, Class<?> clazz)
			throws InstantiationException, IllegalAccessException
	{
		ResourcePath mountAnnotation = clazz.getAnnotation(ResourcePath.class);

		if (mountAnnotation == null || !IResource.class.isAssignableFrom(clazz))
		{
			return;
		}

		String path = mountAnnotation.value();
		final IResource resourceInstance = (IResource) clazz.newInstance();

		application.mountResource(path, new ResourceReference(clazz.getSimpleName())
		{
			@Override
			public IResource getResource()
			{
				return resourceInstance;
			}
		});
	}

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * 
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static Class<?>[] getClasses(String packageName) throws ClassNotFoundException,
			IOException
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements())
		{
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : dirs)
		{
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 * 
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> findClasses(File directory, String packageName)
			throws ClassNotFoundException
	{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists())
		{
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class"))
			{
				classes.add(Class.forName(packageName + '.'
						+ file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

}
