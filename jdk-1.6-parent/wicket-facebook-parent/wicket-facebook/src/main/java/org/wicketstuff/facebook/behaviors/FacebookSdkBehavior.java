package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.facebook.FacebookAppCredentials;

/**
 * @author Till Freier
 *
 */
public class FacebookSdkBehavior extends Behavior
{
	private final FacebookAppCredentials credentials;

	/**
	 * @param credentials
	 */
	public FacebookSdkBehavior(final FacebookAppCredentials credentials)
	{
		super();
		this.credentials = credentials;
	}


	/**
	 * 
	 */
	public FacebookSdkBehavior()
	{
		this(null);
	}


	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		response.renderJavaScriptReference("http://connect.facebook.net/en_US/all.js");

		final StringBuilder js = new StringBuilder();
		js.append("FB.init({");
		if (credentials != null)
			js.append("appId  : '").append(credentials.getAppId()).append("',");
		js.append("status : true,");
		js.append("cookie : true,");
		js.append("xfbml  : true");
		js.append("});");

		response.renderOnDomReadyJavaScript(js.toString());
	}


}
