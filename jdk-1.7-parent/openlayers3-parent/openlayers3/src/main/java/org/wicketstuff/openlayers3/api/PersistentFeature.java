package org.wicketstuff.openlayers3.api;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.openlayers3.api.geometry.Point;
import org.wicketstuff.openlayers3.api.style.Style;

/**
 * Provides a vector object for geographic features that may be manipulated after the map is created.
 */
public class PersistentFeature extends Feature {

    /**
     * Creates a new feature.
     *
     * @param geometry
     *         The geometry of the feature
     * @param name
     *         The name for this feature
     */
    public PersistentFeature(final Point geometry, final String name) {
        this(geometry, name, null);
    }

    /**
     * Creates a new feature.
     *
     * @param geometry
     *         The geometry of the feature
     * @param name
     *         The name for this feature
     * @param style
     *         The style used to render this feature
     */
    public PersistentFeature(final Point geometry, final String name, final Style style) {
        super(geometry, name, style);
    }

    /**
     * Updates the location of the feature.
     *
     * @param target Ajax request target
     * @param point New location for the feature
     */
    public void updateGeometry(AjaxRequestTarget target, Point point) {

        // update our feature
        setGeometry(point);

        // update the map
        target.appendJavaScript(getJsId() + ".getGeometry().setCoordinates(" + point.renderJs() + ");");
    }
}
