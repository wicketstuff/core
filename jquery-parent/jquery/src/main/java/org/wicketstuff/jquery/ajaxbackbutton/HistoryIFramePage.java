package org.wicketstuff.jquery.ajaxbackbutton;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.resource.JQueryResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;

/**
 * @author martin-g
 */
public class HistoryIFramePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public HistoryIFramePage()
	{
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.render(JavaScriptHeaderItem.forReference(JQueryResourceReference.get()));
	}

}
