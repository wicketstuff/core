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
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.wicketstuff.openlayers.api.Control;
import org.wicketstuff.openlayers.api.control.WMSGetFeatureInfo;
import org.wicketstuff.openlayers.api.layer.Layer;
import org.wicketstuff.openlayers.api.layer.WMS;
import org.wicketstuff.openlayers.js.JSUtils;
import org.wicketstuff.openlayers.proxy.WFSProxyBehavior;

/**
 * @author mocleiri
 * 
 *         To showcase the WMSGetFeatureInfo Control
 * 
 *         The javascript was copied from the openlayers.org examples and encoded using the Wicket
 *         Controls.
 * 
 *         See this example: http://openlayers.org/dev/examples/getfeatureinfo-popup.html
 * 
 */
public class MapWithWMSGetFeatureInfoPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MapWithWMSGetFeatureInfoPage()
	{

		List<Layer> layerList = new LinkedList<Layer>();

		HashMap<String, String> politicalOptions = new LinkedHashMap<String, String>();

		politicalOptions.put(JSUtils.getQuotedString("layers"),
			JSUtils.getQuotedString("topp:tasmania_state_boundaries"));
		politicalOptions.put("transparent", "true");
		politicalOptions.put("format", JSUtils.getQuotedString("image/png8"));

		HashMap<String, String> extraOptions = new LinkedHashMap<String, String>();
		extraOptions.put("isBaseLayer", "true");


		WMS political = new WMS("State Boundaries", "http://demo.opengeo.org/geoserver/wms",
			politicalOptions, extraOptions);

		layerList.add(political);


		HashMap<String, String> roadOptions = new LinkedHashMap<String, String>();

		roadOptions.put(JSUtils.getQuotedString("layers"),
			JSUtils.getQuotedString("topp:tasmania_roads"));
		roadOptions.put("transparent", "true");
		roadOptions.put("format", JSUtils.getQuotedString("image/png8"));

		HashMap<String, String> roadExtraOptions = new LinkedHashMap<String, String>();
		roadExtraOptions.put("isBaseLayer", "false");

		WMS roads = new WMS("Roads", "http://demo.opengeo.org/geoserver/wms", roadOptions,
			roadExtraOptions);

		layerList.add(roads);


		HashMap<String, String> mapOptions = new LinkedHashMap<String, String>();

		mapOptions.put("maxExtent", "new OpenLayers.Bounds(143.834,-43.648,148.479,-39.573)");

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

		HashMap<String, String> parameterMap = new LinkedHashMap<String, String>();

		parameterMap.put("url", JSUtils.getQuotedString("http://demo.opengeo.org/geoserver/wms"));
		parameterMap.put("title", JSUtils.getQuotedString("Test"));
		parameterMap.put("layers", "[wms" + roads.getId() + "]");
		parameterMap.put("infoFormat", JSUtils.getQuotedString("text/html"));


		WMSGetFeatureInfo getFeatureInfo = new WMSGetFeatureInfo(parameterMap,
			new AbstractReadOnlyModel<String>()
			{

				private static final long serialVersionUID = -1330688574990681527L;

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.apache.wicket.model.AbstractReadOnlyModel#getObject()
				 */
				@Override
				public String getObject()
				{
					return "alert('event = ' + event.text);";
				}

			});

		map.addControl(getFeatureInfo);
		map.addControl(Control.PanZoomBar);

		map.add(proxyBehaviour);

		add(map);
	}

}
