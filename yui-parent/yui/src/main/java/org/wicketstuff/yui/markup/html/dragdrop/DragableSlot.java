package org.wicketstuff.yui.markup.html.dragdrop;

import org.wicketstuff.yui.helper.YuiImage;

public class DragableSlot{
	private YuiImage slot;
	private YuiImage image;
	private int top;
	private int left;
	
	public DragableSlot(YuiImage slot, YuiImage image, int top, int left){
		this.slot= slot;
		this.image= image;
		this.top= top;
		this.left= left;
	}

	/**
	 * @return the image
	 */
	public YuiImage getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(YuiImage image) {
		this.image = image;
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
