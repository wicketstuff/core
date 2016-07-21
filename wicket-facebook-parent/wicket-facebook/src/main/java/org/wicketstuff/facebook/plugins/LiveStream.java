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

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param facebookAppId
	 *            your facebook application id
	 */
	public LiveStream(final String id, final String facebookAppId)
	{
		super(id, "fb-live-stream");


		add(new AttributeModifier("data-event-app-id", facebookAppId));

		add(new AttributeModifier("data-always-post-to-friends", new PropertyModel<Boolean>(this,
			"alwaysPostToFriends")));
		add(new AttributeModifier("data-xid", new PropertyModel<String>(this, "xid")));
		add(new AttributeModifier("data-via-url", new PropertyModel<String>(this,
			"viaAttributionUrl")));
	}

	/**
	 * @see #setViaAttributionUrl(String)
	 * @return
	 */
	public String getViaAttributionUrl()
	{
		return viaAttributionUrl;
	}

	/**
	 * @see #getXid()
	 * @return
	 */
	public String getXid()
	{
		return xid;
	}

	/**
	 * @see #setAlwaysPostToFriends(boolean)
	 * @return
	 */
	public boolean isAlwaysPostToFriends()
	{
		return alwaysPostToFriends;
	}

	/**
	 * If set, all user posts will always go to their profile. This option should only be used when
	 * users' posts are likely to make sense outside of the context of the event.
	 * 
	 * @param alwaysPostToFriends
	 */
	public void setAlwaysPostToFriends(final boolean alwaysPostToFriends)
	{
		this.alwaysPostToFriends = alwaysPostToFriends;
	}

	/**
	 * The URL that users are redirected to when they click on your app name on a status (if not
	 * specified, your Connect URL is used).
	 * 
	 * @param viaAttributionUrl
	 */
	public void setViaAttributionUrl(final String viaAttributionUrl)
	{
		this.viaAttributionUrl = viaAttributionUrl;
	}

	/**
	 * If you have multiple live stream boxes on the same page, specify a unique `xid` for each.
	 * 
	 * @param xid
	 */
	public void setXid(final String xid)
	{
		this.xid = xid;
	}


}
