package org.wicketstuff.openlayers.api.layer;

/**
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 *
 */
public class Layer {
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

}
