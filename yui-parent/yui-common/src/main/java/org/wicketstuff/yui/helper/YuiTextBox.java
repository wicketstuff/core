package org.wicketstuff.yui.helper;

import java.io.Serializable;

public class YuiTextBox implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String background;
	private int width;
	private int height;
	private String desc;

	public YuiTextBox(String background, int width, int height, String desc)
	{
		this.background = background;
		this.width = width;
		this.height = height;
		this.desc = desc;
	}

	public String getBackground()
	{
		return background;
	}

	public void setBackground(String background)
	{
		this.background = background;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

}