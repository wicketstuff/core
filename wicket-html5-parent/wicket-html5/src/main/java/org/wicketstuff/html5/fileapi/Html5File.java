package org.wicketstuff.html5.fileapi;

import java.util.Calendar;

import org.apache.wicket.util.io.IClusterable;

/**
 * Represents the File type from the Html5 FileApi. This is a simplified version so the Blob
 * supertype is not a separate class.
 * 
 * @author akiraly
 */
public class Html5File implements IClusterable
{
	private static final long serialVersionUID = -7007852130645869724L;

	private final String name;
	private final long size;
	private final String type;
	private final Calendar lastModifiedDate;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            name of the file without path, not-null
	 * @param size
	 *            size of the file in bytes, non-negative
	 * @param type
	 *            mime type of the file, not-null, can be an empty string if not known
	 * @param lastModifiedDate
	 *            last modified date pf the file, can be null
	 */
	public Html5File(String name, long size, String type, Calendar lastModifiedDate)
	{
		super();
		this.name = name;
		this.size = size;
		this.type = type;
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * Defined in FileApi File type.
	 * 
	 * @return name of the file without path, not-null
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Defined in FileApi Blob type.
	 * 
	 * @return size of the file in bytes, non-negative
	 */
	public long getSize()
	{
		return size;
	}

	/**
	 * Defined in FileApi Blob type.
	 * 
	 * @return mime type of the file, not-null, can be an empty string if not known
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Defined in FileApi File type.
	 * 
	 * @return date, when the file was last modified, can be null
	 */
	public Calendar getLastModifiedDate()
	{
		return lastModifiedDate;
	}
}
