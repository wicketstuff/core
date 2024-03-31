package org.wicketstuff.openlayers3.api.overlay;

import org.apache.wicket.Component;
import org.wicketstuff.openlayers3.api.IJavascriptObject;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;

import java.io.Serializable;

/**
 * Provides an object that models a map overlay.
 */
public class Overlay implements IJavascriptObject, Serializable {

    /**
     * Default positioning.
     */
    public final static Positioning DEFAULT_POSITIONING = Positioning.TopLeft;
    /**
     * Default stop event value.
     */
    public final static Boolean DEFAULT_STOP_EVENT = true;
    /**
     * The position of the overlay in the map projection.
     */
    public LongLat position;
    /**
     * Defines how the overlay is actually positioned with respect to its position property.
     */
    public Positioning positioning;
    /**
     * Whether event propagation to the map viewport should be stopped. Default is true. If true the overlay is placed
     * in the same container as that of the controls (CSS class name ol-overlaycontainer-stopevent); if false it is
     * placed in the container with CSS class name ol-overlaycontainer.
     */
    public Boolean stopEvent;
    /**
     * Wicket component tied to this overlay.
     */
    protected Component element;

    /**
     * Creates a new instance.
     */
    public Overlay() {
        this(null, null, DEFAULT_POSITIONING, DEFAULT_STOP_EVENT);
    }

    /**
     * Creates a new instance.
     *
     * @param position
     *         The position of the overlay
     */
    public Overlay(LongLat position) {
        this(null, position, DEFAULT_POSITIONING, DEFAULT_STOP_EVENT);
    }

    /**
     * Creates a new instance.
     *
     * @param component
     *         The component linked to this overlay
     */
    public Overlay(Component component) {
        this(component, null, DEFAULT_POSITIONING, DEFAULT_STOP_EVENT);
    }

    /**
     * Creates a new instance.
     *
     * @param component
     *         The component linked to this overlay
     * @param position
     *         The position of the overlay
     */
    public Overlay(Component component, LongLat position) {
        this(component, position, DEFAULT_POSITIONING, DEFAULT_STOP_EVENT);
    }

    /**
     * Creates a new instance.
     *
     * @param component
     *         The component linked to this overlay
     * @param positioning
     *         Defines how the overlay is actually positioned with respect to its position property.
     */
    public Overlay(Component component, Positioning positioning) {
        this(component, null, positioning, DEFAULT_STOP_EVENT);
    }

    /**
     * Creates a new instance.
     *
     * @param component
     *         The component linked to this overlay
     * @param position
     *         The position of the overlay
     * @param positioning
     *         Defines how the overlay is actually positioned with respect to its position property.
     */
    public Overlay(Component component, LongLat position, Positioning positioning) {
        this(component, position, positioning, DEFAULT_STOP_EVENT);
    }


    /**
     * Creates a new instance.
     *
     * @param component
     *         The component linked to this overlay
     * @param position
     *         The position of the overlay
     * @param positioning
     *         Defines how the overlay is actually positioned with respect to its position property.
     * @param stopEvent
     *         Whether event propagation to the map viewport should be stopped. Default is true. If true the overlay is
     *         placed in the same container as that of the controls (CSS class name ol-overlaycontainer-stopevent); if
     *         false it is placed in the container with CSS class name ol-overlaycontainer.
     */
    public Overlay(Component component, LongLat position, Positioning positioning, Boolean stopEvent) {
        this.element = component;
        this.position = position;
        this.positioning = positioning;
        this.stopEvent = stopEvent;
    }

    /**
     * Returns the position of the overlay.
     *
     * @return Coordinate with the overlay's position
     */
    public LongLat getPosition() {
        return position;
    }

    /**
     * Sets the position of the overlay.
     *
     * @param position
     *         Coordinate for the overlay's position
     */
    public void setPosition(LongLat position) {
        this.position = position;
    }

    /**
     * Sets the position of the overlay.
     *
     * @param position
     *         Coordinate for the overlay's position
     * @return This instance
     */
    public Overlay position(LongLat position) {
        setPosition(position);
        return this;
    }

    /**
     * Returns the positioning of this overlay.
     *
     * @return Positioning value
     */
    public Positioning getPositioning() {
        return positioning;
    }

    /**
     * Sets the positioning of this overlay.
     *
     * @param positioning
     *         New value
     */
    public void setPositioning(Positioning positioning) {
        this.positioning = positioning;
    }

    /**
     * Sets the positioning of this overlay.
     *
     * @param positioning
     *         New value
     * @return This instance
     */
    public Overlay positioning(Positioning positioning) {
        setPositioning(positioning);
        return this;
    }

    /**
     * Returns the element linked to this overlay.
     *
     * @return Wicket component linked to this overlay
     */
    public Component getElement() {
        return element;
    }

    /**
     * Sets the element linked to this overlay.
     *
     * @param element
     *         Wicket component linked to this overlay
     */
    public void setElement(Component element) {
        this.element = element;
    }

    /**
     * Sets the element linked to this overlay.
     *
     * @param element
     *         Wicket component linked to this overlay
     * @return This instance
     */
    public Overlay element(Component element) {
        setElement(element);
        return this;
    }

    /**
     * Returns the stop event value.
     *
     * @return Flag with the stop event value
     */
    public Boolean getStopEvent() {
        return stopEvent;
    }

    /**
     * Sets the stop event value.
     *
     * @param stopEvent
     *         New value
     */
    public void setStopEvent(Boolean stopEvent) {
        this.stopEvent = stopEvent;
    }

    /**
     * Sets the stop event value.
     *
     * @param stopEvent
     *         New value
     * @return This instance
     */
    public Overlay stopEvent(Boolean stopEvent) {
        setStopEvent(stopEvent);
        return this;
    }

    @Override
    public String getJsId() {
        return "overlay_" + element.getMarkupId();
    }

    @Override
    public String getJsType() {
        return "ol.Overlay";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(renderAttributesJs());
        builder.append("}");

        return builder.toString();
    }

    protected String renderAttributesJs() {

        StringBuilder builder = new StringBuilder();

        if (getElement() != null) {
            builder.append("'element': document.getElementById('" + element.getMarkupId() + "'),");
        }

        if (getPosition() != null) {
            builder.append("'position': " + position.renderJs() + ",");
        }

        if (getPositioning() != null) {
            builder.append("'positioning': '" + getPositioning() + "',");
        }

        return builder.toString();
    }

    /**
     * Enumeration of valid overlay positioning values.
     */
    public enum Positioning {
        BottomLeft("bottom-left"), BottomCenter("bottom-center"), BottomRight("bottom-right"),
        CenterLeft("center-left"), CenterCenter("center-center"), CenterRight("center-right"),
        TopLeft("top-left"), TopCenter("top-center"), TopRight("top-right");

        String value;

        Positioning(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Escapes single quotation marks in the provided String. When outputting Javascript code, we're using single
     * quotation marks to surround the content.
     *
     * @param text
     *         Text to escape
     * @return String with escaped text
     */
    protected String escapeQuoteJs(String text) {
        return text.replaceAll("\'", "&apos;");
    }
}
