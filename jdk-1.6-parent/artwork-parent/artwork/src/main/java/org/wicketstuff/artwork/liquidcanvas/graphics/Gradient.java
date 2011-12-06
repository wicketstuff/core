package org.wicketstuff.artwork.liquidcanvas.graphics;

public class Gradient extends Graphics {

	private String from=null,to=null;
	
	public Gradient() {
		this("#fff","#666");
	}
	
	/**
	 * Specify in any web color from and to gradient (rgb hex etc)
	 * @param from
	 * @param to
	 */
	public Gradient(String from, String to) {
		super();
		this.from=from;
		this.to=to;
	}
	
	
	
	@Override
	public String getStringForJS() {
		return "gradient { from:"+from+"; to:"+to+";}";
	}

}
