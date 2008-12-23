package org.wicketstuff.artwork.liquidcanvas.graphics;

public class Image extends Graphics {

	private String url = null;

	public Image(String url) {
		super();
		this.url = url;

	}
public Image() {
	this("http://www.ruzee.com/files/liquid-canvas-image.png");
}

	@Override
	public String getStringForJS() {
		return "image {url:" + url + "}";
	}

}
