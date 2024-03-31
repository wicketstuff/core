package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.FacebookSdk;

public class ActivityFeedPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActivityFeedPage()
	{
		add(new FacebookSdk("fb-root"));

		add(new ActivityFeed("feed", Model.of("localhost")));
	}


}
