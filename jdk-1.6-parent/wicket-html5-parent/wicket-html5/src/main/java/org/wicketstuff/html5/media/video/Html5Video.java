/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 9:19:11 AM
 */
package org.wicketstuff.html5.media.video;

import static org.apache.wicket.util.string.Strings.isEmpty;
import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.wicketstuff.html5.media.Html5Media;
import org.wicketstuff.html5.media.MediaSource;

/**
 * 
 * @author Andrew Lombardi
 */
public class Html5Video extends Html5Media
{

	private static final long serialVersionUID = 1L;


	// TODO add support for poster
	public Html5Video(String id, IModel<List<MediaSource>> model)
	{
		super(id, model);
	}

	/**
	 * Width of the video (optional)
	 * 
	 * @return
	 */
	protected int getWidth()
	{
		return 0;
	}

	/**
	 * Height of the video (optional)
	 * 
	 * @return
	 */
	protected int getHeight()
	{
		return 0;
	}

	/**
	 * Poster of the video (optional)
	 * 
	 * @return
	 */
	protected String getPoster()
	{
		return null;
	}

	/**
	 * Processes the component tag.
	 * 
	 * @param tag
	 *            Tag to modify
	 * @see org.apache.wicket.Component#onComponentTag(org.apache.wicket.markup.ComponentTag)
	 */
	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		if (getWidth() > 0)
		{
			tag.put("width", getWidth());
		}

		if (getHeight() > 0)
		{
			tag.put("height", getHeight());
		}

		if (!isEmpty(getPoster()))
		{
			tag.put("poster", getPoster());
		}

		// Default handling for component tag
		super.onComponentTag(tag);
	}


	/**
	 * video tag
	 * 
	 * @return the tag name for this html5 media
	 */
	@Override
	protected String getTagName()
	{
		return "video";
	}


}
