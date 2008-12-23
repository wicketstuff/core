package org.wicketstuff.artwork.liquidcanvas.graphics;

public class Fill extends Graphics {

	private String color = null;

	public Fill(String color) {
		super();
		this.color = color;

	}
public Fill() {
	this("#aaa");
}

	@Override
	public String getStringForJS() {
		return "fill {color:" + color + "}";
	}

}
