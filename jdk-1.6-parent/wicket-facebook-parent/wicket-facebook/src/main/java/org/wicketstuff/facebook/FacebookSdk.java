package org.wicketstuff.facebook;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * https://developers.facebook.com/docs/reference/javascript/ <br>
 * 
 * The final tag should look like that:
 * 
 * <pre>
 * &lt;div id="fb-root"&gt;&lt;/div&gt;
 * </pre>
 * 
 * In your wicket markup you should add the wicket-id and place the tag right after the opening
 * &lt;body&gt;.
 * 
 * <pre>
 * &lt;html&gt;
 * ...
 * &lt;body&gt;
 * 	&lt;div id="fb-root" wicket:id="..."&gt;&lt;/div&gt;
 *  ...
 * &lt;/body&gt;
 * &lt;/html&gt;
 * </pre>
 * 
 * @author Till Freier
 * 
 */
public class FacebookSdk extends WebMarkupContainer implements FacebookRootProvider
{
	private final String appId;

	/**
	 * @param id
	 */
	public FacebookSdk(final String id)
	{
		this(id, null);
	}

	/**
	 * @param id
	 */
	public FacebookSdk(final String id, final String appId)
	{
		super(id);

		this.appId = appId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMarkupId()
	{
		return "fb-root";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isVisible()
	{
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final IHeaderResponse response)
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
