package org.wicketstuff.twitter.behavior;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;

/**
 * 
 * @author Till Freier
 * 
 */
public class TweetBoxBehaviorPage extends WebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TweetBoxBehaviorPage()
	{
		super();

		final WebMarkupContainer tb = new WebMarkupContainer("tb");
		tb.add(new TweetBoxBehavior("RLVad4j5xse3QwL06eEg", Model.of("Test Tweet"), 400, 300));

		add(tb);
	}

}
