package org.wicketstuff.twitter.behavior;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * 
 * @author Till Freier
 * 
 */
public class LinkifyBehaviorPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LinkifyBehaviorPage()
	{
		final Label label = new Label("label", "This is a linkify test by @tfreier");
		label.add(new LinkifyBehavior("RLVad4j5xse3QwL06eEg"));

		add(label);
	}

}
