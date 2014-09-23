/*
 * Copyright 2014 WicketStuff.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wicketstuff.wicket.mount.core.processor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Context class to manage the construction of Mounts.
 *
 * @author jsarman
 */
public class AutoMountContext
{
	private final PackageElement applicationPackage;
	private final TypeElement applicationClassTypeElement;
	private final List<String> packagesToScan;
	private final Map<TypeElement, String> mountPoints;


	public AutoMountContext(TypeElement applicationClassTypeElement)
	{
		this.applicationPackage = (PackageElement) applicationClassTypeElement.getEnclosingElement();
		this.applicationClassTypeElement = applicationClassTypeElement;
		packagesToScan = new ArrayList<String>();
		mountPoints = new HashMap<TypeElement, String>();
	}

	public <T extends Annotation> T getAppAnnotation(Class<T> annotationClass)
	{
		return applicationClassTypeElement.getAnnotation(annotationClass);
	}

	public PackageElement getApplicationPackage()
	{
		return applicationPackage;
	}

	public TypeElement getApplicationClassTypeElement()
	{
		return applicationClassTypeElement;
	}

	public boolean addPackagesToScan(String... packages)
	{
		return Collections.addAll(packagesToScan, packages);
	}

	public boolean addPackageToScan(String scanPackage)
	{
		return packagesToScan.add(scanPackage);
	}

	public String[] getPackagesToScan()
	{
		return packagesToScan.toArray(new String[packagesToScan.size()]);
	}

	public boolean containsMountPoint(String mountPoint)
	{
		return mountPoints.containsValue(mountPoint);
	}

	public String getMountPoint(TypeElement key)
	{
		return mountPoints.get(key);
	}

	public void setMountPoint(TypeElement key, String path)
	{
		mountPoints.put(key, path);
	}

	public Set<TypeElement> getMountableElements()
	{
		return mountPoints.keySet();
	}

	public Map<TypeElement, String> getMountPoints()
	{
		return Collections.unmodifiableMap(mountPoints);
	}

}
