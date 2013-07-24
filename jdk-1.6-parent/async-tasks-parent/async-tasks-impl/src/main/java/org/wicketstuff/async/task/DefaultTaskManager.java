package org.wicketstuff.async.task;

import com.google.common.collect.MapMaker;

import java.util.UUID;
import java.util.concurrent.*;

public abstract class DefaultTaskManager implements ITaskManager {

    private static final DefaultTaskManager INSTANCE = new DefaultTaskManager() {
        @Override
        protected AbstractTaskModel makeTaskModel(final String id) {
            return new SingletonTaskManagerTaskModel(id);
        }
    };

    private static class SingletonTaskManagerTaskModel extends AbstractTaskModel {

        private SingletonTaskManagerTaskModel(String id) {
            super(id);
        }

        @Override
        protected ITaskManagerHook load() {
            return INSTANCE.findHookForId(getId());
        }
    }

    public static DefaultTaskManager getInstance() {
        return INSTANCE;
    }

    private final ExecutorService executorService;

    private final ConcurrentMap<String, ITaskManagerHook> taskManagerHooks;

    private final ConcurrentNavigableMap<Long, ITaskManagerHook> taskManagerRemovalRegister;
    private final ConcurrentMap<ITaskManagerHook, Long> taskManagerRemovalBackRegister;

    public DefaultTaskManager() {
        this.executorService = Executors.newCachedThreadPool();
        this.taskManagerHooks = new MapMaker().weakValues().makeMap();
        this.taskManagerRemovalRegister = new ConcurrentSkipListMap<Long, ITaskManagerHook>();
        this.taskManagerRemovalBackRegister = new MapMaker().weakKeys().makeMap();
    }

    @Override
    public AbstractTaskModel makeModel(long lifeTime, TimeUnit unit) {
        return makeOrRenewModel(UUID.randomUUID().toString(), lifeTime, unit);
    }

    @Override
    public AbstractTaskModel makeOrRenewModel(String id, long lifeTime, TimeUnit unit) {
        cleanUp();
        taskManagerHooks.putIfAbsent(id, makeTaskManagerHook(id));
        registerRemoval(id, lifeTime, unit);
        return makeTaskModel(id);
    }

    private void registerRemoval(String id, long lifeTime, TimeUnit unit) {
        ITaskManagerHook hook = taskManagerHooks.get(id);
        if (hook != null) {

            long removalTime = unit.toMillis(lifeTime) + System.currentTimeMillis();

            // Remove current scheduled removal
            Long currentRemovalTime = taskManagerRemovalBackRegister.get(hook);
            if (currentRemovalTime != null) {
                taskManagerRemovalRegister.remove(currentRemovalTime);
            }

            // Find an empty spot in the sorted map's key register
            removalTime--;
            do {
                removalTime++;
                taskManagerRemovalRegister.putIfAbsent(removalTime, hook);
            } while (taskManagerRemovalRegister.get(removalTime) != hook);

            // Back reference the removal
            taskManagerRemovalBackRegister.put(hook, removalTime);
        }
    }

    @Override
    public AbstractTaskModel getModelOrFail(String id) {
        if (!taskManagerHooks.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Id %s is not registered", id));
        }
        return makeTaskModel(id);
    }

    protected ITaskManagerHook findHookForId(String id) {
        return taskManagerHooks.get(id);
    }

    protected Future<?> submit(Runnable runnable) {
        return executorService.submit(runnable);
    }

    protected abstract AbstractTaskModel makeTaskModel(String id);

    protected ITaskManagerHook makeTaskManagerHook(String id) {
        return new DefaultTaskManagerHook(id);
    }

    @Override
    public void cleanUp() {
        taskManagerRemovalRegister.headMap(System.currentTimeMillis()).clear();
    }

}
