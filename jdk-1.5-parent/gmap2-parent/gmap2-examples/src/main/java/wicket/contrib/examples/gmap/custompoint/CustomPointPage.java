package wicket.contrib.examples.gmap.custompoint;

import org.apache.wicket.request.resource.PackageResourceReference;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GIcon;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.api.GPoint;
import wicket.contrib.gmap.api.GSize;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class CustomPointPage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public CustomPointPage()
	{
		GMap2 map = new GMap2("map", GMapExampleApplication.get()
				.getGoogleMapsAPIkey());
		map.setCenter(new GLatLng(52.37649, 4.888573));
		add(map);

		GIcon icon = new GIcon(urlFor(new PackageResourceReference(CustomPointPage.class, "image.gif"), null)
				.toString(), urlFor(new PackageResourceReference(CustomPointPage.class, "shadow.png"), null)
				.toString()).iconSize(new GSize(64, 64)).shadowSize(new GSize(64, 64)).iconAnchor(
				new GPoint(19, 40)).infoWindowAnchor(new GPoint(9, 2)).infoShadowAnchor(
				new GPoint(18, 25));

		GOverlay marker = new GMarker(new GLatLng(52.37649, 4.888573), new GMarkerOptions(
				"My Title", icon));

		map.addOverlay(marker);
	}
}