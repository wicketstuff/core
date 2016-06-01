package org.wicketstuff.gmap.api;

import java.io.Serializable;
import org.apache.wicket.Session;
import org.wicketstuff.gmap.GMap;

/**
 * Clusters markers into groups. Supports the use of an options object to adapt
 * settings.
 *
 * It's grouping all the overlays/markers which are shown on the map currently.
 * This can be improved by giving a dedicated list of markers for grouping if
 * needed.
 *
 * https://googlemaps.github.io/js-marker-clusterer/docs/reference.html
 *
 * @author Rob Sonke
 * @author Joachim F. Rohde
 *
 */
public class GMarkerCluster implements Serializable {

    private static final long serialVersionUID = 1L;
    private GMap map;
    private GMarkerClusterOptions options;
    // id is session unique
    private final String id = String.valueOf(Session.get().nextSequenceValue());

    public GMarkerCluster(GMap map)
    {
        this(map, new GMarkerClusterOptions());
    }

    public GMarkerCluster(GMap map, GMarkerClusterOptions options)
    {
        this.map = map;
        this.options = options;
    }

    public String getJSconstructor()
    {
        String ret = "var markers" + id + " = [];";

        for (GOverlay o : map.getOverlays())
        {
            if (o instanceof GMarker)
            {
                ret += "markers" + id + ".push(Wicket.maps['" + map.getMapId() + "'].overlays['overlay" + o.getId() + "']);";
            }
        }

        ret += "var markerCluster" + id + " = new MarkerClusterer("
            + map.getJsReference() + ".map, "
            + "markers" + id + ", "
            + options.getJSconstructor() + ")";
        return ret;
    }
}
