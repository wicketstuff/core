package org.wicketstuff.lambda;

import java.util.Optional;

/**
 * {@link SerializableFunction} mapping {@code T} to {@code R}. Wraps a 
 * {@link SerializableFunction} mapping {@code T} to {@link Optional}<{@code R}>. The {@link #apply} method unwraps the {@link Optional}<{@code R}> value to
 * either {@code R} or a default value if the {@link Optional} is null.
 *
 * @param <T>
 *            - the type of the input to the function
 * @param <R>
 *            - the type of the result of the function
 */
public class OptionalFunction<T, R> implements SerializableFunction<T, R> {

	private SerializableFunction<T, Optional<R>> opFunction;
	private SerializableSupplier<R> defaultValueSupplier;

	/**
	 * @param opFunction
	 *            {@link ISerializableFunction} mapping {@code T} to an {@link Optional}
	 *            {@code R} value
	 */
	public OptionalFunction(SerializableFunction<T, Optional<R>> opFunction) {
		this(opFunction, () -> null);
	}

	/**
	 * @param opFunction
	 *            {@link ISerializableFunction} mapping {@code T} to an {@link Optional}
	 *            {@code R} value
	 * @param defaultValueSupplier
	 *            {@link ISerializableSupplier} that is invoked to provide a
	 *            default value for the function if it returns {@code null}
	 */
	public OptionalFunction(SerializableFunction<T, Optional<R>> opFunction,
			SerializableSupplier<R> defaultValueSupplier) {
		this.opFunction = opFunction;
		this.defaultValueSupplier = defaultValueSupplier;
	}

	@Override
	public R apply(T t) {
		return opFunction.apply(t).orElse(defaultValueSupplier.get());
	}

}
