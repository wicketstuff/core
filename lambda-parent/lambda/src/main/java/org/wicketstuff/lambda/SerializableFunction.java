package org.wicketstuff.lambda;

import java.io.Serializable;
import java.util.function.Function;

/**
 * {@link Function} that is {@link Serializable}.
 * 
 * @param <T>
 *            - the type of the input to the function
 * @param <R>
 *            - the type of the result of the function
 */
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {

}
