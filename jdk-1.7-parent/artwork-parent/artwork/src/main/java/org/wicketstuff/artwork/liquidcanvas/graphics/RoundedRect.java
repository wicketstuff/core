package org.wicketstuff.artwork.liquidcanvas.graphics;

public class RoundedRect extends Graphics {

	byte radius=0;
	
	public RoundedRect(byte radius) {
		this.radius=radius;
	}
	public RoundedRect() {
		this.radius=50;
	}
	
	@Override
	public String getStringForJS() {
		return "roundedRect{radius:"+radius+"}";
	}

}
