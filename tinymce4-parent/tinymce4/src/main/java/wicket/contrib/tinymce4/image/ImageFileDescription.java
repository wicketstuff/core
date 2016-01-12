package wicket.contrib.tinymce4.image;

import java.io.Serializable;

/**
 * Simple POJO which represents image characteristics.
 * 
 * @author Michal Letynski <mikel@mikel.pl>
 */
public class ImageFileDescription implements Serializable
{

	private static final long serialVersionUID = -8438138774598689747L;
	/** Image name */
	private String name;
	/** Image content type */
	private String contentType;

	public ImageFileDescription(String pName)
	{
		name = pName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String pName)
	{
		name = pName;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String pContentType)
	{
		contentType = pContentType;
	}
}