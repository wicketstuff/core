package org.wicketstuff.facebook;

/**
 * 
 * @author Till Freier
 * 
 */
public class FacebookAppCredentials
{
	private final String appId;
	private final String appSecret;

	/**
	 * @param appId
	 * @param appSecret
	 */
	public FacebookAppCredentials(final String appId, final String appSecret)
	{
		super();
		this.appId = appId;
		this.appSecret = appSecret;
	}

	public String getAppId()
	{
		return appId;
	}

	public String getAppSecret()
	{
		return appSecret;
	}
}
