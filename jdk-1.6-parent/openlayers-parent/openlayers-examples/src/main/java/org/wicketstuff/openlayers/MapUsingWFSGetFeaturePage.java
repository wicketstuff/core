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

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.openlayers.api.Control;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.control.GetFeature;
import org.wicketstuff.openlayers.api.layer.Layer;
import org.wicketstuff.openlayers.api.layer.Vector;
import org.wicketstuff.openlayers.api.layer.WMS;
import org.wicketstuff.openlayers.js.JSUtils;
import org.wicketstuff.openlayers.proxy.WFSProxyBehavior;

/**
 * @author mocleiri
 * 
 *         An example that shows how a WFS layer can be used.
 * 
 *         This is based on (i.e. the javascript was copied and encoded from) the Openlayers Example
 *         from : http://openlayers.org/dev/examples/getfeature-wfs.html
 * 
 */
public class MapUsingWFSGetFeaturePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MapUsingWFSGetFeaturePage()
	{
		super();
		List<Layer> layerList = new LinkedList<Layer>();

// function init(){
// OpenLayers.ProxyHost= "proxy.cgi?url=";
// map = new OpenLayers.Map('map', {
// controls: [
// new OpenLayers.Control.PanZoom(),
// new OpenLayers.Control.Permalink(),
// new OpenLayers.Control.Navigation()
// ]
// });
// layer = new OpenLayers.Layer.WMS(
// "States WMS/WFS",
// "http://demo.opengeo.org/geoserver/ows",
// {layers: 'topp:states', format: 'image/gif'}
// );

		HashMap<String, String> options = new LinkedHashMap<String, String>();

		options.put("layers", JSUtils.getQuotedString("topp:states"));
		options.put("format", JSUtils.getQuotedString("image/png8"));

		WMS layer = new WMS("States WMS/WFS", "http://demo.opengeo.org/geoserver/ows", options);

		HashMap<String, String> vectorOptions = new LinkedHashMap<String, String>();
		vectorOptions.put("styleMap",
			"new OpenLayers.Style(OpenLayers.Feature.Vector.style['select'])");

		Vector select = new Vector("Selection", vectorOptions);

		Vector hover = new Vector("Hover");

		layerList.add(layer);
		layerList.add(select);
		layerList.add(hover);


		GetFeature control = new GetFeature(layer, true, true, "shiftKey", "ctrlKey");


//
// control = new OpenLayers.Control.GetFeature({
// protocol: OpenLayers.Protocol.WFS.fromWMSLayer(layer),
// box: true,
// hover: true,
// multipleKey: "shiftKey",
// toggleKey: "ctrlKey"
// });
// map.addControl(control);
// control.activate();
//
// map.setCenter();


		HashMap<String, String> mapOptions = new LinkedHashMap<String, String>();

// mapOptions.put("maxExtent",
// "new OpenLayers.Bounds(143.834,-43.648,148.479,-39.573)");

		final WFSProxyBehavior proxyBehaviour = new WFSProxyBehavior();

		OpenLayersMap map = new OpenLayersMap("map", true, layerList, mapOptions)
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
				return "OpenLayers.ProxyHost='" + proxyBehaviour.getProxyUrl() + "';\n" +
					super.getJSinit();
			}


		};

		map.setCenter(LonLat.parse("-140.444336,25.115234,-44.438477,50.580078"));

		map.setZoom(3);


		map.addControl(control);
		map.addControl(Control.PanZoomBar);
		map.addControl(Control.Navigation);
		map.addControl(Control.Permalink);

		map.add(proxyBehaviour);


		control.registerJavascriptEvent(map, "featureselected",
			new Model<String>("vec" + select.getId() + ".addFeatures([evt.feature]);"));
		control.registerJavascriptEvent(map, "featureunselected",
			new Model<String>("vec" + select.getId() + ".removeFeatures([evt.feature]);"));

		control.registerJavascriptEvent(map, "hoverfeature",
			new Model<String>("vec" + hover.getId() + ".addFeatures([evt.feature]);"));
		control.registerJavascriptEvent(map, "outfeature", new Model<String>("vec" + hover.getId() +
			".removeFeatures([evt.feature]);"));

		add(map);
	}


}
