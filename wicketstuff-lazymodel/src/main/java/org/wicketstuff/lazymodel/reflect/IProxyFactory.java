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

/**
 * A factory of proxies.
 */
public interface IProxyFactory {

	/**
	 * Callback to a method invocation on a proxy.
	 */
	public static interface Callback {
		public Object on(Object obj, Method method, Object[] args)
				throws Throwable;
	}

	/**
	 * Create a proxy class for the given class.
	 * 
	 * @param clazz
	 *            class to proxy
	 * @return proxy class
	 */
	public Class<?> createClass(Class<?> clazz);

	/**
	 * Create a proxy instance.
	 * 
	 * @param proxyClazz
	 *            class of proxy
	 * @param callback
	 *            callback for invocations
	 * @return proxy instance
	 */
	public Object createInstance(final Class<?> proxyClass,
			final Callback callback);

	/**
	 * Get the callback for the given proxy.
	 * 
	 * @param proxy
	 *            proxy
	 * @return callback or {@code null} if not a proxy
	 */
	public Callback getCallback(Object proxy);
}