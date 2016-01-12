package org.wicketstuff.lambda;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * {@link Consumer} that is {@link Serializable}.
 *
 * @param <T>
 *            - the type of the input to the operation
 */
public interface SerializableConsumer<T> extends Consumer<T>, Serializable {

}
