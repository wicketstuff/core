package org.wicketstuff.async.demo;

import org.wicketstuff.async.task.IProgressObservableRunnable;

public class GenericTask implements IProgressObservableRunnable {

    private final int steps;
    private final long waitingTime;
    private final RuntimeException e;


    public GenericTask(int steps, long waitingTime) {
        this(steps, waitingTime, null);
    }

    public GenericTask(int steps, long waitingTime, RuntimeException e) {
        this.steps = steps;
        this.waitingTime = waitingTime;
        this.e = e;
    }

    private double progress;
    private String message;

    @Override
    public double getProgress() {
        return progress;
    }

    @Override
    public String getProgressMessage() {
        return message;
    }

    private void setProgress(double progress) {
        this.progress = progress;
    }

    private void setProgressMessage(String message) {
        this.message = message;
    }

    @Override
    public void run() {

        setProgress(0d);
        setProgressMessage(null);

        double progressIncrement = 1d / steps;

        try {

            for (int i = 0; i < steps; i++) {
                Thread.sleep(waitingTime);
                setProgress(this.progress + progressIncrement);
                setProgressMessage(String.format("Step %d of %d", i + 1, steps));
                System.out.printf("Background task: %.2f (%d/%d)%n", progress, i + 1, steps);
            }

            if (e != null) {
                throw e;
            }

        } catch (InterruptedException e) {
            System.out.printf("Interrupted task: %.2f%n", progress);
        }

        System.out.printf("Progress finished: %.2f%n", progress);
    }
}
