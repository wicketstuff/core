package org.wicketstuff.twitter.behavior;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * 
 * @author Till Freier
 * 
 */
public class HovercardBehaviorPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HovercardBehaviorPage()
	{
		super();


		final Label label = new Label("label", "This is a hovercard test by @tfreier");
		label.add(new HovercardBehavior("RLVad4j5xse3QwL06eEg"));

		add(label);
	}

}
