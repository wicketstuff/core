/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 9:19:11 AM
 */
package org.wicketstuff.html5.media.audio;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.wicketstuff.html5.media.Html5Media;
import org.wicketstuff.html5.media.MediaSource;

/**
 * 
 * @author Andrew Lombardi
 */
public class Html5Audio extends Html5Media
{

	private static final long serialVersionUID = 1L;

	public Html5Audio(String id, IModel<List<MediaSource>> model)
	{
		super(id, model);
	}

	/**
	 * audio tag
	 * 
	 * @return the tag name for this html5 media
	 */
	@Override
	protected String getTagName()
	{
		return "audio";
	}

}