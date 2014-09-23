package org.wicketstuff.twitter;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * 
 * @author Till Freier
 * 
 */
public class TweetButtonPage extends WebPage
{

	/**
	 * 
	 */
	public TweetButtonPage()
	{
		final IModel<String> url = Model.of("https://github.com/tfreier/wicketstuff-core/tree/master/jdk-1.5-parent/twitter-parent");
		final IModel<String> via = Model.of("tfreier");
		final IModel<String> text = Model.of("just testing #twitter widgets for #wicket.");

		final TweetButton button = new TweetButton("tweetButton", url, text, via);

		add(button);
	}

}
