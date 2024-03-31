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
package org.wicketstuff.wicket.mount.secure.processor;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;
import org.wicketstuff.wicket.mount.core.processor.AbstractAutoMountAnnotationProcessor;
import org.wicketstuff.wicket.mount.core.processor.AutoMountContext;
import org.wicketstuff.wicket.servlet3.auth.annotation.SecureAutoMount;

/**
 * Compile time processor to generate source code for mount paths.
 *
 * @author jsarman
 */
@SupportedAnnotationTypes({
		"org.wicketstuff.wicket.servlet3.auth.annotation.SecureAutoMount",
		"org.wicketstuff.wicket.mount.annotation.MountPoint"
})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class SecureMountAnnotationProcessor extends AbstractAutoMountAnnotationProcessor
{

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
	                       RoundEnvironment roundEnv)
	{

		try
		{
			for (Element autoMountElement : roundEnv.getElementsAnnotatedWith(SecureAutoMount.class))
			{

				final AutoMountContext context = getAutoMountContext(autoMountElement, SecureAutoMount.class);
				if (context == null)
				{
					continue;
				}

				for (Element elem : roundEnv.getElementsAnnotatedWith(AuthorizeInstantiation.class))
				{
					final AuthorizeInstantiation mp = elem.getAnnotation(AuthorizeInstantiation.class);
					processMountPoint(context, elem, mp);
				}
				for (Element elem : roundEnv.getElementsAnnotatedWith(MountPath.class))
				{
					final MountPath mp = elem.getAnnotation(MountPath.class);
					processMountPoint(context, elem, mp);
				}

				generateSource(context);
			}
			
		} catch (IOException ex)
		{
			Logger.getLogger(SecureMountAnnotationProcessor.class.getName()).log(Level.SEVERE, null, ex);
		}
		return true;
	}

	@Override
	protected String getDefaultRootPath(AutoMountContext context, TypeElement elem)
	{
		SecureAutoMount auto = context.getAppAnnotation(SecureAutoMount.class);
		if (auto == null)
		{
			return "";
		}
		if (isSecureElement(elem))
		{
			return auto.secureRoot().replaceAll("^/+|/+$", "");
		} else
		{
			return auto.defaultRoot().replaceAll("^/+|/+$", "");
		}
	}

	@Override
	protected void setPackagesToScan(AutoMountContext context)
	{
		final String[] packagesToScan;
		SecureAutoMount annotation = context.getAppAnnotation(SecureAutoMount.class);
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
	protected String getDefaultMimeExtension(AutoMountContext context, TypeElement elem)
	{
		String mimeExtension = "";
		SecureAutoMount annotation = context.getAppAnnotation(SecureAutoMount.class);
		if (annotation != null)
		{
			if (isSecureElement(elem))
			{
				mimeExtension = annotation.secureMimeExtension();
			} else
			{
				mimeExtension = annotation.defaultMimeExtension();
			}
		}
		if (!mimeExtension.isEmpty() && !mimeExtension.startsWith("."))
		{
			mimeExtension = "." + mimeExtension;
		}
		return mimeExtension;
	}

	private boolean isSecureElement(TypeElement elem)
	{
		if (elem.getAnnotation(AuthorizeInstantiation.class) != null)
		{
			return true;
		}
		return elem.getEnclosingElement().getAnnotation(AuthorizeInstantiation.class) != null;
	}
}
