---
title: Async Tasks
category: ui
description: Start, cancel and follow background tasks from the UI without breaking serialization.
artifactId: async-task-impl
tags: background progress
---

The Wicket asynchronous task components allow you to control a background process within a Wicket application. The task information of all components is updated in a fixed, user-specified interval via Ajax. The following components are available:

* `ProgressButton`: Allows to start, restart or cancel a background task. This is the main component of this bundle.
* `ProgressBar`: Shows the state of a background task. A `ProgressBar` always exists as an addition to a `ProgressButton`. If you need a progress bar that displays the progress of a task that is independently started, refer to the Progressbar project within Wicket Stuff.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>async-task-impl</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Documentation

The progress information of the progress is found by implementing the `IProgressObservableRunnable` interface for a background task which provided in this bundle. The implementation is however optional, if no meaningful progress information can be yielded of a task. Even without such implementation, the components of this bundle can still observe the following features of a background task:

* not started (a background process was never started)
* finished (a background process was started and ran successfully)
* running (a background process is currently running)
* error (the background process threw an exception)
* canceled (the background process was interrupted)

A `ProgressButton` will always rerender itself in a fixed interval which can be specified by the user. Note: Setting a high-frequency interval can cause problems with the user pressing the button since the button is constantly rerendered by the browser. The component works however even if a user has JavaScript deactivated. A button's callback methods `onTaskSuccess` and `onTaskError` will however not be called. A button's `onTaskCancel` method might be called if the process is interrupted by using the button and if the process terminates abruptly after cancelation.

The process handlers are stored outside of Wicket's page serialization by a `ITaskManager` that needs to live outside of a Wicket page. Such managers provide models for processes that are created with a limited life time that is specified by the user. Also, processes are not bound to a session and can therefore be shared among users. A default singleton implementation is provided by `DefaultTaskManager#getInstance`. If you prefer your tasks to be contained by e.g. a Spring bean, this is however easy to implement by extending the `DefaultTaskManager` which is itself abstract. Just refer to the existing implementation as an example.

An use of the component would look like the following:

```java
public class DemoPage extends WebPage implements IRunnableFactory {

    public DemoPage() {

        Form<?> form = new Form<Void>("form");
        AbstractTaskContainer taskContainer = DefaultTaskManager.getInstance()
            .makeContainer(1000L, TimeUnit.MINUTES);
        ProgressButton progressButton = new ProgressButton("button", form,
            Model.of(taskContainer), this, Duration.ofMillis(500L));
        ProgressBar progressBar = new ProgressBar("bar", progressButton);

        add(form);
        form.add(progressButton);
        form.add(progressBar);
    }

    @Override
    public Runnable getRunnable() {
        return new IProgressObservableRunnable() {
            // Runnable implementation.
        };
    }
}
```

Furthermore, it is possible to register specific models with each components that load a specified appearance to each component, depending of the state. This makes it easy to integrate with for example Twitter Bootstrap. An example application is provided that demonstrates such functionality (`async-task-demo`). The components are included in `async-task-impl`.

The [`async-tasks-demo`](https://github.com/wicketstuff/core/tree/wicket-10.x/async-tasks-parent/async-tasks-demo) module contains a runnable example application.

Source: [`async-tasks-parent`](https://github.com/wicketstuff/core/tree/wicket-10.x/async-tasks-parent)
