package org.wicketstuff.examples.gmap.simple.ajax;

import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;

public class MapPanel extends Panel
{
  public MapPanel(String id)
  {
    super(id);
    GMap map = new GMap("map");
    map.setStreetViewControlEnabled(false);
    map.setScaleControlEnabled(true);
    map.setScrollWheelZoomEnabled(true);
    map.setCenter(new GLatLng(52.47649, 13.228573));        
    add(map);
  }
  
}
