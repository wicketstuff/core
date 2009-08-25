/**
 * MapWithGetSpecificFeaturePage.java
 *
 * Michael O'Cleirigh
 * 
 * Data Management Group
 * Department of Civil Engineering
 * University of Toronto
 *
 * Created: Aug 25, 2009
 */
package org.wicketstuff.openlayers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebRequest;
import org.wicketstuff.openlayers.api.Control;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.control.GetSpecificFeature;
import org.wicketstuff.openlayers.api.layer.Layer;
import org.wicketstuff.openlayers.api.layer.Vector;
import org.wicketstuff.openlayers.api.layer.WMS;
import org.wicketstuff.openlayers.js.JSUtils;
import org.wicketstuff.openlayers.proxy.WFSProxyBehaviour;

/**
 * @author Michael O'Cleirigh (michael.ocleirigh@dmg.utoronto.ca)
 *
 */
public class MapWithGetSpecificFeaturePage extends WebPage {

	/**
	 * 
	 */
	public MapWithGetSpecificFeaturePage() {
		
		super();
		List<Layer> layerList = new LinkedList<Layer>();
		
//		 function init(){
//	            OpenLayers.ProxyHost= "proxy.cgi?url=";
//	            map = new OpenLayers.Map('map', {
//	                controls: [
//	                    new OpenLayers.Control.PanZoom(),
//	                    new OpenLayers.Control.Permalink(),
//	                    new OpenLayers.Control.Navigation()
//	                ]
//	            });
//	            layer = new OpenLayers.Layer.WMS(
//	                "States WMS/WFS",
//	                "http://demo.opengeo.org/geoserver/ows",
//	                {layers: 'topp:states', format: 'image/gif'}
//	            );
		
				HashMap<String, String> options = new LinkedHashMap<String, String>();
				
				options.put("layers", JSUtils.getQuotedString("topp:states"));
				options.put("format", JSUtils.getQuotedString("image/png8"));
				
				WMS layer = new WMS("States WMS/WFS", "http://127.0.0.1:8081/geoserver/wms", options);

				layerList.add(layer);

				final WFSProxyBehaviour proxyBehaviour = new WFSProxyBehaviour();
				
				final AbstractDefaultAjaxBehavior callbackBehaviour = new AbstractDefaultAjaxBehavior() {
					
					@Override
					protected void respond(AjaxRequestTarget target) {
						
						RequestCycle rc = RequestCycle.get();
						
						 WebRequest wr = (WebRequest) rc.getRequest();
						
						String state = wr.getParameter("propertyValue");
						
						target.prependJavascript("alert('you selected state = "+state+");");
								
						
					}
				};
				
				GetSpecificFeature control = new GetSpecificFeature(layer, proxyBehaviour, callbackBehaviour, "http://127.0.0.1:8081/geoserver/wfs", "topp", "http://www.openplans.org/topp", "states", 4326, "STATE_NAME");
				

		HashMap<String, String> mapOptions = new LinkedHashMap<String, String>();

		mapOptions.put("srs", "4326");
//		mapOptions.put("maxExtent",
//				"new OpenLayers.Bounds(143.834,-43.648,148.479,-39.573)");

		

		OpenLayersMap map = new OpenLayersMap("map", layerList, mapOptions) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.wicketstuff.openlayers.OpenLayersMap#getJSinit()
			 */
			@Override
			protected String getJSinit() {
				return "OpenLayers.ProxyHost='"
						+ proxyBehaviour.getProxyUrl(true) + "';\n"
						+ super.getJSinit();
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
