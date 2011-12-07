package org.wicketstuff.yui.helper;

import java.io.InputStream;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.PackageResource;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.devlib.schmidt.imageinfo.ImageInfo;

/**
 * Class to handle Image Info
 * 
 * @author josh
 */

public class ImageResourceInfo
{
	private PackageResource resource = null;
	private ImageInfo imageInfo = new ImageInfo();
	
	/**
	 * Construct
	 * @param resource
	 */
	public ImageResourceInfo(PackageResource resource)
	{
		this.resource = resource;
		this.imageInfo.setInput(getInputStream());
		this.imageInfo.check();
	}

	/**
	 * Construct.
	 * 
	 * @param reference
	 */
	public ImageResourceInfo(ResourceReference reference)
	{
		this(PackageResource.get(reference.getScope(), reference.getName()));
	}

	/**
	 * get the Input Stream for this resource
	 * 
	 * @return
	 */
	
	private InputStream getInputStream() 
	{	
		IResourceStream rs = this.resource.getResourceStream();
		try
		{
			return rs.getInputStream();
		}
		catch (ResourceStreamNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;  
	}
	
	/**
	 * gets the height 
	 * 
	 * @return
	 */
	public int getHeight()
	{
		return getImageInfo().getHeight();
	}
	
	/**
	 * gets teh width from ImageInfo ... is there a way to do this 
	 * wrapping by delegating all calls to the wrapped ImageInfo object ?
	 * 
	 * @return
	 */
	public int getWidth()
	{
		return getImageInfo().getWidth();
	}
	
	/*
	 * ACCESSORS 
	 */

	public PackageResource getResource()
	{
		return resource;
	}

	public void setResource(PackageResource resource)
	{
		this.resource = resource;
	}

	public ImageInfo getImageInfo()
	{
		return imageInfo;
	}

	public void setImageInfo(ImageInfo imageInfo)
	{
		this.imageInfo = imageInfo;
	}
}
