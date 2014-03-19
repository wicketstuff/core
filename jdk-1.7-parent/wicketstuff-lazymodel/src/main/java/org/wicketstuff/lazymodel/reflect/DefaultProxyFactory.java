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
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.List;

import net.sf.cglib.core.DefaultNamingPolicy;
import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

/**
 * Default factory of proxies, utilizing {@code cglib} for dynamic class
 * creation and {@code objenesis} for constructor-less instance creation.
 */
public final class DefaultProxyFactory implements IProxyFactory {

	private final NamingPolicy NAMING_POLICY = new DefaultNamingPolicy() {
		protected String getTag() {
			return "LAZY";
		};
	};

	private final Objenesis objenesis = new ObjenesisStd();

	@Override
	public Class<?> createClass(Class<?> clazz) {
		Class<?>[] interfaces;

		if (clazz.isInterface()) {
			interfaces = new Class[1];
			interfaces[0] = clazz;
			clazz = Object.class;
		} else if (Proxy.isProxyClass(clazz)) {
			interfaces = clazz.getInterfaces();
			clazz = Object.class;
		} else if (Factory.class.isAssignableFrom(clazz)) {
			interfaces = null;
			clazz = clazz.getSuperclass();
		} else {
			interfaces = null;
		}

		if (Modifier.isFinal(clazz.getModifiers())) {
			// cannot proxy final classes
			return null;
		}

		Enhancer enhancer = new Enhancer() {
			/**
			 * Prevent filtering of private constructors in super implementation.
			 */
			@SuppressWarnings("rawtypes")
			@Override
			protected void filterConstructors(Class sc, List constructors) {
			}
		};
		enhancer.setUseFactory(true);
		enhancer.setSuperclass(clazz);
		enhancer.setInterfaces(interfaces);
		enhancer.setNamingPolicy(NAMING_POLICY);
		enhancer.setCallbackTypes(new Class[] { MethodInterceptor.class });

		return enhancer.createClass();
	}

	@Override
	public Object createInstance(final Class<?> proxyClass,
			final Callback callback) {
		Factory proxy = (Factory) objenesis.newInstance(proxyClass);
		proxy.setCallback(0, new MethodInterceptorImplementation(callback));
		return proxy;
	}

	@Override
	public Callback getCallback(Object proxy) {
		if (proxy instanceof Factory) {
			MethodInterceptorImplementation interceptor = (MethodInterceptorImplementation) ((Factory) proxy)
					.getCallback(0);

			return interceptor.callback;
		}
		return null;
	}

	private final class MethodInterceptorImplementation implements
			MethodInterceptor {

		public final Callback callback;

		private MethodInterceptorImplementation(Callback callback) {
			this.callback = callback;
		}

		@Override
		public Object intercept(Object obj, Method method, Object[] args,
				MethodProxy proxy) throws Throwable {
			return callback.on(obj, method, args);
		}
	}

}
