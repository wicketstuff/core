package org.wicketstuff.openlayers;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.openlayers.event.DrawListenerBehavior;

import com.vividsolutions.jts.geom.Geometry;

/**
 * Homepage
 */
public class MapWithDrawLayerPage extends WebPage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public MapWithDrawLayerPage(
			final PageParameters parameters) {

		OpenLayersMap openLayersMap = new OpenLayersMap("map");
		add(openLayersMap);
		
		

		openLayersMap.add(new DrawListenerBehavior(){@Override
		protected void onDrawEnded(Geometry poly, AjaxRequestTarget target) {
			
			target.appendJavascript("alert('Got geom+"+poly.toText()+"');");
			
		}});


	}
}
