package org.wicketstuff.twitter;

import org.apache.wicket.markup.html.WebPage;

/**
 * 
 * @author Till Freier
 * 
 */
public class FollowButtonPage extends WebPage
{

	/**
	 * 
	 */
	public FollowButtonPage()
	{
		final FollowButton button = new FollowButton("followButton", "tfreier");
		button.setShowFollowerCount(true);

		add(button);
	}

}
