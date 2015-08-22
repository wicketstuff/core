package org.wicketstuff.yui.markup.html.sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;

import org.wicketstuff.yui.helper.ImageResourceInfo;
import org.wicketstuff.yui.helper.CSSInlineStyle;
import org.wicketstuff.yui.helper.YuiImage;

/**
 * A SortSettings allows the user to define the sort settings
 * 
 * @author cptan
 * 
 */
public class SortSettings implements Serializable {
	private static final long serialVersionUID = 1L;

	private int height;

	private List<CSSInlineStyle> imgStyleList = new ArrayList<CSSInlineStyle>();

	private String mode;

	private List<YuiImage> sortList;

	private int width;

	/**
	 * Creates a SortSettings
	 * 
	 */
	public SortSettings() {
	}

	/**
	 * Get the default settings
	 * 
	 * @param mode -
	 *            the mode of sorting
	 * @param sortList -
	 *            a list of images
	 * @return the default settings
	 */
	public static SortSettings getDefault(String mode, List<YuiImage> sortList) {
		SortSettings settings = new SortSettings();
		settings.setResources(mode, sortList);
		return settings;
	}

	/**
	 * Get the height
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the image style list
	 * 
	 * @return the image style list
	 */
	public List<CSSInlineStyle> getImgStyleList() {
		return imgStyleList;
	}

	/**
	 * Get the mode
	 * 
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Get the sort list
	 * 
	 * @return the sort list
	 */
	public List<YuiImage> getSortList() {
		return sortList;
	}

	/**
	 * Get the width
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set the height
	 * 
	 * @param height -
	 *            the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Set the image resources
	 * 
	 * @param sortList -
	 *            the new image resources
	 */
	public void setImageResources(List<YuiImage> sortList) {
		for (int i = 0; i < sortList.size(); i++) {
			YuiImage img = (YuiImage) sortList.get(i);

			ResourceReference imgRR = new ResourceReference(SortSettings.class,
					img.getFileName());

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

			imgStyleList.add(imgStyle);

			this.width = imgWidth;
			this.height = imgHeight;
		}
	}

	/**
	 * Set the image style list
	 * 
	 * @param imgStyleList -
	 *            the new image style list
	 */
	public void setImgStyleList(List<CSSInlineStyle> imgStyleList) {
		this.imgStyleList = imgStyleList;
	}

	/**
	 * Set the mode
	 * 
	 * @param mode -
	 *            the new mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Set the resources
	 * 
	 * @param mode -
	 *            the mode
	 * @param sortList -
	 *            the list of images
	 */
	public void setResources(String mode, List<YuiImage> sortList) {
		setMode(mode);
		setSortList(sortList);
		setImageResources(sortList);
	}

	/**
	 * Set the sort list
	 * 
	 * @param sortList -
	 *            the new sort list
	 */
	public void setSortList(List<YuiImage> sortList) {
		this.sortList = sortList;
	}

	/**
	 * Set the width
	 * 
	 * @param width -
	 *            the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
}
