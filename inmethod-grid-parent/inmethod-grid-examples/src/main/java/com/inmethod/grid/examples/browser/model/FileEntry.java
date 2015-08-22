package com.inmethod.grid.examples.browser.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Simple wrapper for java.io.File
 */
public class FileEntry implements Serializable
{

	private static final long serialVersionUID = 1L;

	public FileEntry(File file)
	{
		this.file = file;
	}

	public boolean isFolder()
	{
		return file.isDirectory();
	}

	public String getSizeAsString()
	{
		if (file.isDirectory())
		{
			return "";
		}
		else
		{
			return "" + file.length();
		}
	};

	public long getSize()
	{
		if (file.isDirectory() == true)
			return 0L;
		else
			return file.length();
	}

	public Date lastModified()
	{
		return new Date(file.lastModified());
	}

	public String getAbsolutePath()
	{
		return file.getAbsolutePath();
	}

	public boolean canRead()
	{
		return file.canRead();
	}

	public boolean canWrite()
	{
		return file.canWrite();
	}

	public boolean canExecute()
	{
		return false;
	}

	public List<FileEntry> getChildren()
	{
		List<FileEntry> result = new ArrayList<FileEntry>();
		File files[] = file.listFiles();
		if (files != null)
		{
			for (File file : files)
			{
				if (file.isHidden() == false)
					result.add(new FileEntry(file));
			}
		}
		// sort the children
		Collections.sort(result, new Comparator<FileEntry>()
		{
			public int compare(FileEntry o1, FileEntry o2)
			{
				if (o1.isFolder() != o2.isFolder())
				{
					return -((Boolean)o1.isFolder()).compareTo(o2.isFolder());
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		return result;
	}

	public String getName()
	{
		return file.getName();
	}

	private File file;
}
