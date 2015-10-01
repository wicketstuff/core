package org.wicketstuff.lambda;

import java.io.Serializable;
import java.util.function.BiFunction;

/**
 * {@link BiFunction} that is {@link Serializable}.
 *
 * @param <T>
 *            - the type of the first argument to the function
 * @param <U>
 *            - the type of the second argument to the function
 * @param <R>
 *            - the type of the result of the function
 * 
 */
public interface SerializableBiFunction<T, U, R> extends BiFunction<T, U, R>, Serializable {

}
