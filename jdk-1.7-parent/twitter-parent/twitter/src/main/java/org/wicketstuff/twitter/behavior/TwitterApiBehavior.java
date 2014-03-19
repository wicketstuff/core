package org.wicketstuff.twitter.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

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

		response.render(JavaScriptHeaderItem.forUrl(WIDGETS_JS));
	}

}
