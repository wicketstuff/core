package wicket.contrib.examples.gmap.polygones;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GPolygon;
import wicket.contrib.gmap.api.GPolyline;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class PolygonesPage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public PolygonesPage()
	{
		GMap2 map = new GMap2("topPanel", GMapExampleApplication.get()
				.getGoogleMapsAPIkey());
		map.addOverlay(new GPolygon("#000000", 4, 0.7f, "#E9601A", 0.7f, new GLatLng(37.3, -122.4),
				new GLatLng(37.2, -122.2), new GLatLng(37.3, -122.0), new GLatLng(37.4, -122.2),
				new GLatLng(37.3, -122.4)));
		map.addOverlay(new GPolyline("#FFFFFF", 8, 1.0f, new GLatLng(37.35, -122.3), new GLatLng(
				37.25, -122.25), new GLatLng(37.3, -122.2), new GLatLng(37.25, -122.15),
				new GLatLng(37.35, -122.1)));
		map.setZoom(10);
		add(map);
	}
}
