package org.wicketstuff.openlayers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.openlayers.api.Bounds;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.SphericalMercatorLonLat;
import org.wicketstuff.openlayers.api.layer.GMap;
import org.wicketstuff.openlayers.api.layer.Layer;

/**
 * Homepage
 */
public class SimpleGoogleMapPage extends WebPage
{

	private static final long serialVersionUID = 1L;
	// localhost:8080
	private static final String GMAPS_KEY = "ABQIAAAA97_buYctDhaanPL-uED8txTwM0brOpm-All5BF6PoaKBxRWWERTl_Z3abREy_5Ldy_yMuCsn5M0FmQ";

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public SimpleGoogleMapPage(final PageParameters parameters)
	{

		List<Layer> layers = new ArrayList<Layer>();
		HashMap<String, String> optionsLayer = new HashMap<String, String>();
		// optionsLayer.put("type", "G_HYBRID_MAP");
		Layer layer = new GMap("GMap", GMAPS_KEY, "2", optionsLayer);
		layers.add(layer);
		HashMap<String, String> mapOptions = new HashMap<String, String>();
		Bounds boundsExtend = new Bounds(new LonLat(-20037508.34,-20037508.34), new LonLat(20037508.34,20037508.34));
		mapOptions.put("maxExtent", boundsExtend.getJSconstructor());
		OpenLayersMap map = new OpenLayersMap("map", true, layers, mapOptions);
		map.setCenter(new SphericalMercatorLonLat(10.2, 48.9), 13);
		add(map);
	}
}
