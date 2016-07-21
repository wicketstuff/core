package org.wicketstuff.async.components;

import java.io.Serializable;

class StateDescription implements Serializable {

    private final TaskState taskState;
    private final InteractionState interactionState;

    StateDescription(TaskState taskState, InteractionState interactionState) {
        this.taskState = taskState;
        this.interactionState = interactionState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateDescription that = (StateDescription) o;

        if (interactionState != that.interactionState) return false;
        if (taskState != that.taskState) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskState.hashCode();
        result = 31 * result + interactionState.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StateDescription{" +
                "taskState=" + taskState +
                ", interactionState=" + interactionState +
                '}';
    }

    TaskState getTaskState() {
        return taskState;
    }

    InteractionState getInteractionState() {
        return interactionState;
    }
}
