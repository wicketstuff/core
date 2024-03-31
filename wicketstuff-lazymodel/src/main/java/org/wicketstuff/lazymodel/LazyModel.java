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
import java.util.List;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IObjectClassAwareModel;
import org.apache.wicket.model.IPropertyReflectionAwareModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.lazymodel.reflect.CachingMethodResolver;
import org.wicketstuff.lazymodel.reflect.DefaultMethodResolver;
import org.wicketstuff.lazymodel.reflect.Evaluation;
import org.wicketstuff.lazymodel.reflect.IMethodResolver;
import org.wicketstuff.lazymodel.reflect.Reflection;

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
public class LazyModel<T> implements IObjectClassAwareModel<T>,
		IObjectTypeAwareModel<T>, IPropertyReflectionAwareModel<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * The resolver for {@link Method}s.
	 */
	public static IMethodResolver methodResolver = new CachingMethodResolver(
			new DefaultMethodResolver());

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
	 * @return {@link Class}, {@link ParameterizedType} or {@code null} if not available
	 */
	@Override
	public Type getObjectType() {
		if (target == null) {
			return null;
		}

		Type type = getTargetType();
		if (type != null)
		{
			MethodIterator methodIterator = new MethodIterator();
			while (methodIterator.hasNext())
			{
				methodIterator.next(Reflection.getClass(type));

				type = Reflection.resultType(type, methodIterator.method.getGenericReturnType());
				if (type == null) {
					break;
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
			MethodIterator methodIterator = new MethodIterator();
			while (methodIterator.hasNext()) {
				methodIterator.next(Reflection.getClass(type));

				type = Reflection.resultType(type, methodIterator.method.getGenericReturnType());
				if (type == null) {
					return null;
				}
			}

			if (Reflection.isGetter(methodIterator.method)) {
				return methodIterator.method;
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
	@Override
	public T getObject() {
		checkBound();

		Object result = target;
		if (result instanceof IModel) {
			result = ((IModel<T>) result).getObject();
		}

		MethodIterator methodIterator = new MethodIterator();
		while (result != null && methodIterator.hasNext()) {
			methodIterator.next(result.getClass());

			result = methodIterator.get(result);
		}

		return (T) result;
	}

	/**
	 * Set the evaluation result.
	 *
	 * @param result
	 *
	 * @throws WicketRuntimeException
	 *             if this model is not bound to a target
	 */
	@Override
	public void setObject(T result) {
		checkBound();

		Object target = this.target;

		MethodIterator methodIterator = new MethodIterator();
		if (!methodIterator.hasNext()) {
			if (target instanceof IModel) {
				((IModel<T>) target).setObject(result);
				return;
			}
			throw new UnsupportedOperationException();
		}

		if (target instanceof IModel) {
			target = ((IModel<T>) target).getObject();
		}
		if (target == null) {
			throw new WicketRuntimeException("no target");
		}
		methodIterator.next(target.getClass());

		while (target != null && methodIterator.hasNext()) {
			target = methodIterator.get(target);

			methodIterator.next(target.getClass());
		}

		methodIterator.set(target, result);
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
		if (target != null) {
			try
			{
				return getPath();
			}
			catch (WicketRuntimeException noPath)
			{
			}
		}

		return "";
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

		MethodIterator methodIterator = new MethodIterator();
		while (methodIterator.hasNext()) {
			methodIterator.next(Reflection.getClass(type));

			if (string.length() > 0) {
				string.append(".");
			}
			string.append(methodIterator.id.toString());

			type = Reflection.resultType(type, methodIterator.method.getGenericReturnType());
		}

		return string.toString();
	}

	/**
	 * The type of the target.
	 */
	private Type getTargetType() {
		Type type = null;

		if (target instanceof IModel) {
			type = getType((IModel<?>)target);

			if (type instanceof TypeVariable<?>) {
				type = null;
			}

			/*
			 * try to improve if no type available or just a non-generic class
			 */
			if (type == null || type instanceof Class) {
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
	 * Iterator over the methods on the stack.
	 */
	private class MethodIterator {

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
			}
			return stack != null && index == 0;
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

			method = methodResolver.getMethod(clazz, id);
			count = method.getParameterTypes().length;
			index += count;
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
				if ((target instanceof List) && Reflection.isListIndex(method)) {
					if (((List<?>) target).size() <= (Integer)args[0]) {
						// evaluate invalid index as null as PropertyModel does it
						return null;
					}
				}
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

		Evaluation<T> evaluation = new BoundEvaluation<T>(target.getClass(), target);

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

		Evaluation<T> evaluation = new Evaluation<T>(targetType);

		return (T) evaluation.proxy();
	}

	/**
	 * Shortcut for {@link #from(Class)} and {@link #bind(Object)}. Use this for quick model building on top of a
	 * type-erased model.
	 *
	 * @param target The model to bind to.
	 * @param type The type parameter for that model.
	 * @param <T>
	 * @return a result proxy for further evaluation.
	 */
	public static <T> T from(IModel<T> target, Class<T> type) {
		return (T) new BoundEvaluation<T>(type, target).proxy();
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

		Type type = getType(target);
		if (type == null) {
			throw new WicketRuntimeException("cannot detect target type");
		}
		Evaluation<T> evaluation = new BoundEvaluation<T>(type, target);

		return (T) evaluation.proxy();
	}

	/**
	 * @return {@code null} if type cannot be detected
	 */
	@SuppressWarnings("rawtypes")
	private static Type getType(IModel<?> model) {
		Type type = null;

		if (model instanceof IObjectTypeAwareModel) {
			type = ((IObjectTypeAwareModel) model).getObjectType();
		} else if (model instanceof IObjectClassAwareModel) {
			type = ((IObjectClassAwareModel) model).getObjectClass();
		}

		if (type == null) {
			try {
				type = model.getClass().getMethod("getObject")
						.getGenericReturnType();
			} catch (Exception fallThrough) {
			}

			if (type instanceof TypeVariable) {
				type = Reflection.resultType(model.getClass(), type);
			}
		}

		return type;
	}

	/**
	 * Create a model for the given evaluation result.
	 *
	 * @param result
	 *            evaluation result
	 * @return lazy model
	 */
	public static <R> LazyModel<R> model(R result) {
		return model(Evaluation.eval(result));
	}

	/**
	 * Create a model for the given evaluation.
	 *
	 * @param evaluation
	 *            evaluation result
	 * @return lazy model
	 */
	private static <R> LazyModel<R> model(Evaluation<R> evaluation) {
		Object target = null;
		if (evaluation instanceof BoundEvaluation) {
			target = ((BoundEvaluation<R>) evaluation).target;
		}

		Object stack;
		if (evaluation.stack.size() == 0) {
			stack = null;
		} else if (evaluation.stack.size() == 1) {
			stack = methodResolver.getId((Method) evaluation.stack.get(0));
		} else {
			Object[] array = new Object[evaluation.stack.size()];
			int index = 0;
			while (index < array.length) {
				Method method = (Method) evaluation.stack.get(index);

				array[index] = methodResolver.getId(method);

				index += 1;

				for (int p = 0; p < method.getParameterTypes().length; p++) {
					Object param = evaluation.stack.get(index);
					if (param instanceof Evaluation) {
						param = model(param);
					}
					array[index] = param;
					index++;
				}
			}
			stack = array;
		}

		return new LazyModel<R>(target, stack);
	}

	/**
	 * Get the method invocation path for an evaluation.
	 *
	 * @param result
	 *            evaluation result
	 * @return method invocation path
	 */
	public static <R> String path(R result) {
		Evaluation<R> evaluation = Evaluation.eval(result);

		StringBuilder path = new StringBuilder();

		int index = 0;
		while (index < evaluation.stack.size()) {
			if (path.length() > 0) {
				path.append(".");
			}

			Method method = (Method) evaluation.stack.get(index);
			path.append(methodResolver.getId(method));

			index += 1 + method.getParameterTypes().length;
		}

		return path.toString();
	}

	/**
	 * A wrapper to make a lazy model also loadable detachable.
	 *
	 * @see LoadableDetachableModel
	 */
	private class LoadableDetachableWrapper extends LoadableDetachableModel<T>
			implements IObjectClassAwareModel<T>, IObjectTypeAwareModel<T>
	{

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
			Type type = getObjectType();
			if (type == null) {
				return null;
			}
			return (Class<T>) Reflection.getClass(type);
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

	/**
	 * An evaluation which is bound to a target.
	 */
	private static class BoundEvaluation<R> extends Evaluation<R> {

		public final Object target;

		public BoundEvaluation(Type type, Object target) {
			super(type);

			this.target = target;
		}
	}
}
