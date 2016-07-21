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
package org.wicketstuff.lazymodel.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reflection utilities.
 * 
 * @author svenmeier
 */
public final class Reflection
{

	private static Logger log = LoggerFactory.getLogger(Reflection.class);
	
	private Reflection()
	{
	}

	/**
	 * Get the resulting type of invoking a method.
	 * 
	 * @param declaringType
	 *            declaring class of the method
	 * @param type
	 *            return type of method
	 * @return resulting type or {@code null} if not known
	 */
	public static Type resultType(Type declaringType, Type type)
	{
		if (type instanceof TypeVariable)
		{
			TypeVariable<?> typeVariable = (TypeVariable<?>)type;

			while (true)
			{
				Class<?> clazz;

				if (declaringType instanceof ParameterizedType)
				{
					ParameterizedType parmeterizedType = (ParameterizedType)declaringType;

					Type variableType = variableType(parmeterizedType, typeVariable);
					if (variableType != null)
					{
						if (variableType instanceof TypeVariable<?>) {
							if (declaringType instanceof BacktrackingParameterizedType) {
								type = resultType(((BacktrackingParameterizedType)declaringType).declaringType, variableType);
							} else {
								log.debug("typeVariable {} resolves to typeVariable {}", typeVariable, variableType);
								type = null;
							}
						} else {
							type = variableType;
						}
						break;
					}

					clazz = (Class<?>)parmeterizedType.getRawType();
				}
				else if (declaringType instanceof Class)
				{
					clazz = (Class<?>)declaringType;
				}
				else
				{
					log.debug("unsupported ownerType {}", declaringType);
					type = null;
					break;
				}

				declaringType = clazz.getGenericSuperclass();
				if (declaringType == Object.class)
				{
					log.debug("typeVariable {} cannot be resolved", typeVariable);
					type = null;
					break;
				}
			}
		} else if (type instanceof ParameterizedType) {
			type = new BacktrackingParameterizedType(declaringType, (ParameterizedType)type);
		}

		return type;
	}

	/**
	 * Get the type for a class type variable.
	 * 
	 * @param previousType
	 *            the owning type
	 * @param variable
	 *            the variable
	 * @return type or {@code null}
	 */
	public static Type variableType(ParameterizedType type, TypeVariable<?> variable)
	{

		Class<?> clazz = (Class<?>)type.getRawType();
		TypeVariable<?>[] typeParameters = clazz.getTypeParameters();

		String name = variable.getName();
		int index = 0;
		for (TypeVariable<?> candidate : typeParameters)
		{
			if (candidate.getName().equals(name))
			{
				return type.getActualTypeArguments()[index];
			}
			index++;
		}

		return null;
	}

	/**
	 * Get the {@link Class} for a generic type.
	 * 
	 * @param type
	 *            {@link Class} or {@link ParameterizedType}
	 * @return class class
	 * @throws IllegalArgumentException
	 *             if type doesn't represent a class
	 */
	public static Class<?> getClass(Type type)
	{
		Class<?> clazz;

		if (type instanceof Class)
		{
			clazz = ((Class<?>)type);
		}
		else if (type instanceof ParameterizedType)
		{
			clazz = (Class<?>)((ParameterizedType)type).getRawType();
		}
		else if (type == null)
		{
			throw new IllegalArgumentException("type must not be null");
		}
		else
		{
			throw new IllegalArgumentException(String.format(
				"%s is not a class or parameterizedType", type));
		}

		return clazz;
	}

	/**
	 * Is the given method a JavaBeans getter.
	 * 
	 * @param method
	 *            method to test
	 * @return {@code true} if method is a getter
	 */
	public static boolean isGetter(Method method)
	{
		String name = method.getName();

		if (method.getParameterTypes().length == 0)
		{
			Class<?> returnType = method.getReturnType();

			if (name.startsWith("get") && name.length() > 3 &&
				Character.isUpperCase(name.charAt(3)) && returnType != Void.TYPE)
			{
				return true;
			}

			if (name.startsWith("is") && name.length() > 2 &&
				Character.isUpperCase(name.charAt(2)) &&
				(returnType == Boolean.TYPE || returnType == Boolean.class))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Does the given method represent a {@link List} index.
	 * 
	 * @param method
	 *            method to test
	 * @return {@code true} if list index
	 */
	public static boolean isListIndex(Method method)
	{
		if ("get".equals(method.getName()))
		{
			Class<?>[] parameters = method.getParameterTypes();
			if (parameters.length == 1 && parameters[0] == Integer.TYPE)
			{
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Allows backtracking of type variables.
	 */
	private static class BacktrackingParameterizedType implements ParameterizedType {

		private Type declaringType;
		
		private ParameterizedType type;

		public BacktrackingParameterizedType(Type declaringType, ParameterizedType type)
		{
			this.declaringType = declaringType;
			this.type = type;
		}

		@Override
		public Type[] getActualTypeArguments()
		{
			return type.getActualTypeArguments();
		}

		@Override
		public Type getRawType()
		{
			return type.getRawType();
		}

		@Override
		public Type getOwnerType()
		{
			return type.getOwnerType();
		}
	}
}