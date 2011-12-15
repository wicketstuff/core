package org.wicketstuff.openlayers;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.openlayers.api.Control;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.control.DefaultSelectFeatureControlOptions;
import org.wicketstuff.openlayers.api.control.SelectFeatureControl;
import org.wicketstuff.openlayers.api.layer.Layer;
import org.wicketstuff.openlayers.api.layer.Vector;
import org.wicketstuff.openlayers.api.layer.WMS;
import org.wicketstuff.openlayers.js.JSUtils;

/**
 * @author mocleiri
 *
 */
public class MapWithTwoSelectableVectorLayersPage extends WebPage {
	private static final Logger log = LoggerFactory
			.getLogger(MapWithTwoSelectableVectorLayersPage.class);

	private static ResourceReference blueMarker = new ResourceReference(MapWithTwoSelectableVectorLayersPage.class, "marker-blue.png");
	private static ResourceReference greenMarker = new ResourceReference(MapWithTwoSelectableVectorLayersPage.class, "marker-green.png");
	private static ResourceReference goldMarker = new ResourceReference(MapWithTwoSelectableVectorLayersPage.class, "marker-gold.png");
	
	
	/**
	 * 
	 */
	public MapWithTwoSelectableVectorLayersPage() {
		
		super();
		
		List<Layer>layers = new LinkedList<Layer>();
		
//		var map, selectControl;
//        OpenLayers.Feature.Vector.style['default']['strokeWidth'] = '2';
//        function init(){
//            map = new OpenLayers.Map('map');
//            var wmsLayer = new OpenLayers.Layer.WMS(
//                "OpenLayers WMS", 
//                "http://vmap0.tiles.osgeo.org/wms/vmap0",
//                {layers: 'basic'}
//            ); 
//
		
		LinkedHashMap<String, String> wmsOptions = new LinkedHashMap<String, String>();
		
		wmsOptions.put("layers", JSUtils.getQuotedString("basic"));
		
		layers.add(new WMS("OpenLayers WMS",  "http://vmap0.tiles.osgeo.org/wms/vmap0", wmsOptions));
		
				
//            // allow testing of specific renderers via "?renderer=Canvas", etc
//            var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
//            renderer = (renderer) ? [renderer] : OpenLayers.Layer.Vector.prototype.renderers;
//
//            var vectors1 = new OpenLayers.Layer.Vector("Vector Layer 1", {
//                renderers: renderer,
//                styleMap: new OpenLayers.StyleMap({
//                    "default": new OpenLayers.Style(OpenLayers.Util.applyDefaults({
//                        externalGraphic: "../img/marker-green.png",
//                        graphicOpacity: 1,
//                        rotation: -45,
//                        pointRadius: 10
//                    }, OpenLayers.Feature.Vector.style["default"])),
//                    "select": new OpenLayers.Style({
//                        externalGraphic: "../img/marker-blue.png"
//                    })
//                })
//            });
		
		
		Vector v1;
		HashMap<String, String> v1Options = new LinkedHashMap<String, String>();
		layers.add(v1 = new Vector("Vector Layer 1", v1Options));
		
		v1Options.put("styleMap", "new OpenLayers.StyleMap({\n" +
            "'default': new OpenLayers.Style(OpenLayers.Util.applyDefaults({\n" +
                "externalGraphic: '"+urlFor(greenMarker)+"'," +
                "graphicOpacity: 1,"+
                "rotation: -45,"+
                "pointRadius: 10"+
            "}, OpenLayers.Feature.Vector.style['default']))," +
            "'select': new OpenLayers.Style({" +
                "externalGraphic: '"+urlFor(blueMarker)+"'" +
            "\n})\n})");
		
		Vector v2;
		HashMap<String, String> v2Options = new LinkedHashMap<String, String>();
		
		
		
		
//            var vectors2 = new OpenLayers.Layer.Vector("Vector Layer 2", {
//                renderers: renderer,
//                styleMap: new OpenLayers.StyleMap({
//                    "default": new OpenLayers.Style(OpenLayers.Util.applyDefaults({
//                        fillColor: "red",
//                        strokeColor: "gray",
//                        graphicName: "square",
//                        rotation: 45,
//                        pointRadius: 15
//                    }, OpenLayers.Feature.Vector.style["default"])),
//                    "select": new OpenLayers.Style(OpenLayers.Util.applyDefaults({
//                        graphicName: "square",
//                        rotation: 45,
//                        pointRadius: 15
//                    }, OpenLayers.Feature.Vector.style["select"]))
//                })
//            });
		
		v2Options.put("styleMap", "new OpenLayers.StyleMap({\n" +
	            "'default': new OpenLayers.Style(OpenLayers.Util.applyDefaults({\n" +
	                "externalGraphic: '"+urlFor(goldMarker)+"'," +
	                "graphicOpacity: 1,"+
	                "rotation: -45,"+
	                "pointRadius: 10"+
	            "}, OpenLayers.Feature.Vector.style['default']))," +
	            "'select': new OpenLayers.Style({" +
	                "externalGraphic: '"+urlFor(blueMarker)+"'" +
	            "\n})\n})");
		
		layers.add(v2 = new Vector("Vector Layer 2", v2Options));
		
		v1.registerOnEventJavascript("featureselected", "alert('selected feature '+feature.feature.id+' on Vector Layer 1');");
		
		v2.registerOnEventJavascript("featureselected", "alert('selected feature '+feature.feature.id+' on Vector Layer 2');");
		
		
		HashMap<String, String> mapOptions = new LinkedHashMap<String, String>();
		
		final StringBuffer initializationBuffer = new StringBuffer();
		
		OpenLayersMap map;
		add (map = new OpenLayersMap("map", layers, mapOptions) {

			/* (non-Javadoc)
			 * @see org.wicketstuff.openlayers.OpenLayersMap#getJSinit()
			 */
			@Override
			protected String getJSinit() {
				
				StringBuffer buf = new StringBuffer();
				
				buf.append(super.getJSinit());
				
				buf.append (initializationBuffer.toString());
				return buf.toString();
			}
			
			
		});
		
		
		map.addControl(Control.LayerSwitcher);
		
		map.addControl(new SelectFeatureControl(Arrays.asList(new Layer[] {v1, v2}), new DefaultSelectFeatureControlOptions(false, true, false, false, false)));
		
		map.setCenter(new LonLat(0,  0), 3);
		
		initializationBuffer.append("function createFeatures() {\n" + 
				"            var extent = "+map.getJSInstance()+".map.getExtent();\n" + 
				"            var features = [];\n" + 
				"            for(var i=0; i<10; ++i) {\n" + 
				"                features.push(new OpenLayers.Feature.Vector(\n" + 
				"                    new OpenLayers.Geometry.Point(extent.left + (extent.right - extent.left) * Math.random(),\n" + 
				"                    extent.bottom + (extent.top - extent.bottom) * Math.random()\n" + 
				"                )));\n" + 
				"            }\n" + 
				"            return features;\n" + 
				"        }\n");
		
		
		initializationBuffer.append(v1.getVariableName()+ ".addFeatures(createFeatures());\n");
		
		initializationBuffer.append(v2.getVariableName()+ ".addFeatures(createFeatures());\n");
		
		  
		
//            map.addLayers([wmsLayer, vectors1, vectors2]);
//            map.addControl(new OpenLayers.Control.LayerSwitcher());
//            
//            selectControl = new OpenLayers.Control.SelectFeature(
//                [vectors1, vectors2],
//                {
//                    clickout: true, toggle: false,
//                    multiple: false, hover: false,
//                    toggleKey: "ctrlKey", // ctrl key removes from selection
//                    multipleKey: "shiftKey" // shift key adds to selection
//                }
//            );
//            
//            map.addControl(selectControl);
//            selectControl.activate();
//            map.setCenter(new OpenLayers.LonLat(0, 0), 3);
		
		
//            vectors1.addFeatures(createFeatures());
//            vectors2.addFeatures(createFeatures());
//            
//            vectors1.events.on({
//                "featureselected": function(e) {
//                    showStatus("selected feature "+e.feature.id+" on Vector Layer 1");
//                },
//                "featureunselected": function(e) {
//                    showStatus("unselected feature "+e.feature.id+" on Vector Layer 1");
//                }
//            });
//            vectors2.events.on({
//                "featureselected": function(e) {
//                    showStatus("selected feature "+e.feature.id+" on Vector Layer 2");
//                },
//                "featureunselected": function(e) {
//                    showStatus("unselected feature "+e.feature.id+" on Vector Layer 2");
//                }
//            });
//        }
//        
//        function createFeatures() {
//            var extent = map.getExtent();
//            var features = [];
//            for(var i=0; i<10; ++i) {
//                features.push(new OpenLayers.Feature.Vector(
//                    new OpenLayers.Geometry.Point(extent.left + (extent.right - extent.left) * Math.random(),
//                    extent.bottom + (extent.top - extent.bottom) * Math.random()
//                )));
//            }
//            return features;
//        }
//        
//        function showStatus(text) {
//            document.getElementById("status").innerHTML = text;            
//        }
	}

	
}
