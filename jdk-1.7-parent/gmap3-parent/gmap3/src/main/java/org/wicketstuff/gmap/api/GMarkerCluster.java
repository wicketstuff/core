package org.wicketstuff.gmap.api;

import java.io.Serializable;

import org.wicketstuff.gmap.GMap;

/**
 * Clusters markers into groups. Supports the use of an options object to adapt
 * settings.
 * 
 * It's grouping all the overlays/markers which are shown on the map currently.
 * This can be improved by giving a dedicated list of markers for grouping if needed.
 * 
 * http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/docs/reference.html
 * 
 * @author Rob Sonke
 * 
 */
public class GMarkerCluster implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private GMap map;
	private GMarkerClusterOptions options;

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
		return "var markerCluster = new MarkerClusterer("+
					map.getJsReference()+".map, "+
					map.getJsReference()+".overlays, "+
					options.getJSconstructor()+");\n";
    }
}
