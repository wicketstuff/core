package org.wicketstuff.foundation.orbitslider;

import org.apache.wicket.request.resource.ResourceReference;

public class OrbitSliderItem {

	private String name;
	private ResourceReference resource;
	private String caption;

	public OrbitSliderItem(String name, ResourceReference resource, String caption) {
		this.name = name;
		this.resource = resource;
		this.caption = caption;
	}
	
	public ResourceReference getResource() {
		return resource;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public String getName() {
		return name;
	}
}
