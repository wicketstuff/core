package org.wicketstuff.lambda;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * {@link Consumer} that is {@link Serializable}.
 *
 * @param <T>
 *            - the type of the input to the operation
 *            
 * @deprecated Use {@link org.danekja.java.util.function.serializable.SerializableConsumer} instead           
 */
public interface SerializableConsumer<T> extends Consumer<T>, Serializable {

}
