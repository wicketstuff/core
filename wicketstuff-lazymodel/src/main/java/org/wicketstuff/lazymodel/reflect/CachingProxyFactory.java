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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 */
public class CachingProxyFactory implements IProxyFactory {

	private final Map<Class<?>, Class<?>> proxyClasses = new ConcurrentHashMap<Class<?>, Class<?>>();

	private final IProxyFactory factory;

	public CachingProxyFactory(IProxyFactory factory) {
		this.factory = factory;
	}

	@Override
	public Class<?> createClass(Class<?> clazz) {
		Class<?> proxyClazz = proxyClasses.get(clazz);
		if (proxyClazz == null) {
			proxyClazz = factory.createClass(clazz);
			if (proxyClazz == null) {
				proxyClazz = NOT_PROXYABLE.class;
			}
			proxyClasses.put(clazz, proxyClazz);
		}

		if (proxyClazz == NOT_PROXYABLE.class) {
			proxyClazz = null;
		}

		return proxyClazz;
	}

	@Override
	public Object createInstance(final Class<?> proxyClass,
			final Callback callback) {
		return factory.createInstance(proxyClass, callback);
	}

	@Override
	public Callback getCallback(Object proxy) {
		return factory.getCallback(proxy);
	}

	private class NOT_PROXYABLE {
	}
}
