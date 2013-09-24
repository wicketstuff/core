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

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.util.lang.Objects;
import org.wicketstuff.lazymodel.reflect.IProxyFactory.Callback;

/**
 * An evaluation of method invocations.
 * 
 * @author svenmeier
 */
@SuppressWarnings("rawtypes")
public class Evaluation implements Callback {

	/**
	 * If not null containing the last invocation result which couldn't be proxied
	 * (i.e. is was primitive or final).
	 * 
	 * @see #proxy()
	 */
	private static final ThreadLocal<Evaluation> lastNonProxyable = new ThreadLocal<Evaluation>();

	/**
	 * The factory of proxies.
	 */
	private IProxyFactory proxyFactory;

	/**
	 * Each invoked method followed by its arguments.
	 */
	public final List<Object> stack = new ArrayList<Object>();

	/**
	 * The current type of evaluation result.
	 */
	private Type type;

	/**
	 * Record an evaluation.
	 * 
	 * @param proxyFactory
	 *            factory for proxies
	 * @param type
	 *            starting type
	 */
	public Evaluation(IProxyFactory proxyFactory, Type type) {
		this.proxyFactory = proxyFactory;
		this.type = type;
	}

	/**
	 * Handle an invocation on a result proxy.
	 * 
	 * @return proxy for the invocation result
	 * 
	 * @see #proxy()
	 */
	@Override
	public Object on(Object obj, Method method, Object[] parameters)
			throws Throwable {
		if ("finalize".equals(method.getName())) {
			super.finalize();
			return null;
		}

		stack.add(method);

		for (Object param : parameters) {
			if (param == null) {
				// could be a non-proxyable nested evaluation
				Evaluation evaluation = lastNonProxyable.get();
				if (evaluation != null) {
					lastNonProxyable.remove();
					stack.add(evaluation);
					continue;
				}
			}

			stack.add(param);
		}

		Type candidate = method.getGenericReturnType();
		if (candidate instanceof TypeVariable) {
			if (type instanceof ParameterizedType) {
				candidate = Reflection.variableType((ParameterizedType) type,
						(TypeVariable) candidate);
			} else {
				candidate = Object.class;
			}
		}
		type = candidate;

		return proxy();
	}

	/**
	 * Create a proxy.
	 * <p>
	 * If the result cannot be proxied, it is accessible via
	 * {@link #lastNonProxyable}.
	 * 
	 * @return proxy or {@code null} if invocation result cannot be proxied
	 */
	@SuppressWarnings("unchecked")
	public Object proxy() {
		Class clazz = Reflection.getClass(type);

		if (clazz.isPrimitive()) {
			lastNonProxyable.set(this);

			return Objects.convertValue(null, clazz);
		}

		Class proxyClass = proxyFactory.createClass(clazz);
		if (proxyClass == null) {
			lastNonProxyable.set(this);

			return null;
		}

		return clazz.cast(proxyFactory.createInstance(proxyClass, this));
	}

	/**
	 * Reverse operation of {@link #proxy()}, i.e. get the evaluation from an
	 * invocation result proxy.
	 * 
	 * @param proxyFactory
	 *            factory of proxies
	 * @param result
	 *            invocation result
	 * @return evaluation
	 */
	public static Evaluation unproxy(IProxyFactory proxyFactory, Object result) {
		Evaluation evaluation = (Evaluation) proxyFactory.getCallback(result);
		if (evaluation == null) {
			evaluation = lastNonProxyable.get();
			lastNonProxyable.remove();
			if (evaluation == null) {
				throw new WicketRuntimeException("no invocation result given");
			}
		}
		return evaluation;
	}
}