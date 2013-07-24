package org.wicketstuff.async.task;

import org.apache.wicket.model.LoadableDetachableModel;

import java.util.concurrent.*;

/**
 * A model representing a hook into a task manager.
 * <p/>
 * <b>Note:</b> Never create a inner class implementation of this class within a custom implementation of
 * a {@link ITaskManager}. A task manager is in general not serializable and this would therefore break the Wicket
 * contract of a model.
 */
public abstract class AbstractTaskModel extends LoadableDetachableModel<ITaskManagerHook> {

    private final String id;

    protected AbstractTaskModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    private Future<?> getFuture() {
        return getObject().getFuture();
    }

    private Runnable getRunnable() {
        return getObject().getRunnable();
    }

    private IProgressObservableRunnable getProgressObservableRunnable() {
        if (getRunnable() == null) {
            return null;
        } else if (getRunnable() instanceof IProgressObservableRunnable) {
            return (IProgressObservableRunnable) getRunnable();
        } else {
            return null;
        }
    }

    public boolean isSubmitted() {
        return getRunnable() != null;
    }

    public boolean isRunning() {
        return isSubmitted() && !getFuture().isDone();
    }

    public boolean isComplete() {
        return getFuture().isDone() && !getFuture().isCancelled() && !isFailed();
    }

    public boolean isFailed() {
        return evaluateExecutionError() != null;
    }

    public boolean isCancelled() {
        return getFuture().isCancelled();
    }

    public Throwable getExecutionError() {
        return evaluateExecutionError();
    }

    public void submit(Runnable runnable) {
        getObject().submit(runnable, true);
    }

    public void cancel() {
        getFuture().cancel(true);
    }

    private Throwable evaluateExecutionError() {
        try {
            getFuture().get(1L, TimeUnit.MICROSECONDS);
            return null;
        } catch (InterruptedException e) {
            /* this should never happen since this call
            should only occur non-blocking */
            throw new AssertionError(e);
        } catch (ExecutionException e) {
            return e.getCause();
        } catch (CancellationException e) {
            return null;
        } catch (TimeoutException e) {
            /* Task is still running */
            return null;
        }
    }

    public Double getProgress() {
        IProgressObservableRunnable runnable = getProgressObservableRunnable();
        if (runnable != null) {
            return runnable.getProgress();
        } else {
            return null;
        }
    }

    public String getProgressMessage() {
        IProgressObservableRunnable runnable = getProgressObservableRunnable();
        if (runnable != null) {
            return runnable.getProgressMessage();
        } else {
            return null;
        }
    }
}
