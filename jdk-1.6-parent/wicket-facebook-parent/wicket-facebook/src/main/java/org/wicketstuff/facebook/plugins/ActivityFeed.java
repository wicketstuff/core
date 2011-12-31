package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * https://developers.facebook.com/docs/reference/plugins/activity/
 * 
 * @author Till Freier
 * 
 */
public class ActivityFeed extends AbstractFacebookPlugin
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LinkTarget linkTarget = LinkTarget._BLANK;
	private boolean recommendations = false;
	private boolean showHeader = false;

	public ActivityFeed(final String id)
	{
		super(id, "fb-activity");

		add(new AttributeModifier("data-recommendations", new PropertyModel<Boolean>(this,
			"recommendations")));
		add(new AttributeModifier("data-header", new PropertyModel<Boolean>(this, "showHeader")));
		add(new AttributeModifier("data-linktarget", new EnumModel(new PropertyModel<LinkTarget>(
			this, "linkTarget"))));
	}


	public ActivityFeed(final String id, final IModel<?> site)
	{
		this(id);

		add(new AttributeModifier("data-site", site));
	}


	public LinkTarget getLinkTarget()
	{
		return linkTarget;
	}

	public boolean isRecommendations()
	{
		return recommendations;
	}

	public boolean isShowHeader()
	{
		return showHeader;
	}

	public void setLinkTarget(final LinkTarget linkTarget)
	{
		this.linkTarget = linkTarget;
	}

	public void setRecommendations(final boolean recommendations)
	{
		this.recommendations = recommendations;
	}

	public void setShowHeader(final boolean showHeader)
	{
		this.showHeader = showHeader;
	}


}
