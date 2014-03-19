package org.wicketstuff.twitter.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

/**
 * https://dev.twitter.com/docs/anywhere/welcome
 * 
 * @author Till Freier
 * 
 */
public abstract class AbstractAnywhereBehavior extends Behavior
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param apiKey
	 * @return
	 */
	private static String createJsApiUrl(final String apiKey)
	{
		final StringBuilder jsUrl = new StringBuilder();
		jsUrl.append("http://platform.twitter.com/anywhere.js?");
		if (apiKey != null)
			jsUrl.append("id=").append(apiKey).append("&");
		jsUrl.append("v=1");

		final String str = jsUrl.toString();
		return str;
	}


	private final String apiKey;

	/**
	 * 
	 */
	public AbstractAnywhereBehavior(final String apiKey)
	{
		this.apiKey = apiKey;
	}

	@Override
	public void beforeRender(final Component component)
	{
		super.beforeRender(component);
		component.setOutputMarkupId(true);
	}

	private String createAnywhereJs(final Component component)
	{
		final StringBuilder js = new StringBuilder();
		js.append("twttr.anywhere(function (T) {\n");
		js.append("T(\"#").append(component.getMarkupId(true)).append("\").");
		js.append(getAnywhereMethod());
		js.append(";\n});\n");

		return js.toString();
	}

	protected abstract String getAnywhereMethod();

	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(JavaScriptHeaderItem.forUrl(createJsApiUrl(apiKey)));

		response.render(JavaScriptHeaderItem.forScript(createAnywhereJs(component), null));
	}


}
