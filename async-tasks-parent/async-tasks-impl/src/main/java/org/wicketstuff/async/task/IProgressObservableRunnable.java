package org.wicketstuff.async.task;

/**
 * A future implementing this interface allows to communicate its progress to the a {@link org.wicketstuff.async.components.ProgressBar}.
 */
public interface IProgressObservableRunnable extends Runnable {

    /**
     * The progress of the current task.
     *
     * @return Should be between {@code 0} and {@code 1}. Other values will be truncated to this interval.
     */
    double getProgress();

    /**
     * Returns a custom message about the progress.
     *
     * @return A message to be displayed to the user.
     */
    String getProgressMessage();
}
