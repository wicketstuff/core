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
package org.wicketstuff.rest.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.wicketstuff.rest.annotations.parameters.AnnotatedParam;

/**
 * Utility methods to work with reflection entities
 * 
 * @author andrea del bene
 * 
 */
public class ReflectionUtils
{
	/**
	 * Check if a parameter is annotated with a given annotation.
	 * 
	 * @param i
	 *            method parameter index.
	 * @param method
	 *            the method the parameter belongs to.
	 * @param targetAnnotation
	 *            the annotation type we want to check for.
	 * 
	 * @return true if the method parameter is annotated with the given annotation, false otherwise.
	 */
	static public boolean isParameterAnnotatedWith(int i, Method method,
		Class<? extends Annotation> targetAnnotation)
	{
		Annotation[][] parametersAnnotations = method.getParameterAnnotations();

		if (parametersAnnotations.length == 0)
			return false;

		Annotation[] parameterAnnotations = parametersAnnotations[i];

		for (int j = 0; j < parameterAnnotations.length; j++)
		{
			Annotation annotation = parameterAnnotations[j];
			if (targetAnnotation.isInstance(annotation))
				return true;
		}
		return false;
	}

	/**
	 * Check if a parameter is annotated with an {@link AnnotatedParam} annotation.
	 * 
	 * @param i
	 *            method parameter index.
	 * @param method
	 *            the method the parameter belongs to.
	 * 
	 * @return true if such an annotation is found, false otherwise.
	 * @see AnnotatedParam
	 */
	static public Annotation getAnnotationParam(int i, Method method)
	{
		Annotation[][] parametersAnnotations = method.getParameterAnnotations();

		if (parametersAnnotations.length == 0)
			return null;

		Annotation[] parameterAnnotations = parametersAnnotations[i];

		for (int j = 0; j < parameterAnnotations.length; j++)
		{
			Annotation annotation = parameterAnnotations[j];
			AnnotatedParam isAnnotatedParam = annotation.annotationType().getAnnotation(
				AnnotatedParam.class);

			if (isAnnotatedParam != null)
				return annotation;
		}

		return null;
	}

	/**
	 * Utility method to find if an annotation type is present in an array of annotations.
	 * 
	 * @param parameterAnnotations
	 *            the array of annotations we will look in.
	 * @param targetAnnotation
	 *            the type of annotation we are looking for.
	 * @return the first occurrence of the targetAnnotation found in the array, null if no
	 *         occurrence was found.
	 */
	static public <T extends Annotation> T findAnnotation(Annotation[] parameterAnnotations,
		Class<T> targetAnnotation)
	{

		for (int i = 0; i < parameterAnnotations.length; i++)
		{
			Annotation annotation = parameterAnnotations[i];

			if (targetAnnotation.isInstance(annotation))
				return targetAnnotation.cast(annotation);
		}

		return null;
	}

	static public <T extends Annotation> T findMethodParameterAnnotation(Method ownerMethod,
		int paramIndex, Class<T> targetAnnotation)
	{
		Annotation[][] paramAnnotations = ownerMethod.getParameterAnnotations();
		return findAnnotation(paramAnnotations[paramIndex], targetAnnotation);
	}

	static public Method findMethod(Class<?> clazz, String name, Class<?>... parameterTypes)
	{
		try
		{
			Method method = clazz.getMethod(name, parameterTypes);
			return method;
		}
		catch (Exception e)
		{
			return null;
		}
	}


	static public <T> T invokeMethod(Object target, String name, Class<?>... parameterTypes)
	{
		Method method = findMethod(target.getClass(), name, parameterTypes);

		try
		{
			return (T)method.invoke(target, parameterTypes);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 
	 * Get the value of an annotation field.
	 * 
	 * @param annotation
	 *            the target annotation
	 * @param fieldName
	 *            the field name
	 * @param defaultValue
	 *            the default value
	 * @return the value of the field.
	 */
	static public <T> T getAnnotationField(Annotation annotation, String fieldName, T defaultValue)
	{
		T methodResult = null;

		if (annotation != null)
			methodResult = ReflectionUtils.invokeMethod(annotation, fieldName);

		return methodResult != null ? methodResult : defaultValue;
	}
}
