package org.wicketstuff.openlayers;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.openlayers.api.Marker;
import org.wicketstuff.openlayers.api.SphericalMercatorLonLat;

/**
 * Homepage
 */
public class MapWithMarkersAndPopupsPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 *
	 * @param parameters
	 *            Page parameters
	 */
	public MapWithMarkersAndPopupsPage(final PageParameters parameters)
	{

		OpenLayersMap openLayersMap = new OpenLayersMap("map", true);

		Marker marker = new Marker(new SphericalMercatorLonLat(50, 50), new PopupPanel("Hello Marker 1"));
		openLayersMap.addOverlay(marker);

		marker = new Marker(new SphericalMercatorLonLat(100, 50), new PopupPanel("Hello Marker 2"));
		openLayersMap.addOverlay(marker);

		add(openLayersMap);
	}
}
