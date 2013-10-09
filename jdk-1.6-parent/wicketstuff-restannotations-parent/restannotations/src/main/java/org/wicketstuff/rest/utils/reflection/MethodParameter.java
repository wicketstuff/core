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

import org.apache.wicket.util.lang.Args;
import org.wicketstuff.rest.annotations.parameters.ValidatorKey;
import org.wicketstuff.rest.resource.MethodMappingInfo;

// TODO: Auto-generated Javadoc
/**
 * The class contains the informations of a method parameter, like its type or its index in the
 * array of method parameters.
 * 
 * @author andrea del bene
 */
public class MethodParameter<T>
{

	/** The parameter class. */
	private final Class<T> parameterClass;

	/** The owner method. */
	private final MethodMappingInfo ownerMethod;

	/** The param index. */
	private final int paramIndex;

	/** Indicates if the parameter is required or not. */
	private final boolean required;

	/** Default value of the method parameter. */
	private final String deaultValue;

	private final String valdatorKey;

	/**
	 * Instantiates a new method parameter.
	 * 
	 * @param type
	 *            the type of the parameter.
	 * @param ownerMethod
	 *            the owner method for the parameter.
	 * @param paramIndex
	 *            the index of the parameter in the array of method's parameters.
	 */
	public MethodParameter(Class<T> type, MethodMappingInfo ownerMethod, int paramIndex)
	{
		Args.notNull(type, "type");
		Args.notNull(ownerMethod, "ownerMethod");

		this.parameterClass = type;
		this.ownerMethod = ownerMethod;
		this.paramIndex = paramIndex;

		Annotation annotation = ReflectionUtils.getAnnotationParam(paramIndex,
			ownerMethod.getMethod());

		this.required = ReflectionUtils.getAnnotationField(annotation, "required", true);
		this.deaultValue = ReflectionUtils.getAnnotationField(annotation, "defaultValue", "");

		annotation = ReflectionUtils.findMethodParameterAnnotation(ownerMethod.getMethod(),
			paramIndex, ValidatorKey.class);

		this.valdatorKey = ReflectionUtils.getAnnotationField(annotation, "value", "");

	}

	/**
	 * Gets the type of the method parameter.
	 * 
	 * @return the parameter class
	 */
	public Class<?> getParameterClass()
	{
		return parameterClass;
	}

	/**
	 * Gets the owner method.
	 * 
	 * @return the owner method
	 */
	public MethodMappingInfo getOwnerMethod()
	{
		return ownerMethod;
	}

	/**
	 * Gets the index of the parameter in the array of method's parameters.
	 * 
	 * @return the parameter index
	 */
	public int getParamIndex()
	{
		return paramIndex;
	}

	/**
	 * Checks if the parameter required.
	 * 
	 * @return true, if is required
	 */
	public boolean isRequired()
	{
		return required;
	}

	/**
	 * Gets the deault value for the parameter.
	 * 
	 * @return the deault value
	 */
	public String getDeaultValue()
	{
		return deaultValue;
	}

	public String getValdatorKey()
	{
		return valdatorKey;
	}

}
