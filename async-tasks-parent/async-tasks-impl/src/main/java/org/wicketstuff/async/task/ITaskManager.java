package org.wicketstuff.async.task;

import java.util.concurrent.TimeUnit;

/**
 * A task manager for managing running tasks. Running tasks can typically not be serialized and should therefore
 * be managed in a separate container. A default implementation is provided by {@link com.blogspot.mydailyjava.wicket.async.task.DefaultTaskManager#getInstance()}.
 * If you prefer for example a Spring managed container, simply implement a {@link DefaultTaskManager} by yourself which returns an
 * {@link AbstractTaskContainer} that back references to the Spring managed bean.
 */
public interface ITaskManager {

    AbstractTaskContainer makeContainer(long lifeTime, TimeUnit unit);

    AbstractTaskContainer makeOrRenewContainer(String id, long lifeTime, TimeUnit unit);

    AbstractTaskContainer getContainerOrFail(String id);

    void cleanUp();
}
