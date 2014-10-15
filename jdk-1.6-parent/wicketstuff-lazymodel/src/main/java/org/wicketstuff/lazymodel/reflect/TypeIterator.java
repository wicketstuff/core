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
import java.util.ArrayList;
import java.util.List;

/**
 * @author svenmeier
 */
public final class TypeIterator {

	private List<Type> types = new ArrayList<Type>();
	
	public TypeIterator(Type type)
	{
		this.types.add(type);
	}

	public void next(Method method)
	{
		Type returnType = method.getGenericReturnType();
		
		if (returnType instanceof TypeVariable) {
			TypeVariable<?> variable = (TypeVariable<?>)returnType;
			
			returnType = resolve(variable);
			if (returnType == null) {
				// use upper bound as fallback
				returnType = method.getReturnType();
			}
		} else if (returnType instanceof Class) {
			types.clear();
		}

		types.add(returnType);
	}

	/**
	 * Resolve a variable.
	 * 
	 * @param variable
	 * @return
	 */
	private Type resolve(TypeVariable<?> variable) {
		return resolve(variable, types.size() - 1);
	}
	
	private Type resolve(TypeVariable<?> variable, int i)
	{
		Type resolved = resolve(variable, types.get(i));
		if (resolved != null) {
			if (resolved instanceof TypeVariable<?>) {
				return resolve((TypeVariable<?>)resolved, i);
			}
			return resolved;
		}
		
		if (i > 0) {
			return resolve(variable, i - 1);
		}
		
		return null;
	}
	
	private Type resolve(TypeVariable<?> variable, Type type) {
		while (type != Object.class) {
			Class<?> clazz;

			if (type instanceof ParameterizedType) {
				ParameterizedType parmeterizedType = (ParameterizedType)type;
				
				Type variableType = Reflection.variableType(parmeterizedType, variable);
				if (variable != null) {
					return variableType;
				}
				
				clazz = (Class<?>) parmeterizedType.getRawType();
			} else if (type instanceof Class) {
				clazz = (Class<?>) type;
			} else {
				break;
			}
			
			type = clazz.getGenericSuperclass();
		}
		
		return null;
	}

	public Class<?> getType()
	{
		return Reflection.getClass(types.get(types.size() - 1));
	}
}