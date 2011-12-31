
package org.wicketstuff.openlayers.api.layer;

import java.io.Serializable;
import java.util.*;

import org.apache.wicket.behavior.HeaderContributor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.openlayers.js.JSUtils;

/**
 * @author mocleiri
 *
 */
public class ArcGISRestLayer extends Layer implements Serializable {
	private static final Logger log = LoggerFactory
			.getLogger(ArcGISRestLayer.class);
	private final String title;
	private final String url;
	private final boolean isBaseLayer;
	private final List<Integer> layers;

	/**
	 * 
	 */
	public ArcGISRestLayer(String title, String url, List<Integer>layers, boolean isBaseLayer) {
		super ("arcgisrest");
		this.title = title;
		this.url = url;
		this.layers = layers;
		this.isBaseLayer = isBaseLayer;
		
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.openlayers.api.layer.Layer#getHeaderContributors()
	 */
	@Override
	public List<HeaderContributor> getHeaderContributors() {
		return new ArrayList<HeaderContributor>();
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.openlayers.api.layer.Layer#getJSconstructor()
	 */
	@Override
	public String getJSconstructor() {
		
		Map<String, String>layerOptionsMap = new LinkedHashMap<String, String>();
		
		StringBuffer layersString = new StringBuffer();
		
		for (int i = 0; i < layers.size(); i++) {
			
			if (i != 0)
				layersString.append(", ");
			
			layersString.append(layers.get(i));
		}
		
		layerOptionsMap.put("layers", JSUtils.getQuotedString(layersString.toString()));
		
		layerOptionsMap.put("format", JSUtils.getQuotedString("png24"));
		
		
		
		Map<String, String>attributeMap = new LinkedHashMap<String, String>();
		
		attributeMap.put("isBaseLayer", String.valueOf(isBaseLayer));
		attributeMap.put("attribution", JSUtils.getQuotedString(title));
		attributeMap.put("opacity", "1");
		attributeMap.put("visibility", "true");
		
		
		// java script example
//		  esriSat = new OpenLayers.Layer.ArcGIS93Rest("ESRI Satellite",
//				  
//				  "http://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/export?f=image",
//				          {   layers :'show:0'
//				          },
//				          {   isBaseLayer:false,
//				              attribution:"ESRI Satellite",
//				              opacity:1,
//				              visibility:true
//				          });
		
		String layerOptions = getJSOptionsMap(layerOptionsMap);
		
		String attributeOptions = getJSOptionsMap(attributeMap);
		
		String content  = getJSconstructor("OpenLayers.Layer.ArcGIS93Rest", Arrays.asList(new String[] {JSUtils.getQuotedString(title), JSUtils.getQuotedString(url), layerOptions, attributeOptions}));
		
		return content;
	}
}
