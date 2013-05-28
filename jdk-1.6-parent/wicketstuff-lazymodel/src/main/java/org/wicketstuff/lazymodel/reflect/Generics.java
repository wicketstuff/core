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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Generics utilities.
 * 
 * @author svenmeier
 */
public final class Generics {

	private Generics() {
	}

	/**
	 * Get the type for a class type variable.
	 * 
	 * @param owner
	 *            the owning type
	 * @param variable
	 *            the variable
	 * @return type
	 */
	@SuppressWarnings("rawtypes")
	public static Class variableType(ParameterizedType owner,
			TypeVariable variable) {

		Class clazz = (Class) owner.getRawType();
		TypeVariable[] typeParameters = clazz.getTypeParameters();

		String name = variable.getName();
		int index = 0;
		for (TypeVariable candidate : typeParameters) {
			if (candidate.getName().equals(name)) {
				return (Class) owner.getActualTypeArguments()[index];
			}
			index++;
		}

		throw new IllegalArgumentException("no type parameter");
	}

	/**
	 * Get the {@link Class} for a generic type.
	 * 
	 * @param type
	 *            type a {@link Class} or {@link ParameterizedType}
	 * @return class class
	 * @throws IllegalArgumentException
	 *             if type doesn't represent a class
	 */
	public static Class<?> getClass(Type type) {
		Class<?> clazz;

		if (type instanceof Class) {
			clazz = ((Class<?>) type);
		} else if (type instanceof ParameterizedType) {
			clazz = (Class<?>) ((ParameterizedType) type).getRawType();
		} else {
			throw new IllegalArgumentException(String.format("%s is not a class or parameterizedType", type));
		}

		return clazz;
	}
}