package org.wicketstuff.offline.mode;

import java.util.UUID;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class OfflineModeHomePage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private static final String id = UUID.randomUUID().toString();

	public OfflineModeHomePage()
	{
		add(new Label("id", id));
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(OfflineModeScript.getInstance()));

		// contribute the offline mode scripts on page request
		OfflineCache.renderHead(response);
	}
}
