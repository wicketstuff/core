package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.FacebookSdk;

public class RecommendationsPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecommendationsPage()
	{
		add(new FacebookSdk("fb-root"));

		add(new Recommendations("recommendations", Model.of("localhost")));
	}


}
