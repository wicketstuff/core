package org.wicketstuff.async.components;

/**
 * Describes the interaction state of a task.
 */
public enum InteractionState {

    /**
     * The task can be started and was never started before.
     */
    STARTABLE,

    /**
     * The task can be started after it was already started before.
     */
    RESTARTABLE,

    /**
     * The task can be canceled.
     */
    CANCELABLE,

    /**
     * The user cannot currently interact with the task.
     */
    NON_INTERACTIVE;

    static InteractionState findInteractionState(ProgressButton progressButton) {
        if (progressButton.canStart()) {
            return STARTABLE;
        } else if (progressButton.canRestart()) {
            return RESTARTABLE;
        } else if (progressButton.canInterrupt()) {
            return CANCELABLE;
        } else {
            return NON_INTERACTIVE;
        }
    }

}
