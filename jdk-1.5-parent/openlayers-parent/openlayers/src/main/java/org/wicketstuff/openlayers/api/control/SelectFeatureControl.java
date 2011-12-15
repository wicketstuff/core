package org.wicketstuff.openlayers.api.control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.api.layer.Layer;
import org.wicketstuff.openlayers.api.layer.Vector;
import org.wicketstuff.openlayers.js.Constructor;

public class SelectFeatureControl extends AbstractControl {
	
	private static final long serialVersionUID = -3832954618666235621L;
	private Map<String, String> parameters = null;

	public SelectFeatureControl() {
		this(null);
	}

	public SelectFeatureControl(Map<String, String> parameters) {
		super("SelectFeature", false);
		this.parameters = parameters;
	}
	
	public SelectFeatureControl (List<Layer>layers, ISelectFeatureControlOptions options) {
		super("SelectFeature", false);
		
		this.setLayerList(layers);
		
		this.parameters = new LinkedHashMap<String, String>();
		
		
		if (options.isBoxSelectionEnabled())
			this.parameters.put("box", String.valueOf(Boolean.TRUE));
		else
			this.parameters.put("box", String.valueOf(Boolean.FALSE));
		
		if (options.isClickoutEnabled())
			this.parameters.put("clickout", String.valueOf(Boolean.TRUE));
		else
			this.parameters.put("clickout", String.valueOf(Boolean.FALSE));
		
		if (options.isHighlightOnlyEnabled())
			this.parameters.put("highlightOnly", String.valueOf(Boolean.TRUE));
		else
			this.parameters.put("highlightOnly", String.valueOf(Boolean.FALSE));
		
		if (options.isHoverEnabled())
			this.parameters.put("hover", String.valueOf(Boolean.TRUE));
		else
			this.parameters.put("hover", String.valueOf(Boolean.FALSE));
		
		
		if (options.isMultipleSelectEnabled())
			this.parameters.put("multiple", String.valueOf(Boolean.TRUE));
		else
			this.parameters.put("multiple", String.valueOf(Boolean.FALSE));
		
		if (options.isToggleByMouseEnabled())
			this.parameters.put("toggle", String.valueOf(Boolean.TRUE));
		else
			this.parameters.put("toggle", String.valueOf(Boolean.FALSE));
				
	}
	

	

	//	new OpenLayers.Control.SelectFeature(
//            vectors,
//            {
//                clickout: false, toggle: false,
//                multiple: false, hover: false,
//                toggleKey: "ctrlKey", // ctrl key removes from selection
//                multipleKey: "shiftKey", // shift key adds to selection
//                box: true
//            }
//        )
	@Override
	public String getJSadd(IOpenLayersMap map) {
		
		if (parameters == null)
			return super.getJSadd(map);
		return super.getJSadd(map, parameters)
				+ super.getJSinvoke(map, "activate()");
		
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}
}
