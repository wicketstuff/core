package org.wicketstuff.async.components;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.async.task.AbstractTaskContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * This component renders a progress bar to an existent task which is controlled by a progress button.
 */
public class ProgressBar extends Panel {

    private final ProgressButton progressButton;

    private final Map<StateDescription, IModel<String>> stateCssClasses;

    public ProgressBar(String id, ProgressButton progressButton) {
        super(id);

        this.progressButton = progressButton;

        WebMarkupContainer wrapper = makeWrapper("wrapper");
        add(wrapper);

        Component bar = makeBar("bar").add(new AttributeAppender("style", new TaskProgressPercentageStyleModel()));
        wrapper.add(bar);
        wrapper.add(new Label("message", new TaskProgressMessageModel()));

        stateCssClasses = new HashMap<StateDescription, IModel<String>>();
        this.add(new AttributeAppender("class", progressButton.new StateDispatcherModel<String>(new Model<String>(), stateCssClasses), " "));

        progressButton.addRefreshDependant(this);

        this.setOutputMarkupId(true);
    }

    private AbstractTaskContainer getTaskContainer() {
        return progressButton.getTaskContainer();
    }

    protected WebMarkupContainer makeWrapper(String id) {
        return new WebMarkupContainer(id);
    }

    protected WebMarkupContainer makeBar(String id) {
        return new WebMarkupContainer(id);
    }

    /**
     * Determines if the task progress in percentage is showed within the bar.
     *
     * @return {@code true} if the number should be shown.
     */
    protected boolean isShowPercentage() {
        return true;
    }

    /**
     * Determines the default width of the progress bar when there is no information available.
     *
     * @return The default width. Should be between {@code 0} and {@code 1}. Values outside this interval will
     *         be trucated.
     */
    protected double getDefaultWidth() {
        return 0d;
    }

    private class TaskProgressMessageModel extends AbstractReadOnlyModel<String> {
        @Override
        public String getObject() {
            Double progress = getTaskContainer().getProgress();
            String suffix = "";
            if (isShowPercentage()) {
                if (progress != null) {
                    suffix = String.format("(%d%%)", getPercentProgress());
                }
            }
            String message = getTaskContainer().getProgressMessage();
            if (message == null) {
                message = "";
            }
            return String.format("%s %s", message, suffix);
        }
    }

    private class TaskProgressPercentageStyleModel extends AbstractReadOnlyModel<String> {
        @Override
        public String getObject() {
            int percentProgress = getPercentProgress();
            return String.format("width: %d%%;", percentProgress);
        }
    }

    private int getPercentProgress() {
        double width = getTaskContainer().getProgress() == null ? getDefaultWidth() : getTaskContainer().getProgress();
        return (int) Math.round(Math.max(Math.min(width, 1d), 0d) * 100d);
    }

    /**
     * Adds a model for a css class which will be appended only if {@code taskState} and {@code interactionState} apply.
     *
     * @param textModel        The model to be appended.
     * @param taskState        The relevant task state.
     * @param interactionState The relevant interaction state.
     */
    public void registerCssClassModel(IModel<String> textModel, TaskState taskState, InteractionState interactionState) {
        stateCssClasses.put(new StateDescription(taskState, interactionState), textModel);
    }

    /**
     * Adds a model for a css class which will be appended if any of the {@code taskStates} apply, disregarding any
     * possible interaction state.
     *
     * @param textModel  The model to be appended.
     * @param taskStates All relevant task states.
     */
    public void registerCssClassModel(IModel<String> textModel, TaskState... taskStates) {
        for (TaskState taskState : taskStates) {
            for (InteractionState interactionState : InteractionState.values()) {
                registerCssClassModel(textModel, taskState, interactionState);
            }
        }
    }

    /**
     * Adds a model for a css class which will be appended if any of the {@code interactionStates} apply, disregarding any
     * possible task state.
     *
     * @param textModel         The model to be appended.
     * @param interactionStates All relevant interaction states.
     */
    public void registerCssClassModel(IModel<String> textModel, InteractionState... interactionStates) {
        for (InteractionState interactionState : interactionStates) {
            for (TaskState taskState : TaskState.values()) {
                registerCssClassModel(textModel, taskState, interactionState);
            }
        }
    }
}
