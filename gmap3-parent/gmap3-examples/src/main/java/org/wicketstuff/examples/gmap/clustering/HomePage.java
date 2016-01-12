package org.wicketstuff.examples.gmap.clustering;

import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerClusterOptions;
import org.wicketstuff.gmap.api.GMarkerClusterStyle;
import org.wicketstuff.gmap.api.GMarkerOptions;
import org.wicketstuff.gmap.api.GMarkerCluster;
import org.wicketstuff.gmap.event.ClickListener;

/**
 * Demonstrates how to use the clustering markers
 */
public class HomePage extends WicketExamplePage
{
    private static final long serialVersionUID = 1L;
    
    private final static String image1 = "http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/images/heart30.png";
    private final static String image2 = "http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/images/heart40.png";
    private final static String image3 = "http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/images/heart50.png";
    
    
    public HomePage()
    {
        final GMap mapDefault = new GMap("mapPanelDefault");
        add(mapDefault);
        
        final GMap map = new GMap("mapPanel");
        add(map);
        
        List<GLatLng> markersToShow = new ArrayList<GLatLng>();
        double minLat = -85.00;
        double maxLat = 85.00;
        double minLon = -180.00;
        double maxLon = 180.00;
        
        // generate a bunch of random markers
        for(int i=0; i<1000; i++)
        {
            double latitude = minLat + (double)(Math.random() * ((maxLat - minLat) + 1));
            double longitude = minLon + (double)(Math.random() * ((maxLon - minLon) + 1));
        	
            GLatLng glatlng = new GLatLng(latitude, longitude);
            markersToShow.add(glatlng);
            
            map.addOverlay(new GMarker("marker"+i, new GMarkerOptions(map, glatlng)));
            mapDefault.addOverlay(new GMarker("marker"+i, new GMarkerOptions(mapDefault, glatlng)));
        }
        
        // set some options
        GMarkerClusterOptions options = new GMarkerClusterOptions();
        options.setMinimumClusterSize(3);
        List<GMarkerClusterStyle> styles = new ArrayList<GMarkerClusterStyle>();
        
        GMarkerClusterStyle style1 = new GMarkerClusterStyle();
        style1.setUrl(image1).setHeight(26).setWidth(30);
        GMarkerClusterStyle style2 = new GMarkerClusterStyle();
        style2.setUrl(image2).setHeight(35).setWidth(40);
        GMarkerClusterStyle style3 = new GMarkerClusterStyle();
        style3.setUrl(image3).setHeight(44).setWidth(50);
        
        styles.add(style1);
        styles.add(style2);
        styles.add(style3);
        options.setStyles(styles);
        
        // cluster all markers with specified settings
        map.setMarkerCluster(new GMarkerCluster(map, options));
        mapDefault.setMarkerCluster(new GMarkerCluster(mapDefault));
        
        // for getting a good overview immediately
        map.fitMarkers(markersToShow);
        mapDefault.fitMarkers(markersToShow);
    }
}