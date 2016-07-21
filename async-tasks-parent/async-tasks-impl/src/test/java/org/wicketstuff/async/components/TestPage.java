package org.wicketstuff.async.components;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.async.task.AbstractTaskContainer;
import org.wicketstuff.async.task.DefaultTaskManager;

public class TestPage extends WebPage implements IRunnableFactory {

    private final Form<?> form;
    private final ProgressButton button;
    private final ProgressBar bar;
    private final CountDownLatch latch = new CountDownLatch(1);
    private volatile Runnable runnable;

    private boolean taskStart, taskSuccess, taskCancel, taskError;

    public TestPage() {

        AbstractTaskContainer taskContainer = DefaultTaskManager.getInstance().makeContainer(5L, TimeUnit.MINUTES);

        form = new Form<Void>("form");
        button = new ProgressButton("button", form, Model.of(taskContainer), this, Duration.milliseconds(300L)) {
            @Override
            protected void onTaskStart(Optional<AjaxRequestTarget> ajaxRequestTarget) {
                taskStart = true;
            }

            @Override
            protected void onTaskSuccess(Optional<AjaxRequestTarget> ajaxRequestTarget) {
                taskSuccess = true;
            }

            @Override
            protected void onTaskCancel(Optional<AjaxRequestTarget> ajaxRequestTarget) {
                taskCancel = true;
            }

            @Override
            protected void onTaskError(Optional<AjaxRequestTarget> ajaxRequestTarget) {
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
    
    public void countDownLatch() {
    	latch.countDown();
	}
    
    public void waitForTaskToComplete() {
    	try {
			latch.await();
		}
		catch (InterruptedException e) {
			throw new WicketRuntimeException(e);
		}
    }
}
