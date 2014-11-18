/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.annotation.scan;

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.core.request.mapper.HomePageMapper;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.config.MatchingResources;

/**
 * Looks for mount information by scanning for classes annotated with {@link MountPath}. You can
 * specify a package to scan (e.g., "org.mycompany.wicket.pages"). Wildcards also work (e.g.,
 * "org.mycompany.*.pages" or "org.mycompany.**.pages").
 * 
 * <p>
 * You can also go more advanced, using any pattern supported by {@link MatchingResources}. For
 * example, the first package example above is turned into
 * "classpath*:/org/mycompany/wicket/pages/**&#47;*.class".
 * 
 * <p>
 * For each class that is annotated, an appropriate {@link IRequestMapper} implementing class is
 * created using the information in the {@link MountPath} annotation and any supplemental
 * annotations. Each instance is added to the list to return. Each item in the returned list can
 * then be mounted.
 * 
 * <p>
 * Typical usage is in your {@link Application#init()} method and utilizes the
 * {@link AnnotatedMountList#mount} convenience method.
 * 
 * <pre>
 * protected void init()
 * {
 * 	new AnnotatedMountScanner().scanPackage(&quot;org.mycompany.wicket.pages&quot;).mount(this);
 * }
 * </pre>
 * 
 * <p>
 * You could scan the entire classpath if you wanted by passing in null, but that might require more
 * time to run than limiting it to known packages which have annotated classes.
 * 
 * <p>
 * Page classes annotation usage is as follows:
 * 
 * <pre>
 * &#064;MountPath
 * private class HelloPage extends Page
 * {
 * }
 * 
 * &#064;MountPath(&quot;hello&quot;)
 * private class HelloPage extends Page
 * {
 * }
 * 
 * &#064;MountPath(value = &quot;dogs&quot;, alt = { &quot;canines&quot;, &quot;k9s&quot; })
 * private class DogsPage extends Page
 * {
 * }
 * </pre>
 * 
 * <p>
 * The first example will mount HelloPage to /HelloPage using the default mapper (as returned by
 * {@link #getRequestMapper} which is {@link MountedMapper}.
 * 
 * <p>
 * The second example will mount HelloPage to /hello using the default mapper (as returned by
 * {@link #getRequestMapper} which is {@link MountedMapper}.
 * 
 * <p>
 * The third example will mount DogsPage at "/dogs" (as the primary) and as "/canines" and "/k9s" as
 * alternates using the {@link MountedMapper}.
 * 
 * @author Doug Donohoe
 * @author Ronald Tetsuo Miura
 */
public class AnnotatedMountScanner
{

	/**
	 * Get the Spring search pattern given a package name or part of a package name
	 * 
	 * @param packageName
	 *            a package name
	 * @return a Spring search pattern for the given package
	 */
	public String getPatternForPackage(String packageName)
	{
		if (packageName == null)
			packageName = "";
		packageName = packageName.replace('.', '/');
		if (!packageName.endsWith("/"))
		{
			packageName += '/';
		}

		return "classpath*:" + packageName + "**/*.class";
	}


	/**
	 * Scan given a package name or part of a package name and return list of classes with MountPath
	 * annotation.
	 * 
	 * @return A List of classes annotated with &#64;MountPath
	 */
	public List<Class<?>> getPackageMatches(String pattern)
	{
		return getPatternMatches(getPatternForPackage(pattern));
	}

	/**
	 * Scan given a Spring search pattern and return list of classes with MountPath annotation.
	 * 
	 * @return A List of classes annotated with &#64;MountPath
	 */
	public List<Class<?>> getPatternMatches(String pattern)
	{
		MatchingResources resources = new MatchingResources(pattern);
		List<Class<?>> mounts = resources.getAnnotatedMatches(MountPath.class);
		for (Class<?> mount : mounts)
		{
			if (!(Page.class.isAssignableFrom(mount)))
			{
				throw new RuntimeException("@MountPath annotated class should subclass Page: " +
					mount);
			}
		}
		return mounts;
	}

	/**
	 * Scan given package name or part of a package name
	 * 
	 * @param packageName
	 *            a package to scan (e.g., "org.mycompany.pages)
	 * @return An {@link AnnotatedMountList}
	 */
	public AnnotatedMountList scanPackage(String packageName)
	{
		return scanList(getPackageMatches(packageName));
	}

	/**
	 * Scan given a Spring search pattern.
	 * 
	 * @param pattern
	 * @return An {@link AnnotatedMountList}
	 */
	public AnnotatedMountList scanPattern(String pattern)
	{
		return scanList(getPatternMatches(pattern));
	}


	/**
	 * Scan a list of classes which are annotated with MountPath
	 * 
	 * @param mounts
	 * @return An {@link AnnotatedMountList}
	 */
	@SuppressWarnings({ "unchecked" })
	protected AnnotatedMountList scanList(List<Class<?>> mounts)
	{
		AnnotatedMountList list = new AnnotatedMountList();
		for (Class<?> mount : mounts)
		{
			Class<? extends Page> page = (Class<? extends Page>)mount;
			scanClass(page, list);
		}
		return list;
	}

	/**
	 * Scan given a class that is a sublass of {@link Page}.
	 * 
	 * @param pageClass
	 *            {@link Page} subclass to scan
	 * @return An {@link AnnotatedMountList} containing the primary and alternate strategies created
	 *         for the class.
	 */
	public AnnotatedMountList scanClass(Class<? extends Page> pageClass)
	{
		AnnotatedMountList list = new AnnotatedMountList();
		scanClass(pageClass, list);
		return list;
	}

	/**
	 * Magic of all this is done here.
	 * 
	 * @param pageClass
	 * @param list
	 */
	private void scanClass(Class<? extends Page> pageClass, AnnotatedMountList list)
	{
		MountPath mountPath = pageClass.getAnnotation(MountPath.class);
		if (mountPath == null)
			return;

		// alternates
		for (String alt : mountPath.alt())
		{
			list.add(getRequestMapper(alt, pageClass));
		}

		String path = mountPath.value();

		// default if no explicit path is provided
		if ("".equals(path))
		{
			path = getDefaultMountPath(pageClass);
		}

		list.add(getRequestMapper(path, pageClass));
	}

	/**
	 * Returns the default mapper given a mount path and class.
	 * 
	 * @param mountPath
	 * @param pageClass
	 * @return {@link MountedMapper}
	 */
	public IRequestMapper getRequestMapper(String mountPath,
	Class<? extends IRequestablePage> pageClass)
	{
		if ("/".equals(mountPath)) {
			return new HomePageMapper(pageClass);
		}
		return new MountedMapper(mountPath, pageClass);
	}

	/**
	 * Returns the default mount path for a given class (used if the path has not been specified in
	 * the <code>@MountPath</code> annotation). By default, this method returns the
	 * pageClass.getSimpleName().
	 * 
	 * @param pageClass
	 * @return the default mount path for pageClass
	 */
	public String getDefaultMountPath(Class<? extends IRequestablePage> pageClass)
	{
		return pageClass.getSimpleName();
	}
}
