package org.wicketstuff.openlayers;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.Marker;
import org.wicketstuff.openlayers.event.PopupMarkerInfoAttributeAppender;

/**
 * Homepage
 */
public class MapWithMarkersAndPopupsInteractionPage extends WebPage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public MapWithMarkersAndPopupsInteractionPage(
			final PageParameters parameters) {

		OpenLayersMap openLayersMap = new OpenLayersMap("map");
		add(openLayersMap);
		Marker marker = new Marker(new LonLat(50, 50), new PopupPanel(
				"Hello Marker 1"));
		openLayersMap.addOverlay(marker);

		WebMarkupContainer popupClicker = new WebMarkupContainer("openPop1");

		popupClicker.add(new PopupMarkerInfoAttributeAppender("onClick", ";",
				marker, openLayersMap));

		add(popupClicker);

		marker = new Marker(new LonLat(100, 50), new PopupPanel(
				"Hello Marker 2"));
		openLayersMap.addOverlay(marker);
		popupClicker = new WebMarkupContainer("openPop2");

		popupClicker.add(new PopupMarkerInfoAttributeAppender("onClick", ";",
				marker, openLayersMap));

		add(popupClicker);



	}
}
