package org.wicketstuff.jquery.ajaxbackbutton;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;

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
		response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
	}

}
