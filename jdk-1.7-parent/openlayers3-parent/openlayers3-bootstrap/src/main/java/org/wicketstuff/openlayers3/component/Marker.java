package org.wicketstuff.openlayers3.component;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.wicketstuff.openlayers3.api.util.Color;

/**
 * Provides a marker that may be placed on a amp.
 */
public class Marker extends Panel {

    /**
     * The color of the marker.
     */
    public Color color;

    /**
     * Creates a new instance.
     *
     * @param id
     *         Wicket element ID for the marker
     * @param color
     *         The color of the marker
     */
    public Marker(final String id, final String color) {
        this(id, new Color(color));
    }

    /**
     * Creates a new instance.
     *
     * @param id
     *         Wicket element ID for the marker
     * @param color
     *         The color of the marker
     */
    public Marker(final String id, final Color color) {
        super(id);
        this.color = color;
    }

    /**
     * Returns the color of the marker.
     *
     * @return The marker's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the marker.
     *
     * @param color
     *         New value
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the color of the marker.
     *
     * @param color
     *         New value
     * @return This instance
     */
    public Marker color(Color color) {
        setColor(color);
        return this;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new WebMarkupContainer("pin").add(
                new AttributeAppender("style", "background-color: " + color + ";")));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(new CssResourceReference(Marker.class,
                "Marker.css")));
    }
}
