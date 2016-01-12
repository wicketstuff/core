package org.wicketstuff.openlayers;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Homepage
 */
public class SimpleMapPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public SimpleMapPage(final PageParameters parameters)
	{

		add(new OpenLayersMap("map", true));
	}
}
