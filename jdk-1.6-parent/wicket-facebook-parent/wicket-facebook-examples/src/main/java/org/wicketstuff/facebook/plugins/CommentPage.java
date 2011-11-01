package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.behaviors.FacebookSdkBehavior;

/**
 * 
 * @author Till Freier
 * 
 */
public class CommentPage extends WebPage
{

	/**
	 * 
	 */
	public CommentPage()
	{
		add(new FacebookSdkBehavior());

		add(new Comments(
			"comments",
			Model.of("http://localhost:8080/wicket/bookmarkable/org.wicketstuff.facebook.plugins.CommentPage")));
	}

}
