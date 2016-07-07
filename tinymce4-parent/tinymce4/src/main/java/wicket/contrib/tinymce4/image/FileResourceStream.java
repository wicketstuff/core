package wicket.contrib.tinymce4.image;

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

	private final String contentType;
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
    @Override
	public InputStream getInputStream() throws ResourceStreamNotFoundException
	{
		return inputStream;
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
	public void close() throws IOException
	{
		if (inputStream != null)
		{
			inputStream.close();
			inputStream = null;
		}
	}
}