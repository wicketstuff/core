/*
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.openlayers;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.openlayers.api.Marker;
import org.wicketstuff.openlayers.api.SphericalMercatorLonLat;
import org.wicketstuff.openlayers.event.PopupMarkerInfoAttributeAppender;

/**
 * Homepage
 */
public class MapWithMarkersAndPopupsInteractionPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 *
	 * @param parameters
	 *            Page parameters
	 */
	public MapWithMarkersAndPopupsInteractionPage(final PageParameters parameters)
	{

		OpenLayersMap openLayersMap = new OpenLayersMap("map", true);
		add(openLayersMap);
		Marker marker = new Marker(new SphericalMercatorLonLat(50, 50), new PopupPanel("Hello Marker 1"));
		openLayersMap.addOverlay(marker);

		WebMarkupContainer popupClicker = new WebMarkupContainer("openPop1");

		popupClicker.add(new PopupMarkerInfoAttributeAppender("onClick", ";", marker, openLayersMap));

		add(popupClicker);

		marker = new Marker(new SphericalMercatorLonLat(100, 50), new PopupPanel("Hello Marker 2"));
		openLayersMap.addOverlay(marker);
		popupClicker = new WebMarkupContainer("openPop2");

		popupClicker.add(new PopupMarkerInfoAttributeAppender("onClick", ";", marker, openLayersMap));

		add(popupClicker);


	}
}
