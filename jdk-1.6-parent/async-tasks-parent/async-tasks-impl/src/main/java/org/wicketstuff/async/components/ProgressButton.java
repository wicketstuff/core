package org.wicketstuff.async.components;

import org.wicketstuff.async.task.AbstractTaskModel;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * A progress button which allows to control a {@link Runnable}. Each such button will refresh itself as given by
 * by the {@link Duration} with which it was constructed. It represents a runnable by a {@link AbstractTaskModel}.
 * In order to create tasks, the button needs to be provided a {@link IRunnableFactory}.
 */
public class ProgressButton extends AjaxFallbackButton {

    private final Map<StateDescription, IModel<String>> stateTextModels;
    private final Map<StateDescription, IModel<String>> stateCssClasses;

    private final Collection<Component> refreshDependants;

    private final IRunnableFactory runnableFactory;
    private final AbstractTaskModel taskModel;

    private final RefreshBehavior refreshBehavior;

    public ProgressButton(String id, Form<?> form, AbstractTaskModel taskModel, Duration duration) {
        this(id, null, form, taskModel, null, duration);
    }

    public ProgressButton(String id, IModel<String> model, Form<?> form, AbstractTaskModel taskModel, Duration duration) {
        this(id, model, form, taskModel, null, duration);
    }

    public ProgressButton(String id, Form<?> form, AbstractTaskModel taskModel, IRunnableFactory runnableFactory, Duration duration) {
        this(id, null, form, taskModel, runnableFactory, duration);
    }

    public ProgressButton(String id, IModel<String> model, Form<?> form, AbstractTaskModel taskModel, IRunnableFactory runnableFactory, Duration duration) {
        super(id, null, form);

        this.taskModel = taskModel;
        this.runnableFactory = runnableFactory;

        this.refreshDependants = new HashSet<Component>();

        this.refreshBehavior = new RefreshBehavior(duration);

        this.stateTextModels = new HashMap<StateDescription, IModel<String>>();
        this.setModel(new StateDispatcherModel<String>(getDefaultTextModel(model), stateTextModels));

        this.stateCssClasses = new HashMap<StateDescription, IModel<String>>();
        this.add(new AttributeAppender("class", new StateDispatcherModel<String>(new Model<String>(), stateCssClasses), " "));

        this.setOutputMarkupId(true);
    }

    private IModel<String> getDefaultTextModel(IModel<String> userModel) {
        if (userModel == null) {
            return new Model<String>();
        } else {
            return userModel;
        }
    }

    protected AbstractTaskModel getTaskModel() {
        return taskModel;
    }

    /**
     * This method can be overridden to implement a custom behavior for starting tasks.
     *
     * @return {@code true} if the button is allowed to start a task.
     */
    protected boolean isAllowStart() {
        return true;
    }

    /**
     * This method can be overridden to implement a custom behavior for restarting tasks.
     *
     * @return {@code true} if the button is allowed to restart a task.
     */
    protected boolean isAllowRestart() {
        return true;
    }

    /**
     * This method can be overridden to implement a custom behavior for interrupt tasks.
     *
     * @return {@code true} if the button is allowed to interrupt a running task.
     */
    protected boolean isAllowInterrupt() {
        return true;
    }

    boolean canStart() {
        return runnableFactory != null && isAllowStart() && !taskModel.isSubmitted() && !taskModel.isRunning();
    }

    boolean canRestart() {
        return runnableFactory != null && isAllowRestart() && taskModel.isSubmitted() && !taskModel.isRunning();
    }

    boolean canInterrupt() {
        return isAllowInterrupt() && !taskModel.isCancelled() && taskModel.isRunning();
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        super.onSubmit(target, form);

        if (canStart() || canRestart()) {
            taskModel.submit(runnableFactory.getRunnable());
            onTaskStart(target);
        } else if (canInterrupt()) {
            taskModel.cancel();
        } else {
            return;
        }

        if (target != null) {
            activateRefresh(target);
            renderAll(target);
        }

        concludeIfApplicable(target);
    }

    private void activateRefresh(AjaxRequestTarget target) {
        if (!taskModel.isRunning()) {
            if (getBehaviors(RefreshBehavior.class).size() > 0) {
                refreshBehavior.stop(target);
            }
        } else if (getBehaviors(RefreshBehavior.class).size() == 0) {
            add(refreshBehavior);
        } else {
            refreshBehavior.restart(target);
        }
    }

    /**
     * Overriding allows a custom refresh behavior. Should call super implementation.
     *
     * @param target The Ajax request target.
     */
    protected void refresh(AjaxRequestTarget target) {
        concludeIfApplicable(target);
        renderAll(target);
    }

    private void concludeIfApplicable(AjaxRequestTarget target) {
        if (!taskModel.isRunning()) {
            if (target != null) {
                refreshBehavior.stop(target);
            }
            if (taskModel.isFailed()) {
                onTaskError(target);
            } else if (!taskModel.isCancelled()) {
                onTaskSuccess(target);
            } else {
                onTaskCancel(target);
            }
        }
    }

    private void renderAll(AjaxRequestTarget target) {
        target.add(this);
        for (Component c : refreshDependants) {
            target.add(c);
        }
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled() && (canStart() || canRestart() || canInterrupt());
    }

    private class RefreshBehavior extends AbstractAjaxTimerBehavior {
        public RefreshBehavior(Duration updateInterval) {
            super(updateInterval);
        }

        @Override
        protected void onTimer(AjaxRequestTarget target) {
            refresh(target);
        }

        @Override
        public boolean canCallListenerInterface(Component component, Method method) {
            // Skip check for the component being enabled
            return component.isVisibleInHierarchy();
        }

        @Override
        protected boolean shouldTrigger() {
            // Again, skip the check for the component being enabled
            return !isStopped() && getComponent().findParent(Page.class) != null;
        }
    }

    /**
     * Adds a text model for the button which will be appended only if {@code taskState} and {@code interactionState} apply.
     *
     * @param textModel        The model to be appended.
     * @param taskState        The relevant task state.
     * @param interactionState The relevant interaction state.
     */
    public void registerMessageModel(IModel<String> textModel, TaskState taskState, InteractionState interactionState) {
        stateTextModels.put(new StateDescription(taskState, interactionState), textModel);
    }

    /**
     * Adds a text model for the button which will be appended if any of the {@code taskStates} apply, disregarding any
     * possible interaction state.
     *
     * @param textModel  The model to be appended.
     * @param taskStates All relevant task states.
     */
    public void registerMessageModel(IModel<String> textModel, TaskState... taskStates) {
        for (TaskState taskState : taskStates) {
            for (InteractionState interactionState : InteractionState.values()) {
                registerMessageModel(textModel, taskState, interactionState);
            }
        }
    }

    /**
     * Adds a text model for the button which will be appended if any of the {@code interactionStates} apply, disregarding any
     * possible task state.
     *
     * @param textModel         The model to be appended.
     * @param interactionStates All relevant interaction states.
     */
    public void registerMessageModel(IModel<String> textModel, InteractionState... interactionStates) {
        for (InteractionState interactionState : interactionStates) {
            for (TaskState taskState : TaskState.values()) {
                registerMessageModel(textModel, taskState, interactionState);
            }
        }
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

    class StateDispatcherModel<T> extends AbstractReadOnlyModel<T> {

        private final IModel<T> defaultValue;

        private final Map<StateDescription, IModel<T>> stateValues;

        StateDispatcherModel(IModel<T> defaultValue, Map<StateDescription, IModel<T>> taskStateValues) {
            this.defaultValue = defaultValue;
            this.stateValues = taskStateValues;
        }

        @Override
        public T getObject() {
            IModel<T> actualModel = getActualModel();
            if (actualModel == null) {
                return defaultValue.getObject();
            } else {
                return actualModel.getObject();
            }
        }

        private IModel<T> getActualModel() {
            return stateValues.get(
                    new StateDescription(
                            TaskState.findRunningState(taskModel),
                            InteractionState.findInteractionState(ProgressButton.this)
                    )
            );
        }
    }

    /**
     * Allows to add a component which is rerendered every time the button is rerendered.
     *
     * @param refreshDependant The dependant component.
     */
    public void addRefreshDependant(Component refreshDependant) {
        refreshDependants.add(refreshDependant);
    }

    /**
     * Allows to remove a component which was rerendered every time the button was rerendered.
     *
     * @param refreshDependant The dependant component.
     */
    public void removeRefreshDependant(Component refreshDependant) {
        refreshDependants.remove(refreshDependant);
    }

    /**
     * Override to trigger a custom action whenever a task is started.
     *
     * @param ajaxRequestTarget The Ajax request target. Might be {@code null}.
     */
    protected void onTaskStart(AjaxRequestTarget ajaxRequestTarget) {
    }

    /**
     * Override to trigger a custom action whenever a task succeeded.
     * <p/>
     * <b>Note:</b> This trigger is only called if Javascript is enabled in the client's browser.
     *
     * @param ajaxRequestTarget The Ajax request target.
     */
    protected void onTaskSuccess(AjaxRequestTarget ajaxRequestTarget) {
    }

    /**
     * Override to trigger a custom action whenever a task was canceled and halted. (A canceled task
     * might continue to run since it is only told to interrupt. The trigger is called when the interrupted
     * task stops running.)
     *
     * @param ajaxRequestTarget The Ajax request target. Might be {@code null}.
     */
    protected void onTaskCancel(AjaxRequestTarget ajaxRequestTarget) {
    }

    /**
     * Override to trigger a custom action whenever an error is occurred.
     * <p/>
     * <b>Note:</b> This trigger is only called if Javascript is enabled in the client's browser.
     *
     * @param ajaxRequestTarget The Ajax request target.
     */
    protected void onTaskError(AjaxRequestTarget ajaxRequestTarget) {
    }
}
