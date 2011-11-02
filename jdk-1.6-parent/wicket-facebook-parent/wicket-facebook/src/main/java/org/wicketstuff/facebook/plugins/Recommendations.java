package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * https://developers.facebook.com/docs/reference/plugins/recommendations/
 * 
 * @author Till Freier
 *
 */
public class Recommendations extends AbstractFacebookPlugin
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	//TODO font, link target
	//TODO border color
	
	private boolean showHeader = false;

	public Recommendations(String id)
	{
		super(id, "fb-recommendations");
		
		add(new AttributeModifier("data-header", new PropertyModel<Boolean>(this, "showHeader")));
	}

	public Recommendations(String id, IModel<?> site)
	{
		this(id);

		add(new AttributeModifier("data-recommendations", site));
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
