package org.wicketstuff.artwork.liquidcanvas.graphics;

public class Border extends Graphics {

	private String color = null;
	private int width;

	public Border(String color, int width) {
		super();
		this.color = color;
		this.width = width;

	}
public Border() {
	this("#f80", 3);
}

	@Override
	public String getStringForJS() {
		return "border {color:" + color + " ; width:" + width + ";}";
	}

}
