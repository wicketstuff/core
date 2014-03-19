package org.wicketstuff.async.task;

import java.util.concurrent.Future;

public class DefaultTaskManagerHook implements ITaskManagerHook {

    private final String id;

    private Runnable runnable;
    private Future<?> future;

    public DefaultTaskManagerHook(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Future<?> getFuture() {
        return future == null ? PseudoFuture.getInstance() : future;
    }

    @Override
    public Runnable getRunnable() {
        return runnable;
    }

    @Override
    public void submit(Runnable runnable, boolean cancelExistent) {
        if (cancelExistent && future != null) {
            future.cancel(true);
        }
        this.runnable = runnable;
        this.future = DefaultTaskManager.getInstance().submit(runnable);
    }
}
