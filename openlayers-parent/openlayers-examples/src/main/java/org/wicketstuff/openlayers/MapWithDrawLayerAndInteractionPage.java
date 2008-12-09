package org.wicketstuff.openlayers;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.Marker;
import org.wicketstuff.openlayers.api.control.RemoveDrawControl;
import org.wicketstuff.openlayers.event.DrawListenerBehavior;

import com.vividsolutions.jts.geom.Geometry;

/**
 * Homepage
 */
public class MapWithDrawLayerAndInteractionPage extends WebPage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public MapWithDrawLayerAndInteractionPage(final PageParameters parameters) {

		OpenLayersMap openLayersMap = new OpenLayersMap("map");
		add(openLayersMap);

		final Marker marker = new Marker(new LonLat(50, 50), new PopupPanel(
				"Hello Marker 1"));
		openLayersMap.addOverlay(marker);

		openLayersMap.add(new DrawListenerBehavior() {
			@Override
			protected void onDrawEnded(Geometry poly, AjaxRequestTarget target) {
				if(poly.covers(marker.getLonLat().getPoint()))
				{
				target.appendJavascript("alert('The geom you drew contained the marker');");
				}
				else{
					target.appendJavascript("alert('The geom you drew did not contain the marker');");	
				}

			}
		});
		WebMarkupContainer removeDiv=new WebMarkupContainer("removeDiv");
		removeDiv.add(new RemoveDrawControl(openLayersMap));
		add(removeDiv);

	}
}
