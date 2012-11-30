package org.wicketstuff.yui.helper;

import java.io.Serializable;

public class YuiImage implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String fileName;
	private String desc;

	private int top;
	private int left;

	public YuiImage(String fileName)
	{
		this.fileName = fileName;
	}

	public YuiImage(String fileName, String desc)
	{
		this.fileName = fileName;
		this.desc = desc;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getLeft()
	{
		return left;
	}

	public void setLeft(int left)
	{
		this.left = left;
	}

	public int getTop()
	{
		return top;
	}

	public void setTop(int top)
	{
		this.top = top;
	}


}
