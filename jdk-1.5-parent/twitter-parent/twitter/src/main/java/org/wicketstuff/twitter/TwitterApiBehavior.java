package org.wicketstuff.twitter;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

public class TwitterApiBehavior extends Behavior
{

	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.renderJavaScriptReference("//platform.twitter.com/widgets.js");
	}


}
