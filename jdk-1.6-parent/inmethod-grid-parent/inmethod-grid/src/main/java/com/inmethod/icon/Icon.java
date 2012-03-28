package com.inmethod.icon;

import java.io.Serializable;

/**
 * Simple interface that represents an icon.
 * 
 * @author Matej Knopp
 */
public interface Icon extends Serializable
{

	/**
	 * Returns the URL of icon image.
	 * 
	 * @return icon URL
	 */
	public CharSequence getUrl();

	/**
	 * Returns the icon width.
	 * 
	 * @return icon width (in pixels) or -1 if the width can not be determined
	 */
	public int getWidth();

	/**
	 * Returns the icon height.
	 * 
	 * @return icon height (in pixels) or -1 if the height can not be determined
	 */
	public int getHeight();
}
