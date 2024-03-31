package org.wicketstuff.html5.fileapi;

import org.apache.wicket.util.io.IClusterable;

/**
 * Represents the FileList type from the Html5 FileApi. Contains a fix sized array of
 * {@link Html5File}-s.
 * 
 * @author akiraly
 */
public class FileList implements IClusterable
{
	private static final long serialVersionUID = 7435304630866924097L;

	private final Html5File[] files;

	private long size;

	/**
	 * Constructor.
	 * 
	 * @param numOfFiles
	 *            number of files in the list, non-negative
	 */
	public FileList(int numOfFiles)
	{
		files = new Html5File[numOfFiles];
	}

	/**
	 * Sets a file in the list.
	 * 
	 * @param index
	 *            the position of the file in the list
	 * @param file
	 *            file to be set, not null
	 */
	public void set(int index, Html5File file)
	{
		Html5File old = files[index];
		files[index] = file;
		if (old != null)
			size -= old.getSize();
		size += file.getSize();
	}

	/**
	 * Retrieves a file from the list.
	 * 
	 * @param index
	 *            the position of the file in the list
	 * @return file, can be null if it was not set
	 */
	public Html5File get(int index)
	{
		return files[index];
	}

	/**
	 * Returns size of the file array.
	 * 
	 * @return number of file slots
	 */
	public int getNumOfFiles()
	{
		return files.length;
	}

	/**
	 * Returns the total byte size of the files in the list.
	 * 
	 * @return size in bytes
	 */
	public long getSize()
	{
		return size;
	}
}
