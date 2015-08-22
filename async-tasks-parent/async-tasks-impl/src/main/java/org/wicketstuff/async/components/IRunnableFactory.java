package org.wicketstuff.async.components;

import java.io.Serializable;

/**
 * Implementing classes can supply {@link Runnable} instances to a {@link ProgressButton}.
 */
public interface IRunnableFactory extends Serializable {

    /**
     * Returns the runnable to be processed by a {@link ProgressButton}.
     *
     * @return The runnable instance to be processed.
     */
    Runnable getRunnable();
}
