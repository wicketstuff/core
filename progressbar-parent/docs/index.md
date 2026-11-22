---
title: Progressbar
category: ui
description: Ajax progress bar for long-running background tasks, with a pluggable task service.
artifactId: wicketstuff-progressbar
tags: task background
---

Wicket Stuff Progressbar provides a progress bar component for Wicket.

![Progress bar example](progressbar-example.png)

The component can be used both for displaying just a progress bar or for showing progress of a background task. The progress of a background task is updated via Ajax.

The `wicketstuff-progressbar-spring` subproject is a task service implementation for the integration of user submitted background tasks in the service layer with the progress bar.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-progressbar</artifactId>
</dependency>
```

For the Spring task service add `wicketstuff-progressbar-spring` as well. Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Documentation

The **ProgressBar** component uses a special model of type **ProgressionModel**. The current progress is encapsulated in a **Progression** value-object as the percentage of completion.

The **onFinished** method is executed when the progress reaches 100 or more generally if the Progression object returns true for isDone.

The progress bar is started to update itself with the start method and stops if the progress is done.

`SimpleProgressExamplePage.java`:

```java
private int progress = 0;

public SimpleProgressExamplePage() {
    final ProgressBar bar;
    add(bar = new ProgressBar("bar", new ProgressionModel() {
        protected Progression getProgression() {
            return new Progression(progress);
        }
    }) {
        protected void onFinished(AjaxRequestTarget target) {
            setVisible(false);
            target.appendJavaScript("alert('Task done!')");
        }
    });
    ...
    form.add(new IndicatingAjaxButton("submit", form) {
        protected void onSubmit(AjaxRequestTarget target) {
            bar.start(target);
            new Thread() {
                public void run() {
                    for(int i = 0; i <= 100; i++) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) { }
                        progress = i;
                    }
                }
            }.start();
        }
    });
    ...
}
```

**Note:** The background process shouldn't be started as a new thread from within the page but rather in a separate service (see `wicketstuff-progressbar-spring`).

On the markup side, just use a div element for the progress bar:

`SimpleProgressExamplePage.html`:

```html
...
<div wicket:id="bar">[Progress]</div>
...
```

The [`progressbar-example`](https://github.com/wicketstuff/core/tree/wicket-11.x/progressbar-parent/progressbar-example) module contains a runnable example application.

Source: [`progressbar-parent`](https://github.com/wicketstuff/core/tree/wicket-11.x/progressbar-parent)
