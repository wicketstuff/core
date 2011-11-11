package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.FacebookSdk;

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
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CommentPage()
	{
		add(new FacebookSdk("fb-root"));

		add(new Comments(
			"comments",
			Model.of("http://localhost:8080/wicket/bookmarkable/org.wicketstuff.facebook.plugins.CommentPage")));
	}

}
