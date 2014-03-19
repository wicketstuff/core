package org.wicketstuff.jwicket.ui.dragdrop;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.Request;
import org.apache.wicket.util.visit.IVisitor;
import org.wicketstuff.jwicket.*;
import org.wicketstuff.jwicket.ui.AbstractJqueryUiEmbeddedBehavior;


/**
 * You can add an instance of this class to a Wicket {@link Component} to make it
 * a droppable {@link Component} i.e. the target of a drag operation.
 * An instance of this class can be added to one and only one
 * {@link Component}. Another {@link Component} that should have exactly the
 * same behavior needs it's own instance.
 */
public class DroppableBehavior extends AbstractDragDropBehavior {

    private static final long serialVersionUID = 1L;

    private static final String DROPPED_COMPONENTID_IDENTIFIER = "wsjqDroppedComponent";

    public static final JQueryResourceReference uiDroppableJs
            = JQuery.isDebug()
            ? new JQueryResourceReference(DraggableBehavior.class, "jquery.ui.droppable.js")
            : new JQueryResourceReference(DraggableBehavior.class, "jquery.ui.droppable.min.js");

    private JsMap options = new JsMap();

    private DraggablesAcceptedByDroppable draggablesAcceptedByDroppable = null;

    // We need to render the drop accept checker function only once
    private boolean dropAcceptedCheckerRendered = false;


    public DroppableBehavior() {
        super(AbstractJqueryUiEmbeddedBehavior.jQueryUiWidgetJs,
                AbstractJqueryUiEmbeddedBehavior.jQueryUiMouseJs,
                DraggableBehavior.jQueryUiDraggableJs,
                uiDroppableJs,
                SpecialKeys.specialKeysJs);
    }

    private boolean onActivatedNotificationWanted = false;

    /**
     * If set to {@code true}, the callback-Method {@link #onActivate(AjaxRequestTarget, Component, SpecialKeys)}
     * is called when the drag operation ends.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DroppableBehavior setWantOnActivatedNotification(final boolean value) {
        this.onActivatedNotificationWanted = value;
        return this;
    }


    private boolean onDeactivateNotificationWanted = false;

    /**
     * If set to {@code true}, the callback-Method {@link #onDeactivate(AjaxRequestTarget, Component, SpecialKeys)}
     * is called when the drag operation ends.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DroppableBehavior setWantOnDeactivateNotification(final boolean value) {
        this.onDeactivateNotificationWanted = value;
        return this;
    }

    /**
     * You can restrict the {@link Component}s that can be dragged and dropped to
     * a {@link Component} marked with this behavior. See {@link DraggablesAcceptedByDroppable}
     * for more information. If the {@link DraggablesAcceptedByDroppable} is empty (i.e. it
     * contains no names) then the droppable does not accept any draggables.
     *
     * @param accepted the accepted
     * @return this object
     */
    public DroppableBehavior setDraggablesAcceptedByDroppable(final DraggablesAcceptedByDroppable accepted) {
        this.draggablesAcceptedByDroppable = accepted;
        if (accepted != null) {
            this.options.put("accept", new JsFunction(accepted.getJsAcceptCheckerFunctionName()));
        } else {
            this.options.remove("accept");
        }
        return this;
    }


    /**
     * Sets the 'activeClass' property for this draggable. Please consult the
     * jQuery documentation for a detailled description of this property.
     *
     * @param activeClass the CSS class' name
     * @return this object
     */
    public DroppableBehavior setActiveClass(final String activeClass) {
        if (activeClass == null) {
            this.options.remove("activeClass");
        } else {
            this.options.put("activeClass", activeClass);
        }
        return this;
    }

    public DroppableBehavior setActiveClass(final AjaxRequestTarget target, final String activeClass) {
        setActiveClass(activeClass);
        if (activeClass != null) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','activeClass','" + activeClass + "');");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','activeClass',false);");
        }
        return this;
    }


    /**
     * Sets the 'addClasses' property for this draggable. Please consult the
     * jQuery documentation for a detailled description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DroppableBehavior setAddClasses(final boolean value) {
        if (value) {
            this.options.remove("addClasses");
        } else {
            this.options.put("addClasses", value);
        }
        return this;
    }

    public DroppableBehavior setAddClasses(final AjaxRequestTarget target, final boolean value) {
        setAddClasses(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','addClasses'," + value + ");");
        return this;
    }


    /**
     * Sets the 'greedy' property for this draggable. Please consult the
     * jQuery documentation for a detailled description of this property.
     *
     * @param value {@code true} or {@code false}.
     * @return this object
     */
    public DroppableBehavior setGreedy(final boolean value) {
        if (value) {
            this.options.remove("greedy");
        } else {
            this.options.put("greedy", value);
        }
        return this;
    }

    public DroppableBehavior setGreedy(final AjaxRequestTarget target, final boolean value) {
        setGreedy(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','greedy'," + value + ");");
        return this;
    }


    /**
     * Sets the 'hoverClass' property for this draggable. Please consult the
     * jQuery documentation for a detailled description of this property.
     *
     * @param hoverClass the CSS class' name
     * @return this object
     */
    public DroppableBehavior setHoverClass(final String hoverClass) {
        if (hoverClass == null) {
            this.options.remove("hoverClass");
        } else {
            this.options.put("hoverClass", hoverClass);
        }
        return this;
    }

    public DroppableBehavior setHoverClass(final AjaxRequestTarget target, final String hoverClass) {
        setHoverClass(hoverClass);
        if (hoverClass != null) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','hoverClass','" + hoverClass + "');");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','hoverClass',false);");
        }
        return this;
    }


    /**
     * Sets the 'scope' property for this draggable. Please consult the
     * jQuery documentation for a detailled description of this property.
     *
     * @param scope the scode
     * @return this object
     */
    public DroppableBehavior setScope(final String scope) {
        if (scope == null) {
            this.options.remove("scope");
        } else {
            this.options.put("scope", scope);
        }
        return this;
    }

    public DroppableBehavior setScope(final AjaxRequestTarget target, final String scope) {
        setScope(scope);
        if (scope != null) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','scope','" + scope + "');");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','scope','default');");
        }
        return this;
    }


    public static enum DropTolerance {

        FTI("fit"),
        INTERSECT("intersect"),
        POINTER("pointer"),
        TOUCH("touch");

        private final String value;

        private DropTolerance(final String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public String toString() {
            return this.value;
        }
    }


    /**
     * Sets the 'tolerance' property for this droppable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param tolerance the tolerance
     * @return this object
     */
    public DroppableBehavior setTolerance(final DropTolerance tolerance) {
        if (tolerance == null) {
            this.options.remove("tolerance");
        } else {
            this.options.put("tolerance", tolerance.getValue());
        }
        return this;
    }

    public DroppableBehavior setScope(final AjaxRequestTarget target, final DropTolerance tolerance) {
        setTolerance(tolerance);
        if (tolerance != null) {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','tolerance','" + tolerance.getValue() + "');");
        } else {
            target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('option','tolerance','" + DropTolerance.INTERSECT + "');");
        }
        return this;
    }


    /**
     * handles the event processing during dragging.
     */
    @Override
    protected void respond(final AjaxRequestTarget target) {
        Component component = getComponent();
        Request request;

        if (component != null && (request = component.getRequest()) != null) {
            EventType dragEventType = EventType.stringToType(request.getRequestParameters().getParameterValue(EventType.IDENTIFIER).toString());

            IVisitor<Component, Component> visitor = getComponentFindingVisitor(request.getRequestParameters().getParameterValue(DROPPED_COMPONENTID_IDENTIFIER).toString());
            Component foundComponent = component.getPage().visitChildren(visitor);

            if (component instanceof IDroppable) {
                IDroppable draggableComponent = (IDroppable) component;
                if (dragEventType == EventType.DROP) {
                    draggableComponent.onDrop(target, foundComponent, new SpecialKeys(request));
                } else if (dragEventType == EventType.DROP_ACTIVATE) {
                    draggableComponent.onActivate(target, foundComponent, new SpecialKeys(request));
                } else if (dragEventType == EventType.DROP_DEACTIVATE) {
                    draggableComponent.onDeactivate(target, foundComponent, new SpecialKeys(request));
                }
            }


            if (dragEventType == EventType.DROP) {
                onDrop(target, foundComponent, new SpecialKeys(request));
            } else if (dragEventType == EventType.DROP_ACTIVATE) {
                onActivate(target, foundComponent, new SpecialKeys(request));
            } else if (dragEventType == EventType.DROP_DEACTIVATE) {
                onDeactivate(target, foundComponent, new SpecialKeys(request));
            }
        }
    }

    protected IVisitor<Component, Component> getComponentFindingVisitor(String markupId) {
        return new ComponentFinder(markupId);
    }


    /**
     * This method is called when a draggable {@link Component} is dropped onto
     * a {@link Component} marked with this behavior.
     *
     * @param target           the AjaxRequestTarget of the drop operation.
     * @param draggedComponent The dragged component
     * @param specialKeys      the special keys that were pressed when the event occurs
     */
    protected void onDrop(AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys) {
    }


    /**
     * This method is called when a draggable {@link Component} is starting to
     * drag and the dragging {@link Component}'s name is accepted to be
     * dropped onto this.
     *
     * @param target           The {@link AjaxRequestTarget} associated with this
     *                         drop operation.
     * @param draggedComponent The dragged component
     * @param specialKeys      the special keys that were pressed when the event occurs
     */
    protected void onActivate(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys) {
    }


    /**
     * This method is called when a draggable {@link Component} has stopped
     * dragging and the dragging {@link Component}'s name was accepted to be
     * dropped onto this.
     *
     * @param target           The {@link AjaxRequestTarget} associated with this
     *                         drop operation.
     * @param draggedComponent The dragged component
     * @param specialKeys      the special keys that were pressed when the event occurs
     */
    protected void onDeactivate(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys) {
    }


    /**
     * Disable the dropping
     *
     * @param target An AjaxRequestTarget
     */
    public void disable(final AjaxRequestTarget target) {
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('disable');");
    }


    /**
     * Enable the dropping
     *
     * @param target An AjaxRequestTarget
     */
    public void enable(final AjaxRequestTarget target) {
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').droppable('enable');");
    }


    @Override
    public void renderHead(Component component, final IHeaderResponse response) {
        super.renderHead(component, response);
        if (this.draggablesAcceptedByDroppable != null && !this.dropAcceptedCheckerRendered) {
            this.draggablesAcceptedByDroppable.renderJsDropAcceptFunction(response);
            this.dropAcceptedCheckerRendered = true;
        }
    }


    @Override
    /**
     * For internal use only.
     */
    protected JsBuilder getJsBuilder() {
        if (this.onDeactivateNotificationWanted) {
            this.options.put("deactivate",
                    new JsFunction("function(ev,ui) { \n" +
                            "wicketAjaxGet('" +
                            this.getCallbackUrl() +
                            "&" + EventType.IDENTIFIER + "=" + EventType.DROP_DEACTIVATE +
                            "&" + DROPPED_COMPONENTID_IDENTIFIER + "='+jQuery(ui.draggable).attr('id')" +
                            "+'&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
                            "); }"));
        }

        this.options.put("drop",
                new JsFunction("function(ev,ui) { \n" +
                        "wicketAjaxGet('" +
                        this.getCallbackUrl() +
                        "&" + EventType.IDENTIFIER + "=" + EventType.DROP +
                        "&" + DROPPED_COMPONENTID_IDENTIFIER + "='+jQuery(ui.draggable).attr('id')" +
                        "+'&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
                        "); " +
                        "}"));

        if (this.onActivatedNotificationWanted) {
            this.options.put("activate",
                    new JsFunction("function(ev,ui) { \n" +
                            "wicketAjaxGet('" +
                            this.getCallbackUrl() +
                            "&" + EventType.IDENTIFIER + "=" + EventType.DROP_ACTIVATE +
                            "&" + DROPPED_COMPONENTID_IDENTIFIER + "='+jQuery(ui.draggable).attr('id')" +
                            "+'&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
                            "); }"));
        }

        JsBuilder builder = new JsBuilder();


        builder.append("jQuery('#" + getComponent().getMarkupId() + "').droppable(");
        builder.append("{");
        builder.append(this.options.toString(this.rawOptions));
        builder.append("}");
        builder.append(");");


        return builder;
    }
}