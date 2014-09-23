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

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementScanner6;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.wicketstuff.wicket.mount.core.annotation.MountPath;

/**
 * @author jsarman
 */
public abstract class AbstractAutoMountAnnotationProcessor extends AbstractProcessor
{

	private TypeElement pageElement;
	private TypeElement applicationElement;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv)
	{
		super.init(processingEnv);
		pageElement = processingEnv.getElementUtils().getTypeElement("org.apache.wicket.Page");
		applicationElement = processingEnv.getElementUtils().getTypeElement("org.apache.wicket.Application");
	}

	private boolean isValidApplicationType(TypeElement typeElement)
	{
		return processingEnv.getTypeUtils().isAssignable(typeElement.asType(), applicationElement.asType());
	}

	protected AutoMountContext getAutoMountContext(Element autoMountElement, Class<? extends Annotation> annotationClass)
	{
		final Annotation autoMount = autoMountElement.getAnnotation(annotationClass);
		if (autoMount == null)
		{
			return null;
		}
		if (autoMountElement.getKind() != ElementKind.CLASS)
		{
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "The " + autoMount.annotationType() + " should only support ElementType.TYPE.");
			return null;
		}
		TypeElement classElement = (TypeElement) autoMountElement;
		if (!isValidApplicationType(classElement))
		{
			processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING,
					"Automounting will not be processed for class "
							+ classElement.getQualifiedName().toString()
							+ " because it is not a subclass of " + applicationElement.getQualifiedName());
			return null;
		}

		final AutoMountContext context = new AutoMountContext(classElement);
		setPackagesToScan(context);
		return context;
	}

	protected abstract void setPackagesToScan(AutoMountContext context);

	protected void processMountPoint(AutoMountContext context, Element elem, Annotation annotation)
	{

		final String customMount = extractMountPath(context, annotation);
		if (elem.getKind() == ElementKind.CLASS)
		{
			TypeElement classElement = (TypeElement) elem;

			if (isValidPageType(classElement, context.getPackagesToScan()))
			{
				if (!customMount.contains("*") && context.containsMountPoint(customMount))
				{
					processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Multiple mount points exist for " + customMount);
				}
				context.setMountPoint(classElement, customMount);
			}
		}
		if (elem.getKind() == ElementKind.PACKAGE)
		{
			final ElementScanner6<Void, AutoMountContext> scanner = new ElementScanner6<Void, AutoMountContext>()
			{
				@Override
				public Void visitType(TypeElement typeElement, AutoMountContext context)
				{
					if (isValidPageType(typeElement, context.getPackagesToScan()))
					{
						//Only add package level mountpoint for type if it doesnt have a set mount point
						if (!customMount.contains("*"))
						{
							context.setMountPoint(typeElement, customMount + "/*");
						} else
						{
							context.setMountPoint(typeElement, customMount);
						}
					}
					return super.visitType(typeElement, context);
				}

			};
			elem.accept(scanner, context);
		}

	}

	protected abstract String getDefaultRootPath(AutoMountContext context, TypeElement elem);

	protected abstract String getDefaultMimeExtension(AutoMountContext context, TypeElement elem);

	protected String extractMountPath(AutoMountContext context, Annotation annotation)
	{
		String path = "/*";
		if (annotation instanceof MountPath)
		{
			path = ((MountPath) annotation).value();
		}

		return path.replaceAll("/+$", "");
	}

	private boolean isValidPageType(TypeElement typeElement, String[] validPackages)
	{
		for (String validPkgName : validPackages)
		{
			final boolean existsInValidPackage;
			final String pkgName = ((PackageElement) typeElement.getEnclosingElement()).getQualifiedName().toString();

			if (validPkgName.endsWith(".*"))
			{
				existsInValidPackage = pkgName.startsWith(validPkgName.substring(0, validPkgName.length() - 2));
			} else
			{
				existsInValidPackage = pkgName.equals(validPkgName);
			}

			if (existsInValidPackage)
			{
				boolean extendsPage = processingEnv.getTypeUtils().isAssignable(typeElement.asType(), pageElement.asType());
				extendsPage &= !typeElement.getModifiers().contains(Modifier.ABSTRACT);
				return extendsPage;
			}
		}
		return false;
	}

	protected String generateGenericName(AutoMountContext context, TypeElement elem, String path)
	{
		if (path == null || path.trim().isEmpty())
		{
			path = "/*";
		}
		path = path.trim();
		if (!path.endsWith("/*"))
		{
			return path;
		}
		final String root;
		if ("/*".equals(path))
		{
			root = getDefaultRootPath(context, elem);
		} else
		{
			root = path.substring(0, path.length() - 2);
		}
		final String elemName = elem.getSimpleName().toString();
		final String mimeExtension = getDefaultMimeExtension(context, elem);

		String genericName = root + "/" + elemName + mimeExtension;

		int i = 1;
		while (context.containsMountPoint(genericName) && i < Integer.MAX_VALUE)
		{
			genericName = root + "/" + elemName + "_" + i++ + mimeExtension;
		}
		return genericName;
	}

	protected void completeAbstractMountPoints(AutoMountContext context)
	{
		// process all the mount points that have a * set in mount point
		for (TypeElement elem : context.getMountableElements())
		{

			String mp = context.getMountPoint(elem);
			if (mp.matches("(.)*\\*(.)*\\*"))
			{
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "MountPoint set for "
						+ elem.getQualifiedName().toString() + " can only contain at most 1 wildcard(*). MountPoint is set to " + mp);
			}
			if (mp.endsWith("/*"))
			{
				final String genericName = generateGenericName(context, elem, mp);
				context.setMountPoint(elem, genericName);
				processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generating generic Mount Point for "
						+ elem.getQualifiedName()
						+ ". Original Mount point set to " + mp
						+ ". New Mount point is " + context.getMountPoint(elem));
			} else
			{
				processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Using set Mount Point for "
						+ elem.getQualifiedName() + ". Mount point is " + context.getMountPoint(elem));
			}
		}
	}

	protected void generateSource(AutoMountContext context) throws IOException
	{
		final JavaFileObject jfo;
		final BufferedWriter bw;

		completeAbstractMountPoints(context);

		String infoClassName = context.getApplicationClassTypeElement().getSimpleName() + "MountInfo";
		jfo = processingEnv.getFiler().createSourceFile(context.getApplicationClassTypeElement().getQualifiedName() + "MountInfo");

		bw = new BufferedWriter(jfo.openWriter());

		Map<TypeElement, String> mountPoints = context.getMountPoints();

		bw.append("package ");
		bw.append(context.getApplicationPackage().getQualifiedName());
		bw.append(";");

		bw.newLine();
		bw.newLine();
		bw.append("import org.wicketstuff.wicket.mount.core.*;");
		bw.newLine();
		bw.append("import org.apache.wicket.request.IRequestMapper;");
		bw.newLine();
		bw.append("import org.apache.wicket.core.request.mapper.MountedMapper;");
		bw.newLine();
		bw.append("import java.util.*;");
		bw.newLine();
		bw.newLine();
		bw.append("public class " + infoClassName + " implements MountInfo");
		bw.newLine();
		bw.append("{");
		bw.newLine();
		bw.append("\t@Override");
		bw.newLine();
		bw.append("\tpublic List<IRequestMapper> getMountList() {");
		bw.newLine();
		bw.append("\t\tList<IRequestMapper> ret = new ArrayList<IRequestMapper>();");
		bw.newLine();
		for (TypeElement ele : mountPoints.keySet())
		{
			bw.append("\t\tret.add(new MountedMapper(\"" + mountPoints.get(ele) + "\", " + ele.getQualifiedName().toString() + ".class));");
			bw.newLine();
		}

		bw.append("\t\treturn ret;");
		bw.newLine();
		bw.append("\t}");
		bw.newLine();
		bw.append("}");
		bw.newLine();
		bw.close();
	}
}
