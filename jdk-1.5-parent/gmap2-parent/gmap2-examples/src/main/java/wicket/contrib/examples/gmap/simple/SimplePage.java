package wicket.contrib.examples.gmap.simple;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GLatLng;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class SimplePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public SimplePage()
	{
		GMap2 map = new GMap2("map", GMapExampleApplication.get()
				.getGoogleMapsAPIkey());
		map.setCenter(new GLatLng(52.37649, 4.888573));
		add(map);
	}
}
