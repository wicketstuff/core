package wicket.contrib.tinymce4.image;

import jakarta.servlet.ServletContext;

import java.io.File;

import org.apache.wicket.Session;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Image upload handler responsible for images operations.
 *
 * @author Michal Letynski (mikel@mikel.pl)
 */
public class ImageUploadHelper
{

	public static final String IMAGE_CONTENT_TYPE = "contenttype";
	public static final String IMAGE_FILE_NAME = "filename";

	private ImageUploadHelper()
	{
		throw new UnsupportedOperationException(
			"You are not allowed to create an instance of this class");
	}

	/**
	 * Get temporary directory path for storing temporary files for e.g images.
	 *
	 * @return temporary directory path for storing temporary files for e.g images.
	 */
	public static String getTemporaryDirPath()
	{
		ServletContext servletContext = WebApplication.get().getServletContext();
		return ((File)servletContext.getAttribute("javax.servlet.context.tempdir")).getPath() +
			File.separatorChar + Session.get().getId() + File.separatorChar;
	}

	/**
	 * Create image xml tag which represets image html tag with proper url generated.
	 *
	 * @param pImageFileDescription
	 *            - image file description.
	 * @param pUrl
	 *            - component url.
	 * @return image xml tag which represets image html tag with proper url generated.
	 */
	public static XmlTag createImageTag(ImageFileDescription pImageFileDescription,
		CharSequence pUrl)
	{
		XmlTag tag = new XmlTag();
		tag.setName("img");
		tag.setType(XmlTag.TagType.OPEN_CLOSE);
		tag.put(IMAGE_FILE_NAME, pImageFileDescription.getName());
		StringBuilder sb = new StringBuilder(pUrl);
		sb.append("&").append(IMAGE_FILE_NAME).append("=").append(pImageFileDescription.getName());
		sb.append("&")
			.append(IMAGE_CONTENT_TYPE)
			.append("=")
			.append(pImageFileDescription.getContentType());
		tag.put(
			"src",
			RequestCycle.get()
				.getOriginalResponse()
				.encodeURL(sb.toString()));
		return tag;
	}
}