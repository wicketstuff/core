package org.wicketstuff.async.demo;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.async.components.*;
import org.wicketstuff.async.task.AbstractTaskContainer;
import org.wicketstuff.async.task.DefaultTaskManager;

import java.util.concurrent.TimeUnit;

public class DemoPage extends WebPage implements IRunnableFactory {

    public DemoPage() {

        // Create form
        Form<?> form = new Form<Void>("form");

        // Create model of task
        AbstractTaskContainer taskContainer = DefaultTaskManager.getInstance().makeContainer(1000L, TimeUnit.MINUTES);

        // Create a progress button.
        ProgressButton progressButton = new ProgressButton("button", form, Model.of(taskContainer), this, Duration.milliseconds(500L));

        progressButton.registerMessageModel(Model.of("Start"), InteractionState.STARTABLE, InteractionState.RESTARTABLE);
        progressButton.registerMessageModel(Model.of("Cancel"), InteractionState.CANCELABLE);
        progressButton.registerMessageModel(Model.of("Running..."), InteractionState.NON_INTERACTIVE);

        progressButton.registerCssClassModel(Model.of("btn-primary"), TaskState.PLAIN_NON_RUNNING, TaskState.CANCELED_NON_RUNNING);
        progressButton.registerCssClassModel(Model.of("btn-warning"), TaskState.PLAIN_RUNNING, TaskState.CANCELED_RUNNING);
        progressButton.registerCssClassModel(Model.of("btn-danger"), TaskState.ERROR_NON_RUNNING);

        // Create a progress bar
        ProgressBar progressBar = new ProgressBar("bar", progressButton);

        progressBar.registerCssClassModel(Model.of("progress-info progress-striped active"), TaskState.PLAIN_RUNNING);
        progressBar.registerCssClassModel(Model.of("progress-warning progress-striped active"), TaskState.CANCELED_RUNNING);
        progressBar.registerCssClassModel(Model.of("progress-info progress-striped"), TaskState.PLAIN_NON_RUNNING);
        progressBar.registerCssClassModel(Model.of("progress-warning  progress-striped"), TaskState.CANCELED_NON_RUNNING);
        progressBar.registerCssClassModel(Model.of("progress-danger progress-striped"), TaskState.ERROR_NON_RUNNING);

        // Add components to page
        add(form);
        form.add(progressButton);
        form.add(progressBar);
    }

    @Override
    public Runnable getRunnable() {
        return new GenericTask(30, 150L);
    }
}
