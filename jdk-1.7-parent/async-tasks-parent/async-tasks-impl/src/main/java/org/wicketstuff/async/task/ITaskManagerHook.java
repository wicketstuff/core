package org.wicketstuff.async.task;

import java.util.concurrent.Future;

/**
 * A hook into a manager instance. Allows a better reuse of {@link AbstractTaskContainer}.
 */
public interface ITaskManagerHook {

    String getId();

    Future<?> getFuture();

    Runnable getRunnable();

    void submit(Runnable runnable, boolean cancelExistent);
}
