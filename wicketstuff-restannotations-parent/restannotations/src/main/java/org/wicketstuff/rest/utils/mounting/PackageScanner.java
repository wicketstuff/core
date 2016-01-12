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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.lang.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.rest.annotations.ResourcePath;

public class PackageScanner
{
	private static final Logger log = LoggerFactory.getLogger(PackageScanner.class);

	public static void scanPackage(String... packageNames)
	{
		Args.notNull(packageNames, "packageNames");

		scanPackage(WebApplication.get(), packageNames);
	}

	public static void scanPackage(WebApplication application, String... packageNames)
	{
		Args.notNull(application, "application");
		Args.notNull(packageNames, "packageNames");

		for (String packageName : packageNames)
		{
			scanPackage(application, packageName);
		}
	}

	public static void scanPackage(WebApplication application, String packageName)
	{
		Args.notNull(application, "application");
		Args.notNull(packageName, "packageName");

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
			/**
        	* 
    		*/
    		private static final long serialVersionUID = 1L;

			@Override
			public IResource getResource()
			{
				return resourceInstance;
			}
		});

		log.info("Resource '" + clazz.getSimpleName() + "' has been mounted to path '" + path
				+ "'");
	}

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * 
	 * Credits: http://www.dzone.com/snippets/get-all-classes-within-package
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
		
		Args.notNull(classLoader, "classLoader");
		
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<>();
		List<JarFile> jars = new ArrayList<>();
		
		while (resources.hasMoreElements())
		{
			URL resource = resources.nextElement();
			String protocol = resource.getProtocol();
			
			if("jar".equals(protocol) || "wsjar".equals(protocol)) 
			{
				String jarFileName = URLDecoder.decode(resource.getFile(), "UTF-8");
		        jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));
		        
		        jars.add(new JarFile(jarFileName));
			}
			else
			{
				dirs.add(new File(resource.getFile()));
			}
		}
		
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		
		for (File directory : dirs)
		{
			classes.addAll(findClasses(directory, packageName));
		}
		
		for (JarFile jarFile : jars)
		{
			classes.addAll(findClasses(jarFile, path));
		}
		
		return classes.toArray(new Class[classes.size()]);
	}


	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 * 
	 * Credits: http://www.dzone.com/snippets/get-all-classes-within-package
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
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class"))
			{
				classes.add(Class.forName(packageName + '.'
						+ file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		
		return classes;
	}
	
	/**
	 * Search for classes in a given jar file and package name
	 * 
	 * Credits: http://www.dzone.com/snippets/get-all-classes-within-package
	 * 
	 * @param jarFile
	 *            The target jar arcive
	 * @param path
	 *            The package to look into as path
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static Collection<? extends Class<?>> findClasses(JarFile jarFile, String path) 
		throws ClassNotFoundException
	{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		
		while (jarEntries.hasMoreElements())
		{
			JarEntry jarEntry = jarEntries.nextElement();
			String entryName = jarEntry.getName();
			int classExtensionIndex = entryName.indexOf(".class");
			
			if(entryName.startsWith(path) && classExtensionIndex >= 0)
			{
                entryName = entryName.substring(0, classExtensionIndex);
                entryName = entryName.replace('/', '.');
                classes.add(Class.forName(entryName));
            }
		}
		
		return classes;
	}
}
