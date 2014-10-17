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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.util.lang.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.lazymodel.reflect.IProxyFactory.Callback;

/**
 * An evaluation of method invocations.
 * 
 * @param R
 *            result type
 *            
 * @author svenmeier
 */
@SuppressWarnings("rawtypes")
public class Evaluation<R> implements Callback {
	
	private static final Logger log = LoggerFactory.getLogger(Evaluation.class);

	/**
	 * If not null containing the last invocation result which couldn't be
	 * proxied (i.e. is was primitive or final).
	 * 
	 * @see #proxy()
	 */
	private static final ThreadLocal<Evaluation<?>> lastNonProxyable = new ThreadLocal<Evaluation<?>>();

	/**
	 * The factory for proxies.
	 */
	private static IProxyFactory proxyFactory = new CachingProxyFactory(
			new DefaultProxyFactory());

	/**
	 * Each invoked method followed by its arguments.
	 */
	public final List<Object> stack = new ArrayList<Object>();

	private Type type;
	
	/**
	 * Evaluation of method invocations on the given type.
	 * 
	 * @param type
	 *            starting type
	 */
	public Evaluation(Type type) {
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

		type = Reflection.resultType(type, method.getGenericReturnType());
		if (type == null) {
			log.debug("falling back to raw type for method {}", method);
			type = method.getReturnType();
		}

		return proxy();
	}

	/**
	 * Create a proxy for the current type.
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
	 * evaluation result.
	 * 
	 * @param result
	 *            invocation result
	 * @return evaluation
	 */
	@SuppressWarnings("unchecked")
	public static <R> Evaluation<R> eval(R result) {
		Evaluation<R> evaluation = (Evaluation<R>) proxyFactory.getCallback(result);
		if (evaluation == null) {
			evaluation = (Evaluation<R>) lastNonProxyable.get();
			lastNonProxyable.remove();
			if (evaluation == null) {
				throw new WicketRuntimeException("no invocation result given");
			}
		}
		return evaluation;
	}

	/**
	 * Start evaluation from the give type.
	 * 
	 * @param type
	 *            starting type
	 * @return proxy
	 */
	@SuppressWarnings("unchecked")
	public static <T> T of(Class<T> type) {
		return (T) new Evaluation(type).proxy();
	}
}