package org.wicketstuff.foundation.orbitslider;

public class OrbitSliderContent {

	private String name;
	private String heading;
	private String subheading;

	public OrbitSliderContent(String name, String heading, String subheading) {
		this.name = name;
		this.heading = heading;
		this.subheading = subheading;
	}
	
	public String getName() {
		return name;
	}
	
	public String getHeading() {
		return heading;
	}
	
	public String getSubheading() {
		return subheading;
	}
}
