package org.wicketstuff.openlayers.api.layer;

import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.wicketstuff.openlayers.IOpenLayersMap;

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
}
