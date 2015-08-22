package org.wicketstuff.openlayers3.behavior.view;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.openlayers3.api.coordinate.CoordinateProjected;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides a data object that models an event emitted by the map's ol.View.
 */
public class ViewEvent implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(ViewEvent.class);

    /**
     * The JsonObject that backs this instance.
     */
    private final JsonObject jsonViewEvent;

    /**
     * The projection for this view.
     */
    private String projection;

    /**
     * Array of two coordinates that specify the extent of this view.
     */
    private CoordinateProjected[] extent;

    /**
     * Coordinate at the center of this view.
     */
    private CoordinateProjected center;

    /**
     * Creates a new view event from the JSON encoded ol.View instance.
     *
     * @param jsonViewEvent JSON encoded ol.View instance
     */
    public ViewEvent(JsonObject jsonViewEvent) {
        this.jsonViewEvent = jsonViewEvent;

        transformValues();
    }

    /**
     * Returns the projection for this view.
     *
     * @return String with the view for this projection
     */
    public String getProjection() {
        return projection;
    }

    /**
     * Returns an array of two coordinates describing the extent of this view.
     *
     * @return Array of two coordinates
     */
    public CoordinateProjected[] getExtent() {
        return extent;
    }

    /**
     * Returns the coordinate at the center of this view.
     *
     * @return Coordinate of the view's center
     */
    public CoordinateProjected getCenter() {
        return center;
    }

    /**
     * Returns the JsonObject backing this instance. You may want to inspect this if you need data that isn't easily
     * accessible from this wrapper.
     *
     * @return The JsonObject backing this instance
     */
    public JsonObject getJsonObject() {
        return jsonViewEvent;
    }

    /**
     * Transforms the source projection data and sets this instance's fields.
     *
     * @return The projection
     */
    private String transformProjection() {
        JsonPrimitive projection = jsonViewEvent.getAsJsonPrimitive("transformedProjection");
        return projection.toString();
    }

    /**
     * Transforms the source extent data and sets this instance's fields.
     *
     * @return The extent
     */
    private CoordinateProjected[] transFormExtent() {

        List<Number> values = new ArrayList<Number>();
        JsonArray transformedExtent = jsonViewEvent.getAsJsonArray("transformedExtent");
        Iterator<JsonElement> iterator = transformedExtent.iterator();
        while(iterator.hasNext()) {
            JsonElement element = iterator.next();
            values.add(element.getAsDouble());
        }

        CoordinateProjected coordinate1 = new CoordinateProjected(values.get(0), values.get(1), getProjection());
        CoordinateProjected coordinate2 = new CoordinateProjected(values.get(2), values.get(3), getProjection());

        return new CoordinateProjected[]{coordinate1, coordinate2};
    }

    /**
     * Transforms the source center data and sets this instance's fields.
     *
     * @return The center
     */
    private CoordinateProjected transformCenter() {

        List<Number> values = new ArrayList<Number>();
        JsonArray transformedExtent = jsonViewEvent.getAsJsonArray("center");
        Iterator<JsonElement> iterator = transformedExtent.iterator();
        while(iterator.hasNext()) {
            JsonElement element = iterator.next();
            values.add(element.getAsDouble());
        }

        CoordinateProjected coordinate = new CoordinateProjected(values.get(0), values.get(1), getProjection());

        return coordinate;
    }

    /**
     * Transforms the source data and sets this instance's fields.
     */
    private void transformValues() {

        projection = transformProjection();
        extent = transFormExtent();
        center = transformCenter();
    }
}
