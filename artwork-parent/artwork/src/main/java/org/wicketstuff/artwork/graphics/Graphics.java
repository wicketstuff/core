package org.wicketstuff.artwork.graphics;

public abstract class Graphics {
	
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
	public void setChainedGraphics(Graphics chainedGraphics) {
		this.chainedGraphics = chainedGraphics;
	}
	public abstract String getStringForJS();

}
