package org.wicketstuff.yui.markup.html.animselect;

import java.io.Serializable;

import org.wicketstuff.yui.helper.YuiImage;

/**
 * Creates an option with four images which animates upon clicking and mouseover
 * @author cptan
 *
 */
public class AnimSelectOption implements Serializable
{
	private static final long serialVersionUID = 1L;

	private YuiImage defaultImg;
	private YuiImage defaultImgOver;
	private YuiImage selectedImg;
	private YuiImage selectedImgOver;
	private String selectedValue;

	/**
	 * Constructor
	 * @param defaultImg
	 * @param defaultImgOver
	 * @param selectedImg
	 * @param selectedImgOver
	 */
	public AnimSelectOption(YuiImage defaultImg, YuiImage defaultImgOver, YuiImage selectedImg, YuiImage selectedImgOver, String selectedValue)
	{
		this.defaultImg = defaultImg;
		this.defaultImgOver = defaultImgOver;
		this.selectedImg = selectedImg;
		this.selectedImgOver = selectedImgOver;
		this.selectedValue = selectedValue;
	}

	/**
	 * Get the selected value
	 * @return the selectedValue
	 */
	public String getSelectedValue()
	{
		return selectedValue;
	}

	/**
	 * Set the selected value
	 * @param selectedValue 
	 */
	public void setSelectedValue(String selectedValue)
	{
		this.selectedValue = selectedValue;
	}

	/**
	 * Get the default image
	 * @return YuiImage
	 */
	public YuiImage getDefaultImg()
	{
		return defaultImg;
	}

	/**
	 * Set the default image 
	 * @param defaultImg
	 */
	public void setDefaultImg(YuiImage defaultImg)
	{
		this.defaultImg = defaultImg;
	}

	/**
	 * Get the default mouseover image
	 * @return YuiImage
	 */
	public YuiImage getDefaultImgOver()
	{
		return defaultImgOver;
	}

	/**
	 * Set the default mouseover image
	 * @param defaultImgOver
	 */
	public void setDefaultImgOver(YuiImage defaultImgOver)
	{
		this.defaultImgOver = defaultImgOver;
	}

	/**
	 * Get the selected image
	 * @return YuiImage
	 */
	public YuiImage getSelectedImg()
	{
		return selectedImg;
	}

	/**
	 * Set the selected image
	 * @param selectedImg
	 */
	public void setSelectedImg(YuiImage selectedImg)
	{
		this.selectedImg = selectedImg;
	}

	/**
	 * Get the selected Mouseover Image
	 * @return YuiImage
	 */
	public YuiImage getSelectedImgOver()
	{
		return selectedImgOver;
	}

	/**
	 * Set the selected mouseOver Image
	 * @param selectedImgOver
	 */
	public void setSelectedImgOver(YuiImage selectedImgOver)
	{
		this.selectedImgOver = selectedImgOver;
	}

}
