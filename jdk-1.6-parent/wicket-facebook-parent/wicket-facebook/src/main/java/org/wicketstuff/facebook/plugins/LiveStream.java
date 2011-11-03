package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.PropertyModel;

/**
 * https://developers.facebook.com/docs/reference/plugins/live-stream/
 * 
 * @author Till Freier
 * 
 */
public class LiveStream extends AbstractFacebookPlugin
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean alwaysPostToFriends = false;
	private String viaAttributionUrl;
	private String xid;

	public LiveStream(String id, String facebookAppId)
	{
		super(id, "fb-live-stream");


		add(new AttributeModifier("data-event-app-id", facebookAppId));

		add(new AttributeModifier("data-always-post-to-friends", new PropertyModel<Boolean>(this,
			"alwaysPostToFriends")));
		add(new AttributeModifier("data-xid", new PropertyModel<String>(this, "xid")));
		add(new AttributeModifier("data-via-url", new PropertyModel<String>(this,
			"viaAttributionUrl")));
	}

	public String getViaAttributionUrl()
	{
		return viaAttributionUrl;
	}

	public String getXid()
	{
		return xid;
	}

	public boolean isAlwaysPostToFriends()
	{
		return alwaysPostToFriends;
	}

	public void setAlwaysPostToFriends(boolean alwaysPostToFriends)
	{
		this.alwaysPostToFriends = alwaysPostToFriends;
	}

	public void setViaAttributionUrl(String viaAttributionUrl)
	{
		this.viaAttributionUrl = viaAttributionUrl;
	}

	public void setXid(String xid)
	{
		this.xid = xid;
	}


}
