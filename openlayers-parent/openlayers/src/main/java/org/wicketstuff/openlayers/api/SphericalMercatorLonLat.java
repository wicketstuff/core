package org.wicketstuff.openlayers.api;

import com.vividsolutions.jts.geom.Point;
import org.wicketstuff.openlayers.OpenLayersMapUtils;

/**
 * Provides a Spherical Mercator coordinate.
 */
public class SphericalMercatorLonLat extends LonLat {

    /**
     * Default projection
     * */
    private static final String DEFAULT_PROJECTION = "EPSG:4326";

    /**
     * Creates a new instance. When added to a map, it will be transformed
     * onto the default OpenLayers projection.
     *
     * @param lng Spherical Mercator Longitude
     * @param lat Spherical Mercator Latitude
     */
    public SphericalMercatorLonLat(double lng, double lat)
    {
        this(OpenLayersMapUtils.createPoint(lng, lat));
    }

    /**
     * Creates a new instance. when added to a map, it will be transformed
     * onto the supplied projection.
     *
     * @param lng Spherical Mercator Longitude
     * @param lat Spherical Mercator Latitude
     * @param targetProjection The target projection for the coordinate
     */
    public SphericalMercatorLonLat(double lng, double lat, String targetProjection)
    {
        this(OpenLayersMapUtils.createPoint(lng, lat), targetProjection);
    }

    /**
     * Creates a new instance. When added to a map, it will be transformed
     * onto the default OpenLayers projection.
     *
     * @param point Spherical Mercator coordinate
     */
    public SphericalMercatorLonLat(Point point)
    {
        this(point, LonLat.DEFAULT_PROJECTION);
    }

    /**
     * Creates a new instance. When added to a map, it will be transformed
     * onto the supplied projection.
     *
     * @param point Spherical Mercator coordinate
     * @param targetProjection The target projection for the coordinate
     */
    public SphericalMercatorLonLat(Point point, String targetProjection)
    {
        super(point, DEFAULT_PROJECTION, targetProjection);
    }
}
