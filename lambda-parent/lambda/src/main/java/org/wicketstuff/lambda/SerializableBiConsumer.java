package org.wicketstuff.lambda;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * {@link BiConsumer} that is {@link Serializable}.
 *
 * @param <T>
 *            - the type of the first argument to the operation
 * @param <U>
 *            - the type of the second argument to the operation
 * @deprecated Use {@link org.danekja.java.util.function.serializable.SerializableBiConsumer} instead
 */
public interface SerializableBiConsumer<T, U> extends Serializable, BiConsumer<T, U> {

}
