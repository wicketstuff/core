package org.wicketstuff.openlayers.api.layer;

import java.util.List;
import java.util.Map;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.util.string.JavascriptUtils;
import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.js.Constructor;
import org.wicketstuff.openlayers.js.ObjectLiteral;

/**
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 *
 */
public abstract class Layer {
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return String.valueOf(System.identityHashCode(this));
	}

	public final String getJSAddLayer(IOpenLayersMap map) {
		return "var layer"
				+ getId()
				+ " = "
				+ getJSconstructor()
				+ ";\n"
				+ map.getJSinvoke("addLayer(layer" + getId() + ", " + getId()
						+ ")");
	}

	public abstract List<HeaderContributor> getHeaderContributors();

	public abstract String getJSconstructor();
	
	/**
	 * A helper to build the { ... } options list from a map.
	 * 
	 * @param options
	 * @return
	 */
	protected String getJSOptionsMap (Map<String, String>options) {
		
		if (options == null || options.size() == 0)
			return null;

		ObjectLiteral builder = new ObjectLiteral();
		
		for (String key : options.keySet()) {
		
			builder.set(key, options.get(key));
			
		}
		
		return builder.toJS();
	}
	
	
	/**
	 * A convience method for the common initialization case.
	 * 
	 * @param javascriptTypeName
	 * @param options
	 * @return the contextualized contstructor for the layer.
	 */
	
	
	protected String getJSconstructor(String javascriptTypeName, List<String>parameterList) {
		
		Constructor c = new Constructor(javascriptTypeName);
		
		for (String parameter : parameterList) {
			
			c.add(parameter);
			
		}
		
		return c.toJS();
	}
}
