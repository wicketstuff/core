package org.wicketstuff.minis.behavior.image;

import org.apache.wicket.util.io.IClusterable;

/**
 * A simple class that can represent a html img tag's width, height attributes.
 * 
 * @author akiraly
 */
public class ImageDimension implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private final String width;
	private final String height;

	/**
	 * Constructor.
	 * 
	 * @param width
	 *            width of the image
	 * @param height
	 *            height of the image
	 */
	public ImageDimension(String width, String height)
	{
		this.width = width;
		this.height = height;
	}

	/**
	 * Getter.
	 * 
	 * @return width of the image
	 */
	public String getWidth()
	{
		return width;
	}

	/**
	 * Getter.
	 * 
	 * @return height of the image
	 */
	public String getHeight()
	{
		return height;
	}
}
