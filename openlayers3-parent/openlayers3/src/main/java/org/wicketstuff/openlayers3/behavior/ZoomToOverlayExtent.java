package org.wicketstuff.openlayers3.behavior;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.wicketstuff.openlayers3.api.util.HeaderUtils;

/**
 * Provides a behavior that zooms the map to include all overlays.
 */
public class ZoomToOverlayExtent extends Behavior {

    /**
     * Buffer to place around the zoomed extent.
     */
    private Number buffer;

    /**
     * Creates a new instance.
     */
    public ZoomToOverlayExtent() {
        this(null);
    }

    /**
     * Creates a new instance.
     *
     * @param buffer
     *         Buffer to add around the zoomed extent.
     */
    public ZoomToOverlayExtent(Number buffer) {
        this.buffer = buffer;
    }

    /**
     * Returns the buffer value.
     *
     * @return Buffer value
     */
    public Number getBuffer() {
        return buffer;
    }

    /**
     * Sets the buffer value.
     *
     * @param buffer
     *         New value
     */
    public void setBuffer(Number buffer) {
        this.buffer = buffer;
    }

    /**
     * Sets the buffer value.
     *
     * @param buffer
     *         New value
     * @return This instance
     */
    public ZoomToOverlayExtent buffer(Number buffer) {
        setBuffer(buffer);
        return this;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        final Map<String, CharSequence> params = new HashMap<String, CharSequence>();
        params.put("componentId", component.getMarkupId());
        params.put("buffer", buffer != null ? buffer.toString() : "NULL");

        HeaderUtils.renderOnDomReady(response, ZoomToOverlayExtent.class,
                "ZoomToOverlayExtent.js", params);
    }
}
