package org.wicketstuff.yui.markup.html.dragdrop;

import org.wicketstuff.yui.helper.YuiImage;

public class TargetSlot {
	private YuiImage slot;
	private int top;
	private int left;
	
	public TargetSlot(YuiImage slot, int top, int left){
		this.slot= slot;
		this.top= top;
		this.left= left;
	}

	/**
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * @return the slot
	 */
	public YuiImage getSlot() {
		return slot;
	}

	/**
	 * @param slot the slot to set
	 */
	public void setSlot(YuiImage slot) {
		this.slot = slot;
	}

	/**
	 * @return the top
	 */
	public int getTop() {
		return top;
	}

	/**
	 * @param top the top to set
	 */
	public void setTop(int top) {
		this.top = top;
	}

}
