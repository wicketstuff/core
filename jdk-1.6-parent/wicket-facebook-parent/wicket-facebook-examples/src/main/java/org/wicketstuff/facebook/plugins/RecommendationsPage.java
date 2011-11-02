package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.behaviors.FacebookSdkBehavior;

public class RecommendationsPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecommendationsPage()
	{
		add(new FacebookSdkBehavior());

		add(new Recommendations("recommendations", Model.of("localhost")));
	}

	
}
