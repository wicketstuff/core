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
package org.wicketstuff.wicket.mount.processor;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.wicketstuff.wicket.mount.annotation.AutoMount;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;
import org.wicketstuff.wicket.mount.core.processor.AbstractAutoMountAnnotationProcessor;
import org.wicketstuff.wicket.mount.core.processor.AutoMountContext;

/**
 * @author jsarman
 */
@SupportedAnnotationTypes({
		"org.wicketstuff.wicket.mount.annotation.AutoMount",
		"org.wicketstuff.wicket.mount.core.annotation.MountPath"
})
public class AutoMountAnnotationProcessor extends AbstractAutoMountAnnotationProcessor
{

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
	                       RoundEnvironment roundEnv)
	{
		try
		{
			for (Element autoMountElement : roundEnv.getElementsAnnotatedWith(AutoMount.class))
			{

				final AutoMountContext context = getAutoMountContext(autoMountElement, AutoMount.class);
				if (context == null)
				{
					continue;
				}

				for (Element elem : roundEnv.getElementsAnnotatedWith(MountPath.class))
				{
					final MountPath mp = elem.getAnnotation(MountPath.class);
					processMountPoint(context, elem, mp);
				}

				generateSource(context);
			}

			return true;
		} catch (IOException ex)
		{
			Logger.getLogger(AutoMountAnnotationProcessor.class.getName()).log(Level.SEVERE, null, ex);
		}
		return true;
	}

	@Override
	protected void setPackagesToScan(AutoMountContext context)
	{
		final String[] packagesToScan;
		AutoMount annotation = context.getAppAnnotation(AutoMount.class);
		if (annotation != null)
		{
			packagesToScan = annotation.packagesToScan();
		} else
		{
			packagesToScan = new String[]{};
		}
		if (packagesToScan.length == 0)
		{
			context.addPackageToScan(context.getApplicationPackage().getQualifiedName().toString() + ".*");
		} else
		{
			context.addPackagesToScan(packagesToScan);
		}
	}

	@Override
	protected String getDefaultRootPath(AutoMountContext context, TypeElement elem)
	{
		AutoMount annotation = context.getAppAnnotation(AutoMount.class);
		if (annotation != null)
		{
			return annotation.defaultRoot().replaceAll("^/+|/+$", "");
		}
		return "";
	}

	@Override
	protected String getDefaultMimeExtension(AutoMountContext context, TypeElement elem)
	{
		String mimeExtension = "";
		AutoMount annotation = context.getAppAnnotation(AutoMount.class);
		if (annotation != null)
		{
			mimeExtension = annotation.mimeExtension();
		}
		if (!mimeExtension.isEmpty() && !mimeExtension.startsWith("."))
		{
			mimeExtension = "." + mimeExtension;
		}
		return mimeExtension;
	}
}
