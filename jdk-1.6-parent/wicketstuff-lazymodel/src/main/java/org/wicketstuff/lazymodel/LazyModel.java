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
package org.wicketstuff.lazymodel;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IObjectClassAwareModel;
import org.apache.wicket.model.IPropertyReflectionAwareModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.Objects;
import org.wicketstuff.lazymodel.reflect.CachingMethodResolver;
import org.wicketstuff.lazymodel.reflect.CachingProxyFactory;
import org.wicketstuff.lazymodel.reflect.DefaultMethodResolver;
import org.wicketstuff.lazymodel.reflect.DefaultProxyFactory;
import org.wicketstuff.lazymodel.reflect.Reflection;
import org.wicketstuff.lazymodel.reflect.IMethodResolver;
import org.wicketstuff.lazymodel.reflect.IProxyFactory;
import org.wicketstuff.lazymodel.reflect.IProxyFactory.Callback;

/**
 * A model for lazy evaluations:
 * 
 * <pre>
 * IModel&lt;String&gt; model = model(from(a).getB().getStrings().get(&quot;key&quot;));
 * 
 * model.setObject(&quot;value&quot;);
 * 
 * String string = model.getObject();
 * </pre>
 * 
 * Evaluations can be nested too:
 * 
 * <pre>
 * IModel&lt;C&gt; model = model(from(a).getB().getCs().get(from(d).getIndex()));
 * </pre>
 * 
 * @param T
 *            model object type
 * 
 * @author svenmeier
 */
@SuppressWarnings("unchecked")
public class LazyModel<T> implements IModel<T>, IObjectClassAwareModel<T>,
		IObjectTypeAwareModel<T>, IPropertyReflectionAwareModel<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * The resolver for {@link Method}s.
	 */
	private static final IMethodResolver methodResolver = new CachingMethodResolver(
			new DefaultMethodResolver());

	/**
	 * The factory for proxies.
	 */
	private static final IProxyFactory proxyFactory = new CachingProxyFactory(
			new DefaultProxyFactory());

	/**
	 * If not null containing the last evaluation which couldn't be proxied
	 * (i.e. is was primitive or final).
	 * 
	 * @see #wrap(Object)
	 */
	private static final ThreadLocal<Evaluation> lastNonProxyableEvaluation = new ThreadLocal<Evaluation>();

	private static final Object[] EMPTY_ARGS = new Object[0];

	/**
	 * The target of the evaluation.
	 */
	protected final Object target;

	/**
	 * Each invoked method's identifier followed by its arguments.
	 */
	protected final Object stack;

	LazyModel(Object target, Object stack) {
		this.target = target;
		this.stack = stack;
	}

	/**
	 * Wrap this model in a {@link LoadableDetachableModel}.
	 * 
	 * @return model wrapper
	 */
	public IModel<T> loadableDetachable() {
		return new LoadableDetachableWrapper();
	}

	/**
	 * Get the evaluation result's class.
	 * 
	 * @return result class or {@code null} if it cannot be determined
	 */
	@Override
	public Class<T> getObjectClass() {
		Type type = getObjectType();
		if (type == null) {
			return null;
		}
		return (Class<T>) Reflection.getClass(type);
	}

	/**
	 * Get the evaluation result's type.
	 * 
	 * @return result type, i.e. {@link Class} or {@link ParameterizedType}
	 * @throws WicketRuntimeException
	 *             if this model is not bound to a target
	 */
	public Type getObjectType() {
		checkBound();

		Type type = getTargetType();
		if (type != null) {
			StackIterator iterator = new StackIterator();
			while (iterator.hasNext()) {
				iterator.next(Reflection.getClass(type));

				Type previousType = type;
				type = iterator.getType();
				if (type instanceof TypeVariable) {
					if (previousType instanceof ParameterizedType) {
						type = Reflection.variableType(
								(ParameterizedType) previousType,
								(TypeVariable<?>) type);
					} else {
						type = Object.class;
					}
				}
			}
		}

		return type;
	}

	/**
	 * LazyModel does not support field access.
	 * 
	 * @return always {@code null}
	 */
	@Override
	public Field getPropertyField() {
		return null;
	}
	
	/**
	 * Get the final getter of the evaluation.
	 * 
	 * @return method representing a JavaBeans getter or {@code null}
	 */
	@Override
	public Method getPropertyGetter() {
		checkBound();

		Type type = getTargetType();
		if (type != null) {
			StackIterator iterator = new StackIterator();
			while (iterator.hasNext()) {
				iterator.next(Reflection.getClass(type));

				Type previousType = type;
				type = iterator.getType();
				if (type instanceof TypeVariable) {
					if (previousType instanceof ParameterizedType) {
						type = Reflection.variableType(
								(ParameterizedType) previousType,
								(TypeVariable<?>) type);
					} else {
						type = Object.class;
					}
				}
			}

			if (Reflection.isGetter(iterator.method)) {
				return iterator.method;
			}
		}
		
		return null;
	}
	
	/**
	 * Get the final setter of the evaluation.
	 * 
	 * @return method representing a JavaBeans setter or {@code null}
	 */
	@Override
	public Method getPropertySetter() {
		checkBound();

		Method getter = getPropertyGetter();
		if (getter == null) {
			return null;
		}
		
		return methodResolver.getSetter(getter);
	}
	
	@Override
	public void detach() {
		if (target instanceof IDetachable) {
			((IDetachable) target).detach();
		}

		if (stack instanceof IDetachable) {
			((IDetachable) stack).detach();
		}

		if (stack instanceof Object[]) {
			for (Object object : (Object[]) stack) {
				if (object instanceof IDetachable) {
					((IDetachable) object).detach();
				}
			}
		}
	}

	/**
	 * Get the evaluation result.
	 * 
	 * @return evaluation result
	 * @throws WicketRuntimeException
	 *             if this model is not bound to a target
	 */
	public T getObject() {
		checkBound();

		Object result = target;
		if (result instanceof IModel) {
			result = ((IModel<T>) result).getObject();
		}

		StackIterator iterator = new StackIterator();
		while (result != null && iterator.hasNext()) {
			iterator.next(result.getClass());

			result = iterator.get(result);
		}

		return (T) result;
	}

	/**
	 * Set the evaluation result.
	 * 
	 * @param evaluation
	 *            result
	 * @throws WicketRuntimeException
	 *             if this model is not bound to a target
	 */
	public void setObject(T result) {
		checkBound();

		Object target = this.target;

		StackIterator iterator = new StackIterator();
		if (!iterator.hasNext()) {
			if (target instanceof IModel) {
				((IModel<T>) target).setObject(result);
				return;
			} else {
				throw new UnsupportedOperationException();
			}
		}

		if (target instanceof IModel) {
			target = ((IModel<T>) target).getObject();
		}
		iterator.next(target.getClass());

		while (target != null && iterator.hasNext()) {
			target = iterator.get(target);

			iterator.next(target.getClass());
		}

		iterator.set(target, result);
	}

	/**
	 * Get the target of this evaluation.
	 * 
	 * @return target, may be {@code null} if this model is not bound to a
	 *         target
	 */
	public Object getTarget() {
		return target;
	}

	/**
	 * Bind this model to a new target.
	 * 
	 * @param target
	 *            target to bind to
	 * @return bound model
	 */
	public LazyModel<T> bind(Object target) {
		return new LazyModel<T>(target, stack);
	}

	/**
	 * String representation of the evaluation.
	 * 
	 * @see #getPath()
	 */
	@Override
	public String toString() {
		if (target == null) {
			return "";
		}

		return getPath();
	}

	/**
	 * Get the invoked method path for the evaluation.
	 * <p>
	 * For evaluations accessing simple properties only, the representation
	 * equals the property expression of a corresponding {@link PropertyModel}.
	 * 
	 * @return invoked method path
	 * @throws WicketRuntimeException
	 *             if this model is not bound
	 * @see PropertyModel#getPropertyExpression()
	 */
	public String getPath() {
		checkBound();

		StringBuilder string = new StringBuilder();

		Type type = getTargetType();
		if (type == null) {
			throw new WicketRuntimeException("cannot detect target type");
		}

		StackIterator iterator = new StackIterator();
		while (iterator.hasNext()) {
			iterator.next(Reflection.getClass(type));

			if (string.length() > 0) {
				string.append(".");
			}
			string.append(iterator.getId().toString());

			type = iterator.getType();
		}

		return string.toString();
	}

	/**
	 * The type of the target.
	 */
	private Type getTargetType() {
		Type type = null;

		if (target instanceof IModel) {
			if (target instanceof IObjectTypeAwareModel) {
				type = ((IObjectTypeAwareModel<?>) target).getObjectType();
			} else if (target instanceof IObjectClassAwareModel) {
				type = ((IObjectClassAwareModel<?>) target).getObjectClass();
			}

			if (type == null) {
				try {
					type = target.getClass().getMethod("getObject")
							.getGenericReturnType();

					if (type instanceof TypeVariable) {
						type = null;
					}
				} catch (Exception ex) {
				}
			}

			if (type == null) {
				Object object = ((IModel<?>) target).getObject();
				if (object != null) {
					type = object.getClass();
				}
			}
		} else {
			type = target.getClass();
		}

		return type;
	}

	/**
	 * Check that the evaluation is bound to a target.
	 * 
	 * @throw {@link WicketRuntimeException} if not bound to a target
	 */
	private void checkBound() {
		if (target == null) {
			throw new WicketRuntimeException("not bound to a target");
		}
	}

	/**
	 * Iterator over the evaluation's methods.
	 */
	private class StackIterator {

		/**
		 * The current index in the stack.
		 */
		private int index = 0;

		/**
		 * The identifier of the current method.
		 */
		private Serializable id;

		/**
		 * The current method.
		 */
		private Method method;

		/**
		 * The count of arguments of the current method.
		 */
		private int count = 0;

		/**
		 * Is there a next invocation.
		 */
		public boolean hasNext() {
			if (stack instanceof Object[]) {
				return index < ((Object[]) stack).length;
			} else {
				return stack != null && index == 0;
			}
		}

		/**
		 * Iterate to the next invocation on the given class.
		 * 
		 * @param clazz
		 *            class
		 */
		public void next(Class<?> clazz) {
			if (stack instanceof Object[]) {
				id = (Serializable) ((Object[]) stack)[index];
			} else {
				id = (Serializable) stack;
			}

			index += 1;

			method = (Method) methodResolver.getMethod(clazz, id);
			count = method.getParameterTypes().length;
			index += count;
		}

		/**
		 * Get the identifier for the current method.
		 * 
		 * @return identified
		 */
		public Serializable getId() {
			return id;
		}

		/**
		 * Get the type for the current method.
		 * 
		 * @return type
		 */
		public Type getType() {
			return method.getGenericReturnType();
		}

		/**
		 * Get the result for the current method.
		 * 
		 * @param target
		 *            target of method invocation
		 * @return result
		 */
		public Object get(Object target) {
			Object[] args;
			if (count == 0) {
				args = EMPTY_ARGS;
			} else {
				args = new Object[count];
				fillArguments(args);
			}

			try {
				return method.invoke(target, args);
			} catch (Exception ex) {
				throw new WicketRuntimeException(ex);
			}
		}

		/**
		 * Set a result for the current method.
		 * 
		 * @param target
		 *            target of method invocation
		 * @param result
		 */
		public void set(Object target, Object result) {
			Method setter = methodResolver.getSetter(method);

			Object[] args = new Object[count + 1];
			fillArguments(args);
			args[count] = result;

			try {
				setter.invoke(target, args);
			} catch (Exception ex) {
				throw new WicketRuntimeException(ex);
			}
		}
		
		/**
		 * Fill the arguments of the current method in the given array.
		 * 
		 * @param args
		 *            arguments to fill
		 */
		private void fillArguments(Object[] args) {
			for (int a = 0; a < count; a++) {
				Object arg = ((Object[]) stack)[index - count + a];

				if (arg instanceof LazyModel<?>) {
					arg = ((LazyModel<T>) arg).getObject();
				}

				args[a] = arg;
			}
		}
	}

	/**
	 * A method evaluation sequence.
	 */
	@SuppressWarnings("rawtypes")
	protected static class Evaluation implements Callback {

		/**
		 * The target of the evaluation, may be {@code null}.
		 */
		private final Object target;

		/**
		 * Each invoked method followed by its arguments.
		 */
		private final List<Object> stack = new ArrayList<Object>();

		/**
		 * The current type of evaluation result.
		 */
		private Type type;

		protected Evaluation(Object target) {
			this.target = target;

			type = target.getClass();
		}

		protected Evaluation(IModel target) {
			this.target = target;

			if (target instanceof IObjectTypeAwareModel) {
				type = ((IObjectTypeAwareModel) target).getObjectType();
			} else if (target instanceof IObjectClassAwareModel) {
				type = ((IObjectClassAwareModel) target).getObjectClass();
			}

			if (type == null) {
				try {
					type = target.getClass().getMethod("getObject")
							.getGenericReturnType();
				} catch (Exception ex) {
					throw new WicketRuntimeException(ex);
				}

				if (type instanceof TypeVariable<?>) {
					throw new WicketRuntimeException(
							"cannot detect target type");
				}
			}
		}

		protected Evaluation(Type type) {
			this.target = null;

			this.type = type;
		}

		public Object getTarget() {
			return target;
		}

		public Object getStack() {
			if (stack.size() == 0) {
				return null;
			} else if (stack.size() == 1) {
				return methodResolver.getId((Method) stack.get(0));
			} else {
				Object[] array = new Object[stack.size()];
				int index = 0;
				while (index < array.length) {
					Method method = (Method) stack.get(index);

					array[index] = methodResolver.getId(method);

					index += 1;

					for (int p = 0; p < method.getParameterTypes().length; p++) {
						array[index] = stack.get(index);
						index++;
					}
				}
				return array;
			}
		}

		/**
		 * Handle invocation on a result proxy.
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
					Evaluation evaluation = lastNonProxyableEvaluation.get();
					if (evaluation != null) {
						lastNonProxyableEvaluation.remove();
						stack.add(new LazyModel(evaluation.getTarget(),
								evaluation.getStack()));
						continue;
					}
				}

				if (param != null) {
					// could be a proxy of a nested evaluation
					Evaluation evaluation = (Evaluation) proxyFactory
							.getCallback(param);
					if (evaluation != null) {
						stack.add(new LazyModel(evaluation.getTarget(),
								evaluation.getStack()));
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
		 * {@link #lastNonProxyableEvaluation}.
		 * 
		 * @return proxy or {@code null} if evaluation result cannot be proxied
		 */
		public Object proxy() {
			Class clazz = Reflection.getClass(type);

			if (clazz.isPrimitive()) {
				lastNonProxyableEvaluation.set(this);

				return Objects.convertValue(null, clazz);
			}

			Class proxyClass = proxyFactory.createClass(clazz);
			if (proxyClass == null) {
				lastNonProxyableEvaluation.set(this);

				return null;
			}

			return clazz.cast(proxyFactory.createInstance(proxyClass, this));
		}

		/**
		 * Path representation.
		 * 
		 * @return method path
		 */
		public String getPath() {
			StringBuilder path = new StringBuilder();

			int index = 0;
			while (index < stack.size()) {
				if (path.length() > 0) {
					path.append(".");
				}

				Method method = (Method) stack.get(index);
				path.append(methodResolver.getId(method));

				index += 1 + method.getParameterTypes().length;
			}

			return path.toString();
		}
	}

	/**
	 * Start a lazy evaluation.
	 * 
	 * @param target
	 *            the target object
	 * @return a result proxy for further evaluation
	 */
	public static <T> T from(T target) {
		if (target == null) {
			throw new WicketRuntimeException("target must not be null");
		}

		Evaluation evaluation = new Evaluation(target);

		return (T) evaluation.proxy();
	}

	/**
	 * Start a lazy evaluation.
	 * 
	 * @param target
	 *            model holding the target object
	 * @return a result proxy for further evaluation
	 */
	public static <T> T from(IModel<T> target) {
		if (target == null) {
			throw new WicketRuntimeException("target must not be null");
		}

		Evaluation evaluation = new Evaluation(target);

		return (T) evaluation.proxy();
	}

	/**
	 * Start a lazy evaluation.
	 * 
	 * @param targetType
	 *            class of target object
	 * @return a result proxy for further evaluation
	 */
	public static <T> T from(Class<T> targetType) {
		if (targetType == null) {
			throw new WicketRuntimeException("target type must not be null");
		}

		Evaluation evaluation = new Evaluation(targetType);

		return (T) evaluation.proxy();
	}

	private static <R> Evaluation evaluation(R result) {
		Evaluation evaluation = (Evaluation) proxyFactory.getCallback(result);
		if (evaluation == null) {
			evaluation = lastNonProxyableEvaluation.get();
			lastNonProxyableEvaluation.remove();
			if (evaluation == null) {
				throw new WicketRuntimeException("no evaluation result given");
			}
		}
		return evaluation;
	}

	/**
	 * Create a model for the given evaluation.
	 * 
	 * @param result
	 *            evaluation result
	 * @return lazy model
	 */
	public static <R> LazyModel<R> model(R result) {
		Evaluation evaluation = evaluation(result);

		return new LazyModel<R>(evaluation.getTarget(), evaluation.getStack());
	}

	/**
	 * Get the method invocation path for the given evaluation.
	 * 
	 * @param result
	 *            evaluation result
	 * @return method invocation path
	 */
	public static <R> String path(R result) {
		Evaluation evaluation = evaluation(result);

		return evaluation.getPath();
	}

	/**
	 * A wrapper to make a lazy model also loadable detachable.
	 * 
	 * @see LoadableDetachableModel
	 */
	private class LoadableDetachableWrapper extends LoadableDetachableModel<T>
			implements IObjectClassAwareModel<T>, IObjectTypeAwareModel<T> {

		private static final long serialVersionUID = 1L;

		private transient Type type;

		@Override
		protected T load() {
			return LazyModel.this.getObject();
		}

		@Override
		public void setObject(T object) {
			super.setObject(object);

			LazyModel.this.setObject(object);
		}

		@Override
		public Class<T> getObjectClass() {
			return (Class<T>) Reflection.getClass(getObjectType());
		}

		@Override
		public Type getObjectType() {
			if (type == null) {
				type = LazyModel.this.getObjectType();
			}
			return type;
		}

		@Override
		public void detach() {
			super.detach();

			type = null;

			LazyModel.this.detach();
		}
	}
}