package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * https://developers.facebook.com/docs/reference/plugins/like-box/
 * 
 * @author Till Freier
 * 
 */
public class LikeBox extends AbstractFacebookPlugin
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean showFaces = true;
	private boolean showHeader = true;
	private boolean showStream = true;

	/**
	 * 
	 * @param id
	 *            wicket-id
	 */
	public LikeBox(final String id)
	{
		super(id, "fb-like-box");

		add(new AttributeModifier("data-show-faces", new PropertyModel<Boolean>(this, "showFaces")));
		add(new AttributeModifier("data-stream", new PropertyModel<Boolean>(this, "showStream")));
		add(new AttributeModifier("data-header", new PropertyModel<Boolean>(this, "showHeader")));
	}

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param facebookUrlModel
	 *            model for the URL of the Facebook Page for this Like Box
	 */
	public LikeBox(final String id, final IModel<?> facebookUrlModel)
	{
		this(id);

		add(new AttributeModifier("data-href", facebookUrlModel));
	}

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param facebookUrl
	 *            the URL of the Facebook Page for this Like Box
	 */
	public LikeBox(final String id, final String facebookUrl)
	{
		this(id, Model.of(facebookUrl));
	}

	/**
	 * @see #setShowFaces(boolean)
	 * @return
	 */
	public boolean isShowFaces()
	{
		return showFaces;
	}

	/**
	 * @see #setShowHeader(boolean)
	 * @return
	 */
	public boolean isShowHeader()
	{
		return showHeader;
	}

	/**
	 * @see #setShowStream(boolean)
	 * @return
	 */
	public boolean isShowStream()
	{
		return showStream;
	}

	/**
	 * 
	 * @param showFaces
	 *            whether or not to display profile photos in the plugin. Default value: true.
	 */
	public void setShowFaces(final boolean showFaces)
	{
		this.showFaces = showFaces;
	}

	/**
	 * 
	 * @param showHeader
	 *            whether to display the Facebook header at the top of the plugin.
	 */
	public void setShowHeader(final boolean showHeader)
	{
		this.showHeader = showHeader;
	}

	/**
	 * 
	 * @param showStream
	 *            whether to display a stream of the latest posts from the Page's wall
	 */
	public void setShowStream(final boolean showStream)
	{
		this.showStream = showStream;
	}


}
