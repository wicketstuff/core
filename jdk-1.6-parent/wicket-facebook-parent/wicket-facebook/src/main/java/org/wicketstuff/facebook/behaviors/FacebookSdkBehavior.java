package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * @author Till Freier
 *
 */
public class FacebookSdkBehavior extends Behavior
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String appId;

	/**
	 * @param credentials
	 */
	public FacebookSdkBehavior(final String appId)
	{
		super();
		this.appId = appId;
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
		response.renderJavaScriptReference("//connect.facebook.net/en_US/all.js");

		final StringBuilder js = new StringBuilder();
		js.append("FB.init({");
		if (appId != null)
			js.append("appId  : '").append(appId).append("',");
		js.append("status : true,");
		js.append("cookie : true,");
		js.append("xfbml  : true");
		js.append("});");

		response.renderOnDomReadyJavaScript(js.toString());
	}


}
