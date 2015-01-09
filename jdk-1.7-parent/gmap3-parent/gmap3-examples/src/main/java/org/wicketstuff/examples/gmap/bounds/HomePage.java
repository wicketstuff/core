package org.wicketstuff.examples.gmap.bounds;

import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;
import org.wicketstuff.gmap.geocoder.Geocoder;

/**
 * Demonstrates how to use the bounds functionality in Google maps
 */
public class HomePage extends WicketExamplePage
{
    private static final long serialVersionUID = 1L;

    public HomePage()
    {
        final GMap map = new GMap("topPanel");
        add(map);
        
        List<GLatLng> markersToShow = new ArrayList<GLatLng>();
        
        // add some markers
        GLatLng glatlng1 = new GLatLng(47.4915285, 8.2050407);
        GMarkerOptions opts1 = new GMarkerOptions(map, glatlng1);
        GMarker marker1 = new GMarker("marker1", opts1);
        map.addOverlay(marker1);
        
        GLatLng glatlng2 = new GLatLng(50.7706934, 2.2164129);
        GMarkerOptions opts2 = new GMarkerOptions(map, glatlng2);
        GMarker marker2 = new GMarker("marker2", opts2);
        map.addOverlay(marker2);
        
        GLatLng glatlng3 = new GLatLng(48.858859, 2.34706);
        GMarkerOptions opts3 = new GMarkerOptions(map, glatlng3);
        GMarker marker3 = new GMarker("marker3", opts3);
        map.addOverlay(marker3);
        
        // add them to the list of coordinates that we need in our viewport
        markersToShow.add(glatlng1);
        markersToShow.add(glatlng2);
        markersToShow.add(glatlng3);
        map.fitMarkers(markersToShow);

        // ###############################################
        // e.g. to center and fit zoom of an address
        // ###############################################
        final GMap mapCenterAddress = new GMap("mapCenterAddress");
        Geocoder gecoder = new Geocoder();
        try
        {
            gecoder.centerAndFitZoomForAdress(mapCenterAddress, "Frankfurt am Main");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        add(mapCenterAddress);
    }
}