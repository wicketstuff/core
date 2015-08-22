package org.wicketstuff.yui.markup.html.anim;

import java.io.Serializable;

import org.wicketstuff.yui.helper.YuiImage;

/**
 * An AnimOption is an option with four images which animates upon clicking and
 * mouseover
 * 
 * @author cptan
 * 
 */
public class AnimOption implements Serializable {
	private static final long serialVersionUID = 1L;

	private YuiImage defaultImg;

	private YuiImage defaultImgOver;

	private YuiImage selectedImg;

	private YuiImage selectedImgOver;

	private String selectedValue;

	/**
	 * Creates an AnimOption
	 * 
	 * @param defaultImg -
	 *            the unselected image
	 * @param defaultImgOver -
	 *            the unselected mouseover image
	 * @param selectedImg -
	 *            the selected image
	 * @param selectedImgOver -
	 *            the selected mouseover image
	 */
	public AnimOption(YuiImage defaultImg, YuiImage defaultImgOver,
			YuiImage selectedImg, YuiImage selectedImgOver, String selectedValue) {
		this.defaultImg = defaultImg;
		this.defaultImgOver = defaultImgOver;
		this.selectedImg = selectedImg;
		this.selectedImgOver = selectedImgOver;
		this.selectedValue = selectedValue;
	}

	/**
	 * Get the selected value
	 * 
	 * @return the selected value
	 */
	public String getSelectedValue() {
		return selectedValue;
	}

	/**
	 * Set the selected value
	 * 
	 * @param selectedValue -
	 *            the new selected value
	 */
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	/**
	 * Get the default image
	 * 
	 * @return the default image
	 */
	public YuiImage getDefaultImg() {
		return defaultImg;
	}

	/**
	 * Set the default image
	 * 
	 * @param defaultImg -
	 *            the new default image
	 */
	public void setDefaultImg(YuiImage defaultImg) {
		this.defaultImg = defaultImg;
	}

	/**
	 * Get the default mouseover image
	 * 
	 * @return the default mouseover image
	 */
	public YuiImage getDefaultImgOver() {
		return defaultImgOver;
	}

	/**
	 * Set the default mouseover image
	 * 
	 * @param defaultImgOver -
	 *            the new default mouseover image
	 */
	public void setDefaultImgOver(YuiImage defaultImgOver) {
		this.defaultImgOver = defaultImgOver;
	}

	/**
	 * Get the selected image
	 * 
	 * @return the selected image
	 */
	public YuiImage getSelectedImg() {
		return selectedImg;
	}

	/**
	 * Set the selected image
	 * 
	 * @param selectedImg -
	 *            the new selected image
	 */
	public void setSelectedImg(YuiImage selectedImg) {
		this.selectedImg = selectedImg;
	}

	/**
	 * Get the selected Mouseover Image
	 * 
	 * @return the selected mouseover image
	 */
	public YuiImage getSelectedImgOver() {
		return selectedImgOver;
	}

	/**
	 * Set the selected mouseOver Image
	 * 
	 * @param selectedImgOver -
	 *            the new selected mouseover image
	 */
	public void setSelectedImgOver(YuiImage selectedImgOver) {
		this.selectedImgOver = selectedImgOver;
	}

}
