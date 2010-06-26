package wicket.contrib.examples.gmap.search;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public HomePage()
	{
		final GMap2 map = new GMap2("topPanel", GMapExampleApplication.get()
				.getGoogleMapsAPIkey());
		map.enableGoogleBar(true);
		add(map);
	}
}
