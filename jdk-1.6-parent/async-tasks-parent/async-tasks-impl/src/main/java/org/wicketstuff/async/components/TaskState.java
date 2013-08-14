package org.wicketstuff.async.components;

import org.wicketstuff.async.task.AbstractTaskContainer;

/**
 * Describes the state of a task.
 */
public enum TaskState {

    /**
     * The task is running and no special event occurred.
     */
    PLAIN_RUNNING,

    /**
     * The task is not running and no special event occured.
     */
    PLAIN_NON_RUNNING,

    /**
     * The task is not running as a result of an error.
     */
    ERROR_NON_RUNNING,

    /**
     * The task is running but was already canceled.
     */
    CANCELED_RUNNING,

    /**
     * The task is not running after it was canceled.
     */
    CANCELED_NON_RUNNING;

    static TaskState findRunningState(AbstractTaskContainer taskContainer) {
        if (taskContainer.isRunning()) {
            if (taskContainer.isCancelled()) {
                return CANCELED_RUNNING;
            } else {
                return PLAIN_RUNNING;
            }
        } else {
            if (taskContainer.isFailed()) {
                return ERROR_NON_RUNNING;
            } else if (taskContainer.isCancelled()) {
                return CANCELED_NON_RUNNING;
            } else {
                return PLAIN_NON_RUNNING;
            }
        }
    }

}
