package org.wicketstuff.artwork.liquidcanvas.graphics;

import java.io.Serializable;

public abstract class Graphics implements Serializable {
	
	private Graphics chainedGraphics=null;
	
	public Graphics getChainedGraphics() {
		return chainedGraphics;
	}
	public boolean isChained(){
		if(chainedGraphics!=null){
			return true;
		}
		return false;
	}
	public Graphics setChainedGraphics(Graphics chainedGraphics) {
		this.chainedGraphics = chainedGraphics;
		return chainedGraphics;
	}
	public abstract String getStringForJS();

}
