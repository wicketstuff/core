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

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A cache of a wrapped {@link IMethodResolver}.
 * 
 * @author svenmeier
 */
public class CachingMethodResolver implements IMethodResolver {

	private final IMethodResolver resolver;

	private final Map<String, Method> methods = new ConcurrentHashMap<String, Method>();

	private final Map<Method, Serializable> ids = new ConcurrentHashMap<Method, Serializable>();

	private final Map<Method, Method> setters = new ConcurrentHashMap<Method, Method>();

	public CachingMethodResolver(IMethodResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public Method getMethod(Class<?> owner, Serializable id) {
		String key = owner.getName() + ":" + id;

		Method method = methods.get(key);
		if (method == null) {
			method = resolver.getMethod(owner, id);
			methods.put(key, method);
		}

		return method;
	}

	@Override
	public Serializable getId(Method method) {
		Serializable id = ids.get(method);
		if (id == null) {
			id = resolver.getId(method);
			ids.put(method, id);
		}
		return id;
	}

	@Override
	public Method getSetter(Method getter) {
		Method setter = setters.get(getter);

		if (setter == null) {
			setter = resolver.getSetter(getter);
			setters.put(getter, setter);
		}

		return setter;
	}
}