package wicket.contrib.tinymce.image;

import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

/**
 * Resource stream for images.
 * 
 * @author Michal Letynski <mikel@mikel.pl>
 */
public class FileResourceStream extends AbstractResourceStream
{

	private static final long serialVersionUID = 1L;

	private String contentType;
	private transient InputStream inputStream;

	public FileResourceStream(String pContentType, InputStream pInputStream)
	{
		inputStream = pInputStream;
		contentType = pContentType;
	}

	@Override
	public String getContentType()
	{
		return contentType;
	}

	/**
	 * {@inheritDoc}
	 */
	public InputStream getInputStream() throws ResourceStreamNotFoundException
	{
		return inputStream;
	}

	/**
	 * {@inheritDoc}
	 */
	public void close() throws IOException
	{
		if (inputStream != null)
		{
			inputStream.close();
			inputStream = null;
		}
	}
}