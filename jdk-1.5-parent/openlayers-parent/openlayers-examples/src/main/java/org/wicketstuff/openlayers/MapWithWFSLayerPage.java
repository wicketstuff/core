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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.openlayers.api.Control;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.layer.Layer;
import org.wicketstuff.openlayers.api.layer.WFS;
import org.wicketstuff.openlayers.api.layer.WMS;
import org.wicketstuff.openlayers.js.JSUtils;
import org.wicketstuff.openlayers.proxy.WFSProxyBehavior;

/**
 * @author mocleiri
 * 
 *         Shows how a WFS layer can be used.
 * 
 *         Created from this example: http://openlayers.org/dev/examples/wfs.html
 * 
 */
public class MapWithWFSLayerPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MapWithWFSLayerPage()
	{
		super();

		List<Layer> layerList = new LinkedList<Layer>();
		HashMap<String, String> backgroundLayerOptions = new LinkedHashMap<String, String>();

		backgroundLayerOptions.put("layers", JSUtils.getQuotedString("basic"));

		HashMap<String, String> extraOptions = new LinkedHashMap<String, String>();

		extraOptions.put("isBaseLayer", "true");

		WMS backgroundLayer = new WMS("OpenLayers WMS", "http://labs.metacarta.com/wms/vmap0",
			backgroundLayerOptions, extraOptions);

		layerList.add(backgroundLayer);

		Map<String, String> wfsLayerOptions = new LinkedHashMap<String, String>();

		wfsLayerOptions.put("typeName", JSUtils.getQuotedString("OWLS"));
		wfsLayerOptions.put("maxFeatures", "10");

		Map<String, String> wfsLayerExtraOptions = new LinkedHashMap<String, String>();

		wfsLayerExtraOptions.put("featureClass", "OpenLayers.Feature.WFS");

		WFS wfsLayer = new WFS("Owl Survey", "http://www.bsc-eoc.org/cgi-bin/bsc_ows.asp?",
			wfsLayerOptions, wfsLayerExtraOptions);

		layerList.add(wfsLayer);

		final WFSProxyBehavior wfsProxy = new WFSProxyBehavior();

		OpenLayersMap map = new OpenLayersMap("map", true, layerList,
			new LinkedHashMap<String, String>())
		{

			private static final long serialVersionUID = 1L;

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.wicketstuff.openlayers.OpenLayersMap#getJSinit()
			 */
			@Override
			protected String getJSinit()
			{
				return "OpenLayers.ProxyHost='" + wfsProxy.getProxyUrl() + "';\n" +
					super.getJSinit();
			}
		};

		// this hides the mapers layer from the layer switcher
		map.setShowMarkersInLayerSwitcher(false);

		map.addControl(Control.LayerSwitcher);

		map.setCenter(LonLat.parse("-100, 60"), 3);

		map.add(wfsProxy);
		add(map);

	}


}
