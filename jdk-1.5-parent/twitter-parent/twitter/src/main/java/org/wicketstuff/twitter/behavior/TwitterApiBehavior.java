package org.wicketstuff.twitter.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * 
 * @author Till Freier
 * 
 */
public class TwitterApiBehavior extends Behavior
{

	public static final String WIDGETS_JS = "//platform.twitter.com/widgets.js";

	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.renderJavaScriptReference(WIDGETS_JS);
	}

}
