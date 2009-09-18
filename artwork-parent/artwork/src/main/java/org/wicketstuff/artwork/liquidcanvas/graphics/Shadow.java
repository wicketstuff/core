package org.wicketstuff.artwork.liquidcanvas.graphics;

public class Shadow extends Graphics {

	private int width;
	private String color;
	private int shift;
	
	public Shadow(int width,String color, int shift) {
	    super();
	    this.width=width;
	    this.color=color;
	    this.shift=shift;
		
	}
	public Shadow() {
		this(3,"#000",2);
	}
	
	
	@Override
	public String getStringForJS() {
				return "shadow { width:"+width+"; color:"+color+"; shift:"+shift+";}";
	}

}
