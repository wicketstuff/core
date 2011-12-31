package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.FacebookSdk;
import org.wicketstuff.facebook.plugins.LikeButton.LikeButtonAction;
import org.wicketstuff.facebook.plugins.LikeButton.LikeButtonLayoutStyle;

/**
 * 
 * @author Till Freier
 * 
 */
public class LikeButtonPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LikeButtonPage()
	{
		add(new FacebookSdk("fb-root"));

		// URL is needed because Facebook doesn't like localhost
		final IModel<String> url = Model.of("http://wicketstuff.org");

		final LikeButton likeButton = new LikeButton("likeButton", url);
		likeButton.setLayoutStyle(LikeButtonLayoutStyle.BUTTON_COUNT);
		likeButton.setAction(LikeButtonAction.RECOMMEND);

		add(likeButton);
	}

}
