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

	//TODO font, link target
	//TODO border color
	private boolean recommendations = false;
	private boolean showHeader = false;
	
	public ActivityFeed(String id)
	{
		super(id, "fb-activity");
		
		add(new AttributeModifier("data-recommendations", new PropertyModel<Boolean>(this, "recommendations")));
		add(new AttributeModifier("data-header", new PropertyModel<Boolean>(this, "showHeader")));
	}
	
	

	public ActivityFeed(String id, IModel<?> site)
	{
		this(id);
		
		add(new AttributeModifier("data-recommendations", site));
	}



	public boolean isRecommendations()
	{
		return recommendations;
	}

	public void setRecommendations(boolean recommendations)
	{
		this.recommendations = recommendations;
	}

	public boolean isShowHeader()
	{
		return showHeader;
	}

	public void setShowHeader(boolean showHeader)
	{
		this.showHeader = showHeader;
	}

	
}
