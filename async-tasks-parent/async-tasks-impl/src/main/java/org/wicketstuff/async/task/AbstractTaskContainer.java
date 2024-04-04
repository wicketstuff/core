package org.wicketstuff.async.task;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.concurrent.*;

/**
 * A serializable representation of a task that is hooked into a task manager.
 * <p/>
 * <b>Note:</b> Never create a non-static inner subclass of this class within a custom implementation of
 * a {@link ITaskManager}. A task manager is in general not serializable and this would therefore break the Wicket
 * contract of a model this representation is based on.
 */
public abstract class AbstractTaskContainer implements IDetachable {

    private final String id;

    private final LoadableDetachableModel<ITaskManagerHook> taskManagerHook;

    protected AbstractTaskContainer(String id) {
        this.id = id;
        // This model must be implemented as an inner instead of by inheritance to avoid Wicket's model chaining.
        taskManagerHook = new LoadableDetachableModel<ITaskManagerHook>() {
            @Override
            protected ITaskManagerHook load() {
                return AbstractTaskContainer.this.load();
            }
        };
    }

    protected abstract ITaskManagerHook load();

    public String getId() {
        return id;
    }

    private Future<?> getFuture() {
        return taskManagerHook.getObject().getFuture();
    }

    private Runnable getRunnable() {
        return taskManagerHook.getObject().getRunnable();
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
        taskManagerHook.getObject().submit(runnable, true);
    }

    public void cancel() {
        getFuture().cancel(true);
    }

    private Throwable evaluateExecutionError() {
        try {
            getFuture().get(1L, TimeUnit.MICROSECONDS);
            return null;
        } catch (InterruptedException e) {
            /* the chances for this scenario are considerably small
             since this call should only occur quasi-non-blocking */
            return null;
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

    @Override
    public void detach() {
        taskManagerHook.detach();
    }
}
