package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
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

	// TODO font, border color

	public LikeBox(String id)
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
	 * @param facebookUrl
	 *            the URL to the facebook page (http://www.facebook.com/...)
	 */
	public LikeBox(String id, IModel<?> facebookUrl)
	{
		this(id);

		if (facebookUrl != null)
			add(new AttributeModifier("data-href", facebookUrl));
	}

	public boolean isShowFaces()
	{
		return showFaces;
	}

	public boolean isShowHeader()
	{
		return showHeader;
	}

	public boolean isShowStream()
	{
		return showStream;
	}

	public void setShowFaces(boolean showFaces)
	{
		this.showFaces = showFaces;
	}

	public void setShowHeader(boolean showHeader)
	{
		this.showHeader = showHeader;
	}

	public void setShowStream(boolean showStream)
	{
		this.showStream = showStream;
	}


}
