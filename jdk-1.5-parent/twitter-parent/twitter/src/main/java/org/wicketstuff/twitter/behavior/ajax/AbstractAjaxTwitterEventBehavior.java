package org.wicketstuff.twitter.behavior.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;

public class AbstractAjaxTwitterEventBehavior extends AbstractAjaxBehavior
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void onRequest()
	{
		// TODO Auto-generated method stub

	}


	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.renderJavaScriptReference("//platform.twitter.com/widgets.js");

	}

}
