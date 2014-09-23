package org.wicketstuff.facebook;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
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

	private final Map<String, String> metaParams = new HashMap<String, String>();

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
		metaParams.put("fb:app_id", appId);
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
		response.render(JavaScriptHeaderItem.forUrl("//connect.facebook.net/en_US/all.js"));

		final StringBuilder js = new StringBuilder();
		js.append("FB.init({");
		if (appId != null)
			js.append("appId  : '").append(appId).append("',");
		js.append("status : true,");
		js.append("cookie : true,");
		js.append("xfbml  : true");
		js.append("});");

		response.render(OnDomReadyHeaderItem.forScript(js));

		for (final Entry<String, String> entry : metaParams.entrySet())
		{
			final StringBuilder sb = new StringBuilder();
			sb.append("<meta property=\"").append(entry.getKey()).append("\" ");
			sb.append("content=\"").append(entry.getValue()).append("\" />");
			sb.append('\n');

			response.render(StringHeaderItem.forString(sb));
		}
	}

	public void setFbAdmins(final String... userId)
	{
		final StringBuilder admins = new StringBuilder();
		for (final String id : userId)
			admins.append(id).append(',');
		if (admins.length() > 0)
			admins.deleteCharAt(admins.length() - 1);

		metaParams.put("fb:admins", admins.toString());
	}

	public void setOgProperty(final String property, final String value)
	{
		final StringBuilder sb = new StringBuilder(property.length() + 3);
		sb.append("og:").append(property);

		metaParams.put(sb.toString(), value);
	}
}
