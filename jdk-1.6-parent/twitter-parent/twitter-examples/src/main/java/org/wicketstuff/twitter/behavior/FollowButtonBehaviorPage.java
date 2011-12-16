package org.wicketstuff.twitter.behavior;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * 
 * @author Till Freier
 * 
 */
public class FollowButtonBehaviorPage extends WebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FollowButtonBehaviorPage()
	{
		super();

		final Label label = new Label("label", "This is a FollowButton test by @tfreier");
		label.add(new FollowButtonBehavior("RLVad4j5xse3QwL06eEg", "tfreier"));

		add(label);
	}

}
