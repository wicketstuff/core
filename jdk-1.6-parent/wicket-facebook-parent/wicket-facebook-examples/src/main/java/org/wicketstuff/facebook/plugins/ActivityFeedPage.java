package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.behaviors.FacebookSdkBehavior;

public class ActivityFeedPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActivityFeedPage()
	{
		add(new FacebookSdkBehavior());

		add(new ActivityFeed("feed", Model.of("localhost")));
	}

	
}
