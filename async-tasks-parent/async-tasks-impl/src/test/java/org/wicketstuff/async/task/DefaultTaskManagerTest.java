package org.wicketstuff.async.task;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultTaskManagerTest {

    private DefaultTaskManager taskManager;

    @BeforeEach
    public void setUp() throws Exception {
        taskManager = new DefaultTaskManager() {
            @Override
            protected AbstractTaskContainer makeTaskContainer(final String id) {
                return new AbstractTaskContainer(id) {
					private static final long serialVersionUID = 1L;

					@Override
                    protected ITaskManagerHook load() {
                        return taskManager.makeTaskManagerHook(id);
                    }
                };
            }
        };
    }

    @Test
    public void testTaskRemovalRemoved() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            final String testId = "test";
            final long removalMilliseconds = 200L;
            final double sleepMultiplier = 2d;

            assertTrue(sleepMultiplier > 1d);

            taskManager.makeOrRenewContainer(testId, removalMilliseconds, TimeUnit.MILLISECONDS);

            Thread.sleep((long) (removalMilliseconds * sleepMultiplier));

            // Since the implementation is built on top of weak references, GC must be triggered manually
            // Depending of the JVM implementation, this test might unfortunately fail
            taskManager.cleanUp();
            System.gc();

            taskManager.getContainerOrFail(testId);
        });
    }

    @Test
    public void testTaskRemovalNonRemoved() throws Exception {

        final String testId = "test";
        final long removalMilliseconds = 200L;
        final double sleepMultiplier = 0.5d;

        assertTrue(sleepMultiplier < 1d);

        taskManager.makeOrRenewContainer(testId, removalMilliseconds, TimeUnit.MILLISECONDS);

        Thread.sleep((long) (removalMilliseconds * sleepMultiplier));

        // Same problem as above
        taskManager.cleanUp();
        System.gc();

        taskManager.getContainerOrFail(testId);
    }

    @Test
    public void testTaskExtension() throws Exception {

        final String testId = "test";
        final long removalMilliseconds = 200L;
        final double sleepMultiplier1 = 0.5d;
        final double sleepMultiplier2 = 2d;

        assertTrue(sleepMultiplier1 < 1d);
        assertTrue(sleepMultiplier2 > 1d);

        taskManager.makeOrRenewContainer(testId, (long) (removalMilliseconds * sleepMultiplier1), TimeUnit.MILLISECONDS);
        taskManager.makeOrRenewContainer(testId, (long) (removalMilliseconds * sleepMultiplier2), TimeUnit.MILLISECONDS);

        Thread.sleep(removalMilliseconds);

        // Same problem as above
        taskManager.cleanUp();
        System.gc();

        taskManager.getContainerOrFail(testId);
    }

    @Test
    public void testExecution() throws Exception {

        final boolean[] val = new boolean[1];

        AbstractTaskContainer taskModel = taskManager.makeContainer(1L, TimeUnit.MINUTES);
        taskModel.submit(new Runnable() {
            @Override
            public void run() {
                val[0] = true;
            }
        });

        // This is assumed to be efficient, even for slow machines
        Thread.sleep(500L);

        assertTrue(val[0]);
    }
}
