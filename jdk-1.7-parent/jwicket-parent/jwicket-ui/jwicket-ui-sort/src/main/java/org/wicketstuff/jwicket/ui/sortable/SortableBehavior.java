package org.wicketstuff.jwicket.ui.sortable;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;
import org.apache.wicket.util.visit.IVisitor;
import org.wicketstuff.jwicket.*;
import org.wicketstuff.jwicket.ui.AbstractJqueryUiEmbeddedBehavior;

import java.io.Serializable;


public class SortableBehavior extends AbstractJqueryUiEmbeddedBehavior implements IStyleResolver {

    private static final long serialVersionUID = 1L;

    private static final String CONNECT_WITH_OPTION = "connectWith";

    public static final JQueryResourceReference uiSortableJs
            = JQuery.isDebug()
            ? new JQueryResourceReference(SortableBehavior.class, "jquery.ui.sortable.js")
            : new JQueryResourceReference(SortableBehavior.class, "jquery.ui.sortable.min.js");
    public static final JQueryResourceReference jWicketSortJs = new JQueryResourceReference(SortableBehavior.class, "jWicketSort.js");

    protected JsMap options = new JsMap();


    public SortableBehavior() {
        super(
                AbstractJqueryUiEmbeddedBehavior.jQueryUiMouseJs,
                AbstractJqueryUiEmbeddedBehavior.jQueryUiWidgetJs,
                uiSortableJs,
                jWicketSortJs
        );
        addCssResources(getCssResources());
    }


    /**
     * Sets the 'placeholder' property for this sortable. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @param value the placeholder css class name
     * @return this object
     */
    public SortableBehavior setPlaceholder(final String value) {
        if (value == null) {
            this.options.remove("placeholder");
        } else {
            this.options.put("placeholder", value);
        }
        return this;
    }

    public SortableBehavior setPlaceholder(final AjaxRequestTarget target, final String value) {
        setPlaceholder(value);
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').sortable('option','placeholder','" + value + "');");
        return this;
    }

    /**
     * Sets the default 'placeholder' property for this sortable: 'ui-state-highlight'. Please consult the
     * jquery documentation for a detailled description of this property.
     *
     * @return this object
     */
    public SortableBehavior setPlaceholder() {
        this.options.put("placeholder", "ui-state-highlight");
        return this;
    }

    public SortableBehavior setPlaceholder(final AjaxRequestTarget target) {
        setPlaceholder("ui-state-highlight");
        target.appendJavaScript("jQuery('#" + getComponent().getMarkupId() + "').sortable('option','placeholder','ui-state-highlight');");
        return this;
    }

    /**
     * Handles the event processing during sorting.
     */
    @Override
    protected void respond(final AjaxRequestTarget target) {
        Component component = getComponent();
        Request request;
        if (component != null && (request = component.getRequest()) != null) {
            EventType eventType = EventType.stringToType(request.getRequestParameters().getParameterValue(EventType.IDENTIFIER).toString());

            String draggedItemId = request.getRequestParameters().getParameterValue("draggedItemId").toString();

            // We need the body of the <li> tag, the component inside it
            ChildrenFinder childrenFinder = new ChildrenFinder(draggedItemId);
            component.getPage().visitChildren(childrenFinder);
            if (childrenFinder.getFoundComponents().size() != 1) {
                throw new WicketRuntimeException("this should not happen");
            }
            Component sortedComponent = childrenFinder.getFoundComponents().get(0);

            if (eventType == EventType.STOP) {
                try {
                    String s = request.getRequestParameters().getParameterValue("newPosition").toString();
                    int newPosition = Integer.parseInt(s);

                    if (sortedComponent instanceof ISortable) {
                        ((ISortable) sortedComponent).onSorted(target, newPosition);
                    }

                    onSorted(target, sortedComponent, newPosition);
                } catch (Exception e) {
                    // don't process
                }
            } else if (eventType == EventType.RECEIVE) {
                try {
                    String s = request.getRequestParameters().getParameterValue("newPosition").toString();

                    int newPosition = Integer.parseInt(s);
                    String otherSortableId = request.getRequestParameters().getParameterValue("otherSortableId").toString();

                    if (sortedComponent instanceof ISortable) {
                        ((ISortable) sortedComponent).onReceived(target, newPosition);
                    }

                    IVisitor<Component, Component> visitor = new ComponentFinder(otherSortableId);
                    Component otherSortable = component.getPage().visitChildren(visitor);
                    onReceived(target, sortedComponent, newPosition, (Sortable<?>) otherSortable);
                } catch (Exception e) {
                    // don't process
                }
            } else if (eventType == EventType.REMOVE) {
                if (sortedComponent instanceof ISortable) {
                    ((ISortable) sortedComponent).onRemoved(target);
                }

                onRemoved(target, sortedComponent);
            }
        }
    }


    public void connectWith(final Sortable<?> other) {
        Component otherSortable = other.get(Sortable.SORTABLE_COMPONENT_ID);
        this.options.put(CONNECT_WITH_OPTION, "#" + otherSortable.getMarkupId());
    }


    protected void onSorted(final AjaxRequestTarget target, final Component movedComponent, final int newPosition) {
    }

    protected void onReceived(final AjaxRequestTarget target, final Component movedComponent, final int newPosition, final Sortable<?> from) {
    }

    protected void onRemoved(final AjaxRequestTarget target, final Component movedComponent) {
    }


    @Override
    protected JsBuilder getJsBuilder() {
        this.options.put(EventType.STOP.eventName,
                new JsFunction(
                        "function(ev,ui) {" +
                                "jQuery.handleSortStop('" + this.getCallbackUrl() + "',ui,'" + getComponent().getMarkupId() + "');" +
                                "}"
                )
        );

        this.options.put(EventType.RECEIVE.eventName,
                new JsFunction(
                        "function(ev,ui) {" +
                                "jQuery.handleReceive('" + this.getCallbackUrl() + "',ui,'" + getComponent().getMarkupId() + "');" +
                                "}"
                )
        );

        this.options.put(EventType.REMOVE.eventName,
                new JsFunction(
                        "function(ev,ui) {" +
                                "jQuery.handleRemove('" + this.getCallbackUrl() + "',ui,'" + getComponent().getMarkupId() + "');" +
                                "}"
                )
        );


        JsBuilder builder = new JsBuilder();

        builder.append("jQuery('#" + getComponent().getMarkupId() + "').sortable(");
        builder.append("{");
        builder.append(this.options.toString(this.rawOptions));
        builder.append("}");
        builder.append(");");

        return builder;
    }


    private enum EventType implements Serializable {

        UNKNOWN("*"),
        STOP("stop"),
        RECEIVE("receive"),
        REMOVE("remove");

        public static final String IDENTIFIER = "jWicketEvent";

        private final String eventName;

        private EventType(final String eventName) {
            this.eventName = eventName;
        }

        public String getEventName() {
            return this.eventName;
        }

        public static EventType stringToType(final String s) {
            for (EventType t : EventType.values()) {
                if (t.getEventName().equals(s)) {
                    return t;
                }
            }
            return UNKNOWN;
        }

        public String toString() {
            return this.eventName;
        }
    }

    @Override
    public JQueryCssResourceReference[] getCssResources() {
        return new JQueryCssResourceReference[]{
                AbstractJqueryUiEmbeddedBehavior.jQueryUiCss,
                AbstractJqueryUiEmbeddedBehavior.jQueryUiThemeCss
        };
    }
}