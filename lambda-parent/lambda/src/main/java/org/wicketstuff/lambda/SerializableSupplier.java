package org.wicketstuff.lambda;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * {@link Supplier} that is {@link Serializable}.
 *
 * @param <T>
 *            - the type of results supplied by this supplier
 */
public interface SerializableSupplier<T> extends Supplier<T>, Serializable {

}
