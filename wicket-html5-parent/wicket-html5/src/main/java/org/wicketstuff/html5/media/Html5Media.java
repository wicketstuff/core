/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 8:26:39 AM
 */
package org.wicketstuff.html5.media;

import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.wicketstuff.html5.Html5UtilsBehavior;

/**
 * 
 * @author Andrew Lombardi
 */
public class Html5Media extends WebMarkupContainer
{

	private static final long serialVersionUID = 1L;

	private final IModel<List<MediaSource>> sources;

/*
 * public Html5Media(final String id) { this(id, new WildcardListModel<MediaSource>(new
 * ArrayList<MediaSource>())); }
 */

	public Html5Media(String id, final IModel<List<MediaSource>> model)
	{
		super(id, model);
		sources = wrap(model);
		add(new Html5UtilsBehavior());
	}

	/**
	 * List of sources for audio / video
	 * 
	 * @return
	 */
	public List<MediaSource> getSources()
	{
		List<MediaSource> sources;
		if (this.sources != null)
		{
			sources = this.sources.getObject();
		}
		else
		{
			throw new NullPointerException(
				"List of sources is null - Was the supplied 'Sources' model empty?");
		}

		return sources;
	}

	/**
	 * Should we autobuffer?
	 * 
	 * @return
	 */
	protected boolean isAutoBuffer()
	{
		return false;
	}

	/**
	 * Should we autoplay?
	 * 
	 * @return
	 */
	protected boolean isAutoPlay()
	{
		return false;
	}

	/**
	 * Should we loop?
	 * 
	 * @return
	 */
	protected boolean isLoop()
	{
		return false;
	}

	/**
	 * Should we show controls?
	 * 
	 * @return
	 */
	protected boolean isControls()
	{
		return false;
	}


	/**
	 * Processes the component tag.
	 * 
	 * @param tag
	 *            Tag to modify
	 * @see org.apache.wicket.Component#onComponentTag(ComponentTag)
	 */
	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		String tagName = getTagName();
		if (tagName != null)
		{
			checkComponentTag(tag, tagName);
		}

		List<MediaSource> sources = getSources();

		if (sources != null && sources.size() == 1)
		{
			MediaSource source = sources.get(0);
			tag.put("src", source.getSrc());
		}

		if (isAutoBuffer())
		{
			tag.put("autobuffer", true);
		}

		if (isAutoPlay())
		{
			tag.put("autoplay", true);
		}

		if (isLoop())
		{
			tag.put("loop", true);
		}

		if (isControls())
		{
			tag.put("controls", true);
		}

		// Default handling for component tag
		super.onComponentTag(tag);
	}

	protected String getTagName()
	{
		return null;
	}

	/**
	 * Handle the container's body.
	 * 
	 * @param markupStream
	 *            The markup stream
	 * @param openTag
	 *            The open tag for the body
	 * @see org.apache.wicket.Component#onComponentTagBody(org.apache.wicket.markup.MarkupStream ,
	 *      ComponentTag)
	 */
	@Override
	public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
	{

		List<MediaSource> sources = getSources();

		if (sources != null && sources.size() > 1)
		{
			final AppendingStringBuffer buffer = new AppendingStringBuffer();
			for (int index = 0; index < sources.size(); index++)
			{
				final MediaSource source = sources.get(index);
				buffer.append("\n<source ");
				buffer.append("src='");
				buffer.append(source.getSrc());
				buffer.append("'");
				if (source.getType() != null)
				{
					buffer.append(" type='");
					buffer.append(source.getType());
					buffer.append("'");
				}
				if (source.getMedia() != null)
				{
					buffer.append(" media='");
					buffer.append(source.getMedia());
					buffer.append("'");
				}

				buffer.append(" />");
			}

			buffer.append("\n");

			getResponse().write(buffer.toString());

		}
		super.onComponentTagBody(markupStream, openTag);
	}
}