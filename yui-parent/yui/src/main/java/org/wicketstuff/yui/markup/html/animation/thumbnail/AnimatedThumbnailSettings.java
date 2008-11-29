package org.wicketstuff.yui.markup.html.animation.thumbnail;

import java.io.Serializable;

public class AnimatedThumbnailSettings implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String thumbnailURI;
	
	private String pictureURI;
	
	private String captionText;
	
	private String selectValue;
	
	/**
	 * the thubmail width
	 */
	private int thumbnailWidth = 100;
	
	/**
	 * the thumbnail height
	 */
	private int thumbnailHeight= 100;
	
	/**
	 * the width of the picture
	 */
	private int pictureWidth = 100;
	
	/**
	 * the height of the picture 
	 */
	private int pictureHeight = 100;
	
	/**
	 * the display position for the picture top  
	 */
	private int pictureTop = 0;
	
	/**
	 * the display left position for the picture
	 */
	private int pictureLeft = 0;
	
	/**
	 * the opacity setting for 
	 * a. in which the caption text will appear.
	 * b. affects the picture dis/appearing
	 */
	private float opacity = 0.65f;

	private String fontFamily;

	private String color;

	private int fontSize;
	
	/**
	 * constructs a thumbnail resource 
	 * @param thumbnailRes
	 * @param pictureRes
	 * @param caption
	 */
	public AnimatedThumbnailSettings(String thumbnailRes, String pictureRes, String caption)
	{
		this.thumbnailURI = thumbnailRes;
		this.pictureURI = pictureRes;
		this.captionText = caption;
	}

	public AnimatedThumbnailSettings(String thumbnailRes, String pictureRes, String caption, String value)
	{
		this(thumbnailRes, pictureRes, caption);
		this.selectValue = value;
	}

	public String getThumbnailURI()
	{
		return thumbnailURI;
	}

	public void setThumbnailURI(String thumbnailURI)
	{
		this.thumbnailURI = thumbnailURI;
	}

	public String getPictureURI()
	{
		return pictureURI;
	}

	public void setPictureURI(String pictureURI)
	{
		this.pictureURI = pictureURI;
	}

	public String getCaptionText()
	{
		return captionText;
	}

	public void setCaptionText(String captionText)
	{
		this.captionText = captionText;
	}

	public int getThumbnailWidth()
	{
		return thumbnailWidth;
	}

	public void setThumbnailWidth(int thumbnailWidth)
	{
		this.thumbnailWidth = thumbnailWidth;
	}

	public int getThumbnailHeight()
	{
		return thumbnailHeight;
	}

	public void setThumbnailHeight(int thumbnailHeight)
	{
		this.thumbnailHeight = thumbnailHeight;
	}

	public int getPictureWidth()
	{
		return pictureWidth;
	}

	public void setPictureWidth(int pictureWidth)
	{
		this.pictureWidth = pictureWidth;
	}

	public int getPictureHeight()
	{
		return pictureHeight;
	}

	public void setPictureHeight(int pictureHeight)
	{
		this.pictureHeight = pictureHeight;
	}

	public float getOpacity()
	{
		return this.opacity;
	}
	
	public void setOpacity(float opacity)
	{
		this.opacity = opacity;
	}

	/**
	 * just a short cut
	 * @param width
	 * @param height
	 */
	public void setThumbnailDimension(int width, int height)
	{
		this.thumbnailHeight = height;
		this.thumbnailWidth = width;
	}

	/**
	 * another short cut
	 * @param width
	 * @param height
	 */
	public void setPictureDimension(int width, int height)
	{
		this.pictureHeight = height;
		this.pictureWidth = width;
	}

	public void setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
	}

	public void setColor(String fontColor)
	{
		this.color = fontColor;
	}

	public void setFontFamily(String fontFamily)
	{
		this.fontFamily = fontFamily;
	}

	public String getFontFamily()
	{
		return fontFamily;
	}

	public String getColor()
	{
		return color;
	}

	public int getFontSize()
	{
		return fontSize;
	}


	public void setPictureLeft(int pictureLeft)
	{
		this.pictureLeft = pictureLeft;
	}

	public void setPictureTop(int pictureTop)
	{
		this.pictureTop = pictureTop;
	}

	/**
	 * the position for the Picture display
	 * @param left
	 * @param top
	 */
	public void setPicturePosition(int left, int top)
	{
		setPictureLeft(left);
		setPictureTop(top);
	}

	public String getSelectValue()
	{
		return selectValue;
	}

	public void setSelectValue(String selectValue)
	{
		this.selectValue = selectValue;
	}

	public int getPictureTop()
	{
		return this.pictureTop;
	}

	public int getPictureLeft()
	{
		return this.pictureLeft;
	}

}
