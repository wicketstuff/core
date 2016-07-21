package org.wicketstuff.openlayers;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.openlayers.api.Control;

/**
 * Homepage
 */
public class SimpleMapWithControlsPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public SimpleMapWithControlsPage(final PageParameters parameters)
	{
		OpenLayersMap openLayersMap = new OpenLayersMap("map", true);
		add(openLayersMap);
		openLayersMap.getControls().add(Control.LayerSwitcher);
		openLayersMap.getControls().add(Control.PanZoomBar);
		openLayersMap.getControls().add(Control.MouseToolbar);
		openLayersMap.getControls().add(Control.MousePosition);

	}
}
