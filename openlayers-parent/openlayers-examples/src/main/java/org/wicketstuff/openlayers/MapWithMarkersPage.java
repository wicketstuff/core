package org.wicketstuff.openlayers;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.Marker;

/**
 * Homepage
 */
public class MapWithMarkersPage extends WebPage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public MapWithMarkersPage(final PageParameters parameters) {

		OpenLayersMap<?>openLayersMap = new OpenLayersMap<Void>("map");

		Marker marker = new Marker(new LonLat(50, 50));
		openLayersMap.addOverlay(marker);

		marker = new Marker(new LonLat(100, 50));
		openLayersMap.addOverlay(marker);

		add(openLayersMap);
	}
}
