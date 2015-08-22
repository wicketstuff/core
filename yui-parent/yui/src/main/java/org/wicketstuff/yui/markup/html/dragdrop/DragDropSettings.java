package org.wicketstuff.yui.markup.html.dragdrop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.wicketstuff.yui.helper.CSSInlineStyle;
import org.wicketstuff.yui.helper.ImageResourceInfo;
import org.wicketstuff.yui.helper.YuiImage;

/**
 * A SortSettings allows the user to define the sort settings
 * 
 * @author cptan
 * 
 */
public class DragDropSettings implements Serializable {
	private static final long serialVersionUID = 1L;

	// the dragable list consists of dragable slots images and dragable images
	private DragableSlotList dragableSlotList;
	// the target list consists of target slots images
	private TargetSlotList targetSlotList;

	private List<CSSInlineStyle> dragableImgStyleList = new ArrayList<CSSInlineStyle>();
	private int dragableImgWidth;
	private int dragableImgHeight;

	private List<CSSInlineStyle> targetSlotStyleList = new ArrayList<CSSInlineStyle>();
	private int targetSlotWidth;
	private int targetSlotHeight;

	private List<CSSInlineStyle> dragableSlotStyleList = new ArrayList<CSSInlineStyle>();
	private int dragableSlotWidth;
	private int dragableSlotHeight;

	public DragDropSettings() {
	}

	public static DragDropSettings getDefault(DragableSlotList dragableSlotList, TargetSlotList targetSlotList) {
		DragDropSettings settings = new DragDropSettings();
		settings.setResources(dragableSlotList, targetSlotList);
		return settings;
	}

	public void setResources(DragableSlotList dragableSlotList, TargetSlotList targetSlotList) {
		setDragableSlotList(dragableSlotList);
		setTargetSlotList(targetSlotList);
		setDragableImageResources(dragableSlotList);
		setDragableSlotResources(dragableSlotList);
		setTargetSlotResources(targetSlotList);
	}

	public void setDragableImageResources(DragableSlotList dragableSlotList) {

		for (int i = 0; i < dragableSlotList.getSize(); i++) {
			DragableSlot slot = (DragableSlot) dragableSlotList.getDragableSlot(i);
			YuiImage img = slot.getImage();

			ResourceReference imgRR = new ResourceReference(
					DragDropSettings.class, img.getFileName());

			ImageResourceInfo imgInfo = new ImageResourceInfo(imgRR);
			int imgWidth = imgInfo.getWidth();
			int imgHeight = imgInfo.getHeight();

			CSSInlineStyle imgStyle = new CSSInlineStyle();

			imgStyle.add("background", "url("
					+ RequestCycle.get().urlFor(imgRR) + ")");

			imgStyle.add("width", imgWidth + "px");
			imgStyle.add("height", imgHeight + "px");

			imgStyle.add("top", img.getTop() + "px");
			imgStyle.add("left", img.getLeft() + "px");

			dragableImgStyleList.add(imgStyle);

			this.dragableImgWidth = imgWidth;
			this.dragableImgHeight = imgHeight;
		}
	}

	public void setDragableSlotResources(DragableSlotList dragableSlotList) {
		for (int i = 0; i < dragableSlotList.getSize(); i++) {
			DragableSlot slot = (DragableSlot) dragableSlotList.getDragableSlot(i);
			YuiImage img = slot.getSlot();

			ResourceReference imgRR = new ResourceReference(
					DragDropSettings.class, img.getFileName());

			ImageResourceInfo imgInfo = new ImageResourceInfo(imgRR);
			int imgWidth = imgInfo.getWidth();
			int imgHeight = imgInfo.getHeight();

			CSSInlineStyle imgStyle = new CSSInlineStyle();
			imgStyle.add("background", "url("
					+ RequestCycle.get().urlFor(imgRR) + ")");
			imgStyle.add("width", imgWidth + "px");
			imgStyle.add("height", imgHeight + "px");

			imgStyle.add("top", img.getTop() + "px");
			imgStyle.add("left", img.getLeft() + "px");

			dragableSlotStyleList.add(imgStyle);

			this.dragableSlotWidth = imgWidth;
			this.dragableSlotHeight = imgHeight;
		}
	}

	public void setTargetSlotResources(TargetSlotList targetSlotList) {
		for (int i = 0; i < targetSlotList.getSize(); i++) {
			TargetSlot slot = (TargetSlot) targetSlotList.getTargetSlot(i);
			YuiImage img = slot.getSlot();

			ResourceReference imgRR = new ResourceReference(
					DragDropSettings.class, img.getFileName());

			ImageResourceInfo imgInfo = new ImageResourceInfo(imgRR);
			int imgWidth = imgInfo.getWidth();
			int imgHeight = imgInfo.getHeight();

			CSSInlineStyle imgStyle = new CSSInlineStyle();
			imgStyle.add("background", "url("
					+ RequestCycle.get().urlFor(imgRR) + ")");
			imgStyle.add("width", imgWidth + "px");
			imgStyle.add("height", imgHeight + "px");

			imgStyle.add("top", img.getTop() + "px");
			imgStyle.add("left", img.getLeft() + "px");

			targetSlotStyleList.add(imgStyle);

			this.targetSlotWidth = imgWidth;
			this.targetSlotHeight = imgHeight;
		}
	}

	/**
	 * @return the dragableImgHeight
	 */
	public int getDragableImgHeight() {
		return dragableImgHeight;
	}

	/**
	 * @param dragableImgHeight the dragableImgHeight to set
	 */
	public void setDragableImgHeight(int dragableImgHeight) {
		this.dragableImgHeight = dragableImgHeight;
	}

	/**
	 * @return the dragableImgStyleList
	 */
	public List<CSSInlineStyle> getDragableImgStyleList() {
		return dragableImgStyleList;
	}

	/**
	 * @param dragableImgStyleList the dragableImgStyleList to set
	 */
	public void setDragableImgStyleList(List<CSSInlineStyle> dragableImgStyleList) {
		this.dragableImgStyleList = dragableImgStyleList;
	}

	/**
	 * @return the dragableImgWidth
	 */
	public int getDragableImgWidth() {
		return dragableImgWidth;
	}

	/**
	 * @param dragableImgWidth the dragableImgWidth to set
	 */
	public void setDragableImgWidth(int dragableImgWidth) {
		this.dragableImgWidth = dragableImgWidth;
	}

	/**
	 * @return the dragableSlotHeight
	 */
	public int getDragableSlotHeight() {
		return dragableSlotHeight;
	}

	/**
	 * @param dragableSlotHeight the dragableSlotHeight to set
	 */
	public void setDragableSlotHeight(int dragableSlotHeight) {
		this.dragableSlotHeight = dragableSlotHeight;
	}

	/**
	 * @return the dragableSlotList
	 */
	public DragableSlotList getDragableSlotList() {
		return dragableSlotList;
	}

	/**
	 * @param dragableSlotList the dragableSlotList to set
	 */
	public void setDragableSlotList(DragableSlotList dragableSlotList) {
		this.dragableSlotList = dragableSlotList;
	}

	/**
	 * @return the dragableSlotStyleList
	 */
	public List<CSSInlineStyle> getDragableSlotStyleList() {
		return dragableSlotStyleList;
	}

	/**
	 * @param dragableSlotStyleList the dragableSlotStyleList to set
	 */
	public void setDragableSlotStyleList(List<CSSInlineStyle> dragableSlotStyleList) {
		this.dragableSlotStyleList = dragableSlotStyleList;
	}

	/**
	 * @return the dragableSlotWidth
	 */
	public int getDragableSlotWidth() {
		return dragableSlotWidth;
	}

	/**
	 * @param dragableSlotWidth the dragableSlotWidth to set
	 */
	public void setDragableSlotWidth(int dragableSlotWidth) {
		this.dragableSlotWidth = dragableSlotWidth;
	}

	/**
	 * @return the targetSlotHeight
	 */
	public int getTargetSlotHeight() {
		return targetSlotHeight;
	}

	/**
	 * @param targetSlotHeight the targetSlotHeight to set
	 */
	public void setTargetSlotHeight(int targetSlotHeight) {
		this.targetSlotHeight = targetSlotHeight;
	}

	/**
	 * @return the targetSlotList
	 */
	public TargetSlotList getTargetSlotList() {
		return targetSlotList;
	}

	/**
	 * @param targetSlotList the targetSlotList to set
	 */
	public void setTargetSlotList(TargetSlotList targetSlotList) {
		this.targetSlotList = targetSlotList;
	}

	/**
	 * @return the targetSlotStyleList
	 */
	public List<CSSInlineStyle> getTargetSlotStyleList() {
		return targetSlotStyleList;
	}

	/**
	 * @param targetSlotStyleList the targetSlotStyleList to set
	 */
	public void setTargetSlotStyleList(List<CSSInlineStyle> targetSlotStyleList) {
		this.targetSlotStyleList = targetSlotStyleList;
	}

	/**
	 * @return the targetSlotWidth
	 */
	public int getTargetSlotWidth() {
		return targetSlotWidth;
	}

	/**
	 * @param targetSlotWidth the targetSlotWidth to set
	 */
	public void setTargetSlotWidth(int targetSlotWidth) {
		this.targetSlotWidth = targetSlotWidth;
	}
	


}
