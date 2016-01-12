package org.wicketstuff.jquery.crop;

import org.wicketstuff.jquery.Options;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 * 
 */
public class CropOptions extends Options
{

	private static final long serialVersionUID = 1L;

	public CropOptions dragHandle(Boolean dragHandle)
	{
		if (dragHandle)
			set("dragHandle", true);
		return this;
	}

	public CropOptions minWidth(int minWidth)
	{
		set("minWidth", minWidth);
		return this;
	}

	public CropOptions minHeight(int minHeight)
	{
		set("minHeight", minHeight);
		return this;
	}

	public CropOptions maxWidth(int maxWidth)
	{
		set("maxWidth", maxWidth);
		return this;
	}

	public CropOptions maxHeight(int maxHeight)
	{
		set("maxHeight", maxHeight);
		return this;
	}

	public CropOptions minTop(int minTop)
	{
		set("minTop", minTop);
		return this;
	}

	public CropOptions minLeft(int minLeft)
	{
		set("minLeft", minLeft);
		return this;
	}

	public CropOptions maxRight(int maxRight)
	{
		set("maxRight", maxRight);
		return this;
	}

	public CropOptions maxBottom(int maxBottom)
	{
		set("maxBottom", maxBottom);
		return this;
	}


}
