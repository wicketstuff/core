package org.wicketstuff.async.components;

import org.wicketstuff.async.task.AbstractTaskModel;
import org.wicketstuff.async.task.DefaultTaskManager;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.util.time.Duration;

import java.util.concurrent.TimeUnit;

public class TestPage extends WebPage implements IRunnableFactory {

    private final Form<?> form;
    private final ProgressButton button;
    private final ProgressBar bar;

    private Runnable runnable;

    private boolean taskStart, taskSuccess, taskCancel, taskError;

    public TestPage() {

        AbstractTaskModel model = DefaultTaskManager.getInstance().makeModel(5L, TimeUnit.MINUTES);

        form = new Form<Void>("form");
        button = new ProgressButton("button", form, model, this, Duration.milliseconds(300L)) {
            @Override
            protected void onTaskStart(AbstractTaskModel taskModel) {
                taskStart = true;
            }

            @Override
            protected void onTaskSuccess(AbstractTaskModel taskModel) {
                taskSuccess = true;
            }

            @Override
            protected void onTaskCancel(AbstractTaskModel taskModel) {
                taskCancel = true;
            }

            @Override
            protected void onTaskError(AbstractTaskModel taskModel) {
                taskError = true;
            }
        };


        bar = new ProgressBar("bar", button);

        form.add(button);
        form.add(bar);
        add(form);

    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public Runnable getRunnable() {
        return runnable;
    }

    public boolean isTaskStart() {
        return taskStart;
    }

    public boolean isTaskSuccess() {
        return taskSuccess;
    }

    public boolean isTaskCancel() {
        return taskCancel;
    }

    public boolean isTaskError() {
        return taskError;
    }

    public Form<?> getForm() {
        return form;
    }

    public ProgressButton getButton() {
        return button;
    }

    public ProgressBar getBar() {
        return bar;
    }
}
