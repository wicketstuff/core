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

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.wicketstuff.openlayers.api.Control;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.control.GetSpecificFeature;
import org.wicketstuff.openlayers.api.layer.Layer;
import org.wicketstuff.openlayers.api.layer.WMS;
import org.wicketstuff.openlayers.js.JSUtils;
import org.wicketstuff.openlayers.proxy.WFSProxyBehavior;

/**
 * @author mocleiri
 * 
 */
public class MapWithGetSpecificFeaturePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MapWithGetSpecificFeaturePage()
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
		options.put("srs", JSUtils.getQuotedString("EPSG:4326"));


		WMS layer = new WMS("States WMS/WFS", "http://demo.opengeo.org:80/geoserver/wms", options);

		layerList.add(layer);

		final WFSProxyBehavior proxyBehaviour = new WFSProxyBehavior();

		final AbstractDefaultAjaxBehavior callbackBehaviour = new AbstractDefaultAjaxBehavior()
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target)
			{

				RequestCycle rc = RequestCycle.get();

				WebRequest wr = (WebRequest)rc.getRequest();

				String state = wr.getRequestParameters()
					.getParameterValue("propertyValue")
					.toString();

				target.prependJavaScript("alert('you selected state = " + state + ");");


			}
		};

		GetSpecificFeature control = new GetSpecificFeature(layer, proxyBehaviour,
			callbackBehaviour, "http://demo.opengeo.org:80/geoserver/wms", "topp",
			"http://www.openplans.org/topp", "states", 4326, "STATE_NAME");


		HashMap<String, String> mapOptions = new LinkedHashMap<String, String>();

		mapOptions.put("projection", JSUtils.getQuotedString("EPSG:4326"));
		mapOptions.put("units", JSUtils.getQuotedString("degrees"));
// mapOptions.put("maxExtent",
// "new OpenLayers.Bounds(143.834,-43.648,148.479,-39.573)");


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
		map.add(callbackBehaviour);

		add(map);

	}


}
